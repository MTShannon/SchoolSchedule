package markshannon.android.schedule;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;


/**
 * Created by mshan on 12/28/2017.
 */

// screen where you edit the name and date of a schedule entry
// pulls up the detail page
public class ScheduleActivity extends SingleFragmentActivity{

    public static final String EXTRA_BIRHTDAY_ID = "mark.shannon.android.schedulereminder.schedule_id";

    public static Intent newIntent(Context packageContext, UUID scheduleId) {
        Intent intent = new Intent(packageContext, ScheduleActivity.class);
        intent.putExtra(EXTRA_BIRHTDAY_ID, scheduleId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID scheduleId = (UUID) getIntent().getSerializableExtra(EXTRA_BIRHTDAY_ID);
        return ScheduleFragment.newInstance(scheduleId);
    }
}
