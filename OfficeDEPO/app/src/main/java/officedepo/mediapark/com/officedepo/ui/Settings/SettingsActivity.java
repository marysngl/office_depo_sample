package officedepo.mediapark.com.officedepo.ui.Settings;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import officedepo.mediapark.com.officedepo.Base.BaseFragmentActivity;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.Toolbar.ToolbarFragment;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class SettingsActivity extends BaseFragmentActivity {

    private static final String TOOLBAR_FRAGMENT = "toolbar_fragment";
    private static final String SETTINGS_FRAGMENT = "settings_fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);

        ToolbarFragment toolbarFragment = new ToolbarFragment();
        SettingsFragment settingsFragment = new SettingsFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.settings_toolbar_container, toolbarFragment, TOOLBAR_FRAGMENT);
        fragmentTransaction.add(R.id.settings_container, settingsFragment, SETTINGS_FRAGMENT);
        fragmentTransaction.commit();

        toolbarFragment.setTitle(getString(R.string.settings_title));
    }
}
