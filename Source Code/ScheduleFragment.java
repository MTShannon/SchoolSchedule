package markshannon.android.schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

import markshannon.android.schedule.R;

/**
 * Created by mshan on 12/28/2017.
 */

// Schedule Fragment is what pops up when someone wants to edit a schedule
// The detail page
public class ScheduleFragment extends Fragment{

    private static final String ARG_BIRTHDAY_ID = "schedule_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final String TAG = "setName";


    private static final int REQUEST_DATE = 0;

    private EditText mNameText;
    private EditText mTeacherText;
    private EditText mLocationText;
    private EditText mTimeText;
    private EditText mDaysText;
    private Schedule mSchedule;


    public ScheduleFragment() {

    }

    public static ScheduleFragment newInstance(UUID scheduleId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BIRTHDAY_ID, scheduleId);

        ScheduleFragment fragment = new  ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {


        //getActivity().setTheme(R.style.DarkTheme);

        UUID scheduleId = (UUID) getArguments().getSerializable(ARG_BIRTHDAY_ID);
        mSchedule = ScheduleSingleton.get(getActivity()).getSchedule(scheduleId);

        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    // Schedules get modified in ScheduleFragment and need to be updated when ScheduleFragment is done
    @Override
    public void onPause() {
        super.onPause();

        ScheduleSingleton.get(getActivity()).updateSchedule(mSchedule);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        Log.i(TAG, "About to set name: " + mSchedule.getName());
        mNameText = (EditText) v.findViewById(R.id.name);
        mNameText.setText(mSchedule.getName());
        mNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int before, int count) {

                // left blank on purpose
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSchedule.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // left blank on purpose
            }
        });

        mTeacherText = (EditText) v.findViewById(R.id.teacher);
        mTeacherText.setText(mSchedule.getTeacher());
        mTeacherText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int before, int count) {

                // left blank on purpose
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSchedule.setTeacher(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // left blank on purpose
            }
        });

        mLocationText = (EditText) v.findViewById(R.id.location);
        mLocationText.setText(mSchedule.getLocation());
        mLocationText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int before, int count) {

                // left blank on purpose
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSchedule.setLocation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // left blank on purpose
            }
        });

        mTimeText = (EditText) v.findViewById(R.id.time);
        mTimeText.setText(mSchedule.getTime());
        mTimeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int before, int count) {

                // left blank on purpose
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSchedule.setTime(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // left blank on purpose
            }
        });

        mDaysText = (EditText) v.findViewById(R.id.days);
        mDaysText.setText(mSchedule.getDays());
        mDaysText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int before, int count) {

                // left blank on purpose
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSchedule.setDays(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // left blank on purpose
            }
        });
        return v;
    }

    //inflates the delete menu action item view
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // puts the menu view up in the menu instance
        inflater.inflate(R.menu.fragment_schedule_delete, menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_schedule:
                UUID scheduleId = (UUID) getArguments().getSerializable(ARG_BIRTHDAY_ID);
                ScheduleSingleton schedules = ScheduleSingleton.get(getActivity());
                mSchedule = schedules.getSchedule(scheduleId);



                schedules.deleteSchedule(mSchedule);
                getActivity().finish();
                return true;

                // return true to indicate no further processing is needed

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}

