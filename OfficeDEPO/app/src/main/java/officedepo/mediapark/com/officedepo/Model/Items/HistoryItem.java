package officedepo.mediapark.com.officedepo.Model.Items;

import org.joda.time.DateTime;
import org.parceler.Parcel;

/**
 * Created by Mary Songal on 10.11.2016.
 */

@Parcel
public class HistoryItem {

    public int id;
    public DateTime bonusDate;
    public String description;
    public int price;
    public int bonus;
    public boolean viewed;

}
