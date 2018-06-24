package site.kobatomo.akushukai.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import site.kobatomo.akushukai.Member.MainMember;
import site.kobatomo.akushukai.Model.KobetsuModel;
import site.kobatomo.akushukai.Model.ZenkokuModel;
import site.kobatomo.akushukai.R;
import site.kobatomo.akushukai.UserContract;

/**
 * Created by tomoya on 2018/06/20.
 */

public class BaseEvent extends FragmentActivity{

    public void setTitle(int color, int titleId, int titlecolorId, int typecolorId) {
        ((TextView)findViewById(titleId)).setText(R.string.package_more);
        findViewById(titlecolorId).setBackgroundResource(color);
        findViewById(typecolorId).setBackgroundResource(color);;
    }

    public void setEventRelatedLook(MainMember evt, int colorid){
        ((TextView) findViewById(R.id.date_event)).setText(evt.getYear()+"/"+evt.getMonth()+"/"+evt.getDay());
        ((TextView) findViewById(R.id.type_event)).setText(evt.getType());
        (findViewById(R.id.type_event)).setBackgroundResource(colorid);
        (findViewById(R.id.typecolor)).setBackgroundResource(colorid);
        ((TextView) findViewById(R.id.location_event)).setText(evt.getPlace());
    }


    public MainMember eventInformationfromModel(KobetsuModel kobetsuModel, String eventId) {
        MainMember mM = new MainMember();
        Cursor ec = kobetsuModel.searchEventData(eventId);
        ec.moveToFirst();

        try {
            mM.setYear(ec.getString(ec.getColumnIndex(UserContract.Event.YEAR)));
            mM.setMonth(ec.getString(ec.getColumnIndex(UserContract.Event.MONTH)));
            mM.setDay(ec.getString(ec.getColumnIndex(UserContract.Event.DAY)));
            mM.setType(ec.getString(ec.getColumnIndex(UserContract.Event.COL_TYPE)));
            mM.setPlace(ec.getString(ec.getColumnIndex(UserContract.Event.PLACE)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mM;
    }

    public MainMember eventInformationfromModel(ZenkokuModel zenkokuModel, String eventId) {
        MainMember mM = new MainMember();
        Cursor ec = zenkokuModel.searchEventData(eventId);
        ec.moveToFirst();

        try {
            mM.setYear(ec.getString(ec.getColumnIndex(UserContract.Event.YEAR)));
            mM.setMonth(ec.getString(ec.getColumnIndex(UserContract.Event.MONTH)));
            mM.setDay(ec.getString(ec.getColumnIndex(UserContract.Event.DAY)));
            mM.setType(ec.getString(ec.getColumnIndex(UserContract.Event.COL_TYPE)));
            mM.setPlace(ec.getString(ec.getColumnIndex(UserContract.Event.PLACE)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mM;
    }

    public void floatingButtonClickIntent(Intent intent){
        findViewById(R.id.addEvent).setOnClickListener(v -> {
            startActivity(intent);
        });
    }
    public void mvButtonClickIntent(Intent intent){
        findViewById(R.id.mv_event).setOnClickListener(v -> {
            startActivity(intent);
        });
    }



}
