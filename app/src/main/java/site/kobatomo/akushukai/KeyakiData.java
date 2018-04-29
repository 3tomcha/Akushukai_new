package site.kobatomo.akushukai;

/**
 * Created by bicpc on 2017/11/01.
 */

import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

import site.kobatomo.akushukai.Model.MainModel;


public class KeyakiData extends FragmentActivity {
    private ArrayList list_type;
    private ArrayList list_date;
    private ArrayList list_loc;
    private ArrayList list_ticket;
    private ArrayList id;


    /*タイプを入れる*/
    public KeyakiData() {
        list_type = new ArrayList();
        list_type.addAll(MainModel.getType());

        list_date = new ArrayList();
        list_date.addAll(MainModel.getDate());

        list_loc = new ArrayList();
        list_loc.addAll(MainModel.getLocation());

        id = new ArrayList();
        id.addAll(MainModel.getId());

        list_ticket = new ArrayList();
        list_ticket.addAll(MainModel.getTicket());



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
