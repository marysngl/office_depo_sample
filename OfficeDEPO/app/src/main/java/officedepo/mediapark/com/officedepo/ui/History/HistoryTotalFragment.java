package officedepo.mediapark.com.officedepo.ui.History;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.Util.Util;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class HistoryTotalFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.history_period_textview)
    TextView periodTextview;
    @BindView(R.id.history_total_cash)
    TextView totalCashTextview;
    @BindView(R.id.history_total_bonus)
    TextView totalBonusTextview;

    // Кто знает, может, клиент когда отрезвеет
    //private DateTime registrationDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_sum, container, false);
        unbinder = ButterKnife.bind(this, view);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy");
        DateTime dateTo = DateTime.now();
        DateTime dateFrom = new DateTime(dateTo.getYear(), dateTo.getMonthOfYear(), 1, 0, 0);
        periodTextview.setText(String.format(getString(R.string.history_period),
                dateTimeFormatter.print(dateFrom), dateTimeFormatter.print(dateTo)));
        return view;
    }

    /*
    public void setRegistrationDate(DateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
    */

    public void setTotalCash(double totalCash) {
        totalCashTextview.setText(Util.getFormattedDouble(totalCash));
    }

    public void setTotalBonus(double totalBonus) {
        totalBonusTextview.setText(Util.getFormattedDouble(totalBonus));
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

}
