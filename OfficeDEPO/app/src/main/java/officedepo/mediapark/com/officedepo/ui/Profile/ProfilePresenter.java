package officedepo.mediapark.com.officedepo.ui.Profile;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import officedepo.mediapark.com.officedepo.Base.BasePresenter;
import officedepo.mediapark.com.officedepo.Model.DataManager;
import officedepo.mediapark.com.officedepo.Model.Items.UserResponse;
import officedepo.mediapark.com.officedepo.Model.PreferenceHelper;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mary Songal on 11.11.2016.
 */

public class ProfilePresenter extends BasePresenter<ProfileMvpView> {

    private final DataManager dataManager;
    private PreferenceHelper preferenceHelper;
    private CompositeSubscription subscription = new CompositeSubscription();

    private static final String TAG = "ProfilePresenter";

    @Inject
    public ProfilePresenter(DataManager dataManager, PreferenceHelper preferenceHelper) {
        this.dataManager = dataManager;
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public void attachView(ProfileMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        Log.d(TAG, "detachView");
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public String getUserId() {
        return preferenceHelper.getUserId();
    }

    public void getUser(String phone) {
        subscription.add(dataManager.getUser(phone)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserResponse>() {
                    @Override
                    public void onCompleted() {
                        // do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError:", e);
                        getMvpView().showUserError();
                    }

                    @Override
                    public void onNext(UserResponse user) {
                        getMvpView().fillProfile(user);
                    }
                })
        );
    }

    public void updateProfile(UserResponse user) {
        String password = preferenceHelper.getPassword();
        subscription.add(dataManager.updateProfile(user, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        // do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError:", e);
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                        preferenceHelper.putUserId(user.phone);
                        getMvpView().showSuccess();
                    }
                })
        );
    }

}
