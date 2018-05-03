package site.kobatomo.akushukai;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by tomoya on 2018/05/01.
 */

    class ZenkokuAdapter extends BaseAdapter {
    private ArrayList<ZenkokuEvent.MemberInfomation> data = null;
    private Context context = null;
    LayoutInflater layoutInflater = null;
    private ViewHolder vh;
    private View convertview;
    private Activity mActivity;
    private int busuu;
    private int kari_busuu;

    class ViewHolder {
        ImageView icon_member_zenkoku;
        TextView member_zenkoku;
        TextView busuu_zenkoku;
        LinearLayout plus_ticket_zenkoku;
        LinearLayout minus_ticket_zenkoku;
    }

    ZenkokuAdapter(Context context, ArrayList<ZenkokuEvent.MemberInfomation> data,Activity activity) {
//            super();
        this.data = data;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.mActivity = activity;
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
        return data.get(position).getId();
    }

//    ZenkokuEvent,ZenkokuModelと変数を共通化する
    public String getBussu(){
        return String.valueOf(busuu);
    }

    public String getKaribusuu(){
        return String.valueOf(kari_busuu);
    }




    @Override
    public View getView(final int position, View ConvertView, final ViewGroup parent) {
        vh = null;
        convertview = ConvertView;

        if (convertview == null) {
            convertview = layoutInflater.inflate(R.layout.list_zenkoku_event, parent, false);
            vh = new ViewHolder();

            vh.icon_member_zenkoku = (ImageView) convertview.findViewById(R.id.icon_member_zenkoku);
            vh.member_zenkoku = (TextView) convertview.findViewById(R.id.member_zenkoku);
            vh.busuu_zenkoku = (TextView) convertview.findViewById(R.id.busuu_zenkoku);
            vh.plus_ticket_zenkoku = (LinearLayout) convertview.findViewById(R.id.plus_ticket_zenkoku);
            vh.minus_ticket_zenkoku = (LinearLayout) convertview.findViewById(R.id.minus_ticket_zenkoku);


            convertview.setTag(vh);

        } else {
            vh = (ViewHolder) convertview.getTag();
        }

        vh.busuu_zenkoku.setText(data.get(position).getbusuu());

        vh.plus_ticket_zenkoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.plus_ticket_zenkoku);
            }
        });

        vh.minus_ticket_zenkoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.minus_ticket_zenkoku);
            }
        });


        if (!TextUtils.isEmpty(data.get(position).geturl())) {
            Glide.with(context).
                    load(data.get(position).geturl()).into(vh.icon_member_zenkoku);
        }
        if (!TextUtils.isEmpty(data.get(position).getmember())) {
            vh.member_zenkoku.setText(data.get(position).getmember());
        }


//   メンバー毎の部数の増減処理
        final ListView zenkoku_list2 = (ListView) parent.findViewById(R.id.zenkoku_list);
            zenkoku_list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position, long id) {

//                    ListView list5=findViewById(R.id.zenkoku_list);
                    View childlist = zenkoku_list2.getChildAt(position);
                    TextView busuu_zenkoku=childlist.findViewById(R.id.busuu_zenkoku);
//
//                    TextView member_zenkoku=childlist.findViewById(R.id.member_zenkoku);
//                    String string_member_zenkoku=member_zenkoku.getText().toString();
                    busuu=Integer.parseInt(busuu_zenkoku.getText().toString());

                    TextView kari_col_ticket = mActivity.findViewById(R.id.kari_col_ticket);
                    kari_busuu = Integer.parseInt(kari_col_ticket.getText().toString());


                    switch (view.getId()) {
                        case R.id.plus_ticket_zenkoku:
                            busuu++;
                            kari_busuu++;
                            busuu_zenkoku.setText(String.valueOf(busuu));
                            kari_col_ticket.setText(String.valueOf(kari_busuu));

                            break;

                        case R.id.minus_ticket_zenkoku:
                            busuu--;
                            kari_busuu--;
                            busuu_zenkoku.setText(String.valueOf(busuu));
                            kari_col_ticket.setText(String.valueOf(kari_busuu));

                            Log.d("click","遷移先マイナス");
                            break;
                    }
                }
            });
        return convertview;
    }
}


