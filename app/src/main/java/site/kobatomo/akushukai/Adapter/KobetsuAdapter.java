package site.kobatomo.akushukai.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import site.kobatomo.akushukai.Member.KobetsuMember;
import site.kobatomo.akushukai.R;

/**
 * Created by tomoya on 2018/05/30.
 */

public class KobetsuAdapter extends BaseAdapter{
        private Context context = null;
        LayoutInflater layoutInflater = null;
        private KobetsuMember data;


    public KobetsuAdapter(Context context, KobetsuMember data) {
            super();
            this.data = data;
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.getMember().size();
        }

        @Override
        public Object getItem(int position) {
            return data.getMember().get(position);
        }

        @Override
        public long getItemId(int position) {
//            return data.get(position).getId();
            return 0;
        }

        /*
        KobetsuMemberクラスに挿入したデータを、それぞれ列にあった物で表示する
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_kobetsu_event, parent, false);
            }

            ImageView icon_member_kobetsu = (ImageView) convertView.findViewById(R.id.icon_member_kobetsu);
            TextView member_kobetsu = (TextView) convertView.findViewById(R.id.member_kobetsu);
            TextView busuu_member = (TextView) convertView.findViewById(R.id.busuu_kobetsu);

            Glide.with(context).
                    load(data.getUrl().get(position)).into(icon_member_kobetsu);
            busuu_member.setText(data.getMaisuu().get(position).toString());
            member_kobetsu.setText(data.getMember().get(position).toString());
            return convertView;
        }
    }




