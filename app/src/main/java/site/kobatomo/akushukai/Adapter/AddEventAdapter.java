package site.kobatomo.akushukai.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import site.kobatomo.akushukai.R;

/**
 * Created by tomoya on 2018/05/03.
 */

public class AddEventAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    ArrayList data;
    Activity activity;

    public AddEventAdapter(Context context, int layout , ArrayList data){
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
            TextView discription = convertview.findViewById(R.id.discription);

            JSONObject jsonObject = (JSONObject) data.get(position);
            Log.d("なんとか","感とか");
//            date.setText();

        try {
            String year = jsonObject.getString("year");
            String month = jsonObject.getString("month");
            String day = jsonObject.getString("day");
            date.setText(year+"/"+month+"/"+day);
            type.setText(jsonObject.getString("type"));
            place.setText(jsonObject.getString("place"));
            discription.setText(jsonObject.getString("cdnum")+jsonObject.getString("cdtitle")
            );

        } catch (JSONException e) {
            e.printStackTrace();
        }

//            date.setText("なんとか");

        return convertview;
    }
}
