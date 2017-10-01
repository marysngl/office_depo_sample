package officedepo.mediapark.com.officedepo.Model;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import officedepo.mediapark.com.officedepo.Local.DatabaseHelper;
import officedepo.mediapark.com.officedepo.Model.API.ApiService;
import officedepo.mediapark.com.officedepo.Model.Items.DealResponse;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryResponse;
import officedepo.mediapark.com.officedepo.Model.Items.RegisterResponse;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import officedepo.mediapark.com.officedepo.Model.Items.UserLoginResponse;
import officedepo.mediapark.com.officedepo.Model.Items.UserResponse;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by Mary Songal on 03.11.2016.
 */

@Singleton
public class DataManager {

    private static final String TAG = DataManager.class.getSimpleName();
    private final DatabaseHelper databaseHelper;
    private final PreferenceHelper preferenceHelper;
    private final ApiService apiService;

    @Inject
    public DataManager(DatabaseHelper databaseHelper, PreferenceHelper preferenceHelper, ApiService apiService) {
        this.databaseHelper = databaseHelper;
        this.preferenceHelper = preferenceHelper;
        this.apiService = apiService;
    }

    public Observable<RegisterResponse> addUser(User user, String deviceId) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fname", user.name);
            jsonObject.put("lname", user.surname);
            jsonObject.put("phone", user.phone);
            jsonObject.put("password", "111111");
            jsonObject.put("device_id", deviceId);
            return apiService.register(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Observable<UserLoginResponse> checkUser(User user, String deviceId) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", user.phone);
            jsonObject.put("password", user.password);
            jsonObject.put("device_id", deviceId);
            return apiService.signIn(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Observable<UserResponse> getUser(String phone) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            return apiService.getUser(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Observable<List<DealResponse>> getHotDeals() {
        JSONObject jsonObject = new JSONObject();
        return apiService.getHotDeals(jsonObject);
    }

    public Observable<List<DealResponse>> getDeals() {
        JSONObject jsonObject = new JSONObject();
        return apiService.getDeals(jsonObject);
    }

    public Observable<List<HistoryResponse>> getHistory(String phone) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            return apiService.getHistory(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Observable<ResponseBody> updateProfile(UserResponse updatedUser, String password) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", updatedUser.phone);
            jsonObject.put("fname", updatedUser.name);
            jsonObject.put("lname", updatedUser.surname);
            jsonObject.put("gender", updatedUser.gender);
            jsonObject.put("password", password);
            return apiService.updateUser(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Observable<ResponseBody> sendConfirmationCode(String phone) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            return apiService.sendConfirmationCode(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Observable<RegisterResponse> checkConfirmationCode(String phone, String code, String password) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            jsonObject.put("code", code);
            jsonObject.put("password", password);
            return apiService.checkConfirmationCode(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Observable<ResponseBody> sendPasswordConfirmationCode(String phone) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            return apiService.sendPasswordConfirmationCode(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Observable<UserLoginResponse> updatePasswordViaCode(String phone, String code, String password) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            jsonObject.put("code", code);
            jsonObject.put("password", password);
            return apiService.updatePasswordViaCode(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Observable<UserLoginResponse> updatePassword(String phone, String password) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", phone);
            jsonObject.put("password", password);
            return apiService.updatePassword(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    public Observable<Integer> updatePassword(String password, String updatedPassword, String userId) {
        return databaseHelper.updatePassword(password, updatedPassword, userId);
    }

    public Observable<Integer> updateUserRegisteredEvent(String userId) {
        return databaseHelper.updateUserRegisteredEvent(userId);
    }
    */

    /*
    public Observable<List<HistoryItem>> getHistory(String userId) {
        return databaseHelper.getHistory(userId);
    }
    */

    /*
    public Observable<Integer> getTotalSpent(String userId) {
        return databaseHelper.getTotalSpent(userId);
    }

    public Observable<Integer> getTotalBonus(String userId) {
        return databaseHelper.getTotalBonus(userId);
    }
    */

    /*
    public Observable<String> sendConfirmationCode() {
        return Observable.create(subscriber -> {
            preferenceHelper.putConfirmationCode();
            subscriber.onNext(null);
            subscriber.onCompleted();
        });
    }

    public Observable<Boolean> checkConfirmationCode(String code) {
        return Observable.create(subscriber -> {
            String confirmationCode = preferenceHelper.getConfirmationCode();
            subscriber.onNext(confirmationCode.equals(code));
            subscriber.onCompleted();
        });
    }
    */

    public Observable<Bitmap> generateQrCode(String codeWord) {
        return Observable.create(subscriber -> {
            try {
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix bitMatrix = writer.encode(codeWord, BarcodeFormat.QR_CODE, 512, 512);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
                }
                subscriber.onNext(bmp);
                subscriber.onCompleted();
            } catch (WriterException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        });
    }

}
