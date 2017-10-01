package officedepo.mediapark.com.officedepo.ui.Login;

import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;

import officedepo.mediapark.com.officedepo.Base.BasePresenter;
import officedepo.mediapark.com.officedepo.Model.DataManager;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import officedepo.mediapark.com.officedepo.Model.Items.UserLoginResponse;
import officedepo.mediapark.com.officedepo.Model.PreferenceHelper;
import officedepo.mediapark.com.officedepo.Push.GcmConfig;
import officedepo.mediapark.com.officedepo.ui.App;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mary Songal on 03.11.2016.
 */

public class LoginPresenter extends BasePresenter<LoginMvpView> {

    private final DataManager dataManager;
    private PreferenceHelper preferenceHelper;
    private CompositeSubscription subscription = new CompositeSubscription();

    private static final String TAG = "LoginPresenter";

    @Inject
    public LoginPresenter(DataManager dataManager, PreferenceHelper preferenceHelper) {
        this.dataManager = dataManager;
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
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

    public void sendPasswordConfirmationCode(String phone) {
        preferenceHelper.putUserId(phone);
        subscription.add(dataManager.sendPasswordConfirmationCode(phone)
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
                        //preferenceHelper.putPassword(user.password);
                        //getMvpView().onSignIn(user.phone);
                    }

                    @Override
                    public void onNext(ResponseBody response) {
                        getMvpView().showChangePassword();
                    }
                })
        );
    }

    public void checkUser(User user) {
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(GcmConfig.FIREBASE_SHARED_PREF, 0);
        String deviceId = sharedPreferences.getString(GcmConfig.FIREBASE_SHARED_PREF_FIELD, null);
        subscription.add(dataManager.checkUser(user, deviceId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserLoginResponse>() {
                    @Override
                    public void onCompleted() {
                        // do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError:", e);
                        getMvpView().showError();
                        //preferenceHelper.putPassword(user.password);
                        //getMvpView().onSignIn(user.phone);
                    }

                    @Override
                    public void onNext(UserLoginResponse response) {
                        // TODO: грохнуть локальную базу
                        if (response == null) {
                            getMvpView().showError();
                        } else {
                            preferenceHelper.putLoggedIn(true);
                            preferenceHelper.putUserId(user.phone);
                            preferenceHelper.putPassword(user.password);
                            getMvpView().onSignIn(user.phone);
                        }
                    }
                })
        );
    }

}
