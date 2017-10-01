package officedepo.mediapark.com.officedepo.Model.Items;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Mary Songal on 20.12.2016.
 */

@Parcel
public class HistoryResponse {

    HistoryResponse() {
    }

    @SerializedName("NAME")
    public String item;

    @SerializedName("DATE")
    public String date;

    @SerializedName("BUY_SUM")
    public String spent;

    @SerializedName("LOYALITY")
    public String bonus;

    @Override
    public String toString() {
        return "HistoryResponse{" +
                "NAME=" + item +
                ", DATE=" + date +
                ", BUY_SUM=" + spent +
                ", LOYALITY=" + bonus +
                '}';
    }

}
