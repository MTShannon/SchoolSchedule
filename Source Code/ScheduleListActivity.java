package markshannon.android.schedule;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

// class that hosts the list fragment that shows all the schedules
public class ScheduleListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ScheduleListFragment();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ScheduleListActivity.class);
    }
}
