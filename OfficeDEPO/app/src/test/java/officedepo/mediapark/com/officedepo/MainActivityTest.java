package officedepo.mediapark.com.officedepo;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;

import officedepo.mediapark.com.officedepo.ui.Deals.DealsActivity;
import officedepo.mediapark.com.officedepo.ui.History.HistoryActivity;
import officedepo.mediapark.com.officedepo.ui.Main.MainActivity;
import officedepo.mediapark.com.officedepo.ui.Main.MainInfoFragment;
import officedepo.mediapark.com.officedepo.ui.Main.MainOptionsFragment;
import officedepo.mediapark.com.officedepo.ui.Main.MainUseBonusFragment;
import officedepo.mediapark.com.officedepo.ui.QR.QRCodeActivity;
import officedepo.mediapark.com.officedepo.ui.Settings.SettingsActivity;

/**
 * Created by Mary Songal on 15.11.2016.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class MainActivityTest  {

    private MainActivity mainActivity;

    @Before
    public void setup() {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().start().resume().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        Assert.assertNotNull(mainActivity);
    }

    @Test
    public void checkFragmentsNotNull() {
        FragmentManager fragmentManager = mainActivity.getFragmentManager();
        Assert.assertNotNull(fragmentManager.findFragmentById(R.id.main_info_container));
        Assert.assertNotNull(fragmentManager.findFragmentById(R.id.main_options_container));
        Assert.assertNotNull(fragmentManager.findFragmentById(R.id.main_bonus_container));
        Assert.assertNotNull(fragmentManager.findFragmentById(R.id.main_use_bonus_container));
    }

    private View getMainInfoView() {
        FragmentManager fragmentManager = mainActivity.getFragmentManager();
        MainInfoFragment mainInfoFragment = (MainInfoFragment) fragmentManager.findFragmentById(R.id.main_info_container);
        return mainInfoFragment.getView();
    }

    private View getOptionsView() {
        FragmentManager fragmentManager = mainActivity.getFragmentManager();
        MainOptionsFragment mainOptionsFragment = (MainOptionsFragment) fragmentManager.findFragmentById(R.id.main_options_container);
        return mainOptionsFragment.getView();
    }

    private View getUseBonusView() {
        FragmentManager fragmentManager = mainActivity.getFragmentManager();
        MainUseBonusFragment mainUseBonusFragment = (MainUseBonusFragment) fragmentManager.findFragmentById(R.id.main_use_bonus_container);
        return mainUseBonusFragment.getView();
    }

    @Test
    public void checkSettings() {
        View mainInfoView = getMainInfoView();
        ImageView settingsButton = (ImageView) mainInfoView.findViewById(R.id.main_settings_button);
        settingsButton.performClick();
        Intent intent = Shadows.shadowOf(mainActivity).peekNextStartedActivity();
        Assert.assertTrue(SettingsActivity.class.getCanonicalName().equals(intent.getComponent().getClassName()));
    }

    @Test
    public void checkHistoryOption() {
        View optionsView = getOptionsView();
        ImageView historyButton = (ImageView) optionsView.findViewById(R.id.main_history_button);
        historyButton.performClick();
        Intent intent = Shadows.shadowOf(mainActivity).peekNextStartedActivity();
        Assert.assertTrue(HistoryActivity.class.getCanonicalName().equals(intent.getComponent().getClassName()));
    }

    @Test
    public void checkDealsOption() {
        View optionsView = getOptionsView();
        ImageView dealsButton = (ImageView) optionsView.findViewById(R.id.main_deals_button);
        dealsButton.performClick();
        Intent intent = Shadows.shadowOf(mainActivity).peekNextStartedActivity();
        Assert.assertTrue(DealsActivity.class.getCanonicalName().equals(intent.getComponent().getClassName()));
    }

    @Test
    public void checkCallOption() {
        View optionsView = getOptionsView();
        ImageView callButton = (ImageView) optionsView.findViewById(R.id.main_call_button);
        callButton.performClick();
        AlertDialog alertDialog = ShadowAlertDialog.getLatestAlertDialog();
        ShadowAlertDialog shadowAlertDialog = Shadows.shadowOf(alertDialog);
        Assert.assertTrue(shadowAlertDialog.getMessage().equals(
                String.format(mainActivity.getString(R.string.main_call_alert), mainActivity.getString(R.string.main_support_phone))));
    }

    @Test
    public void checkUseBonus() {
        View useBonusView = getUseBonusView();
        Button useBonusButton = (Button) useBonusView.findViewById(R.id.main_use_bonus_button);
        useBonusButton.performClick();
        Intent intent = Shadows.shadowOf(mainActivity).peekNextStartedActivity();
        Assert.assertTrue(QRCodeActivity.class.getCanonicalName().equals(intent.getComponent().getClassName()));
    }

}
