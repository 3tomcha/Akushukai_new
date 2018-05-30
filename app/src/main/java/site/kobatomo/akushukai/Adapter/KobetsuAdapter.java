package site.kobatomo.akushukai.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import site.kobatomo.akushukai.Member.KobetsuMember;
import site.kobatomo.akushukai.R;

/**
 * Created by tomoya on 2018/05/01.
 */

class KobetsuAdapter extends BaseAdapter {
        private ArrayList<KobetsuMember> data = null;
        private Context context = null;
        LayoutInflater layoutInflater = null;



        public KobetsuAdapter(Context context, ArrayList<KobetsuMember> data) {
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
//            return data.get(position).getId();
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_kobetsu_event, parent, false);
            }

            ImageView icon_member_kobetsu = (ImageView) convertView.findViewById(R.id.icon_member_kobetsu);
            TextView member_kobetsu = (TextView) convertView.findViewById(R.id.member_kobetsu);
            TextView busuu_member = (TextView) convertView.findViewById(R.id.busuu_kobetsu);

//            Glide.with(KobetsuEvent.this).
//                    load(data.get(position).geturl()).into(icon_member_kobetsu);

//            busuu_member.setText(data.get(position).getbusuu());
//            member_kobetsu.setText(data.get(position).getmember());
            return convertView;
        }
        /*アダプター*/
    }




