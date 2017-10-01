package officedepo.mediapark.com.officedepo.ui.Login;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.Base.BasePhoneEdittext;
import officedepo.mediapark.com.officedepo.Base.BasePhoneNumberClickListener;
import officedepo.mediapark.com.officedepo.Base.BasePhoneNumberWatcher;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.Util.Util;
import officedepo.mediapark.com.officedepo.ui.StyledAlertProducer;

/**
 * Created by Mary Songal on 31.10.2016.
 */

public class LoginEnterFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.login_phone_edittext)
    MaterialEditText phoneEdittext;
    @BindView(R.id.login_password_edittext)
    MaterialEditText passwordEdittext;
    @BindView(R.id.login_enter_button)
    Button enterButton;
    @BindView(R.id.login_remind_button)
    Button remindButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_enter, container, false);
        unbinder = ButterKnife.bind(this, view);
        Util.makeEdittextPhoneTemplate(phoneEdittext);
        return view;
    }

    @OnClick(R.id.login_remind_button)
    void showRemindAlert() {
        String phone = phoneEdittext.getText().toString();
        if (!Util.validatePhone(phone)) {
            StyledAlertProducer.showDismissiveStyledAlert(getActivity(), R.string.register_error_empty_phone, getString(R.string.login_password_remind_error),
                    android.R.string.ok);
        } else {
            StyledAlertProducer.showStyledAlert(getActivity(), R.string.login_password_alert_title, String.format(getString(R.string.login_password_alert), phone),
                    R.string.login_password_positive, R.string.login_password_negative, new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            String phoneNumber = phone.replaceAll("[^0-9]", "");
                            ((LoginActivity)getActivity()).sendPasswordConfirmationCode(phoneNumber);
                            dialog.dismiss();
                        }
                    });
        }
    }

    @OnClick(R.id.login_enter_button)
    public void signIn() {
        String phone = phoneEdittext.getText().toString();
        String password = passwordEdittext.getText().toString();
        if (!Util.validatePhone(phone)) {
            phoneEdittext.setError(getString(R.string.login_error_empty_phone));
        } else if (password.isEmpty()) {
            passwordEdittext.setError(getString(R.string.login_error_empty_password));
        } else {
            phone = phone.replaceAll("[^0-9]", "");
            ((LoginActivity) getActivity()).signIn(new User(phone, password));
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
