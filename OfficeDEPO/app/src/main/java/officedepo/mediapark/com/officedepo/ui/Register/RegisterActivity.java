package officedepo.mediapark.com.officedepo.ui.Register;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import officedepo.mediapark.com.officedepo.Base.BaseFragmentActivity;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.Main.MainActivity;

/**
 * Created by Mary Songal on 02.11.2016.
 */

public class RegisterActivity extends BaseFragmentActivity implements RegisterMvpView{

    private static final String REGISTER_FRAGMENT = "register_fragment";
    private static final String REGISTER_CONFIRM_CODE_FRAGMENT = "register_confirm_code_fragment";
    private static final String REGISTER_RESEND_FRAGMENT = "register_resend_fragment";

    @BindView(R.id.register_code_confirm_container)
    FrameLayout confirmCodeContainer;

    @Inject
    RegisterPresenter registerPresenter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        registerPresenter.attachView(this);

        RegisterFragment registerFragment = new RegisterFragment();
        RegisterConfirmCodeFragment registerConfirmCodeFragment = new RegisterConfirmCodeFragment();
        RegisterResendFragment registerResendFragment = new RegisterResendFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.register_container, registerFragment, REGISTER_FRAGMENT);
        fragmentTransaction.add(R.id.register_code_confirm_container, registerConfirmCodeFragment, REGISTER_CONFIRM_CODE_FRAGMENT);
        fragmentTransaction.add(R.id.register_resend_container, registerResendFragment, REGISTER_RESEND_FRAGMENT);
        fragmentTransaction.commit();
    }

    public void sendConfirmationCode() {
        if (getFragmentManager().findFragmentById(R.id.register_code_confirm_container) != null) {
            ((RegisterConfirmCodeFragment)getFragmentManager().findFragmentById(R.id.register_code_confirm_container)).clearFields();
        }
        String phone = "";
        if (getFragmentManager().findFragmentById(R.id.register_container) != null) {
            RegisterFragment registerFragment = ((RegisterFragment)getFragmentManager().findFragmentById(R.id.register_container));
            phone = registerFragment.getPhone();
        }
        registerPresenter.sendConfirmationCode(phone);
    }

    public void confirmCode(String code) {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.standard_progress_dialog_check_code_message), true);
        String phone = "";
        String password = "";
        if (getFragmentManager().findFragmentById(R.id.register_container) != null) {
            RegisterFragment registerFragment = ((RegisterFragment)getFragmentManager().findFragmentById(R.id.register_container));
            phone = registerFragment.getPhone();
        }
        if (getFragmentManager().findFragmentById(R.id.register_code_confirm_container) != null) {
            password = ((RegisterConfirmCodeFragment)getFragmentManager().findFragmentById(R.id.register_code_confirm_container)).getPassword();
        }
        registerPresenter.checkConfirmationCode(phone, code, password);
    }

    @Override
    public void showPhoneConfirm() {
        confirmCodeContainer.setVisibility(View.VISIBLE);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(confirmCodeContainer, View.TRANSLATION_Y, -350.0f, 0.0f);
        animator.setDuration(1500);
        animatorSet.play(animator);
        animatorSet.start();
        if (getFragmentManager().findFragmentById(R.id.register_resend_container) != null) {
            ((RegisterResendFragment)getFragmentManager().findFragmentById(R.id.register_resend_container)).showResendView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registerPresenter.detachView();
    }

    @Override
    public void register() {
        User user = new User();
        /*
        if (getFragmentManager().findFragmentById(R.id.register_code_confirm_container) != null) {
            user.password = ((RegisterConfirmCodeFragment)getFragmentManager().findFragmentById(R.id.register_code_confirm_container)).getPassword();
        }
        */
        if (getFragmentManager().findFragmentById(R.id.register_container) != null) {
            RegisterFragment registerFragment = ((RegisterFragment)getFragmentManager().findFragmentById(R.id.register_container));
            user.phone = registerFragment.getPhone();
            user.name = registerFragment.getName();
            user.surname = registerFragment.getSurname();
        }
        registerPresenter.addUser(user);
    }

    @Override
    public void showError() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(this, R.string.register_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showIncorrectCodeError() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(this, R.string.register_code_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRegisterSuccess(String phone) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(MainActivity.EXTRA_USER_PHONE, phone);
        intent.putExtra(MainActivity.EXTRA_REGISTRATION_COMPLETE, true);
        startActivity(intent);
        finish();
    }
}
