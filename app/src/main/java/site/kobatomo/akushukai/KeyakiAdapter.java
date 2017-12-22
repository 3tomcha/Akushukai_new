package site.kobatomo.akushukai;

/**
 * Created by bicpc on 2017/11/01.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class KeyakiAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private KeyakiData data;

    public KeyakiAdapter(Context context) {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        data = new KeyakiData();


    }

    @Override
    public int getCount() {
        return data.getSize();
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public Object getItem(int position) {
        return data.getType(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(data.getId(position));
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list, null);

            TextView type = (TextView)convertView.findViewById(R.id.type);
            type.setText(data.getType(position));

            if(data.getType(position).equals("全国")){
                type.setBackgroundResource(R.color.zenkokucolor);
                TextView typecolor=convertView.findViewById(R.id.typecolor);
                typecolor.setBackgroundResource(R.color.zenkokucolor);
            }

            if(data.getType(position).equals("個別")) {
                type.setBackgroundResource(R.color.kobetsucolor);
                TextView typecolor = convertView.findViewById(R.id.typecolor);
                typecolor.setBackgroundResource(R.color.kobetsucolor);
            }

            TextView date = (TextView)convertView.findViewById(R.id.date);
            date.setText(data.getDate(position));

            TextView location = (TextView)convertView.findViewById(R.id.location);
            location.setText(data.getLoc(position));

            TextView ticket = (TextView)convertView.findViewById(R.id.col_ticket);
            ticket.setText(data.getTicket(position));


            LinearLayout mOre = convertView.findViewById(R.id.more);

            mOre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ListView) parent).performItemClick(view, position, R.id.more);
                }
            });


            /*Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");*/
            /*Typeface font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
            TextView tv4 = (TextView)convertView.findViewById(R.id.fb_icon);
            tv4.setTypeface(font);*/



        }
        return convertView;
    }

}
