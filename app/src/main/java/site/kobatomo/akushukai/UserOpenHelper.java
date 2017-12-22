package site.kobatomo.akushukai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by taguchi on 2015/05/13.
 */
public class UserOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "app.db";
    public static final int DB_VERSION = 85;
    public static final String CREATE_TABLE =
            "create table " + UserContract.Users.TABLE_NAME + " (" +
                    UserContract.Users._ID + " integer primary key, " +
                    UserContract.Users.COL_TYPE + " text," +
                    UserContract.Users.COL_DATE + " text," +
                    UserContract.Users.COL_LOC + " text," +
                    UserContract.Users.COL_TICKET + " text)";

    public static final String CREATE_TABLE_KOBETSU =
            "create table " + UserContract.Kobetsu.TABLE_NAME + " (" +
                    UserContract.Kobetsu._ID + " integer primary key, " +
                    UserContract.Kobetsu.EVENT_ID + " text," +
                    UserContract.Kobetsu.MEMBER + " text," +
                    UserContract.Kobetsu.NANBU + " text," +
                    UserContract.Kobetsu.BUSUU + " text)";

    public static final String CREATE_TABLE_ZENKOKU =
            "create table " + UserContract.Zenkoku.TABLE_NAME + " (" +
                    UserContract.Zenkoku._ID + " integer primary key, " +
                    UserContract.Zenkoku.EVENT_ID + " text," +
                    UserContract.Zenkoku.MEMBER + " text," +
                    UserContract.Zenkoku.BUSUU + " text)";

    public static final String CREATE_TABLE_MEMBER =
            "create table " + UserContract.Member.TABLE_NAME + " (" +
                    UserContract.Member._ID + " integer primary key, " +
                    UserContract.Member.NAME + " text," +
                    UserContract.Member.URL + " text)";



    public static final String DROP_TABLE =
            "drop table if exists event";

    public static final String DROP_TABLE_KOBETSU =
            "drop table if exists kobetsu";
    public static final String DROP_TABLE_ZENKOKU =
            "drop table if exists zenkoku";
    public static final String DROP_TABLE_MEMBER =
            "drop table if exists "+UserContract.Member.TABLE_NAME;


    /*public static final String UPDATE_ID ="ALTER TABLE users AUTO_INCREMENT = 1;";*/

    public UserOpenHelper(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create table

        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_KOBETSU);
        sqLiteDatabase.execSQL(CREATE_TABLE_ZENKOKU);
        sqLiteDatabase.execSQL(CREATE_TABLE_MEMBER);
//        sqLiteDatabase.execSQL(INIT_TABLE_MEMBER);

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // drop table
        sqLiteDatabase.execSQL(DROP_TABLE);
        sqLiteDatabase.execSQL(DROP_TABLE_KOBETSU);
        sqLiteDatabase.execSQL(DROP_TABLE_ZENKOKU);
        sqLiteDatabase.execSQL(DROP_TABLE_MEMBER);
        // onCreate
        onCreate(sqLiteDatabase);
    }
}