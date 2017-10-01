package officedepo.mediapark.com.officedepo;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import officedepo.mediapark.com.officedepo.ui.Register.RegisterActivity;
import officedepo.mediapark.com.officedepo.ui.Register.RegisterConfirmCodeFragment;
import officedepo.mediapark.com.officedepo.ui.Register.RegisterFragment;
import officedepo.mediapark.com.officedepo.ui.Register.RegisterResendFragment;

/**
 * Created by Mary Songal on 14.11.2016.
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class RegisterActivityTest {

    private RegisterActivity registerActivity;

    @Before
    public void setup() {
        registerActivity = Robolectric.buildActivity(RegisterActivity.class).create().start().resume().get();
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        Assert.assertNotNull(registerActivity);
    }

    @Test
    public void checkFragmentsNotNull() {
        FragmentManager fragmentManager = registerActivity.getFragmentManager();
        Assert.assertNotNull(fragmentManager.findFragmentById(R.id.register_container));
        Assert.assertNotNull(fragmentManager.findFragmentById(R.id.register_code_confirm_container));
        Assert.assertNotNull(fragmentManager.findFragmentById(R.id.register_resend_container));
    }

    private View getRegisterView() {
        FragmentManager fragmentManager = registerActivity.getFragmentManager();
        RegisterFragment registerFragment = (RegisterFragment) fragmentManager.findFragmentById(R.id.register_container);
        return registerFragment.getView();
    }

    private View getConfirmCodeView() {
        FragmentManager fragmentManager = registerActivity.getFragmentManager();
        RegisterConfirmCodeFragment registerConfirmCodeFragment = (RegisterConfirmCodeFragment) fragmentManager.findFragmentById(R.id.register_code_confirm_container);
        return  registerConfirmCodeFragment.getView();
    }

    private View getResendView() {
        FragmentManager fragmentManager = registerActivity.getFragmentManager();
        RegisterResendFragment registerResendFragment = (RegisterResendFragment) fragmentManager.findFragmentById(R.id.register_resend_container);
        return registerResendFragment.getView();
    }

    @Test
    public void checkFragmentsVisibility() {
        FrameLayout registerContainer = (FrameLayout) registerActivity.findViewById(R.id.register_container);
        FrameLayout confirmCodeContainer = (FrameLayout) registerActivity.findViewById(R.id.register_code_confirm_container);
        FrameLayout resendContainer = (FrameLayout) registerActivity.findViewById(R.id.register_resend_container);
        Assert.assertTrue(registerContainer.getVisibility() == View.VISIBLE);
        Assert.assertTrue(confirmCodeContainer.getVisibility() == View.GONE);
        Assert.assertTrue(resendContainer.getVisibility() == View.VISIBLE);
    }

    private void assertCodeConfirmNotVisible(FrameLayout confirmCodeContainer, TextView resendTextView, Button resendButton) {
        Assert.assertTrue(confirmCodeContainer.getVisibility() == View.GONE);
        Assert.assertTrue(resendButton.getVisibility() == View.GONE);
        Assert.assertTrue(resendTextView.getVisibility() == View.GONE);
    }

    @Test
    public void checkRegistrationData() {
        View registerView = getRegisterView();
        MaterialEditText nameEditText = (MaterialEditText) registerView.findViewById(R.id.register_name_edittext);
        MaterialEditText surnameEditText = (MaterialEditText) registerView.findViewById(R.id.register_surname_edittext);
        Button confirmPhoneButton = (Button) registerView.findViewById(R.id.register_confirm_phone_button);
        View resendView = getResendView();
        TextView resendTextView = (TextView) resendView.findViewById(R.id.register_resend_instructions_textview);
        Button resendButton = (Button) resendView.findViewById(R.id.register_resend_button);
        FrameLayout confirmCodeContainer = (FrameLayout) registerActivity.findViewById(R.id.register_code_confirm_container);

        confirmPhoneButton.performClick();
        assertCodeConfirmNotVisible(confirmCodeContainer, resendTextView, resendButton);
        nameEditText.setText("name");
        confirmPhoneButton.performClick();
        assertCodeConfirmNotVisible(confirmCodeContainer, resendTextView, resendButton);
        surnameEditText.setText("surname");
        confirmPhoneButton.performClick();
        assertCodeConfirmNotVisible(confirmCodeContainer, resendTextView, resendButton);
    }

    @Test
    public void checkPhoneConfirm() {
        View codeConfirmView = getConfirmCodeView();
        MaterialEditText codeEditText = (MaterialEditText) codeConfirmView.findViewById(R.id.register_confirm_code_edittext);
        MaterialEditText passwordEditText = (MaterialEditText) codeConfirmView.findViewById(R.id.register_password_edittext);
        Button registerButton = (Button) codeConfirmView.findViewById(R.id.register_button);
        registerButton.performClick();
        Intent intent = Shadows.shadowOf(registerActivity).peekNextStartedActivity();
        Assert.assertNull(intent);
        codeEditText.setText("office111");
        intent = Shadows.shadowOf(registerActivity).peekNextStartedActivity();
        Assert.assertNull(intent);
        passwordEditText.setText("111");
        // Минимальная длина пароля сейчас 5 символов
        intent = Shadows.shadowOf(registerActivity).peekNextStartedActivity();
        Assert.assertNull(intent);
    }


}
