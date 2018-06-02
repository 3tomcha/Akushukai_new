package site.kobatomo.akushukai.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import site.kobatomo.akushukai.Member.AddEventMember;
import site.kobatomo.akushukai.R;

/**
 * Created by tomoya on 2018/05/03.
 */

public class AddEventAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    ArrayList<AddEventMember> data;
    Activity activity;

    public AddEventAdapter(Context context, int layout , ArrayList data) throws JSONException {
        this.context = context;
        this.data = data;
        this.activity = activity;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    public String getDate(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("year");
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View convertview = convertView;

        if(convertview==null) {
            convertview = layoutInflater.inflate(R.layout.list_addevent, parent, false);
        }
        else {
        }

            TextView date = convertview.findViewById(R.id.date);
            TextView type = convertview.findViewById(R.id.type);
            TextView place = convertview.findViewById(R.id.place);
        LinearLayout typecolor = convertview.findViewById(R.id.typecolor);

            Log.d("なんとか","感とか");
            String year = data.get(position).getYear();
            String month = data.get(position).getMonth();
            String day = data.get(position).getDay();

            date.setText(year+"/"+month+"/"+day);
            type.setText(data.get(position).getType());

            if(data.get(position).getType().equals("全国")){
                type.setBackgroundColor(R.color.zenkokucolor);
                typecolor.setBackgroundColor(R.color.zenkokucolor);
            }else{
                type.setBackgroundColor(R.color.kobetsucolor);
                typecolor.setBackgroundColor(R.color.kobetsucolor);
            }

            place.setText(data.get(position).getPlace());

            Log.d("なんとか","感とか");

//

//            date.setText("なんとか");

        return convertview;
    }
}
