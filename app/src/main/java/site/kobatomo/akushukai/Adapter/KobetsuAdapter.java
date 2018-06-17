package site.kobatomo.akushukai.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
        final String DEVIDELABEL = "0";
        private int separetorResourceId = R.layout.listview_sep; //区切り用のフィールド


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
            return data.getUrl().get(position);
        }

        @Override
        public long getItemId(int position) {
//            return data.get(position).getId();
            return 0;
        }
        @Override
        public boolean isEnabled(int position){ // 区切りの場合はfalse, そうでない場合はtrueを返す
            return !(getItem(position).toString().equals(DEVIDELABEL));
        }

        /*
        KobetsuMemberクラスに挿入したデータを、それぞれ列にあった物で表示する
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            viewHolder = new ViewHolder();
            int count=0;

            //使用できる→通常レイアウト
            if (count == 0) {
                convertView = layoutInflater.inflate(R.layout.list_kobetsu_event, parent, false);
                viewHolder.relativeLayout = convertView.findViewById(R.id.wrapper_list_event);
                viewHolder.busuuText = convertView.findViewById(R.id.busuu_kobetsu);
                viewHolder.memberText = convertView.findViewById(R.id.member_kobetsu);
                viewHolder.icon = convertView.findViewById(R.id.icon_member_kobetsu);
                int height = convertView.getMeasuredHeight();
                count = 1;
                convertView.setTag(viewHolder);
            }
            //通常レイアウトで２回目
            if(count != 0 && isEnabled(position)) {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if(!isEnabled(position)) {
                convertView = layoutInflater.inflate(R.layout.listview_sep, parent, false);
                TextView test = convertView.findViewById(R.id.test);
//                test.setText("第１部")
                test.setText(data.getNanbu().get(position).toString());
                count=0;
            }else{
                Glide.with(context).
                        load(data.getUrl().get(position)).into(viewHolder.icon);
                viewHolder.busuuText.setText(data.getMaisuu().get(position).toString());
                viewHolder.memberText.setText(data.getMember().get(position).toString());
                convertView.setTag(viewHolder);
            }
            return convertView;
        }

    private class ViewHolder {
        TextView memberText;
        TextView busuuText;
        TextView testText;
        ImageView icon;
        RelativeLayout relativeLayout;
    }
}




