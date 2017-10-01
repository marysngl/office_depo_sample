package officedepo.mediapark.com.officedepo.Injection;

/**
 * Created by Mary Songal on 03.11.2016.
 */

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import officedepo.mediapark.com.officedepo.Model.API.ApiFactory;
import officedepo.mediapark.com.officedepo.Model.API.ApiService;
import officedepo.mediapark.com.officedepo.Model.MainThreadBus;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    MainThreadBus provideEventBus() {
        return new MainThreadBus();
    }

    @Provides
    @Singleton
    ApiService provideApiService() {
        return ApiFactory.createService(ApiService.class);
    }

}

