package officedepo.mediapark.com.officedepo.Local;

import android.database.Cursor;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import org.joda.time.DateTime;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import officedepo.mediapark.com.officedepo.Model.Items.Deal;
import officedepo.mediapark.com.officedepo.Model.Items.Gender;
import officedepo.mediapark.com.officedepo.Model.Items.HistoryItem;
import officedepo.mediapark.com.officedepo.Model.Items.HotDeal;
import officedepo.mediapark.com.officedepo.Model.Items.User;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Mary Songal on 03.11.2016.
 */


@Singleton
public class DatabaseHelper {

    private static final String TAG = "DatabaseHelper";
    private final BriteDatabase db;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        db = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper, Schedulers.io());
        db.setLoggingEnabled(true);
    }

    public Observable<String> addUser(User user) {
        // TODO: начисление баллов тоже будет происходить на сервере (как и определяться дата действия)
        user.bonus = 100;
        user.expirationDate = DateTime.now().plusHours(2);
        user.registrationDate = DateTime.now();
        user.gender = Gender.UNKNOWN;
        user.qrCode = user.phone;
        user.userRegisteredEvent = true;
        return Observable.create(subscriber -> {
            db.insert(Db.UserTable.TABLE_NAME, Db.UserTable.toContentValues(user));
            subscriber.onNext(null);
            subscriber.onCompleted();
        });
    }

    public Observable<List<User>> checkUser(User user) {
        return db.createQuery(Db.UserTable.TABLE_NAME, "SELECT * FROM " + Db.UserTable.TABLE_NAME + " WHERE " +
                Db.UserTable.COLUMN_PHONE + "=" + "'" + user.phone + "'" + " AND " + Db.UserTable.COLUMN_PASSWORD + "=" + "'" + user.password + "'" + " ")
                .mapToList(Db.UserTable::parseCursor);
    }

    public Observable<List<User>> getUser(String phone) {
        return db.createQuery(Db.UserTable.TABLE_NAME, "SELECT * FROM " + Db.UserTable.TABLE_NAME + " WHERE " +
                Db.UserTable.COLUMN_PHONE + "=" + "'" + phone + "'" + " ")
                .mapToList(Db.UserTable::parseCursor);
    }

    public Observable<List<HotDeal>> getHotDeals() {
        return db.createQuery(Db.HotDealsTable.TABLE_NAME, "SELECT * FROM " + Db.HotDealsTable.TABLE_NAME + " ")
                .mapToList(Db.HotDealsTable::parseCursor);
    }

    public Observable<List<Deal>> getDeals() {
        return db.createQuery(Db.DealsTable.TABLE_NAME, "SELECT * FROM " + Db.DealsTable.TABLE_NAME + " ")
                .mapToList(Db.DealsTable::parseCursor);
    }

    public Observable<List<HistoryItem>> getHistory(String userId) {
        return db.createQuery(Db.HistoryTable.TABLE_NAME, "SELECT * FROM " + Db.HistoryTable.TABLE_NAME +
                " WHERE " + Db.HistoryTable.COLUMN_USER_ID + "='" + userId + "'" +
                " ORDER BY " + Db.HistoryTable.COLUMN_DATE + " DESC ")
                .mapToList(Db.HistoryTable::parseCursor);
    }

    public Observable<Integer> getTotalSpent(String userId) {
        return Observable.create(subscriber -> {
            Cursor cursor = db.query("SELECT SUM(" + Db.HistoryTable.COLUMN_PRICE + ") FROM " + Db.HistoryTable.TABLE_NAME +
                    " WHERE " + Db.HistoryTable.COLUMN_USER_ID + "='" + userId + "' ;");
            int sum = 0;
            if (cursor.moveToFirst()) {
                sum = cursor.getInt(0);
            }
            subscriber.onNext(sum);
            subscriber.onCompleted();
        });
    }

    public Observable<Integer> getTotalBonus(String userId) {
        return Observable.create(subscriber -> {
            Cursor cursor = db.query("SELECT SUM(" + Db.HistoryTable.COLUMN_BONUS + ") FROM " + Db.HistoryTable.TABLE_NAME +
                    " WHERE " + Db.HistoryTable.COLUMN_USER_ID + "='" + userId + "' ;");
            int sum = 0;
            if (cursor.moveToFirst()) {
                sum = cursor.getInt(0);
            }
            subscriber.onNext(sum);
            subscriber.onCompleted();
        });
    }

    /*
    public Observable<Integer> updateProfile(User updatedUser, String userId) {
        return Observable.create(subscriber -> {
            int rows = db.update(Db.UserTable.TABLE_NAME, Db.UserTable.toProfileContentValues(updatedUser), Db.UserTable.COLUMN_PHONE + "='" + userId + "' ");
            subscriber.onNext(rows);
            subscriber.onCompleted();
        });
    }
    */

    public Observable<Integer> updatePassword(String password, String updatedPassword, String userId) {
        return Observable.create(subscriber -> {
            int rows = db.update(Db.UserTable.TABLE_NAME, Db.UserTable.toPasswordContentValues(updatedPassword),
                    Db.UserTable.COLUMN_PHONE + "='" + userId + "' AND " + Db.UserTable.COLUMN_PASSWORD + "='" + password + "' ");
            subscriber.onNext(rows);
            subscriber.onCompleted();
        });
    }

    public Observable<Integer> updateUserRegisteredEvent(String userId) {
        return Observable.create(subscriber -> {
            int rows = db.update(Db.UserTable.TABLE_NAME, Db.UserTable.toRegisteredEventContentValues(false),
                    Db.UserTable.COLUMN_PHONE + "='" + userId + "' ");
            subscriber.onNext(rows);
            subscriber.onCompleted();
        });
    }

    public Observable<Integer> setHistoryItemViewed(int itemId, boolean viewed) {
        return Observable.create(subscriber -> {
            int rows = db.update(Db.HistoryTable.TABLE_NAME, Db.HistoryTable.toViewedContentValues(viewed),
                    Db.HistoryTable.COLUMN_ID + "=" + itemId + " ");
            subscriber.onNext(rows);
            subscriber.onCompleted();
        });
    }


}
