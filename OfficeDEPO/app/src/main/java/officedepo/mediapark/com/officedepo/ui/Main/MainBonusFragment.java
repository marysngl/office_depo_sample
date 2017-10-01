package officedepo.mediapark.com.officedepo.ui.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.R;

/**
 * Created by Mary Songal on 08.11.2016.
 */

public class MainBonusFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.main_bonus_textview)
    TextView bonusTextview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_bonus, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setBonus(int bonus) {
        bonusTextview.setText(String.valueOf(bonus));
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
