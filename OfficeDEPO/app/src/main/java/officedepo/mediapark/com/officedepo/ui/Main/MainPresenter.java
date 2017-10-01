package officedepo.mediapark.com.officedepo.ui.Main;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import officedepo.mediapark.com.officedepo.Base.BasePresenter;
import officedepo.mediapark.com.officedepo.Model.DataManager;
import officedepo.mediapark.com.officedepo.Model.Items.DealResponse;
import officedepo.mediapark.com.officedepo.Model.Items.HotDeal;
import officedepo.mediapark.com.officedepo.Model.Items.UserResponse;
import officedepo.mediapark.com.officedepo.Model.PreferenceHelper;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mary Songal on 08.11.2016.
 */

public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager dataManager;
    private PreferenceHelper preferenceHelper;
    private CompositeSubscription subscription = new CompositeSubscription();

    private static final String TAG = "MainPresenter";

    @Inject
    public MainPresenter(DataManager dataManager, PreferenceHelper preferenceHelper) {
        this.dataManager = dataManager;
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
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
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(UserResponse user) {
                        getMvpView().setupUser(user);
                    }
                })
        );
    }

    public void getHotDeals() {
        subscription.add(dataManager.getHotDeals()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DealResponse>>() {
                    @Override
                    public void onCompleted() {
                        // do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError:", e);
                        getMvpView().showHotDealsError();
                    }

                    @Override
                    public void onNext(List<DealResponse> deals) {
                        getMvpView().displayHotDeals(deals);
                    }
                })
        );
    }

    /*
    public void updateRegisteredEvent(String userId) {
        subscription.add(dataManager.updateUserRegisteredEvent(userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        // do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError:", e);
                    }

                    @Override
                    public void onNext(Integer rows) {
                        // do nothing
                    }
                })
        );
    }
    */

    public void saveUserId(String userId) {
        preferenceHelper.putUserId(userId);
    }

    public String getUserId() {
        return preferenceHelper.getUserId();
    }

}
