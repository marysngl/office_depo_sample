package officedepo.mediapark.com.officedepo.ui.Register;


import android.support.v7.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.StyledAlertProducer;

/**
 * Created by Mary Songal on 02.11.2016.
 */

public class RegisterResendFragment extends Fragment {

    private Unbinder unbinder;

    private static final float HDPI_DENSITY = 1.6f;

    @BindView(R.id.register_resend_instructions_textview)
    TextView resendTextview;
    @BindView(R.id.register_resend_button)
    Button resendButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resend, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.register_resend_button)
    public void resendCode() {
        ((RegisterActivity)getActivity()).sendConfirmationCode();
    }

    public void showResendView() {
        resendButton.setVisibility(View.VISIBLE);
        resendButton.setAlpha(0.0f);
        resendButton.animate()
                .alpha(1.0f)
                .setDuration(1200)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Ещё немного извращений
                        if (getResources().getDisplayMetrics().density < HDPI_DENSITY) {
                            StyledAlertProducer.showDismissiveStyledAlert(getActivity(), R.string.popup_standard_title,
                                    getString(R.string.register_resend_instruction_hdpi), android.R.string.ok);
                        }
                    }
                });
        // Поскольку нужно поддерживать 480 на 800, нужно изврашаться
        if (getResources().getDisplayMetrics().density > HDPI_DENSITY) {
            resendTextview.setVisibility(View.VISIBLE);
            resendTextview.setAlpha(0.0f);
            resendTextview.animate()
                    .alpha(1.0f)
                    .setDuration(1200);
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
