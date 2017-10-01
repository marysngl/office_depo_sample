package officedepo.mediapark.com.officedepo.Model.API;

import org.json.JSONObject;

import java.util.List;

import officedepo.mediapark.com.officedepo.Model.Items.DealResponse;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryResponse;
import officedepo.mediapark.com.officedepo.Model.Items.RegisterResponse;
import officedepo.mediapark.com.officedepo.Model.Items.UserLoginResponse;
import officedepo.mediapark.com.officedepo.Model.Items.UserResponse;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Mary Songal on 29.11.2016.
 */

public interface ApiService   {

    @POST("/api/1.0/")
    @FormUrlEncoded
    Observable<RegisterResponse> register(@Field("userHandler.Reg") JSONObject json);

    @POST("/api/1.0/")
    @FormUrlEncoded
    Observable<ResponseBody> sendConfirmationCode(@Field("userHandler.ActivateAccount") JSONObject json);

    @POST("/api/1.0/")
    @FormUrlEncoded
    Observable<RegisterResponse> checkConfirmationCode(@Field("userHandler.ActivateAccount") JSONObject json);

    @POST("/api/1.0/")
    @FormUrlEncoded
    Observable<ResponseBody> sendPasswordConfirmationCode(@Field("userHandler.SendPassword") JSONObject json);

    @POST("/api/1.0/")
    @FormUrlEncoded
    Observable<UserLoginResponse> signIn(@Field("userHandler.Auth") JSONObject json);

    @POST("/api/1.0/")
    @FormUrlEncoded
    Observable<List<DealResponse>> getDeals(@Field("actionhandler.getAction") JSONObject json);

    @POST("/api/1.0/")
    @FormUrlEncoded
    Observable<List<DealResponse>> getHotDeals(@Field("actionhandler.getHotAction") JSONObject json);

    @POST("/api/1.0/")
    @FormUrlEncoded
    Observable<UserResponse> getUser(@Field("userHandler.GetUser") JSONObject json);

    @POST("/api/1.0/")
    @FormUrlEncoded
    Observable<ResponseBody> updateUser(@Field("userHandler.Change") JSONObject json);

    @POST("/api/1.0/")
    @FormUrlEncoded
    Observable<List<HistoryResponse>> getHistory(@Field("userHandler.GetUserHystory") JSONObject json);

    @POST("/api/1.0/")
    @FormUrlEncoded
    Observable<UserLoginResponse> updatePasswordViaCode(@Field("userHandler.ActivateAccount") JSONObject json);

    @POST("/api/1.0/")
    @FormUrlEncoded
    Observable<UserLoginResponse> updatePassword(@Field("userHandler.Change") JSONObject json);

}
