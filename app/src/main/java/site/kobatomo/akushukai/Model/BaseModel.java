package site.kobatomo.akushukai.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Random;

import site.kobatomo.akushukai.UserContract;

/**
 * Created by tomoya on 2018/06/20.
 */

public class BaseModel {

    protected Context context;
    protected int randomNum;

    public BaseModel(Context context){
        this.context=context;
        Random rand = new Random();
        randomNum=rand.nextInt(1000000);
    }

    public Cursor searchEventData(String eventId){
        Cursor result = null;
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getReadableDatabase();

        String SEARCH ="select * from "+ UserContract.Event.TABLE_NAME+" where "+
                UserContract.Event.EVENT_ID+" = "+eventId;
        result = db.rawQuery(SEARCH, null);
        return result;
    }
}
