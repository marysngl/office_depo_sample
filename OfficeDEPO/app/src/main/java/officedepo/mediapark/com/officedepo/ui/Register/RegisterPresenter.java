package officedepo.mediapark.com.officedepo.ui.Register;

import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;

import officedepo.mediapark.com.officedepo.Base.BasePresenter;
import officedepo.mediapark.com.officedepo.Model.DataManager;
import officedepo.mediapark.com.officedepo.Model.Items.RegisterResponse;
import officedepo.mediapark.com.officedepo.Model.MainThreadBus;
import officedepo.mediapark.com.officedepo.Model.PreferenceHelper;
import officedepo.mediapark.com.officedepo.Model.Items.User;
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

public class RegisterPresenter extends BasePresenter<RegisterMvpView> {

    private final DataManager dataManager;
    private PreferenceHelper preferenceHelper;
    private final MainThreadBus bus;
    private CompositeSubscription subscription = new CompositeSubscription();

    private static final String TAG = "RegisterPresenter";

    @Inject
    public RegisterPresenter(DataManager dataManager, PreferenceHelper preferenceHelper, MainThreadBus bus) {
        this.dataManager = dataManager;
        this.preferenceHelper = preferenceHelper;
        this.bus = bus;
    }

    @Override
    public void attachView(RegisterMvpView mvpView) {
        super.attachView(mvpView);
        bus.register(this);
    }

    @Override
    public void detachView() {
        super.detachView();
        Log.d(TAG, "detachView");
        if (subscription != null) {
            subscription.unsubscribe();
        }
        bus.unregister(this);
    }

    public void addUser(User user) {
        SharedPreferences sharedPreferences = App.getAppContext().getSharedPreferences(GcmConfig.FIREBASE_SHARED_PREF, 0);
        String deviceId = sharedPreferences.getString(GcmConfig.FIREBASE_SHARED_PREF_FIELD, null);
        subscription.add(dataManager.addUser(user, deviceId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<RegisterResponse>() {
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
                public void onNext(RegisterResponse response) {
                    if (response == null) {
                        getMvpView().showError();
                    } else {
                        sendConfirmationCode(user.phone);
                    }
                }
            })
        );
    }

    public void sendConfirmationCode(String phone) {
        subscription.add(dataManager.sendConfirmationCode(phone)
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
                        //getMvpView().showError();
                    }

                    @Override
                    public void onNext(ResponseBody s) {
                        if (s == null) {
                            //getMvpView().showError();
                        } else {
                            getMvpView().showPhoneConfirm();
                        }
                    }
                })
        );
    }

    public void checkConfirmationCode(String phone, String code, String password) {
        subscription.add(dataManager.checkConfirmationCode(phone, code, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterResponse>() {
                    @Override
                    public void onCompleted() {
                        // do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError:", e);
                        getMvpView().showIncorrectCodeError();
                    }

                    @Override
                    public void onNext(RegisterResponse codeCorrect) {
                        if (codeCorrect == null) {
                            getMvpView().showIncorrectCodeError();
                        } else {
                            preferenceHelper.putLoggedIn(true);
                            preferenceHelper.putUserId(phone);
                            preferenceHelper.putPassword(password);
                            getMvpView().showRegisterSuccess(phone);
                        }
                    }
                })
        );
    }


}
