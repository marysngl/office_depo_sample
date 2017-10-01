package officedepo.mediapark.com.officedepo.ui.Deals;

import android.util.Log;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import officedepo.mediapark.com.officedepo.Base.BasePresenter;
import officedepo.mediapark.com.officedepo.Model.DataManager;
import officedepo.mediapark.com.officedepo.Model.Items.Deal;
import officedepo.mediapark.com.officedepo.Model.Items.DealResponse;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class DealsPresenter extends BasePresenter<DealsMvpView> {

    private final DataManager dataManager;
    private CompositeSubscription subscription = new CompositeSubscription();

    private static final String TAG = "DealsPresenter";

    @Inject
    public DealsPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(DealsMvpView mvpView) {
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

    public void getDeals() {
        subscription.add(dataManager.getDeals()
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
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<DealResponse> deals) {
                        filterDeals(deals);
                        getMvpView().setupDeals(deals);
                    }
                })
        );
    }

    // TODO: лучше бы на серверной стороне
    private void filterDeals(List<DealResponse> deals) {
        Iterator<DealResponse> iterator = deals.iterator();
        while (iterator.hasNext()) {
            DealResponse deal = iterator.next();
            if (deal.isHotDeal()) {
                iterator.remove();
            }
        }
    }


}
