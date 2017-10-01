package officedepo.mediapark.com.officedepo.Model.Items;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Mary Songal on 30.11.2016.
 */


@Parcel
public class RegisterResponse {

    RegisterResponse() {
    }

    @SerializedName("userId")
    public String userId;

    @SerializedName("sessId")
    public String sessionId;

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "userId=" + userId +
                ", sessId=" + sessionId +
                '}';
    }

}
