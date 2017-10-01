package officedepo.mediapark.com.officedepo.Injection;

import dagger.Component;
import officedepo.mediapark.com.officedepo.ui.Deals.DealsActivity;
import officedepo.mediapark.com.officedepo.ui.History.HistoryActivity;
import officedepo.mediapark.com.officedepo.ui.Login.LoginActivity;
import officedepo.mediapark.com.officedepo.ui.Main.MainActivity;
import officedepo.mediapark.com.officedepo.ui.Password.PasswordActivity;
import officedepo.mediapark.com.officedepo.ui.Profile.ProfileActivity;
import officedepo.mediapark.com.officedepo.ui.QR.QRCodeActivity;
import officedepo.mediapark.com.officedepo.ui.Register.RegisterActivity;
import officedepo.mediapark.com.officedepo.ui.Settings.SettingsActivity;

/**
 * Created by Mary Songal on 03.11.2016.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(RegisterActivity registerActivity);

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(DealsActivity dealsActivity);

    void inject(HistoryActivity historyActivity);

    void inject(SettingsActivity settingsActivity);

    void inject(ProfileActivity profileActivity);

    void inject(PasswordActivity passwordActivity);

    void inject(QRCodeActivity qrCodeActivity);

}
