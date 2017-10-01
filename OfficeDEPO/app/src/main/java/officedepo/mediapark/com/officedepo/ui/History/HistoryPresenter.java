package officedepo.mediapark.com.officedepo.ui.History;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import officedepo.mediapark.com.officedepo.Base.BasePresenter;
import officedepo.mediapark.com.officedepo.Model.DataManager;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryItem;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryResponse;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class HistoryPresenter extends BasePresenter<HistoryMvpView> {

    private final DataManager dataManager;
    private CompositeSubscription subscription = new CompositeSubscription();

    private static final String TAG = "HistoryPresenter";

    @Inject
    public HistoryPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(HistoryMvpView mvpView) {
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

    public void getHistory(String userId) {
        subscription.add(dataManager.getHistory(userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<HistoryResponse>>() {
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
                    public void onNext(List<HistoryResponse> historyItems) {
                        getMvpView().setupHistory(historyItems);
                    }
                })
        );
    }

    /*
    public void getTotalSpent(String userId) {
        subscription.add(dataManager.getTotalSpent(userId)
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
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(Integer totalSpent) {
                        getMvpView().setupTotalSpent(totalSpent);
                    }
                })
        );
    }

    public void getTotalBonus(String userId) {
        subscription.add(dataManager.getTotalBonus(userId)
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
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(Integer totalBonus) {
                        getMvpView().setupTotalBonus(totalBonus);
                    }
                })
        );
    }
    */

}
