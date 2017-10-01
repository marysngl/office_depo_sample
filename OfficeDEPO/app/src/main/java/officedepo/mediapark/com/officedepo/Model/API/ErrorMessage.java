package officedepo.mediapark.com.officedepo.Model.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mary Songal on 05.12.2016.
 */

public class ErrorMessage  {

    @SerializedName("error")
    public String errorMessage;

    @SerializedName("errorno")
    public int errorNumber;

}
