package officedepo.mediapark.com.officedepo.ui.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.Model.Items.DealResponse;
import officedepo.mediapark.com.officedepo.Model.Items.HotDeal;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.Settings.SettingsActivity;

/**
 * Created by Mary Songal on 08.11.2016.
 */

public class MainInfoFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.main_name_surname_textview)
    TextView nameSurnameTextview;

    @BindView(R.id.main_banner_puncture)
    ImageView bannerPunctureLine;
    @BindView(R.id.main_banner_layout)
    LinearLayout bannerLayout;
    @BindView(R.id.main_banner_part_1_textview)
    TextView bannerTextviewPart1;
    @BindView(R.id.main_banner_part_2_textview)
    TextView bannerTextviewPart2;

    @BindView(R.id.main_settings_button)
    ImageView settingsButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.main_settings_button)
    void showSettings() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }

    public void setNameSurname(String name, String surname) {
        nameSurnameTextview.setText(name + "\n" + surname);
    }

    public void toggleBannerVisibility(boolean visible) {
        bannerPunctureLine.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        bannerLayout.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    public void showDeal(DealResponse hotDeal) {
        toggleBannerVisibility(true);
        bannerTextviewPart1.setText(Html.fromHtml(hotDeal.previewText));
        bannerTextviewPart2.setText(Html.fromHtml(hotDeal.detailText));
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
