package officedepo.mediapark.com.officedepo.ui.QR;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import officedepo.mediapark.com.officedepo.Base.BaseFragmentActivity;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.Main.MainActivity;

/**
 * Created by Mary Songal on 11.11.2016.
 */

public class QRCodeActivity extends BaseFragmentActivity implements QRMvpView {

    @BindView(R.id.qr_code_imageview)
    ImageView qrCodeImage;
    @BindView(R.id.qr_back_button)
    Button backButton;

    @Inject
    QRCodePresenter qrCodePresenter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        qrCodePresenter.attachView(this);

        String qrCode = getIntent().getStringExtra(MainActivity.EXTRA_QR_CODE);
        progressDialog = ProgressDialog.show(this, "", getString(R.string.standard_progress_dialog_qr_message), true);
        qrCodePresenter.generateQrCode(qrCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        qrCodePresenter.detachView();
    }

    @OnClick(R.id.qr_back_button)
    void backToMain() {
        finish();
    }

    @Override
    public void showError() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(this, R.string.qr_generation_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setupQRCode(Bitmap qr) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        qrCodeImage.setImageBitmap(qr);
    }
}
