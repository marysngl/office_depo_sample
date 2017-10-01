package officedepo.mediapark.com.officedepo.ui.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.joda.time.DateTime;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.ButterKnife;
import officedepo.mediapark.com.officedepo.Base.BaseFragmentActivity;
import officedepo.mediapark.com.officedepo.Model.Items.DealResponse;
import officedepo.mediapark.com.officedepo.Model.Items.HotDeal;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import officedepo.mediapark.com.officedepo.Model.Items.UserResponse;
import officedepo.mediapark.com.officedepo.Push.GcmConfig;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.Util.NotificationUtils;
import officedepo.mediapark.com.officedepo.ui.StyledAlertProducer;

/**
 * Created by Mary Songal on 08.11.2016.
 */

public class MainActivity extends BaseFragmentActivity implements MainMvpView {

    public static final String EXTRA_USER_PHONE = "extra_user_phone";
    public static final String EXTRA_REGISTRATION_COMPLETE = "extra_registration_complete";
    public static final String EXTRA_REGISTRATION_DATE = "extra_registration_date";
    public static final String EXTRA_QR_CODE = "extra_qr_code";

    private static final String MAIN_INFO_FRAGMENT = "main_info_fragment";
    private static final String MAIN_OPTIONS_FRAGMENT = "main_options_fragment";
    private static final String MAIN_BONUS_FRAGMENT = "main_bonus_fragment";
    private static final String MAIN_USE_BONUS_FRAGMENT = "main_use_bonus_fragment";

    private String userPhone;
    private DateTime registrationDate;
    private String qrCode;
    private boolean registrationComplete = false;
    private boolean showExpirationDateMessage = true;

    private boolean paused = false;

    @Inject
    MainPresenter mainPresenter;

    private ProgressDialog progressDialog;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        mainPresenter.attachView(this);

        userPhone = getIntent().getStringExtra(EXTRA_USER_PHONE);
        registrationComplete = getIntent().getBooleanExtra(EXTRA_REGISTRATION_COMPLETE, false);

        MainInfoFragment mainInfoFragment = new MainInfoFragment();
        MainOptionsFragment mainOptionsFragment = new MainOptionsFragment();
        MainBonusFragment mainBonusFragment = new MainBonusFragment();
        MainUseBonusFragment mainUseBonusFragment = new MainUseBonusFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_info_container, mainInfoFragment, MAIN_INFO_FRAGMENT);
        fragmentTransaction.add(R.id.main_options_container, mainOptionsFragment, MAIN_OPTIONS_FRAGMENT);
        fragmentTransaction.add(R.id.main_bonus_container, mainBonusFragment, MAIN_BONUS_FRAGMENT);
        fragmentTransaction.add(R.id.main_use_bonus_container, mainUseBonusFragment, MAIN_USE_BONUS_FRAGMENT);
        fragmentTransaction.commit();

        mainPresenter.getHotDeals();
        mainPresenter.saveUserId(userPhone);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(GcmConfig.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(GcmConfig.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(GcmConfig.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    public String getUserId() {
        return userPhone;
    }

    public DateTime getUserRegistrationDate() {
        return registrationDate;
    }

    public String getQrCode() {
        return qrCode;
    }

    @Override
    protected void onResume() {
        super.onResume();
        paused = false;
        userPhone = mainPresenter.getUserId();
        progressDialog = ProgressDialog.show(this, "", getString(R.string.standard_progress_dialog_message), true);
        mainPresenter.getUser(userPhone);
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GcmConfig.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(GcmConfig.PUSH_NOTIFICATION));
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    @Override
    protected void onPause() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
        paused = true;
    }

    @Override
    public void showError() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (!paused) {
            Toast.makeText(this, R.string.main_get_user_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showHotDealsError() {
        Toast.makeText(this, R.string.main_get_hot_deals_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setupUser(UserResponse user) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        qrCode = user.phone;
        if (getFragmentManager().findFragmentById(R.id.main_info_container) != null) {
            ((MainInfoFragment)getFragmentManager().findFragmentById(R.id.main_info_container)).setNameSurname(user.name, user.surname);
        }
        if (getFragmentManager().findFragmentById(R.id.main_bonus_container) != null && user.bonus != null) {
            ((MainBonusFragment)getFragmentManager().findFragmentById(R.id.main_bonus_container)).setBonus(Integer.valueOf(user.bonus));
        }
        if (getFragmentManager().findFragmentById(R.id.main_use_bonus_container) != null) {
            DateTime expirationDate = new DateTime(DateTime.now().getYear(), 12, 31, 23, 59);
            ((MainUseBonusFragment)getFragmentManager().findFragmentById(R.id.main_use_bonus_container)).setExpirationDate(expirationDate, showExpirationDateMessage);
            showExpirationDateMessage = false;
        }
        registrationDate = new DateTime(DateTime.now().getYear(), 1, 1, 0, 0);
        if (registrationComplete) {
            StyledAlertProducer.showDismissiveStyledAlert(this, R.string.popup_registered_event_title,
                    String.format(getString(R.string.popup_registered_event), user.bonus), android.R.string.ok);
            registrationComplete = false;
        }
    }

    @Override
    public void displayHotDeals(List<DealResponse> deals) {
        if (deals.isEmpty()) {
            if (getFragmentManager().findFragmentById(R.id.main_info_container) != null) {
                ((MainInfoFragment)getFragmentManager().findFragmentById(R.id.main_info_container)).toggleBannerVisibility(false);
            }
            return;
        }
        Random rndGenerator = new Random();
        int randomDealIndex = rndGenerator.nextInt(deals.size());
        DealResponse hotDeal = deals.get(randomDealIndex);
        if (getFragmentManager().findFragmentById(R.id.main_info_container) != null) {
            ((MainInfoFragment)getFragmentManager().findFragmentById(R.id.main_info_container)).showDeal(hotDeal);
        }
    }
}
