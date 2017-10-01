package officedepo.mediapark.com.officedepo.Model.Items;

/**
 * Created by Mary Songal on 11.11.2016.
 */

public enum Gender {

    UNKNOWN(""),
    MALE("M"),
    FEMALE("F");

    private final String gender;
    Gender(String gender) {
        this.gender = gender;
    }

    public String getGenderText() {
        return gender;
    }

}
