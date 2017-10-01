package officedepo.mediapark.com.officedepo.ui.Main;

import java.util.List;

import officedepo.mediapark.com.officedepo.Base.MvpView;
import officedepo.mediapark.com.officedepo.Model.Items.DealResponse;
import officedepo.mediapark.com.officedepo.Model.Items.HotDeal;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import officedepo.mediapark.com.officedepo.Model.Items.UserResponse;

/**
 * Created by Mary Songal on 08.11.2016.
 */

public interface MainMvpView extends MvpView {

    void showError();

    void showHotDealsError();

    void setupUser(UserResponse user);

    void displayHotDeals(List<DealResponse> deals);

}
