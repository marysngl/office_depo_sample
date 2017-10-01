package officedepo.mediapark.com.officedepo.Model.Items;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Mary Songal on 28.12.2016.
 */

@Parcel
public class UserLoginResponse {

    UserLoginResponse() {
    }

    @SerializedName("userId")
    String userId;

    @SerializedName("sessId")
    String sessionId;

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "userId=" + userId +
                ", sessId=" + sessionId +
                '}';
    }

}
