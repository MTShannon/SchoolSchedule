package markshannon.android.schedule;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import markshannon.android.schedule.R;


/**
 * Created by mshan on 12/28/2017.
 */

// list fragment that shows up first
public class ScheduleListFragment extends Fragment {

    private Schedule mSchedule;
    private TextView mNameField;
    private TextView mDateField;

    private RecyclerView mScheduleRecyclerView;
    private ConstraintLayout mScheduleConstraintLayout;
    private ImageButton mImageButton;
    private ScheduleAdapter mAdapter;
    private File mScheduleFile;


    private static final String OnResume = "OnResume";

    private String themeType = "light";



    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        mScheduleFile = getScheduleFile();

        // let the FragmentManager know that the fragment should receive a call to onCreateOptionsMenu(...)
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        final ScheduleSingleton mScheduleSingleton = ScheduleSingleton.get(getActivity());
        List<Schedule> mSchedules = mScheduleSingleton.getSchedules();

        // inflate the fragments view with layout resource id (where the view goes), the container
        // (the view's parent), and 3rd parameter, tells layout inflater whether to add the inflated view
        // to the view's parent, false because add view in activit's code
        view = inflater.inflate(R.layout.fragment_schedule_list, container, false);

        mScheduleRecyclerView = (RecyclerView) view.findViewById(R.id.schedule_recycler_view);
        mScheduleRecyclerView.setLayoutManager(new LinearLayoutManager((getActivity())));

        mScheduleConstraintLayout = (ConstraintLayout) view.findViewById(R.id.empty_list_display);
        mImageButton = (ImageButton) view.findViewById(R.id.first_add_button);


        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);




            inflater.inflate(R.menu.fragment_schedule_list, menu);

    }

    // when an action item is pressed, onOptionsItemSelected(...) is called
    // the MenuItem argument describes what was done
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_schedule:
                Schedule schedule = new Schedule();
                ScheduleSingleton.get(getActivity()).addSchedule(schedule);
                Intent intent = ScheduleActivity.newIntent(getActivity(), schedule.getId());
                startActivity(intent);

                // return true to indicate no further processing is needed
                return true;

            case R.id.export:

                writeSchedule();

                final Intent sendEmail = new Intent(Intent.ACTION_SEND);
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "markshannon.android.schedulereminder.fileprovider",mScheduleFile);

                sendEmail.setType("text/plain");
                sendEmail.putExtra(Intent.EXTRA_EMAIL, "");
                sendEmail.putExtra(Intent.EXTRA_SUBJECT,"Class Schedule");
                sendEmail.putExtra(Intent.EXTRA_TEXT,"");
                sendEmail.putExtra(Intent.EXTRA_STREAM, uri);

                startActivity(Intent.createChooser(sendEmail,"Send Email..."));

                return true;

            case R.id.change_theme:
                if (SingleFragmentActivity.themeType == "light") {
                    SingleFragmentActivity.themeType = "dark";
                    getActivity().recreate();
                }
                else {
                    SingleFragmentActivity.themeType = "light";
                    getActivity().recreate();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // instantiate the adapter by passing in the list of schedules from the singleton
    // connect the RecyclerView with the Adapter
    private void updateUI() {
        final ScheduleSingleton singleton = ScheduleSingleton.get(getActivity());
        List<Schedule> schedules = singleton.getSchedules();

        if(mAdapter == null) {
            mAdapter = new ScheduleAdapter(schedules);
            mScheduleRecyclerView.setAdapter(mAdapter);
        }
        else {
            // have to update adapter because the List<Schedules> returned by getSchedules() is
            // like a snapshot of the Schedule(s) at one point in time
            mAdapter.setSchedules(schedules);
            mAdapter.notifyDataSetChanged();
        }

        if (schedules.isEmpty()) {
            mScheduleConstraintLayout.setVisibility(View.VISIBLE);

            mImageButton.setOnClickListener(new View.OnClickListener() {
                      @Override
                       public void onClick(View v) {
                            Schedule schedule = new Schedule();
                            singleton.addSchedule(schedule);
                            Intent intent = ScheduleActivity.newIntent(getActivity(), schedule.getId());
                           startActivity(intent);
                        }
                   });
        }
        else {
            mScheduleConstraintLayout.setVisibility(View.GONE);
        }
    }

    // Adapter that binds a view holder and it's data
    public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {
        private List<Schedule> mSchedules;

        public ScheduleAdapter(List<Schedule> schedules) {
            mSchedules = schedules;
        }

        // creates and returns a view holder when RecyclerView needs a  new ViewHolder to display
        // create a LayoutInflater and use it for viewholder
        @Override
        public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ScheduleViewHolder(layoutInflater, parent);
        }

        // binds the schedule data to the view holder
        @Override
        public void onBindViewHolder(ScheduleViewHolder holder, int position) {
            Schedule schedule = mSchedules.get(position);
            holder.bind(schedule);
        }

        // returns # of schedules in the list
        @Override
        public int getItemCount() {
            return mSchedules.size();
        }


        public void setSchedules(List<Schedule> schedules) {
            mSchedules = schedules;
        }
    }

    // View holder that holds a View for a schedule item
    private class ScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mDateTextView;
        private TextView mDetails;
        private Schedule mschedule;

        public ScheduleViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.schedule_item, parent, false));

            mNameTextView = (TextView) itemView.findViewById(R.id.schedule_name);
            mDateTextView = (TextView) itemView.findViewById(R.id.schedule_date);
            mDetails = (TextView) itemView.findViewById(R.id.schedule_details);
            itemView.setOnClickListener(this);
        }

        public void bind(Schedule schedule) {
            mschedule = schedule;
            mNameTextView.setText(mschedule.getName());
            mDateTextView.setText(mschedule.getTeacher());
            mDetails.setText(mschedule.formatDetails());

        }

        @Override
        public void onClick(View v) {
            Intent intent = ScheduleActivity.newIntent(getActivity(), mschedule.getId());
            startActivity(intent);
        }


    }

   public String getFileName() {
       return "ClassSchedule.txt";
   }

   public File getScheduleFile() {
       File filesDir = getContext().getFilesDir();
       return new File(filesDir, getFileName());

   }


    public void writeSchedule() {
        List<Schedule> classes = ScheduleSingleton.get(getActivity()).getSchedules();
        try {

            FileWriter writer = new FileWriter(mScheduleFile);
            if (classes.isEmpty()) {
                writer.append("You haven't added any classes yet");
            }
            else {
                for (Schedule subject : classes) {

                    writer.append(subject.getName() + ": " + subject.getTeacher() + " - " +
                            subject.getLocation() + " - " + subject.getTime() + " on " + subject.getDays());
                    writer.append("\n");
                }
                writer.flush();
                writer.close();
            }

        }
        catch (Exception e) {

        }
    }




}
