package site.kobatomo.akushukai.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Random;

import site.kobatomo.akushukai.UserContract;

/**
 * Created by tomoya on 2018/04/29.
 */

public class ZenkokuAddModel {
    Context context;
    int randomNum;
    //    SQLiteDatabase db;
    public ZenkokuAddModel(Context context){
        this.context=context;
        Random rand = new Random();
        randomNum=rand.nextInt(1000000);
    }


//    public void insertEvent(String year,String month,String day, String place){
//        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//        String query = "insert into "+UserContract.Zenkoku.TABLE_NAME+ " ("+UserContract.Zenkoku.EVENT_ID+","+UserContract.Zenkoku.YEAR +","+UserContract.Zenkoku.MONTH +","+UserContract.Zenkoku.DAY +","+UserContract.Zenkoku.PLACE+")"
//                +" values ("
//                +"'"+String.valueOf(randomNum)+","+"'"+year+"'"+","+"'"+month+"'"+","+"'"+day+"'"+","+"'"+place+"'"+");";
//        db.execSQL(query);
//    }

    public void insertEvent(String year,String month,String day, String place){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            cv.put(UserContract.Zenkoku.EVENT_ID,String.valueOf(randomNum));
            cv.put(UserContract.Zenkoku.YEAR,year);
            cv.put(UserContract.Zenkoku.MONTH,month);
            cv.put(UserContract.Zenkoku.PLACE,place);
            cv.put(UserContract.Zenkoku.DAY,day);
            db.insert(UserContract.Zenkoku.TABLE_NAME,null,cv);
        }catch (Exception ex){
            Log.d("エラー",ex.getMessage());
        }finally {
            db.close();
        }
    }

    public void insertMember(String member,String lane,String busuu){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            cv.put(UserContract.ZenkokuMember.EVENT_ID,String.valueOf(randomNum));
            cv.put(UserContract.ZenkokuMember.MEMBER,member);
            cv.put(UserContract.ZenkokuMember.LANE,lane);
            cv.put(UserContract.ZenkokuMember.BUSUU,busuu);
            db.insert(UserContract.ZenkokuMember.TABLE_NAME,null,cv);
        }catch (Exception ex){
            Log.d("エラー",ex.getMessage());
        }finally {
            db.close();
        }
    }

//    public void insertMember(String member,String lane,String bussu) {
//        String query2 = "insert into " + UserContract.ZenkokuMember.TABLE_NAME + " (" +
//                UserContract.ZenkokuMember.EVENT_ID + "," + UserContract.ZenkokuMember.MEMBER + ","
//                + UserContract.ZenkokuMember.LANE + "," + UserContract.ZenkokuMember.BUSUU + ")"
//                + " values (" + "'" + String.valueOf(randomNum) + "'" + "'" + member + "'" + "'" + lane + "'" + "'" + bussu + "'";
//    }


}
