package officedepo.mediapark.com.officedepo.Local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.inject.Inject;

import officedepo.mediapark.com.officedepo.Injection.ApplicationContext;
import officedepo.mediapark.com.officedepo.R;
import officedepo.mediapark.com.officedepo.Util.Util;

/**
 * Created by Mary Songal on 02.11.2016.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "officedepo.db";
    private static final int DB_VERSION = 1;

    @Inject
    public DbOpenHelper(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("PRAGMA foreign_keys=ON;");
            db.execSQL(Db.UserTable.CREATE);
            db.execSQL(Db.HotDealsTable.CREATE);
            db.execSQL(Db.DealsTable.CREATE);
            db.execSQL(Db.HistoryTable.CREATE);
            //TODO: для теста, браться с сервера всё будет
            db.execSQL(Db.HotDealsTable.FILL_TABLE);
            SQLiteStatement sqLiteStatement = db.compileStatement(Db.DealsTable.INSERT_ITEM_1);
            sqLiteStatement.bindBlob(1, Util.getImageFromResources(R.drawable.deal_test_1));
            sqLiteStatement.execute();
            sqLiteStatement = db.compileStatement(Db.DealsTable.INSERT_ITEM_2);
            sqLiteStatement.bindBlob(1, Util.getImageFromResources(R.drawable.deal_test_2));
            sqLiteStatement.execute();
            sqLiteStatement = db.compileStatement(Db.DealsTable.INSERT_ITEM_3);
            sqLiteStatement.bindBlob(1, Util.getImageFromResources(R.drawable.deal_test_3));
            sqLiteStatement.execute();
            db.execSQL(Db.HistoryTable.INSERT_ITEM_1);
            db.execSQL(Db.HistoryTable.INSERT_ITEM_2);
            db.execSQL(Db.HistoryTable.INSERT_ITEM_3);
            //
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO: при изменении структуры мигрировать базу
    }
}
