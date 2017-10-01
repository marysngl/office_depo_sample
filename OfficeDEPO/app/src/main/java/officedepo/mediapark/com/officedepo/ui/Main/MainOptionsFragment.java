package officedepo.mediapark.com.officedepo.ui.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.Deals.DealsActivity;
import officedepo.mediapark.com.officedepo.ui.History.HistoryActivity;
import officedepo.mediapark.com.officedepo.ui.StyledAlertProducer;

/**
 * Created by Mary Songal on 08.11.2016.
 */

public class MainOptionsFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.main_deals_button)
    ImageView dealsButton;
    @BindView(R.id.main_history_button)
    ImageView historyButton;
    @BindView(R.id.main_call_button)
    ImageView callButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_options, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.main_deals_button)
    void showDeals() {
        Intent intent = new Intent(getActivity(), DealsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.main_history_button)
    void showHistory() {
        Intent intent = new Intent(getActivity(), HistoryActivity.class);
        intent.putExtra(MainActivity.EXTRA_USER_PHONE, ((MainActivity)getActivity()).getUserId());
        intent.putExtra(MainActivity.EXTRA_REGISTRATION_DATE, ((MainActivity)getActivity()).getUserRegistrationDate());
        startActivity(intent);
    }

    @OnClick(R.id.main_call_button)
    void showCallAlert() {
        StyledAlertProducer.showStyledAlert(getActivity(), R.string.main_call_alert_title,
                String.format(getString(R.string.main_call_alert), getString(R.string.main_support_phone)),
                R.string.main_call_positive, R.string.main_call_negative, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+ getString(R.string.main_support_phone)));
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
