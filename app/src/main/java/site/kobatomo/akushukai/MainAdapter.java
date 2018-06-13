package site.kobatomo.akushukai;

/**
 * Created by bicpc on 2017/11/01.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import site.kobatomo.akushukai.Member.MainMember;

public class MainAdapter extends BaseAdapter {
    private Context context = null;
    private LayoutInflater layoutInflater = null;
    private List<MainMember> data;

    public MainAdapter(Context context, List<MainMember> data) {
        super();
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

/*
日付、場所、枚数をリストビューにセットする
 */
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_main, null);
        }

        TextView dateView = convertView.findViewById(R.id.date);
        String date = data.get(position).getYear() + "/" + data.get(position).getMonth() + "/" + data.get(position).getDay();
        dateView.setText(date);

        ((TextView) convertView.findViewById(R.id.location)).setText(data.get(position).getPlace());

        String type = data.get(position).getType();
        TextView typeView = convertView.findViewById(R.id.type);
        TextView typecolor = convertView.findViewById(R.id.typecolor);

        typeView.setText(type);

/*
全国か個別かで色を変更する
 */
        int Resource = (type.equals("全国")) ? R.color.zenkokucolor : R.color.kobetsucolor;
        typeView.setBackgroundResource(Resource);
        typecolor.setBackgroundResource(Resource);

        ((TextView) convertView.findViewById(R.id.col_ticket)).setText(data.get(position).getBusuu());

/*
個別のリストビューをクリックできるようにする
 */
        (convertView.findViewById(R.id.more)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.more);
            }
        });

        return convertView;
    }

}
