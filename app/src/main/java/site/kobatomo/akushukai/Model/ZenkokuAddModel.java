package site.kobatomo.akushukai.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import site.kobatomo.akushukai.UserContract;

/**
 * Created by tomoya on 2018/04/29.
 */

public class ZenkokuAddModel {
    Context context;
    //    SQLiteDatabase db;
    public ZenkokuAddModel(Context context){
        this.context=context;
    }
    public void insert(String event_date, String place , String lane, String member, String bussu){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        String query6 = "insert into zenkoku "+ "("+UserContract.Zenkoku.EVENT_DATE +","+UserContract.Zenkoku.PLACE+","+UserContract.Zenkoku.LANE+","+UserContract.Zenkoku.MEMBER+"," +UserContract.Zenkoku.BUSUU+")"+" values ("+event_date
        +","+place+","+lane+","+member+","+bussu+")";
    }


}
