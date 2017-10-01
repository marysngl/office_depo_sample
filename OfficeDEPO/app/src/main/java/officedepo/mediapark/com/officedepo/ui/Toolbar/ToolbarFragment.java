package officedepo.mediapark.com.officedepo.ui.Toolbar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.R;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class ToolbarFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.toolbar_title_textview)
    TextView titleTextView;
    @BindView(R.id.toolbar_back_button)
    ImageView backButton;

    String title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toolbar, container, false);
        unbinder = ButterKnife.bind(this, view);
        titleTextView.setText(title);
        return view;
    }

    @OnClick(R.id.toolbar_back_button)
    void finishActivity() {
        getActivity().finish();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
