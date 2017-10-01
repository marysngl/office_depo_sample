package officedepo.mediapark.com.officedepo.ui.Password;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import officedepo.mediapark.com.officedepo.Base.BaseFragmentActivity;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.Toolbar.ToolbarFragment;

/**
 * Created by Mary Songal on 11.11.2016.
 */

public class PasswordActivity extends BaseFragmentActivity implements PasswordMvpView {

    private static final String TOOLBAR_FRAGMENT = "toolbar_fragment";
    private static final String PASSWORD_FRAGMENT = "password_fragment";

    public static final String EXTRA_REMIND_PASSWORD = "remind_password_flag";

    @Inject
    PasswordPresenter passwordPresenter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        passwordPresenter.attachView(this);

        boolean remindPasswordView = getIntent().getBooleanExtra(EXTRA_REMIND_PASSWORD, false);

        ToolbarFragment toolbarFragment = new ToolbarFragment();
        PasswordFragment passwordFragment = new PasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(PasswordFragment.ARG_REMIND_MODE, remindPasswordView);
        passwordFragment.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.password_toolbar_container, toolbarFragment, TOOLBAR_FRAGMENT);
        fragmentTransaction.add(R.id.password_container, passwordFragment, PASSWORD_FRAGMENT);
        fragmentTransaction.commit();

        toolbarFragment.setTitle(getString(R.string.password_title));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        passwordPresenter.detachView();
    }

    public void updatePassword(String password, String updatedPassword) {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.standard_progress_dialog_save_message), true);
        passwordPresenter.updatePassword(password, updatedPassword);
    }

    public void updatePasswordViaCode(String code, String password) {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.standard_progress_dialog_save_message), true);
        passwordPresenter.updatePasswordViaCode(code, password);
    }

    @Override
    public void showError() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(this, R.string.password_save_fail, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showConfirmationError() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(this, R.string.password_confirm_fail, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccess() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(this, R.string.password_save_success, Toast.LENGTH_LONG).show();
    }
}
