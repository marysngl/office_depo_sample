package officedepo.mediapark.com.officedepo.ui.Profile;

import officedepo.mediapark.com.officedepo.Base.MvpView;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import officedepo.mediapark.com.officedepo.Model.Items.UserResponse;

/**
 * Created by Mary Songal on 11.11.2016.
 */

public interface ProfileMvpView extends MvpView {

    void showError();

    void showUserError();

    void showSuccess();

    void fillProfile(UserResponse user);

}
