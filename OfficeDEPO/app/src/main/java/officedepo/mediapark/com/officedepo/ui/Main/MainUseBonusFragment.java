package officedepo.mediapark.com.officedepo.ui.Main;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.QR.QRCodeActivity;
import officedepo.mediapark.com.officedepo.ui.StyledAlertProducer;

/**
 * Created by Mary Songal on 08.11.2016.
 */

public class MainUseBonusFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.main_bonus_expiration_textview)
    TextView expirationTextview;
    @BindView(R.id.main_use_bonus_button)
    Button useBonusButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_use_bonus, container, false);
        unbinder = ButterKnife.bind(this, view);
        // Извращения с 480 на 800
        if (getResources().getDisplayMetrics().density < 1.6) {
            expirationTextview.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) useBonusButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.bottomMargin = 15;
            useBonusButton.setLayoutParams(layoutParams);
        }
        return view;
    }

    @OnClick(R.id.main_use_bonus_button)
    void showQRCode() {
        Intent intent = new Intent(getActivity(), QRCodeActivity.class);
        intent.putExtra(MainActivity.EXTRA_QR_CODE, ((MainActivity)getActivity()).getQrCode());
        startActivity(intent);
    }

    public void setExpirationDate(DateTime date, boolean showExpirationMessage) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");
        String day = dateTimeFormatter.print(date);
        dateTimeFormatter = DateTimeFormat.forPattern("HH:mm");
        String hour = dateTimeFormatter.print(date);
        // Извращения hdpi
        if (getResources().getDisplayMetrics().density < 1.6) {
            if (showExpirationMessage) {
                StyledAlertProducer.showDismissiveStyledAlert(getActivity(), R.string.popup_standard_title,
                        String.format(getString(R.string.main_expiration_date), day, hour), android.R.string.ok);
            }
        } else {
            expirationTextview.setText(String.format(getString(R.string.main_expiration_date), day, hour));
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
