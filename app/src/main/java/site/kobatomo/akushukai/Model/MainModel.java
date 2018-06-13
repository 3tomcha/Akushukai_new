package site.kobatomo.akushukai.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import site.kobatomo.akushukai.UserContract;

/**
 * Created by tomoya on 2018/04/29.
 */

public class MainModel {
    Context context;

    public MainModel(Context context){
        this.context=context;
    }

    public Cursor searchEventData(){
        Cursor result = null;
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getReadableDatabase();

        String SEARCH ="select * from "+ UserContract.Event.TABLE_NAME;
        result = db.rawQuery(SEARCH, null);
        return result;
    }


//    private static ArrayList<Integer> id = new ArrayList<Integer>();
//    private static ArrayList<String> type = new ArrayList<String>();
//    private static ArrayList<String> date = new ArrayList<String>();
//    private static ArrayList<String> location = new ArrayList<String>();
//    private static ArrayList<String> position = new ArrayList<String>();
//    private static ArrayList<String> ticket = new ArrayList<String>();
//
//    public MainModel(Context context){
//        this.context=context;
//    }
//
//    public MainModel(){
//    }
//    public void getall(){
//
//    ///*データベースから取得*/
//    UserOpenHelper userOpenHelper = new UserOpenHelper(MainActivity.getInstance());
//    SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//
//    Cursor c = null;
//    c = db.query(
//    UserContract.Event.TABLE_NAME,
//            null, // fields
//            null, // where
//            null, // where arg
//            null, // groupBy
//            null, // having
//            null // order by
//            );
///*一旦リセットする*/
//        id.clear();
//        type.clear();
//        date.clear();
//        location.clear();
//        ticket.clear();
///*一旦リセットする*/
//
//        while (c.moveToNext()) {
//        id.add(c.getInt(c.getColumnIndex(UserContract.Event._ID)));
//        type.add(c.method(c.getColumnIndex(UserContract.Event.COL_TYPE)));
//        date.add(c.method(c.getColumnIndex(UserContract.Event.COL_DATE)));
//        location.add(c.method(c.getColumnIndex(UserContract.Event.COL_LOC)));
//        ticket.add(c.method(c.getColumnIndex(UserContract.Event.COL_TICKET)));
//
//    }
//    }
//
//    public void delete(String index){
//        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//
//        String DELETE_EVENT = "DELETE FROM "+ UserContract.Event.TABLE_NAME+" where "+ UserContract.Event._ID+"="+index;
//        db.execSQL(DELETE_EVENT);
//    }
//
//    public void update(String clicked_id,String kari_col_ticlet){
//        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//
//        String query = "update "+ UserContract.Event.TABLE_NAME
//            +" set " + UserContract.Event.COL_TICKET+" = "+kari_col_ticlet
//            + " where " + UserContract.Event._ID + " = "+clicked_id+";";
//        db.execSQL(query);
//    }
//
//
//    /*何番目かを取得する*/
//        public String getType(int index) {
//            return type.get(index);
//        }
//        public String getDate(int index) {
//            return date.get(index);
//        }
//        public String getLoc(int index) {
//            return location.get(index);
//        }
//        public String getTicket(int index) {
//            return ticket.get(index);
//        }
//        public String getId(int index) {
//            return id.get(index).toString();
//        }
//
//    public int getSize() {
//            return id.size();
//        }

}
