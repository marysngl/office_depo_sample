package officedepo.mediapark.com.officedepo;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;

import officedepo.mediapark.com.officedepo.ui.Login.LoginActivity;
import officedepo.mediapark.com.officedepo.ui.Login.LoginEnterFragment;
import officedepo.mediapark.com.officedepo.ui.Login.LoginRegisterFragment;
import officedepo.mediapark.com.officedepo.ui.Register.RegisterActivity;

/**
 * Created by Mary Songal on 14.11.2016.
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class LoginActivityTest {

    private LoginActivity loginActivity;

    @Before
    public void setup() {
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        Assert.assertNotNull(loginActivity);
    }

    @Test
    public void checkFragmentsNotNull() {
        FragmentManager fragmentManager = loginActivity.getFragmentManager();
        Assert.assertNotNull(fragmentManager.findFragmentById(R.id.login_enter_container));
        Assert.assertNotNull(fragmentManager.findFragmentById(R.id.login_or_container));
        Assert.assertNotNull(fragmentManager.findFragmentById(R.id.login_register_button_container));
    }

    private View getEnterLoginFragmentView() {
        FragmentManager fragmentManager = loginActivity.getFragmentManager();
        LoginEnterFragment loginEnterFragment = (LoginEnterFragment) fragmentManager.findFragmentById(R.id.login_enter_container);
        return loginEnterFragment.getView();
    }

    private View getLoginRegisterView() {
        FragmentManager fragmentManager = loginActivity.getFragmentManager();
        LoginRegisterFragment loginRegisterFragment = (LoginRegisterFragment) fragmentManager.findFragmentById(R.id.login_register_button_container);
        return loginRegisterFragment.getView();
    }

    @Test
    // Проверяем, что ничего не должно открываться, если пытаемся войти с пустыми полями
    public void checkEmptyFields() {
        View loginEnterView = getEnterLoginFragmentView();
        MaterialEditText phoneEditText = (MaterialEditText) loginEnterView.findViewById(R.id.login_phone_edittext);
        Assert.assertNotNull(phoneEditText);
        Button enterButton = (Button) loginEnterView.findViewById(R.id.login_enter_button);
        Assert.assertNotNull(enterButton);
        enterButton.performClick();
        Intent intent = Shadows.shadowOf(loginActivity).peekNextStartedActivity();
        Assert.assertNull(intent);
        MaterialEditText passwordEditText = (MaterialEditText) loginEnterView.findViewById(R.id.login_password_edittext);
        passwordEditText.setText("");
        enterButton.performClick();
        intent = Shadows.shadowOf(loginActivity).peekNextStartedActivity();
        Assert.assertNull(intent);
    }

    @Test
    // Должен показываться алерт
    public void checkForgotPassword() {
        View loginEnterView = getEnterLoginFragmentView();
        Button forgotPasswordButton = (Button) loginEnterView.findViewById(R.id.login_remind_button);
        forgotPasswordButton.performClick();
        AlertDialog alertDialog = ShadowAlertDialog.getLatestAlertDialog();
        ShadowAlertDialog shadowAlertDialog = Shadows.shadowOf(alertDialog);
        Assert.assertTrue(shadowAlertDialog.getMessage().toString().equals(loginActivity.getString(R.string.login_password_alert)));
    }

    @Test
    // Регистрация должна отркывать соответствующее активити
    public void checkRegister() {
        View loginRegisterView = getLoginRegisterView();
        Button registerButton = (Button) loginRegisterView.findViewById(R.id.login_register_button);
        registerButton.performClick();
        Intent intent = Shadows.shadowOf(loginActivity).peekNextStartedActivity();
        Assert.assertTrue(RegisterActivity.class.getCanonicalName().equals(intent.getComponent().getClassName()));
    }

}
