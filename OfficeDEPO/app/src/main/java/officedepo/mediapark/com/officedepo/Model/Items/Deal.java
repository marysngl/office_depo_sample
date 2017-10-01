package officedepo.mediapark.com.officedepo.Model.Items;

import android.graphics.Bitmap;

import org.parceler.Parcel;

/**
 * Created by Mary Songal on 10.11.2016.
 */

@Parcel
public class Deal {

    public int id;
    public Bitmap image;
    public String description;
    public int price;
    public DealType dealType;

    public int percentDiscount;
    public int oldPrice;

}
