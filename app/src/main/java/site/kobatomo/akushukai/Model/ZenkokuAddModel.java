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
    public void insert(String year,String month,String day, String place , String lane, String member, String bussu){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        String query = "insert into "+UserContract.Zenkoku.TABLE_NAME+ " ("+UserContract.Zenkoku.YEAR +","+UserContract.Zenkoku.MONTH +","+UserContract.Zenkoku.DAY +","+UserContract.Zenkoku.PLACE+","+UserContract.Zenkoku.LANE+","+UserContract.Zenkoku.MEMBER+"," +UserContract.Zenkoku.BUSUU+")"+" values ("
        +"'"+year+"'"+","+"'"+month+"'"+","+"'"+day+"'"+","+"'"+place+"'"+","+"'"+lane+"'"+","+"'"+member+"'"+","+"'"+bussu+"'"+");";
        db.execSQL(query);
    }


}
