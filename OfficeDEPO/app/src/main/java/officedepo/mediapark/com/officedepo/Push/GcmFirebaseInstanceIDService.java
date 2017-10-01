package officedepo.mediapark.com.officedepo.Push;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Mary Songal on 16.01.2017.
 */

public class GcmFirebaseInstanceIDService extends com.google.firebase.iid.FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        saveToken(refreshedToken);
        Intent registrationComplete = new Intent(GcmConfig.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void saveToken(String token) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(GcmConfig.FIREBASE_SHARED_PREF, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(GcmConfig.FIREBASE_SHARED_PREF_FIELD, token);
        editor.commit();
    }

}
