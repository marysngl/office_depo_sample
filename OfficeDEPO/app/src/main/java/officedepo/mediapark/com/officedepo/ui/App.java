package officedepo.mediapark.com.officedepo.ui;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import officedepo.mediapark.com.officedepo.Injection.ApplicationComponent;
import officedepo.mediapark.com.officedepo.Injection.ApplicationModule;
import officedepo.mediapark.com.officedepo.Injection.DaggerApplicationComponent;
import officedepo.mediapark.com.officedepo.Model.API.ErrorMessage;
import officedepo.mediapark.com.officedepo.Model.MainThreadBus;
import officedepo.mediapark.com.officedepo.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Mary Songal on 31.10.2016.
 */

public class App extends Application {

    ApplicationComponent mApplicationComponent;
    public static MainThreadBus bus = new MainThreadBus();

    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bus.register(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/DidactGothic.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        appContext = getApplicationContext();
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
            mApplicationComponent.eventBus().register(this);
        }
        return mApplicationComponent;
    }

    @Subscribe
    public void getMessage(ErrorMessage message) {
        Toast.makeText(getApplicationContext(), message.errorMessage, Toast.LENGTH_LONG).show();
    }

}
