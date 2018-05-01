package site.kobatomo.akushukai.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import site.kobatomo.akushukai.UserContract;

/**
 * Created by tomoya on 2018/04/29.
 */

public class ZenkokuModel {
    Context context;
    public ZenkokuModel(Context context){
        this.context=context;
    }

    public void delete(String index){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        String DELETE_EVENT = "DELETE FROM "+ UserContract.Zenkoku.TABLE_NAME+" where "+UserContract.Zenkoku.EVENT_ID+"="+index;
        db.execSQL(DELETE_EVENT);
    }
}
