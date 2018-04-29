package site.kobatomo.akushukai;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import site.kobatomo.akushukai.Model.UserOpenHelper;

/**
 * Created by bicpc on 2017/11/10.
 */

public class KobetsuEvent extends FragmentActivity {

    private String selected_type;
    private String selected_date;
    private String selected_loc;
    private String selected_ticket;
    private String clicked_id;
    private String selected_first;
    private String selected_second;
    private String selected_third;
    private String selected_fourth;
    private String selected_fifth;
    private ImageView menu;

    final int EVENTDIALOG = 1;
    /*private static ArrayList<String> added_member = new ArrayList<String>();
    private static ArrayList<String> added_nanbu = new ArrayList<String>();
    private static ArrayList<String> added_busuu = new ArrayList<String>();
*/
    private static ArrayList<MyItem> arr1 = new ArrayList<MyItem>();
    private static ArrayList<MyItem> arr2 = new ArrayList<MyItem>();
    private static ArrayList<MyItem> arr3 = new ArrayList<MyItem>();
    private static ArrayList<MyItem> arr4 = new ArrayList<MyItem>();
    private static ArrayList<MyItem> arr5 = new ArrayList<MyItem>();
    public TextView nanbu = null;
    private int sum_busuu;
    private LinearLayout inflateLayout;
    private ListView list_1bu;
    private ListView list_2bu;
    private ListView list_3bu;
    private ListView list_4bu;
    private ListView list_5bu;
    private String delete_nanbu;
    private String delete_member;
    private int ct=0;
    private DrawerLayout drawer;
    private TextView col_ticket;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kobetsu_event);

        setTitle();
        loadDate();
        loadDatabase();
        setNavigetion();




        /*イベント追加ボタンの実装*/
        FloatingActionButton addEvent = findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(KobetsuEvent.this, KobetsuAddEvent.class);
                intent2.putExtra("clicked_id", clicked_id);
                startActivity(intent2);
            }
        });

        LinearLayout et_kobetsu_event = findViewById(R.id.mv_event);

        et_kobetsu_event.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(KobetsuEvent.this, KobetsuAddEvent.class);
                intent2.putExtra("clicked_id", clicked_id);
                startActivity(intent2);
            }
        });

//        LinearLayout mvevent=findViewById(R.id.mv_event);
//        mvevent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent2 = new Intent(KobetsuEvent.this, UpdateEvent.class);
//                intent2.putExtra("clicked_id", clicked_id);
//                startActivity(intent2);
//            }
//        });

        setColTicket();


