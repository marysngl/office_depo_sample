package officedepo.mediapark.com.officedepo.ui.Register;

import officedepo.mediapark.com.officedepo.Base.MvpView;

/**
 * Created by Mary Songal on 03.11.2016.
 */

public interface RegisterMvpView extends MvpView {

    void showError();

    void showIncorrectCodeError();

    void showRegisterSuccess(String phone);

    void showPhoneConfirm();

    void register();

}
