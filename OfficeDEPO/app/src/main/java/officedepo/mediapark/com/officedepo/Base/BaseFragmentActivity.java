package officedepo.mediapark.com.officedepo.Base;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import officedepo.mediapark.com.officedepo.Injection.ActivityComponent;
import officedepo.mediapark.com.officedepo.Injection.ActivityModule;
import officedepo.mediapark.com.officedepo.Injection.DaggerActivityComponent;
import officedepo.mediapark.com.officedepo.ui.App;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Mary Songal on 06.11.2016.
 */

public class BaseFragmentActivity extends FragmentActivity {

    private ActivityComponent mActivityComponent;

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(App.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