//        削除処理
        list_1bu.setLongClickable(true);
        list_2bu.setLongClickable(true);
        list_3bu.setLongClickable(true);
        list_4bu.setLongClickable(true);
        list_5bu.setLongClickable(true);


        list_1bu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                long_getView(parent,view,position,id);
                showFragmentDialog();

                return false;
            }
        });
        list_2bu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                long_getView(parent,view,position,id);
                showFragmentDialog();

                return false;
            }
        });
        list_3bu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                long_getView(parent,view,position,id);
                showFragmentDialog();

                return false;
            }
        });
        list_4bu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                long_getView(parent,view,position,id);
                showFragmentDialog();

                return false;
            }
        });
        list_5bu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                long_getView(parent,view,position,id);
                showFragmentDialog();

                return false;
            }
        });


    }

    /*前画面でクリックされたIDから他情報を検索する*/
    private void loadDate() {
        Intent intent2 = getIntent();
        clicked_id = intent2.getStringExtra("clicked_id");

        //        クリックされたIDを元に情報を検索する
        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getReadableDatabase();

        String query6 = "select * from " + UserContract.Users.TABLE_NAME + " where " + UserContract.Users._ID + "=" + clicked_id;
        Cursor cursor6 = db.rawQuery(query6, null);
        if (cursor6.moveToFirst()) {
            do {
                TextView date_k = findViewById(R.id.date_event);
                date_k.setText(cursor6.getString(cursor6.getColumnIndex(UserContract.Users.COL_DATE)));
                TextView type_k = findViewById(R.id.type_event);
                type_k.setText(cursor6.getString(cursor6.getColumnIndex(UserContract.Users.COL_TYPE)));
                TextView location_k = findViewById(R.id.location_event);
                location_k.setText(cursor6.getString(cursor6.getColumnIndex(UserContract.Users.COL_LOC)));
                TextView typecolor = findViewById(R.id.typecolor);

                if (type_k.getText().equals("全国")) {
                    type_k.setBackgroundResource(R.color.zenkokucolor);
                    typecolor.setBackgroundResource(R.color.zenkokucolor);
                }
                if (type_k.getText().equals("個別")) {
                    type_k.setBackgroundResource(R.color.kobetsucolor);
                    typecolor.setBackgroundResource(R.color.kobetsucolor);
                }

            } while (cursor6.moveToNext());
        }


//        TextView package_title = findViewById(R.id.package_title);
//        package_title.setText(R.string.package_more);

    }


    //リストに使うデータ用クラス
    class MyItem {
        private String member = null;
        private String busuu = null;
        private String url = null;
        private long id = 0;

        public MyItem(String member, String busuu, String url) {
            super();
            this.member = member;
            this.busuu = busuu;
            this.url = url;

            this.id = new Date().getTime();
        }

        public String getmember() {
            return member;
        }

        /*public String getnanbu() {
            return nanbu;
        }*/
        public String getbusuu() {
            return busuu;
        }

        public long getId() {
            return id;
        }


        public String geturl() {
            return url;
        }


    }


    //データMyItemを管理するアダプタークラス
    class MyAdapter extends BaseAdapter {
        private ArrayList<MyItem> data = null;
        private Context context = null;
        LayoutInflater layoutInflater = null;


        public MyAdapter(Context context, ArrayList<MyItem> data) {
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
            return data.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_kobetsu_event, parent, false);
            }

            ImageView icon_member_kobetsu = (ImageView) convertView.findViewById(R.id.icon_member_kobetsu);
            TextView member_kobetsu = (TextView) convertView.findViewById(R.id.member_kobetsu);
            TextView busuu_member = (TextView) convertView.findViewById(R.id.busuu_kobetsu);

            Glide.with(KobetsuEvent.this).
                    load(data.get(position).geturl()).into(icon_member_kobetsu);

            busuu_member.setText(data.get(position).getbusuu());
            member_kobetsu.setText(data.get(position).getmember());
            return convertView;
        }
        /*アダプター*/
    }

    //    データベース処理
    class Query {
        void setQuery(String query, ArrayList arr) {
            arr.clear();
            UserOpenHelper userOpenHelper = new UserOpenHelper(KobetsuEvent.this);
            SQLiteDatabase db = userOpenHelper.getReadableDatabase();
            Cursor c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    String added_member = c.getString(c.getColumnIndex(UserContract.Kobetsu.MEMBER));
                    String added_busuu = c.getString(c.getColumnIndex(UserContract.Kobetsu.BUSUU));

                    /*画像を探す処理も加える*/
                    String added_url = search_Memberimg(added_member, db);

                    sum_busuu += Integer.parseInt(added_busuu);
                    arr.add(new MyItem(added_member, added_busuu, added_url));
                } while (c.moveToNext());
            } else {
                Log.v("nanntoka", "nantoka");
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        } else {
            Intent intent = new Intent(KobetsuEvent.this, MainActivity.class);
            saveDate();
            startActivity(intent);
            return true;
        }
    }


    //    画像読み込み処理
    public class ImageLoader extends AsyncTask<String, String, Bitmap> {

        private ImageView imageView;

        public ImageLoader(ImageView imageView) {

            // コンストラクタ
            // ImageViewを渡す
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... arg0) {

            // バックグラウンド処理

            Bitmap bitmap = null;
            try {

                // 指定したURLの画像を読み込む
                URL url = new URL(arg0[0]);
                InputStream input = url.openStream();
                bitmap = BitmapFactory.decodeStream(input);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap res) {
            //バックグラウンド処理が完了した時の処理

            // 読み込んだ画像をImageViewに設定
            this.imageView.setImageBitmap(res);
        }
    }

    private String search_Memberimg(String member, SQLiteDatabase db) {

        String query = "select url from member where name = ?";

        Cursor c = db.rawQuery(query, new String[]{member});

        if (c.moveToFirst()) {
            do {
                String url = c.getString(0);
                return url;
            } while (c.moveToNext());
        } else {
            Log.v("nanntoka", "nantoka");
            return null;
        }

    }

    private void setTitle() {
        TextView package_title = findViewById(R.id.package_title);
        package_title.setText(R.string.package_more);
        RelativeLayout title = findViewById(R.id.title);
        title.setBackgroundResource(R.color.kobetsucolor);
        TextView col_ticket=findViewById(R.id.col_ticket);
    }




    private void setColTicket() {
        col_ticket = findViewById(R.id.col_ticket);
        col_ticket.setText(String.valueOf(sum_busuu));
    }

    private void loadDatabase() {

        String query1 = "select * from " + UserContract.Kobetsu.TABLE_NAME + " where " + UserContract.Kobetsu.EVENT_ID + " = " + clicked_id + " and " + UserContract.Kobetsu.NANBU + " ='1部' ";
        String query2 = "select * from " + UserContract.Kobetsu.TABLE_NAME + " where " + UserContract.Kobetsu.EVENT_ID + " = " + clicked_id + " and " + UserContract.Kobetsu.NANBU + " ='2部' ";
        String query3 = "select * from " + UserContract.Kobetsu.TABLE_NAME + " where " + UserContract.Kobetsu.EVENT_ID + " = " + clicked_id + " and " + UserContract.Kobetsu.NANBU + " ='3部' ";
        String query4 = "select * from " + UserContract.Kobetsu.TABLE_NAME + " where " + UserContract.Kobetsu.EVENT_ID + " = " + clicked_id + " and " + UserContract.Kobetsu.NANBU + " ='4部' ";
        String query5 = "select * from " + UserContract.Kobetsu.TABLE_NAME + " where " + UserContract.Kobetsu.EVENT_ID + " = " + clicked_id + " and " + UserContract.Kobetsu.NANBU + " ='5部' ";

    /*データベースから取得したデータをセット*/
        Query query = new Query();
        query.setQuery(query1, arr1);
        query.setQuery(query2, arr2);
        query.setQuery(query3, arr3);
        query.setQuery(query4, arr4);
        query.setQuery(query5, arr5);

        list_1bu = (ListView) findViewById(R.id.list_1bu);
        list_2bu = (ListView) findViewById(R.id.list_2bu);
        list_3bu = (ListView) findViewById(R.id.list_3bu);
        list_4bu = (ListView) findViewById(R.id.list_4bu);
        list_5bu = (ListView) findViewById(R.id.list_5bu);
        list_1bu.setAdapter(new MyAdapter(this, arr1));
        list_2bu.setAdapter(new MyAdapter(this, arr2));
        list_3bu.setAdapter(new MyAdapter(this, arr3));
        list_4bu.setAdapter(new MyAdapter(this, arr4));
        list_5bu.setAdapter(new MyAdapter(this, arr5));

//        各部で存在すればタイトルを表示する

        if (!arr1.isEmpty()) {
            (findViewById(R.id.TV1bu_kobetsu)).setVisibility(View.VISIBLE);
        }
        if (!arr2.isEmpty()) {
            (findViewById(R.id.TV2bu_kobetsu)).setVisibility(View.VISIBLE);
        }
        if (!arr3.isEmpty()) {
            (findViewById(R.id.TV3bu_kobetsu)).setVisibility(View.VISIBLE);
        }
        if (!arr4.isEmpty()) {
            (findViewById(R.id.TV4bu_kobetsu)).setVisibility(View.VISIBLE);
        }
        if (!arr5.isEmpty()) {
            (findViewById(R.id.TV5bu_kobetsu)).setVisibility(View.VISIBLE);
        }


    }

    public void deleteItem() {

        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        String sql="delete from "+UserContract.Kobetsu.TABLE_NAME+" where "+
                UserContract.Kobetsu.EVENT_ID+" = "+clicked_id+" and "+
                UserContract.Kobetsu.NANBU+" = "+"'"+delete_nanbu+"'"+" and "+
                UserContract.Kobetsu.MEMBER+" = "+"'"+delete_member+"'"+";";

        db.execSQL(sql);
        reload();

    }
    public void long_getView(AdapterView<?> arg0, View arg1, int position, long id){

        View childlist = arg0.getChildAt(position);

        TextView tv_delete_member = (TextView)childlist.findViewById(R.id.member_kobetsu);
        delete_member=tv_delete_member.getText().toString();

        String[] nanbu=getResources().getStringArray(R.array.nanbu);

        switch (arg0.getId()) {
            case R.id.list_1bu:
                delete_nanbu =nanbu[0];
                break;
            case R.id.list_2bu:
                delete_nanbu =nanbu[1];
                break;
            case R.id.list_3bu:
                delete_nanbu =nanbu[2];
                break;
            case R.id.list_4bu:
                delete_nanbu =nanbu[3];
                break;
            case R.id.list_5bu:
                delete_nanbu =nanbu[4];
                break;
        }
    }
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public static class KobetsuDialogFragment extends TestDialogFragment
    {
        public void onTestDialogOKClick() {
            KobetsuEvent activity=(KobetsuEvent)getActivity();
            activity.deleteItem();
        }

    }
    public static class KobetsuEventDialogFragment extends TestDialogFragment
    {
        public void onTestDialogOKClick() {
            KobetsuEvent activity=(KobetsuEvent)getActivity();
            activity.deleteEvent();

        Intent intent3 =new Intent(activity,MainActivity.class);
        startActivity(intent3);
        }

    }


    private void showFragmentDialog() {
                DialogFragment dialogFragment = new KobetsuDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
    }

    private void showFragmentDialog(int EVENTDIALOG) {
        KobetsuEventDialogFragment dialogFragment2 = new KobetsuEventDialogFragment();
        dialogFragment2.show(getSupportFragmentManager(), "fragment_dialog");
    }



    private void setNavigetion(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        menu=findViewById(R.id.menu);
        menu.setClickable(true);
        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(ct%2==0) {
                    drawer.openDrawer(GravityCompat.START);
                    drawer.bringToFront();
                    ct++;
                }
                else{
                    drawer.closeDrawer(GravityCompat.START);
                    View wrapper_listview=findViewById(R.id.wrapper_listview);
                    wrapper_listview.bringToFront();
                    ct++;
                }

            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.updateEvent:
                        Intent intent1 =new Intent(KobetsuEvent.this,UpdateEvent.class);
                        intent1.putExtra("clicked_id", clicked_id);
                        startActivity(intent1);
                        break;
                    case R.id. kobetsuaddEvent:
                        Intent intent2 =new Intent(KobetsuEvent.this,KobetsuAddEvent.class);
                        intent2.putExtra("clicked_id", clicked_id);
                        startActivity(intent2);
                        break;
                    case R.id. deleteEvent:

                        showFragmentDialog(EVENTDIALOG);
                        deleteEvent();
                        break;

                    case R.id. reload:
                        reload();
                        break;

                    default:
                        break;
                }
                return false;
            }
        });


    }
    private void saveDate(){
        //      MainAcitivityに総計を送る

        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        String query2 = "update "+ UserContract.Users.TABLE_NAME
                +" set " + UserContract.Users.COL_TICKET+" = "+Integer.parseInt(col_ticket.getText().toString())
                + " where " + UserContract.Users._ID + " = "+clicked_id+";";

        db.execSQL(query2);

    }
    private void deleteEvent(){
        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        String query2 = "delete from "+ UserContract.Users.TABLE_NAME
                + " where " + UserContract.Users._ID + " = "+clicked_id+";";

        db.execSQL(query2);

        String query3 = "delete from "+ UserContract.Kobetsu.TABLE_NAME
                + " where " + UserContract.Kobetsu.EVENT_ID + " = "+clicked_id+";";

        db.execSQL(query3);
    }


}




























