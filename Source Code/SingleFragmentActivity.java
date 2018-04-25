package markshannon.android.schedule;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import markshannon.android.schedule.R;

/**
 * Created by mshan on 12/26/2017.
 * Abstract controller class for creating a fragment and adding it to the fragment manager
 */


public abstract class SingleFragmentActivity extends AppCompatActivity {

    // subclasses will impliment createFragment();
    protected abstract Fragment createFragment();

    public static String themeType = "light";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = super.getTheme();
        if(themeType == "light"){
            theme.applyStyle(R.style.AppTheme, true);
        }
        else {
            theme.applyStyle(R.style.DarkTheme, true);
        }
        // you could also use a switch if you have many themes that could apply
        return theme;
    }
}
