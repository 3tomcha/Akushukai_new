package site.kobatomo.akushukai.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


    public Cursor getall(String index){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        String query6 = "select * from " + UserContract.Users.TABLE_NAME + " where " + UserContract.Users._ID + "=" + index;
        Cursor cursor6 = db.rawQuery(query6, null);
        return cursor6;
    }

    public void delete(String index){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        String DELETE_EVENT = "DELETE FROM "+ UserContract.Zenkoku.TABLE_NAME+" where "+UserContract.Zenkoku.EVENT_ID+"="+index;
        db.execSQL(DELETE_EVENT);
    }

    public void update(String busuu,String clicked_id,String member_zenkoku){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        String query1 = "update "+ UserContract.Zenkoku.TABLE_NAME
                +" set " + UserContract.Zenkoku.BUSUU+" = '"+busuu
                + "' where " + UserContract.Zenkoku.EVENT_ID + " = '"+clicked_id+"' and "+UserContract.Zenkoku.MEMBER+ " = '"+member_zenkoku+"';";
        db.execSQL(query1);
    }
}
