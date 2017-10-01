package officedepo.mediapark.com.officedepo.Injection;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import officedepo.mediapark.com.officedepo.Local.DatabaseHelper;
import officedepo.mediapark.com.officedepo.Model.DataManager;
import officedepo.mediapark.com.officedepo.Model.MainThreadBus;
import officedepo.mediapark.com.officedepo.Model.PreferenceHelper;

/**
 * Created by Mary Songal on 03.11.2016.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    /*void inject(SyncService syncService);*/

    @ApplicationContext
    Context context();
    Application application();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    MainThreadBus eventBus();
    PreferenceHelper preferenceHelper();

}
