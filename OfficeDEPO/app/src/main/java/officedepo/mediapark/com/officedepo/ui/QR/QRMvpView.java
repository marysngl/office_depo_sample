package officedepo.mediapark.com.officedepo.ui.QR;

import android.graphics.Bitmap;

import officedepo.mediapark.com.officedepo.Base.MvpView;

/**
 * Created by Mary Songal on 16.11.2016.
 */

public interface QRMvpView extends MvpView {

    void showError();

    void setupQRCode(Bitmap qr);

}
