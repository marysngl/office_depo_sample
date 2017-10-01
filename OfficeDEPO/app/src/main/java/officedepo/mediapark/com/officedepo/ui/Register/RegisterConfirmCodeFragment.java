package officedepo.mediapark.com.officedepo.ui.Register;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.Util.Util;

/**
 * Created by Mary Songal on 02.11.2016.
 */

public class RegisterConfirmCodeFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.register_button)
    Button registerButton;
    @BindView(R.id.register_password_edittext)
    MaterialEditText passwordEdittext;
    @BindView(R.id.register_confirm_code_edittext)
    MaterialEditText confirmCodeEdittext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_code_confirm, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.register_button)
    public void register() {
        String code = confirmCodeEdittext.getText().toString();
        if (code.isEmpty()) {
            confirmCodeEdittext.setError(getString(R.string.register_error_empty_confirmation_code));
        } else if (!Util.validatePassword(getPassword())) {
            passwordEdittext.setError(String.format(getString(R.string.register_error_empty_password), Util.MIN_PASSWORD_CHARACTERS));
        } else {
            ((RegisterActivity) getActivity()).confirmCode(code);
        }
    }

    public String getPassword() {
        return passwordEdittext.getText().toString();
    }

    public void clearFields() {
        confirmCodeEdittext.setText(null);
    }

}
