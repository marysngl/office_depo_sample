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

public class RegisterFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.register_confirm_phone_button)
    Button confirmPhoneButton;
    @BindView(R.id.register_phone_edittext)
    MaterialEditText phoneEdittext;

    @BindView(R.id.register_name_edittext)
    MaterialEditText nameEdittext;
    @BindView(R.id.register_surname_edittext)
    MaterialEditText surnameEdittext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        Util.makeEdittextPhoneTemplate(phoneEdittext);
        return view;
    }

    private boolean validatePhone(String phone) {
        // Серверная магия
        return phone.startsWith("+7") && phone.length() >= 11;
    }

    @OnClick(R.id.register_confirm_phone_button)
    void confirmPhone() {
        if (nameEdittext.getText().toString().isEmpty()) {
            nameEdittext.setError(getString(R.string.register_error_empty_name));
        } else if (surnameEdittext.getText().toString().isEmpty()) {
            surnameEdittext.setError(getString(R.string.register_error_empty_surname));
        } else if (!Util.validatePhone(phoneEdittext.getText().toString())) {
            phoneEdittext.setError(getString(R.string.register_error_empty_phone));
        } else if (!validatePhone(phoneEdittext.getText().toString())) {
            phoneEdittext.setError(getString(R.string.register_error_incorrect_phone_format));
        } else {
            //((RegisterActivity) getActivity()).sendConfirmationCode();
            ((RegisterActivity) getActivity()).register();
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public String getPhone() {
        return phoneEdittext.getText().toString().replaceAll("[^0-9]", "");
    }

    public String getName() {
        return nameEdittext.getText().toString();
    }

    public String getSurname() {
        return surnameEdittext.getText().toString();
    }

}
