package officedepo.mediapark.com.officedepo.ui.Login;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import officedepo.mediapark.com.officedepo.Base.BaseFragmentActivity;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import officedepo.mediapark.com.officedepo.Model.PreferenceHelper;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.Main.MainActivity;
import officedepo.mediapark.com.officedepo.ui.Password.PasswordActivity;


/**
 * Created by Mary Songal on 31.10.2016.
 */

public class LoginActivity extends BaseFragmentActivity implements LoginMvpView {

    private static final String LOGIN_ENTER_FRAGMENT = "login_enter_fragment";
    private static final String LOGIN_OR_FRAGMENT = "login_or_fragment";
    private static final String LOGIN_REGISTER_FRAGMENT = "login_register_fragment";

    @Inject
    LoginPresenter loginPresenter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        loginPresenter.attachView(this);

        LoginEnterFragment loginEnterFragment = new LoginEnterFragment();
        LoginOrFragment loginOrFragment = new LoginOrFragment();
        LoginRegisterFragment loginRegisterFragment = new LoginRegisterFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.login_enter_container, loginEnterFragment, LOGIN_ENTER_FRAGMENT);
        fragmentTransaction.add(R.id.login_or_container, loginOrFragment, LOGIN_OR_FRAGMENT);
        fragmentTransaction.add(R.id.login_register_button_container, loginRegisterFragment, LOGIN_REGISTER_FRAGMENT);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }

    public void signIn(User user) {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.standard_progress_dialog_login_message), true);
        loginPresenter.checkUser(user);
    }

    public void sendPasswordConfirmationCode(String phone) {
        loginPresenter.sendPasswordConfirmationCode(phone);
    }

    @Override
    public void showError() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(this, R.string.login_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignIn(String phone) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_USER_PHONE, phone);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showChangePassword() {
        Intent intent = new Intent(this, PasswordActivity.class);
        intent.putExtra(PasswordActivity.EXTRA_REMIND_PASSWORD, true);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
