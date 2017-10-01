package officedepo.mediapark.com.officedepo.Model.Items;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Mary Songal on 12.12.2016.
 */


@Parcel
public class DealResponse {

    DealResponse() {
    }

    @SerializedName("ID")
    public String id;

    @SerializedName("NAME")
    public String name;

    @SerializedName("PREVIEW_PICTURE")
    public String pictureUrl;

    @SerializedName("ACTION_TYPE_ID")
    public String dealType;

    @SerializedName("PREVIEW_TEXT")
    public String previewText;

    @SerializedName("DETAIL_TEXT")
    public String detailText;

    @SerializedName("IS_HOT_ACTION")
    public int hotDealFlag;

    @SerializedName("PRICE")
    public String price;

    @SerializedName("OLD_PRICE")
    public String oldPrice;

    @SerializedName("PERCENT")
    public String percent;

    @SerializedName("DOP_PARAM")
    public String promoText;

    public boolean isHotDeal() {
        return hotDealFlag == 1;
    }

    @Override
    public String toString() {
        return "DealResponse{" +
                "ID=" + id +
                ", NAME=" + name +
                ", PREVIEW_PICTURE=" + pictureUrl +
                ", ACTION_TYPE_ID=" + dealType +
                ", DOP_PARAM=" + promoText +
                ", PREVIEW_TEXT=" + previewText +
                ", DETAIL_TEXT=" + detailText +
                ", IS_HOT_ACTION=" + hotDealFlag +
                ", PRICE=" + price +
                ", OLD_PRICE=" + oldPrice +
                ", PERCENT=" + percent +
                '}';
    }

}
