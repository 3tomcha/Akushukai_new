package site.kobatomo.akushukai;

/**
 * Created by bicpc on 2017/11/01.
 */

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.ArrayList;


public class KeyakiData extends FragmentActivity {
    private ArrayList list_type;
    private ArrayList list_date;
    private ArrayList list_loc;
    private ArrayList list_ticket;
    private ArrayList id;


    /*タイプを入れる*/
    public KeyakiData() {
        list_type = new ArrayList();
        list_type.addAll(MainActivity.getType());

        list_date = new ArrayList();
        list_date.addAll(MainActivity.getDate());

        list_loc = new ArrayList();
        list_loc.addAll(MainActivity.getLocation());

        id = new ArrayList();
        id.addAll(MainActivity.getId());

        list_ticket = new ArrayList();
        list_ticket.addAll(MainActivity.getTicket());



    }

    /*サイズを取得する*/
    public int getSize() {
        return list_type.size();
    }
    /*サイズを取得する*/

    /*何番目かを取得する*/
    public String getType(int index) {
        return list_type.get(index).toString();
    }
    public String getDate(int index) {
        return list_date.get(index).toString();
    }
    public String getLoc(int index) {
        return list_loc.get(index).toString();
    }
    public String getTicket(int index) {
        return list_ticket.get(index).toString();
    }
    public String getId(int index) {
        return id.get(index).toString();
    }

    /*何番目かを取得する*/

}
