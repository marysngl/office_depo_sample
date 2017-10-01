package officedepo.mediapark.com.officedepo.ui.Profile;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.Model.Items.Gender;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import officedepo.mediapark.com.officedepo.Model.Items.UserResponse;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.Util.Util;

/**
 * Created by Mary Songal on 11.11.2016.
 */

public class ProfileFragment extends Fragment {

    private Unbinder unbinder;

    private String[] genders;
    private int currentGender;
    private UserResponse user;

    @BindView(R.id.profile_name_button)
    Button nameButton;
    @BindView(R.id.profile_name_edittext)
    MaterialEditText nameEdittext;

    @BindView(R.id.profile_surname_button)
    Button surnameButton;
    @BindView(R.id.profile_surname_edittext)
    MaterialEditText surnameEdittext;

    @BindView(R.id.profile_gender_button)
    Button genderButton;

    @BindView(R.id.profile_phone_button)
    Button phoneButton;
    @BindView(R.id.profile_phone_edittext)
    MaterialEditText phoneEdittext;

    @BindView(R.id.profile_save_button)
    Button saveButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        genders = getResources().getStringArray(R.array.gender_array);
        return view;
    }

    private Gender getGenderFromString(String textGender) {
        if (textGender.equals(Gender.FEMALE.getGenderText())) {
            return Gender.FEMALE;
        } else if (textGender.equals(Gender.MALE.getGenderText())) {
            return  Gender.MALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

    public void fillUser(UserResponse user) {
        this.user = user;
        nameButton.setText(Util.underline(user.name));
        nameEdittext.setText(user.name);
        surnameButton.setText(Util.underline(user.surname));
        surnameEdittext.setText(user.surname);
        currentGender =  getGenderFromString(user.gender).ordinal();
        genderButton.setText(Util.underline(genders[currentGender]));
        phoneButton.setText(Util.underline(user.phone));
        phoneEdittext.setText(user.phone);
    }

    private void toggleButtonEdittext(Button button, MaterialEditText editText, boolean editVisible) {
        if (!editVisible) {
            button.setText(Util.underline(editText.getText().toString()));
        }
        editText.setVisibility(editVisible ? View.VISIBLE : View.GONE);
        button.setVisibility(editVisible ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.profile_name_button)
    void editName() {
        toggleButtonEdittext(surnameButton, surnameEdittext, false);
        toggleButtonEdittext(phoneButton, phoneEdittext, false);
        toggleButtonEdittext(nameButton, nameEdittext, true);
    }

    @OnClick(R.id.profile_surname_button)
    void editSurname() {
        toggleButtonEdittext(surnameButton, surnameEdittext, true);
        toggleButtonEdittext(phoneButton, phoneEdittext, false);
        toggleButtonEdittext(nameButton, nameEdittext, false);
    }

    @OnClick(R.id.profile_phone_button)
    void editPhone() {
        toggleButtonEdittext(phoneButton, phoneEdittext, true);
        toggleButtonEdittext(surnameButton, surnameEdittext, false);
        toggleButtonEdittext(nameButton, nameEdittext, false);
    }

    @OnClick(R.id.profile_gender_button)
    void chooseGender() {
        new MaterialDialog.Builder(getActivity())
                .items(R.array.gender_array)
                .itemsColorRes(R.color.colorPrimaryDark)
                .itemsCallbackSingleChoice(currentGender, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        currentGender = which;
                        genderButton.setText(Util.underline(genders[currentGender]));
                        dialog.dismiss();
                        return true;
                    }
                })
                .keyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        if (i == KeyEvent.KEYCODE_BACK) {
                            dialogInterface.dismiss();
                        }
                        return true;
                    }
                })
                .widgetColorRes(R.color.colorAccent)
                .typeface("DidactGothic.ttf", "DidactGothic.ttf")
                .show();
    }

    @OnClick(R.id.profile_save_button)
    void saveProfile() {
        user.name = nameEdittext.getText().toString();
        user.surname = surnameEdittext.getText().toString();
        user.gender = Gender.values()[currentGender].getGenderText();
        user.phone = phoneEdittext.getText().toString();
        ((ProfileActivity)getActivity()).saveProfile(user);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
