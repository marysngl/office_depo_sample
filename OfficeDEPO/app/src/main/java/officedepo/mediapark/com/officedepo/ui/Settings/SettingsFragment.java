package officedepo.mediapark.com.officedepo.ui.Settings;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.Model.PreferenceHelper;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.App;
import officedepo.mediapark.com.officedepo.ui.Login.LoginActivity;
import officedepo.mediapark.com.officedepo.ui.Password.PasswordActivity;
import officedepo.mediapark.com.officedepo.ui.Profile.ProfileActivity;
import officedepo.mediapark.com.officedepo.ui.Splash.SplashActivity;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class SettingsFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.edit_profile_button)
    Button editProfileButton;
    @BindView(R.id.edit_password_button)
    Button editPasswordButton;
    @BindView(R.id.exit_button)
    Button exitButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.edit_profile_button)
    void showProfileEditActivity() {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.edit_password_button)
    void showPasswordActivity() {
        Intent intent = new Intent(getActivity(), PasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.exit_button)
    void exitApp() {
        PreferenceHelper preferenceHelper = new PreferenceHelper(getActivity());
        preferenceHelper.putLoggedIn(false);
        preferenceHelper.putPassword("");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
