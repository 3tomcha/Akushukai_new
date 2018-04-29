package site.kobatomo.akushukai.Model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import site.kobatomo.akushukai.MainActivity;
import site.kobatomo.akushukai.UserContract;

/**
 * Created by tomoya on 2018/04/29.
 */

public class MainModel {


    private static ArrayList<Integer> id = new ArrayList<Integer>();
    private static ArrayList<String> type = new ArrayList<String>();
    private static ArrayList<String> date = new ArrayList<String>();
    private static ArrayList<String> location = new ArrayList<String>();
    private static ArrayList<String> position = new ArrayList<String>();
    private static ArrayList<String> ticket = new ArrayList<String>();

    public void getall(){

    ///*データベースから取得*/
    UserOpenHelper userOpenHelper = new UserOpenHelper(MainActivity.getInstance());
    SQLiteDatabase db = userOpenHelper.getWritableDatabase();

    Cursor c = null;
    c = db.query(
    UserContract.Users.TABLE_NAME,
            null, // fields
            null, // where
            null, // where arg
            null, // groupBy
            null, // having
            null // order by
            );
/*一旦リセットする*/
        id.clear();
        type.clear();
        date.clear();
        location.clear();
        ticket.clear();
/*一旦リセットする*/

        while (c.moveToNext()) {
        id.add(c.getInt(c.getColumnIndex(UserContract.Users._ID)));
        type.add(c.getString(c.getColumnIndex(UserContract.Users.COL_TYPE)));
        date.add(c.getString(c.getColumnIndex(UserContract.Users.COL_DATE)));
        location.add(c.getString(c.getColumnIndex(UserContract.Users.COL_LOC)));
        ticket.add(c.getString(c.getColumnIndex(UserContract.Users.COL_TICKET)));

    }
    }
    /*何番目かを取得する*/
        public String getType(int index) {
            return type.get(index);
        }
        public String getDate(int index) {
            return date.get(index);
        }
        public String getLoc(int index) {
            return location.get(index);
        }
        public String getTicket(int index) {
            return ticket.get(index);
        }
        public String getId(int index) {
            return id.get(index).toString();
        }

    public int getSize() {
            return id.size();
        }

}