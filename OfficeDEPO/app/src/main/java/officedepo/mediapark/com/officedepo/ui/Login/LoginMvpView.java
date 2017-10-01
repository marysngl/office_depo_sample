package officedepo.mediapark.com.officedepo.ui.Login;

import officedepo.mediapark.com.officedepo.Base.MvpView;

/**
 * Created by Mary Songal on 03.11.2016.
 */

public interface LoginMvpView extends MvpView {

    void showError();

    void onSignIn(String phone);

    void showChangePassword();

}
