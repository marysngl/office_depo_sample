package officedepo.mediapark.com.officedepo.ui.Deals;

import java.util.List;

import officedepo.mediapark.com.officedepo.Base.MvpView;
import officedepo.mediapark.com.officedepo.Model.Items.Deal;
import officedepo.mediapark.com.officedepo.Model.Items.DealResponse;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public interface DealsMvpView extends MvpView {

    void showError();

    void setupDeals(List<DealResponse> deals);

}
