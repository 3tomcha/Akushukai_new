package site.kobatomo.akushukai.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import site.kobatomo.akushukai.UserContract;

/**
 * Created by tomoya on 2018/04/29.
 */

public class ZenkokuModel {
    Context context;
//    SQLiteDatabase db;
    public ZenkokuModel(Context context){
        this.context=context;
    }


//    public Cursor getall(String index){
//        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//        String query6 = "select * from " + UserContract.Users.TABLE_NAME + " where " + UserContract.Users._ID + "=" + index;
//        Cursor cursor6 = db.rawQuery(query6, null);
//        return cursor6;
//    }

//    日付でサーチする仕組みにする
//    IDも取得できるはず
    public Cursor getall(String year, String month, String day){

        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//        String query = "select * from " + UserContract.Zenkoku.TABLE_NAME + " where " + UserContract.Zenkoku.YEAR + " = " + year+" and "
//                +UserContract.Zenkoku.MONTH+" = "+month+" and "+UserContract.Zenkoku.DAY+" = "+day;
        String query = "select * from "+UserContract.Zenkoku.TABLE_NAME+";";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

//    public void insertMember(String member,String lane,String busuu){
//        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//        try{
//            ContentValues cv = new ContentValues();
//            cv.put(UserContract.ZenkokuMember.EVENT_ID,String.valueOf(randomNum));
//            cv.put(UserContract.ZenkokuMember.MEMBER,member);
//            cv.put(UserContract.ZenkokuMember.LANE,lane);
//            cv.put(UserContract.ZenkokuMember.BUSUU,busuu);
//            db.insert(UserContract.ZenkokuMember.TABLE_NAME,null,cv);
//        }catch (Exception ex){
//            Log.d("エラー",ex.getMessage());
//        }finally {
//            db.close();
//        }
//    }




//    public void delete(String index){
//        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//        String DELETE_EVENT = "DELETE FROM "+ UserContract.Zenkoku.TABLE_NAME+" where "+UserContract.Zenkoku.EVENT_DATE +"="+index;
//        db.execSQL(DELETE_EVENT);
//    }

//    public void update(String busuu,String clicked_id,String member_zenkoku){
//        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//        String query1 = "update "+ UserContract.Zenkoku.TABLE_NAME
//                +" set " + UserContract.Zenkoku.BUSUU+" = '"+busuu
//                + "' where " + UserContract.Zenkoku.EVENT_DATE + " = '"+clicked_id+"' and "+UserContract.Zenkoku.MEMBER+ " = '"+member_zenkoku+"';";
//        db.execSQL(query1);
//    }

    public String search_Memberimg(String member) {
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getReadableDatabase();

        String query = "select url from member where name = ?";
        Cursor c = db.rawQuery(query, new String[]{member});

        if (c.moveToFirst()) {
            do {
                String url = c.getString(0);
                return url;
            } while (c.moveToNext());
        } else {
            Log.v("nanntoka", "nantoka");
            return null;
        }
    }

    public Cursor searchMemberInfomation(String eventId){
            UserOpenHelper userOpenHelper = new UserOpenHelper(context);
            SQLiteDatabase db = userOpenHelper.getReadableDatabase();
//            String SEARCH = "select * from " + UserContract.Zenkoku.TABLE_NAME + " where " + UserContract.Zenkoku.EVENT_DATE + " = " + clicked_id;
            String SEARCH = "select * from "+UserContract.ZenkokuMember.TABLE_NAME+ " where "+UserContract.ZenkokuMember.EVENT_ID+" = "+eventId;
//        もしIDが本当に入っているなら等しいという条件でいけるはず
            Cursor cursor = db.rawQuery(SEARCH,null);
            return cursor;
    }


//アレイリストに、データベースから取得したものを加えていく


//    class Query {
//        private String added_url;
//        void setQuery(String query, ArrayList arr) {
//            arr.clear();
//            UserOpenHelper userOpenHelper = new UserOpenHelper(ZenkokuEvent.this);
//            SQLiteDatabase db = userOpenHelper.getReadableDatabase();
//            Cursor c = db.rawQuery(query, null);
//            if (c.moveToFirst()) {
//                do {
//                    String added_member = c.getString(c.getColumnIndex(UserContract.Zenkoku.MEMBER));
//                    String added_busuu = c.getString(c.getColumnIndex(UserContract.Zenkoku.BUSUU));
//                    if(added_member.equals("未定")) {
//                        arr.add(new MyItem(added_member,added_busuu));
//                    /*画像を探す処理も加える*/
//                    }
//                    else{
//                        added_url = zenkokuModel.search_Memberimg(added_member, db);
//                        arr.add(new MyItem(added_member, added_busuu, added_url));
//                    }
//                    sum_busuu += Integer.parseInt(added_busuu);
//
//                } while (c.moveToNext());
//            } else {
//                Log.v("nanntoka", "nantoka");
//            }
//        }
//    }


//    //リストに使うデータ用クラス
//    class MyItem {
//        private String member = null;
//        private String busuu = null;
//        private String url = null;
//        private long id = 0;
//
//        public MyItem(String member, String busuu, String url) {
//            super();
//            this.member = member;
//            this.busuu = busuu;
//            this.url = url;
//            /*画像に関しても加える*/
//
//            this.id = new Date().getTime();
//        }
//        public MyItem(String member,String busuu) {
//            super();
//            this.member = member;
//            this.busuu = busuu;
//            /*画像に関しても加える*/
//            this.id = new Date().getTime();
//        }
//
//        public String getmember() {
//            return member;
//        }
//        public String geturl() {
//            return url;
//        }
//        public String getbusuu() {
//            return busuu;
//        }
//        public long getId() {
//            return id;
//        }
//
//    }


}
