package officedepo.mediapark.com.officedepo.ui.Password;

import android.util.Log;

import javax.inject.Inject;

import officedepo.mediapark.com.officedepo.Base.BasePresenter;
import officedepo.mediapark.com.officedepo.Model.DataManager;
import officedepo.mediapark.com.officedepo.Model.Items.UserLoginResponse;
import officedepo.mediapark.com.officedepo.Model.PreferenceHelper;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mary Songal on 11.11.2016.
 */

public class PasswordPresenter extends BasePresenter<PasswordMvpView> {

    private final DataManager dataManager;
    private PreferenceHelper preferenceHelper;
    private CompositeSubscription subscription = new CompositeSubscription();

    private static final String TAG = "PasswordPresenter";

    @Inject
    public PasswordPresenter(DataManager dataManager, PreferenceHelper preferenceHelper) {
        this.dataManager = dataManager;
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public void attachView(PasswordMvpView mvpView) {
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

    public void updatePasswordViaCode(String code, String updatedPassword) {
        String phone = preferenceHelper.getUserId();
        subscription.add(dataManager.updatePasswordViaCode(phone, code, updatedPassword)
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
                            getMvpView().showConfirmationError();
                        }

                        @Override
                        public void onNext(UserLoginResponse responseBody) {
                            if (responseBody == null) {
                                getMvpView().showConfirmationError();
                            } else {
                                getMvpView().showSuccess();
                            }
                        }
                    })
        );
    }

    public void updatePassword(String password, String updatedPassword) {
        String phone = preferenceHelper.getUserId();
        String oldPassword = preferenceHelper.getPassword();
        if (password.equals(oldPassword)) {
            subscription.add(dataManager.updatePassword(phone, updatedPassword)
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
                        }

                        @Override
                        public void onNext(UserLoginResponse responseBody) {
                            if (responseBody == null) {
                                getMvpView().showError();
                            } else {
                                getMvpView().showSuccess();
                            }
                        }
                    })
            );
        } else {
            getMvpView().showError();
        }
    }

}
