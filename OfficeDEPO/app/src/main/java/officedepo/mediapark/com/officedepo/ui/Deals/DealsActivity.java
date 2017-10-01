package officedepo.mediapark.com.officedepo.ui.Deals;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import officedepo.mediapark.com.officedepo.Base.BaseFragmentActivity;
import officedepo.mediapark.com.officedepo.Model.Items.Deal;
import officedepo.mediapark.com.officedepo.Model.Items.DealResponse;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.Toolbar.ToolbarFragment;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class DealsActivity extends BaseFragmentActivity implements DealsMvpView {

    private static final String TOOLBAR_FRAGMENT = "toolbar_fragment";
    private static final String DEALS_FRAGMENT = "deals_fragment";

    @Inject
    DealsPresenter dealsPresenter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        dealsPresenter.attachView(this);

        ToolbarFragment toolbarFragment = new ToolbarFragment();
        DealsFragment dealsFragment = new DealsFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.deals_toolbar_container, toolbarFragment, TOOLBAR_FRAGMENT);
        fragmentTransaction.add(R.id.deals_container, dealsFragment, DEALS_FRAGMENT);
        fragmentTransaction.commit();

        toolbarFragment.setTitle(getString(R.string.deals_title));
        progressDialog = ProgressDialog.show(this, "", getString(R.string.standard_progress_dialog_message), true);
        dealsPresenter.getDeals();
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dealsPresenter.detachView();
    }

    @Override
    public void showError() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        Toast.makeText(this, R.string.deals_retrieve_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setupDeals(List<DealResponse> deals) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (getFragmentManager().findFragmentById(R.id.deals_container) != null) {
            ((DealsFragment)getFragmentManager().findFragmentById(R.id.deals_container)).setDeals(deals);
        }
    }
}
