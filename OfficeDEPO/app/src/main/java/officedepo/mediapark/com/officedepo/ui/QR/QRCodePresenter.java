package officedepo.mediapark.com.officedepo.ui.QR;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import officedepo.mediapark.com.officedepo.Base.BasePresenter;
import officedepo.mediapark.com.officedepo.Model.DataManager;
import officedepo.mediapark.com.officedepo.Model.Items.Deal;
import officedepo.mediapark.com.officedepo.ui.Deals.DealsMvpView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mary Songal on 16.11.2016.
 */

public class QRCodePresenter extends BasePresenter<QRMvpView> {

    private final DataManager dataManager;
    private CompositeSubscription subscription = new CompositeSubscription();

    private static final String TAG = "QRCodePresenter";

    @Inject
    public QRCodePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(QRMvpView mvpView) {
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

    public void generateQrCode(String baseWord) {
        subscription.add(dataManager.generateQrCode(baseWord)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
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
                    public void onNext(Bitmap qr) {
                        getMvpView().setupQRCode(qr);
                    }
                })
        );
    }

}
