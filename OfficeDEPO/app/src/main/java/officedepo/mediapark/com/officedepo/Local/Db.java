package officedepo.mediapark.com.officedepo.Local;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.BitmapFactory;

import org.joda.time.DateTime;

import officedepo.mediapark.com.officedepo.Model.Items.Deal;
import officedepo.mediapark.com.officedepo.Model.Items.Gender;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryItem;
import officedepo.mediapark.com.officedepo.Model.Items.HotDeal;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import officedepo.mediapark.com.officedepo.Model.Items.DealType;

/**
 * Created by Mary Songal on 02.11.2016.
 */

public class Db {

    public Db() {}

    // TODO: это мокап, конечно, пароли не будут тут храниться :)
    public static abstract class UserTable {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SURNAME = "surname";
        public static final String COLUMN_BONUS = "bonus";
        public static final String COLUMN_EXPIRATION_DATE = "expiration_date";
        public static final String COLUMN_REGISTRATION_DATE = "registration_date";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_QR_CODE = "qr_code";
        public static final String COLUMN_REGISTERED_EVENT = "registered_event";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_PHONE + " TEXT PRIMARY KEY, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_SURNAME + " TEXT, " +
                        COLUMN_BONUS + " INTEGER, " +
                        COLUMN_EXPIRATION_DATE + " INTEGER, " +
                        COLUMN_REGISTRATION_DATE + " INTEGER, " +
                        COLUMN_GENDER + " INTEGER, " +
                        COLUMN_QR_CODE + " TEXT, " +
                        COLUMN_REGISTERED_EVENT + " INTEGER, " +
                        COLUMN_PASSWORD + " TEXT " +
                        " ); ";

