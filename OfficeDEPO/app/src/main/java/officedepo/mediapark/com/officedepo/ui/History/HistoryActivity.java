package officedepo.mediapark.com.officedepo.ui.History;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import officedepo.mediapark.com.officedepo.Base.BaseFragmentActivity;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryItem;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryResponse;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.Main.MainActivity;
import officedepo.mediapark.com.officedepo.ui.Toolbar.ToolbarFragment;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class HistoryActivity extends BaseFragmentActivity implements HistoryMvpView {

    private static final String TOOLBAR_FRAGMENT = "toolbar_fragment";
    private static final String HISTORY_FRAGMENT = "history_fragment";
    private static final String HISTORY_TOTAL_FRAGMENT = "history_total_fragment";

    @Inject
    HistoryPresenter historyPresenter;

    private ProgressDialog progressDialog;
    boolean receivedHistory = false;
    boolean receivedTotalSpent = false;
    boolean receivedTotalBonus = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        historyPresenter.attachView(this);

        ToolbarFragment toolbarFragment = new ToolbarFragment();
        HistoryFragment historyFragment = new HistoryFragment();
        HistoryTotalFragment historyTotalFragment = new HistoryTotalFragment();

        DateTime registrationDate = getIntent().getParcelableExtra(MainActivity.EXTRA_REGISTRATION_DATE);
        //historyTotalFragment.setRegistrationDate(registrationDate);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.history_toolbar_container, toolbarFragment, TOOLBAR_FRAGMENT);
        fragmentTransaction.add(R.id.history_container, historyFragment, HISTORY_FRAGMENT);
        fragmentTransaction.add(R.id.history_sum_container, historyTotalFragment, HISTORY_TOTAL_FRAGMENT);
        fragmentTransaction.commit();

        toolbarFragment.setTitle(getString(R.string.history_title));
        String userId = getIntent().getStringExtra(MainActivity.EXTRA_USER_PHONE);
        progressDialog = ProgressDialog.show(this, "", getString(R.string.standard_progress_dialog_message), true);
        historyPresenter.getHistory(userId);
        //historyPresenter.getTotalSpent(userId);
        //historyPresenter.getTotalBonus(userId);
    }

    private boolean finishedLoading() {
        return true;//receivedHistory && receivedTotalBonus && receivedTotalSpent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        historyPresenter.detachView();
    }

    @Override
    public void showError() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(this, R.string.history_retrieve_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setupHistory(List<HistoryResponse> historyItems) {
        receivedHistory = true;
        if (finishedLoading() && progressDialog != null) {
            progressDialog.dismiss();
        }
        if (getFragmentManager().findFragmentById(R.id.history_container) != null) {
            ((HistoryFragment)getFragmentManager().findFragmentById(R.id.history_container)).setHistoryItems(historyItems);
        }
        if (getFragmentManager().findFragmentById(R.id.history_sum_container) != null) {
            double totalCash = 0;
            double totalBonus = 0;
            for (HistoryResponse historyResponse : historyItems) {
                String bonus = historyResponse.bonus.replace(",", ".");
                String cash = historyResponse.spent.replace(",", ".");
                bonus = bonus.replaceAll("[^(0-9.)]", "");
                cash = cash.replaceAll("[^(0-9.)]", "");
                totalBonus += Double.valueOf(bonus);
                totalCash += Double.valueOf(cash);
            }
            ((HistoryTotalFragment)getFragmentManager().findFragmentById(R.id.history_sum_container)).setTotalBonus(totalBonus);
            ((HistoryTotalFragment)getFragmentManager().findFragmentById(R.id.history_sum_container)).setTotalCash(totalCash);
        }
    }

    @Override
    public void setupTotalSpent(int totalSpent) {
        receivedTotalSpent = true;
        if (finishedLoading() && progressDialog != null) {
            progressDialog.dismiss();
        }
        if (getFragmentManager().findFragmentById(R.id.history_sum_container) != null) {
            ((HistoryTotalFragment)getFragmentManager().findFragmentById(R.id.history_sum_container)).setTotalCash(totalSpent);
        }
    }

    @Override
    public void setupTotalBonus(int totalBonus) {
        receivedTotalBonus = true;
        if (finishedLoading() && progressDialog != null) {
            progressDialog.dismiss();
        }
        if (getFragmentManager().findFragmentById(R.id.history_sum_container) != null) {
            ((HistoryTotalFragment)getFragmentManager().findFragmentById(R.id.history_sum_container)).setTotalBonus(totalBonus);
        }
    }
}
