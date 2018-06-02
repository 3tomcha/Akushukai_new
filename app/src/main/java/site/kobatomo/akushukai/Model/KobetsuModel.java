package site.kobatomo.akushukai.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Random;

import site.kobatomo.akushukai.UserContract;

/**
 * Created by tomoya on 2018/06/02.
 */

public class KobetsuModel {
    private Context context;
    private int randomNum;

    public KobetsuModel(Context context){
        this.context=context;
        Random rand = new Random();
        randomNum=rand.nextInt(1000000);
    }

    public void insertEvent(String year,String month,String day, String place){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
        try{
            ContentValues cv = new ContentValues();
            cv.put(UserContract.Event.EVENT_ID,String.valueOf(randomNum));
            cv.put(UserContract.Event.YEAR,year);
            cv.put(UserContract.Event.MONTH,month);
            cv.put(UserContract.Event.DAY,day);
            cv.put(UserContract.Event.PLACE,place);
            db.insert(UserContract.Event.TABLE_NAME,null,cv);
        }catch (Exception ex){
            Log.d("エラー",ex.getMessage());
        }finally {
            db.close();
        }
    }



}
