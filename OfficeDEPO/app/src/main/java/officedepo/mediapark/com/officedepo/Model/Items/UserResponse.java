package officedepo.mediapark.com.officedepo.Model.Items;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Mary Songal on 13.12.2016.
 */


@Parcel
public class UserResponse {

    UserResponse() {
    }

    @SerializedName("userId")
    public String id;

    @SerializedName("userFName")
    public String name;

    @SerializedName("userLName")
    public String surname;

    @SerializedName("userPhone")
    public String phone;

    @SerializedName("userGander")
    public String gender;

    @SerializedName("userBuySumm")
    public String totalSpent;

    @SerializedName("userLoyalitySumm")
    public String bonus;

    @SerializedName("userIsActive")
    public String userActivatedAccount;

    boolean hasUserActivatedAccount() {
        return userActivatedAccount.equals("Y");
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "userId=" + id +
                ", userFName=" + name +
                ", userLName=" + surname +
                ", userPhone=" + phone +
                ", userGander=" + gender +
                ", userBuySumm=" + totalSpent +
                ", userLoyalitySumm=" + bonus +
                ", userIsActive=" + userActivatedAccount +
                '}';
    }

}
