package officedepo.mediapark.com.officedepo.ui.Profile;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import officedepo.mediapark.com.officedepo.Base.BaseFragmentActivity;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import officedepo.mediapark.com.officedepo.Model.Items.UserResponse;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.Toolbar.ToolbarFragment;

/**
 * Created by Mary Songal on 11.11.2016.
 */

public class ProfileActivity extends BaseFragmentActivity implements ProfileMvpView {

    private static final String TOOLBAR_FRAGMENT = "toolbar_fragment";
    private static final String PROFILE_FRAGMENT = "profile_fragment";

    @Inject
    ProfilePresenter profilePresenter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        profilePresenter.attachView(this);

        ToolbarFragment toolbarFragment = new ToolbarFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.profile_toolbar_container, toolbarFragment, TOOLBAR_FRAGMENT);
        fragmentTransaction.add(R.id.profile_container, profileFragment, PROFILE_FRAGMENT);
        fragmentTransaction.commit();

        toolbarFragment.setTitle(getString(R.string.profile_title));
        progressDialog = ProgressDialog.show(this, "", getString(R.string.standard_progress_dialog_message), true);
        profilePresenter.getUser(profilePresenter.getUserId());
    }

    public void saveProfile(UserResponse user) {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.standard_progress_dialog_save_message), true);
        profilePresenter.updateProfile(user);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profilePresenter.detachView();
    }

    @Override
    public void showError() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(this, R.string.profile_save_fail, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserError() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(this, R.string.profile_get_user_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccess() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(this, R.string.profile_save_success, Toast.LENGTH_LONG).show();
    }

    @Override
    public void fillProfile(UserResponse user) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (getFragmentManager().findFragmentById(R.id.profile_container) != null) {
            ((ProfileFragment)getFragmentManager().findFragmentById(R.id.profile_container)).fillUser(user);
        }
    }
}
