package officedepo.mediapark.com.officedepo.Model.Items;

import org.joda.time.DateTime;
import org.parceler.Parcel;

import officedepo.mediapark.com.officedepo.Model.Items.Gender;

/**
 * Created by Mary Songal on 02.11.2016.
 */

@Parcel
public class User {

    public User() {}
    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String phone;
    public String password;
    public String name;
    public String surname;
    public int bonus;
    public DateTime expirationDate;
    public DateTime registrationDate;
    public Gender gender;
    public String qrCode;
    public boolean userRegisteredEvent;

    public String userId;
    public String sessionId;

}