        public static User parseCursor(Cursor cursor) {
            User user = new User();
            user.phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
            user.password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            user.name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            user.surname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURNAME));
            user.bonus = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BONUS));
            user.expirationDate = new DateTime(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_EXPIRATION_DATE)));
            user.registrationDate = new DateTime(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_REGISTRATION_DATE)));;
            user.gender = Gender.values()[cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GENDER))];
            user.qrCode = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QR_CODE));
            user.userRegisteredEvent = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REGISTERED_EVENT)) == 1;
            return user;
        }

        public static ContentValues toContentValues(User user) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PHONE, user.phone);
            contentValues.put(COLUMN_PASSWORD, user.password);
            contentValues.put(COLUMN_NAME, user.name);
            contentValues.put(COLUMN_SURNAME, user.surname);
            contentValues.put(COLUMN_BONUS, user.bonus);
            contentValues.put(COLUMN_EXPIRATION_DATE, user.expirationDate.getMillis());
            contentValues.put(COLUMN_REGISTRATION_DATE, user.registrationDate.getMillis());
            contentValues.put(COLUMN_GENDER, user.gender.ordinal());
            contentValues.put(COLUMN_QR_CODE, user.qrCode);
            contentValues.put(COLUMN_REGISTERED_EVENT, user.userRegisteredEvent ? 1 : 0);
            return contentValues;
        }

        public static ContentValues toProfileContentValues(User user) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PHONE, user.phone);
            contentValues.put(COLUMN_NAME, user.name);
            contentValues.put(COLUMN_SURNAME, user.surname);
            contentValues.put(COLUMN_GENDER, user.gender.ordinal());
            return contentValues;
        }

        public static ContentValues toPasswordContentValues(String updatedPassword) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PASSWORD, updatedPassword);
            return contentValues;
        }

        public static ContentValues toRegisteredEventContentValues(boolean registered) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_REGISTERED_EVENT, registered ? 1 : 0);
            return contentValues;
        }
    }

    public static abstract class HotDealsTable {
        public static final String TABLE_NAME = "hot_deals";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TEXT_1 = "column_text_one";
        public static final String COLUMN_TEXT_2 = "column_text_two";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_TEXT_1 + " TEXT, " +
                        COLUMN_TEXT_2 + " TEXT " +
                        " ); ";

        //TODO: для теста
        public static final String FILL_TABLE =
                "INSERT INTO " + TABLE_NAME + " (" +
                        COLUMN_ID + ", " + COLUMN_TEXT_1 + ", " + COLUMN_TEXT_2 + ") " +
                        "VALUES (1, 'При покупке кресла серии VIRE', '200 рублей в ПОДАРОК!')";

        public static HotDeal parseCursor(Cursor cursor) {
            HotDeal hotDeal = new HotDeal();
            hotDeal.id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            hotDeal.text1 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXT_1));
            hotDeal.text2 = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEXT_2));
            return hotDeal;
        }
    }

    @SuppressWarnings("all")
    // TODO: тут используется bytearray намеренно, для теста - когда не понадобится, убрать supress
    public static abstract class DealsTable {
        public static final String TABLE_NAME = "deals";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_OLD_PRICE = "old_price";
        public static final String COLUMN_DISCOUNT = "discount";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_IMAGE + " BLOB, " +
                        COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_PRICE + " INTEGER, " +
                        COLUMN_TYPE + " INTEGER, " +
                        COLUMN_OLD_PRICE + " INTEGER, " +
                        COLUMN_DISCOUNT + " INTEGER " +
                        " ); ";

        //TODO: для теста
        public static final String INSERT_ITEM_1 =
                "INSERT INTO " + TABLE_NAME + " (" +
                        COLUMN_ID + ", " + COLUMN_IMAGE + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_PRICE +
                        ", " + COLUMN_TYPE + ", " + COLUMN_OLD_PRICE + ", " + COLUMN_DISCOUNT + ") " +
                        "VALUES (1, ?, 'Принтер Epson Stylus TX117', 5000, 2, 7590, 0)";
        public static final String INSERT_ITEM_2 =
                "INSERT INTO " + TABLE_NAME + " (" +
                        COLUMN_ID + ", " + COLUMN_IMAGE + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_PRICE +
                        ", " + COLUMN_TYPE + ", " + COLUMN_OLD_PRICE + ", " + COLUMN_DISCOUNT + ") " +
                        "VALUES (2, ?, 'Бумага для принтера снегурочка А4', 4000, 0, 0, 0)";
        public static final String INSERT_ITEM_3 =
                "INSERT INTO " + TABLE_NAME + " (" +
                        COLUMN_ID + ", " + COLUMN_IMAGE + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_PRICE +
                        ", " + COLUMN_TYPE + ", " + COLUMN_OLD_PRICE + ", " + COLUMN_DISCOUNT + ") " +
                        "VALUES (3, ?, 'Тонер B&W Standart для Samsung ML-1220M', 2000, 1, 0, 30)";

        public static Deal parseCursor(Cursor cursor) {
            Deal deal = new Deal();
            byte[] imageBlob = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
            deal.image = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
            deal.description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            deal.price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
            deal.dealType = DealType.values()[cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TYPE))];
            deal.oldPrice = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_OLD_PRICE));
            deal.percentDiscount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DISCOUNT));
            return deal;
        }
    }

    public static abstract class HistoryTable {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_DATE = "bonus_date";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_BONUS = "bonus";
        public static final String COLUMN_VIEWED = "viewed";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_USER_ID + " TEXT, " +
                        COLUMN_DATE + " INTEGER, " +
                        COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_PRICE + " INTEGER, " +
                        COLUMN_BONUS + " INTEGER, " +
                        COLUMN_VIEWED + " INTEGER " +
                        " ); ";

        //TODO: для теста
        public static final String INSERT_ITEM_1 =
                "INSERT INTO " + TABLE_NAME + " (" +
                        COLUMN_ID + ", " + COLUMN_USER_ID + ", " + COLUMN_DATE + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_PRICE + ", " + COLUMN_BONUS + ", " + COLUMN_VIEWED + ") " +
                        "VALUES (1, '145', 1475272800000, 'Принтер Epson Stylus TX117', 5000, 50, 0)";
        public static final String INSERT_ITEM_2 =
                "INSERT INTO " + TABLE_NAME + " (" +
                        COLUMN_ID + ", " + COLUMN_USER_ID + ", " + COLUMN_DATE + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_PRICE + ", " + COLUMN_BONUS + ", " + COLUMN_VIEWED +  ") " +
                        "VALUES (2, '145', 1475100000000, 'Бумага для принтера снегурочка А4', 4000, 40, 1)";
        public static final String INSERT_ITEM_3 =
                "INSERT INTO " + TABLE_NAME + " (" +
                        COLUMN_ID + ", " + COLUMN_USER_ID + ", " + COLUMN_DATE + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_PRICE + ", " + COLUMN_BONUS + ", " + COLUMN_VIEWED + ") " +
                        "VALUES (3, '145', 1474149600000, 'Тонер B&W Standart для Samsung ML-1220M', 2000, 20, 1)";

        public static HistoryItem parseCursor(Cursor cursor) {
            HistoryItem historyItem = new HistoryItem();
            historyItem.id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            historyItem.bonusDate = new DateTime(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
            historyItem.description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            historyItem.price = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
            historyItem.bonus = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BONUS));
            historyItem.viewed = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VIEWED)) == 1;
            return historyItem;
        }

        public static ContentValues toViewedContentValues(boolean viewed) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_VIEWED, viewed ? 1 : 0);
            return contentValues;
        }

    }



}
