package officedepo.mediapark.com.officedepo.ui.Splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import officedepo.mediapark.com.officedepo.Model.PreferenceHelper;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.Util.Util;
import officedepo.mediapark.com.officedepo.ui.Login.LoginActivity;
import officedepo.mediapark.com.officedepo.ui.Main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceHelper preferenceHelper = new PreferenceHelper(this);
        boolean userLoggedIn = preferenceHelper.isUserLoggedIn();
        Intent intent = new Intent(this, userLoggedIn ? MainActivity.class : LoginActivity.class);
        if (userLoggedIn) {
            intent.putExtra(MainActivity.EXTRA_USER_PHONE, preferenceHelper.getUserId());
        }
        if (!Util.isOnline()) {
            Toast.makeText(getApplicationContext(), R.string.no_connection_error, Toast.LENGTH_LONG).show();
        }
        startActivity(intent);
        finish();
    }
    
}
