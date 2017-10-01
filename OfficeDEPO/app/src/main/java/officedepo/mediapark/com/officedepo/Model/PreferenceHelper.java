package officedepo.mediapark.com.officedepo.Model;

import android.content.Context;
import android.content.SharedPreferences;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.inject.Inject;
import javax.inject.Singleton;

import officedepo.mediapark.com.officedepo.Injection.ApplicationContext;

/**
 * Created by Mary Songal on 06.11.2016.
 */

@Singleton
@SuppressWarnings("all")
public class PreferenceHelper {

    public static final String PREF_FILE_NAME = "office_depo_preferences";

    private static final String KEY_CONFIRMATION_CODE = "confirmation_code";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_PASSWORD = "user_password";
    private static final String KEY_USER_LOGGED_IN = "is_user_logged_in";

    private final SharedPreferences preferences;

    @Inject
    public PreferenceHelper(@ApplicationContext Context context) {
        preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    // TODO: конечно, это работа сервера
    private String generateConfirmationCode() {
        return "office111";
    }

    public void putConfirmationCode() {
        preferences.edit()
                .putString(KEY_CONFIRMATION_CODE, generateConfirmationCode())
                .commit();
    }

    public void clearConfirmationCode() {
        preferences.edit()
                .putString(KEY_CONFIRMATION_CODE, null)
                .commit();
    }

    public String getConfirmationCode() {
        return preferences.getString(KEY_CONFIRMATION_CODE, null);
    }

    public void putUserId(String userId) {
        preferences.edit()
                .putString(KEY_USER_ID, userId)
                .commit();
    }

    public String getUserId() {
        return preferences.getString(KEY_USER_ID, null);
    }

    public void putPassword(String password) {
        preferences.edit()
                .putString(KEY_PASSWORD, password)
                .commit();
    }

    public String getPassword() {
        return preferences.getString(KEY_PASSWORD, "");
    }

    public void putLoggedIn(boolean loggedIn) {
        preferences.edit()
                .putBoolean(KEY_USER_LOGGED_IN, loggedIn)
                .commit();
    }

    public boolean isUserLoggedIn() {
        return preferences.getBoolean(KEY_USER_LOGGED_IN, false);
    }

}
