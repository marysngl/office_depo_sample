package officedepo.mediapark.com.officedepo.ui.Password;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.Util.Util;

/**
 * Created by Mary Songal on 11.11.2016.
 */

public class PasswordFragment extends Fragment {

    private Unbinder unbinder;

    public static final String ARG_REMIND_MODE = "arg_remind_mode";

    @BindView(R.id.password_old_textview)
    TextView oldPasswordTextView;
    @BindView(R.id.password_old_edittext)
    MaterialEditText oldPasswordEdittext;
    @BindView(R.id.password_new_edittext)
    MaterialEditText newPasswordEdittext;
    @BindView(R.id.password_repeat_new_edittext)
    MaterialEditText repeatNewPasswordEdittext;

    @BindView(R.id.password_save_button)
    Button saveButton;

    private boolean remindPasswordMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        remindPasswordMode = getArguments().getBoolean(ARG_REMIND_MODE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (remindPasswordMode) {
            oldPasswordTextView.setText(R.string.password_confirmation_code);
        }
        return view;
    }

    @OnClick(R.id.password_save_button)
    void savePassword() {
        String oldPassword = oldPasswordEdittext.getText().toString();
        String newPassword = newPasswordEdittext.getText().toString();
        String repeatNewPassword = repeatNewPasswordEdittext.getText().toString();
        if (oldPassword.isEmpty()) {
            oldPasswordEdittext.setError(getString(remindPasswordMode ? R.string.register_error_empty_confirmation_code : R.string.password_error_empty_old));
        } else if (newPassword.isEmpty()) {
            newPasswordEdittext.setError(getString(R.string.password_error_empty_new));
        } else if (!Util.validatePassword(newPassword)) {
            newPasswordEdittext.setError(String.format(getString(R.string.register_error_empty_password), Util.MIN_PASSWORD_CHARACTERS));
        } else if (!newPassword.equals(repeatNewPassword)) {
            repeatNewPasswordEdittext.setError(getString(R.string.password_passwords_not_match));
        } else {
            if (!remindPasswordMode) {
                ((PasswordActivity) getActivity()).updatePassword(oldPassword, newPassword);
            } else {
                ((PasswordActivity) getActivity()).updatePasswordViaCode(oldPassword, newPassword);
            }
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
