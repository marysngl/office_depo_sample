package officedepo.mediapark.com.officedepo.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.text.Spanned;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;

import officedepo.mediapark.com.officedepo.Base.BasePhoneNumberClickListener;
import officedepo.mediapark.com.officedepo.Base.BasePhoneNumberTouchListener;
import officedepo.mediapark.com.officedepo.Base.BasePhoneNumberWatcher;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.ui.App;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public class Util {

    public static final int MIN_PASSWORD_CHARACTERS = 6;
    private static final int NUMBER_OF_PHONE_DIGITS = 11;

    public static boolean validatePhone(String phone) {
        return phone != null && phone.replaceAll("[^0-9]", "").length() == NUMBER_OF_PHONE_DIGITS;
    }

    public static boolean validatePassword(String password) {
        return password.length() >= MIN_PASSWORD_CHARACTERS;
    }

    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public static byte[] getImageFromResources(int resourceId) {
        return getBitmapAsByteArray(BitmapFactory.decodeResource(App.getAppContext().getResources(), resourceId));
    }

    public static Spanned underline(String text) {
        return Html.fromHtml("<u>" + text + "</u>");
    }

    public static String getFormattedDouble(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(number);
    }

    public static void makeEdittextPhoneTemplate(EditText editText) {
        editText.setText(App.getAppContext().getString(R.string.phone_template));
        editText.setSelection(4);
        editText.setOnClickListener(new BasePhoneNumberClickListener(editText));
        editText.addTextChangedListener(new BasePhoneNumberWatcher(editText));
        editText.setOnTouchListener(new BasePhoneNumberTouchListener(editText));
    }

}
