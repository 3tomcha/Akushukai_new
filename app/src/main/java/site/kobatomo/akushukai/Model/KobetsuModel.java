package site.kobatomo.akushukai.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public String getEventId(){
        return String.valueOf(randomNum);
    }

    public void insertEvent(String year,String month,String day, String place){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(UserContract.Event.EVENT_ID,String.valueOf(randomNum));
        cv.put(UserContract.Event.YEAR,year);
        cv.put(UserContract.Event.MONTH,month);
        cv.put(UserContract.Event.DAY,day);
        cv.put(UserContract.Event.PLACE,place);
        cv.put(UserContract.Event.COL_TYPE,"個別");
        db.insert(UserContract.Event.TABLE_NAME,null,cv);
    }
/*
初回メンバー登録
 */
    public void insertMember(String nanbu, String member, String url, String maisuu){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(UserContract.Kobetsu.EVENT_ID,String.valueOf(randomNum));
            cv.put(UserContract.Kobetsu.NANBU,nanbu);
            cv.put(UserContract.Kobetsu.MEMBER,member);
            cv.put(UserContract.Kobetsu.URL,url);
            cv.put(UserContract.Kobetsu.BUSUU,maisuu);
            cv.put(UserContract.Kobetsu.URL,url);
            db.insert(UserContract.Kobetsu.TABLE_NAME,null,cv);
    }


/*
２回目以降のメンバー登録
 */

    public void insertMember(String nanbu, String member, String url, String maisuu, String eventId){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(UserContract.Kobetsu.EVENT_ID,eventId);
            cv.put(UserContract.Kobetsu.NANBU,nanbu);
            cv.put(UserContract.Kobetsu.MEMBER,member);
            cv.put(UserContract.Kobetsu.URL,url);
            cv.put(UserContract.Kobetsu.BUSUU,maisuu);
            cv.put(UserContract.Kobetsu.URL,url);
            db.insert(UserContract.Kobetsu.TABLE_NAME,null,cv);
    }

    public Cursor searchEventData(String eventId){
        Cursor result = null;
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getReadableDatabase();

        String SEARCH ="select * from "+UserContract.Event.TABLE_NAME+" where "+
                UserContract.Event.EVENT_ID+" = "+eventId;
        result = db.rawQuery(SEARCH, null);
        return result;
    }


/*
メンバー情報を部ごとに整列し取得する
 */

    public Cursor searchMemberData(String eventId){
        Cursor result = null;
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getReadableDatabase();
        String SEARCH ="select * from "+UserContract.Kobetsu.TABLE_NAME+" where "+
                UserContract.Kobetsu.EVENT_ID+" = "+eventId+" order by "+UserContract.Kobetsu.NANBU+" ASC";
        result = db.rawQuery(SEARCH, null);
        return result;
    }

    public Cursor calcSumMaisu(String eventId){
        Cursor result = null;
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getReadableDatabase();
        String SEARCH ="select "+"sum("+UserContract.Kobetsu.BUSUU+")"+" from "+UserContract.Kobetsu.TABLE_NAME+" where "+
                UserContract.Kobetsu.EVENT_ID+" = "+eventId;
        result = db.rawQuery(SEARCH, null);
        return result;
    }

    public void updateMaisu(String eventId, String sumMaisuu){
        UserOpenHelper userOpenHelper = new UserOpenHelper(context);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(UserContract.Event.COL_TICKET,sumMaisuu);

        db.update(UserContract.Event.TABLE_NAME,cv,UserContract.Event.EVENT_ID+"= "+eventId,null);
    }


}
