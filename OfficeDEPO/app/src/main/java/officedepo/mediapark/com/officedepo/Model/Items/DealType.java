package officedepo.mediapark.com.officedepo.Model.Items;

/**
 * Created by Mary Songal on 10.11.2016.
 */

public enum DealType {

    NEW(2),
    PERCENT_DISCOUNT(3),
    PRICE_DROP(1);

    private final int typeId;
    DealType(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

}
