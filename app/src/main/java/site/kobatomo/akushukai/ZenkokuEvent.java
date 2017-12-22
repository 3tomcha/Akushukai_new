package site.kobatomo.akushukai;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
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

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tomoya on 2017/11/28.
 */

public class ZenkokuEvent extends FragmentActivity {


    private String clicked_id;
    private static ArrayList<MyItem> arr1 = new ArrayList<MyItem>();
    private static ArrayList<MyItem> arr2 = new ArrayList<MyItem>();
    private static ArrayList<MyItem> arr3 = new ArrayList<MyItem>();
    private static ArrayList<MyItem> arr4 = new ArrayList<MyItem>();
    private static ArrayList<MyItem> arr5 = new ArrayList<MyItem>();
    private ArrayList<String> param = new ArrayList<String>();
    private View typecolor;
    private LinearLayout click_over_inner;
    private ImageView delete;
    private TextView package_title;
    private RelativeLayout title;
    private ImageView edit;

    private ArrayList<ArrayList<String>> outer=new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> outer_outer=new ArrayList<ArrayList<String>>();
    private ArrayList<String> inner=new ArrayList<String>();


    private int sum_busuu;
    private ImageView member_img;
    private GestureDetector mGestureDetector;
    private ListView zenkoku_list;

    final int MEMBERDIALOG = 0;
    final int EVENTDIALOG = 1;
    private String delete_member;
    private TextView kari_col_ticket;
    private TextView col_ticket;
    private int kari_busuu;
    private DrawerLayout drawer;
    private int ct=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zenkoku_event);

        setTitle();
        setNavigetion();


        Intent intent2 = getIntent();
        clicked_id = intent2.getStringExtra("clicked_id");


//        クリックされたIDを元に情報を検索する(MainActivityより）
        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getReadableDatabase();

        String query6 = "select * from " + UserContract.Users.TABLE_NAME + " where " + UserContract.Users._ID + "=" + Integer.parseInt(clicked_id);
        Cursor cursor6 = db.rawQuery(query6, null);
        if (cursor6.moveToFirst()) {
            do {
                TextView date_z = findViewById(R.id.date_event);
                date_z.setText(cursor6.getString(cursor6.getColumnIndex(UserContract.Users.COL_DATE)));
                TextView type_z = findViewById(R.id.type_event);
                type_z.setText(cursor6.getString(cursor6.getColumnIndex(UserContract.Users.COL_TYPE)));
                TextView location_z = findViewById(R.id.location_event);
                location_z.setText(cursor6.getString(cursor6.getColumnIndex(UserContract.Users.COL_LOC)));
                TextView typecolor = findViewById(R.id.typecolor);
                    type_z.setBackgroundResource(R.color.zenkokucolor);
                    typecolor.setBackgroundResource(R.color.zenkokucolor);
            } while (cursor6.moveToNext());
        }



        /*イベント追加ボタンの実装*/
        FloatingActionButton addEvent = findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            Intent intent2 = new Intent(ZenkokuEvent.this, ZenkokuAddEvent.class);
                                            intent2.putExtra("clicked_id", clicked_id);
                                            saveData();
                                            startActivity(intent2);
                                        }
                                    });

//      同様の処理をリストビューをクリックしてもできるようにする
        LinearLayout et_zenkoku_event = findViewById(R.id.mv_event);

        et_zenkoku_event.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent2 = new Intent(ZenkokuEvent.this, ZenkokuAddEvent.class);
                    intent2.putExtra("clicked_id", clicked_id);
                    saveData();
                    startActivity(intent2);
                }
                });




        //        MoreAddEventで追加したデータベースからメンバーの登録を取得
        String query1 = "select * from " + UserContract.Zenkoku.TABLE_NAME + " where " + UserContract.Zenkoku.EVENT_ID + " = " + clicked_id;
        Query query = new Query();
        query.setQuery(query1, arr1);
        zenkoku_list = (ListView) findViewById(R.id.zenkoku_list);
        zenkoku_list.setAdapter(new MyAdapter(this, arr1));



        zenkoku_list.setLongClickable(true);
        zenkoku_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {

                long_getView(arg0,arg1,position,id);
                showFragmentDialog(MEMBERDIALOG);

                return true;
            }
        });
        setColTicket();


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
            /*画像に関しても加える*/

            this.id = new Date().getTime();
        }
        public MyItem(String member,String busuu) {
            super();
            this.member = member;
            this.busuu = busuu;
            /*画像に関しても加える*/
            this.id = new Date().getTime();
        }

        public String getmember() {
            return member;
        }
        public String geturl() {
            return url;
        }
        public String getbusuu() {
            return busuu;
        }
        public long getId() {
            return id;
        }

    }


    //データMyItemを管理するアダプタークラス
    class MyAdapter extends BaseAdapter {
        private ArrayList<MyItem> data = null;
        private Context context = null;
        LayoutInflater layoutInflater = null;
        private ViewHolder vh;
        private View convertview;

        class ViewHolder{
        ImageView icon_member_zenkoku;
        TextView member_zenkoku;
        TextView busuu_zenkoku;
        LinearLayout plus_ticket_zenkoku;
        LinearLayout minus_ticket_zenkoku;
        }



        public MyAdapter(Context context, ArrayList<ZenkokuEvent.MyItem> data) {
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
        public View getView(final int position, View ConvertView, final ViewGroup parent) {
            vh =null;
            convertview = ConvertView;

            if (convertview == null) {
                convertview = layoutInflater.inflate(R.layout.list_zenkoku_event, parent, false);
                vh =new ViewHolder();

                vh.icon_member_zenkoku=(ImageView) convertview.findViewById(R.id.icon_member_zenkoku);
                vh.member_zenkoku = (TextView) convertview.findViewById(R.id.member_zenkoku);
                vh.busuu_zenkoku = (TextView) convertview.findViewById(R.id.busuu_zenkoku);
                vh.plus_ticket_zenkoku = (LinearLayout) convertview.findViewById(R.id.plus_ticket_zenkoku);
                vh.minus_ticket_zenkoku = (LinearLayout) convertview.findViewById(R.id.minus_ticket_zenkoku);


                convertview.setTag(vh);

            }else {
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


            if(!TextUtils.isEmpty(data.get(position).geturl())) {
                    Glide.with(ZenkokuEvent.this).
                            load(data.get(position).geturl()).into(vh.icon_member_zenkoku);
            }
            if(!TextUtils.isEmpty(data.get(position).getmember())) {
                vh.member_zenkoku.setText(data.get(position).getmember());
            }
//            else{
//                vh.member_zenkoku.setText("未定");
//            }



            // アイテムクリック時ののイベントを追加
            final ListView zenkoku_list2=findViewById(R.id.zenkoku_list);
            zenkoku_list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position, long id) {

                    ListView list5=findViewById(R.id.zenkoku_list);
                    View childlist = list5.getChildAt(position);
                    TextView busuu_zenkoku5=childlist.findViewById(R.id.busuu_zenkoku);
//
//                    TextView member_zenkoku=childlist.findViewById(R.id.member_zenkoku);
//                    String string_member_zenkoku=member_zenkoku.getText().toString();
                    int busuu=Integer.parseInt(busuu_zenkoku5.getText().toString());

                    kari_col_ticket=findViewById(R.id.kari_col_ticket);
                    kari_busuu = Integer.parseInt(kari_col_ticket.getText().toString());




                    switch (view.getId()) {
                        case R.id.plus_ticket_zenkoku:
                            busuu++;
                            kari_busuu++;
                            busuu_zenkoku5.setText(String.valueOf(busuu));
                            kari_col_ticket.setText(String.valueOf(kari_busuu));

                            break;

                        case R.id.minus_ticket_zenkoku:
                            busuu--;
                            kari_busuu--;
                            busuu_zenkoku5.setText(String.valueOf(busuu));
                            kari_col_ticket.setText(String.valueOf(kari_busuu));

                            Log.d("click","遷移先マイナス");
                            break;
                    }


                }
            });


            return convertview;

        }
    }
        /*アダプター*/


    //    データベース処理
    class Query {
        private String added_url;
        void setQuery(String query, ArrayList arr) {
            arr.clear();
            UserOpenHelper userOpenHelper = new UserOpenHelper(ZenkokuEvent.this);
            SQLiteDatabase db = userOpenHelper.getReadableDatabase();
            Cursor c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    String added_member = c.getString(c.getColumnIndex(UserContract.Zenkoku.MEMBER));
                    String added_busuu = c.getString(c.getColumnIndex(UserContract.Zenkoku.BUSUU));
                    if(added_member.equals("未定")) {
                        arr.add(new MyItem(added_member,added_busuu));
                    /*画像を探す処理も加える*/
                    }
                    else{
                        added_url = search_Memberimg(added_member, db);
                        arr.add(new MyItem(added_member, added_busuu, added_url));
                    }
                    sum_busuu += Integer.parseInt(added_busuu);

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
            Intent intent= new Intent(ZenkokuEvent.this,MainActivity.class);
            saveData();
            startActivity(intent);
            return true;
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

//    数量を変更した時のデータベース処理
private void saveData() {
    String string_member_zenkoku="未定";
    UserOpenHelper userOpenHelper = new UserOpenHelper(this);
    SQLiteDatabase db = userOpenHelper.getWritableDatabase();

    ListView listview=findViewById(R.id.zenkoku_list);
    MyAdapter adapter=(MyAdapter) listview.getAdapter();

    for (int i=0; i<adapter.getCount(); i++) {
        View childlist = listview.getChildAt(i);
        TextView busuu_zenkoku5 = childlist.findViewById(R.id.busuu_zenkoku);
        String string_busuu = busuu_zenkoku5.getText().toString();
        TextView member_zenkoku = childlist.findViewById(R.id.member_zenkoku);
        if (!TextUtils.isEmpty(member_zenkoku.getText().toString())) {
            string_member_zenkoku = member_zenkoku.getText().toString();
        }

        String query1 = "update "+ UserContract.Zenkoku.TABLE_NAME
                +" set " + UserContract.Zenkoku.BUSUU+" = '"+string_busuu
                + "' where " + UserContract.Zenkoku.EVENT_ID + " = '"+clicked_id+"' and "+UserContract.Zenkoku.MEMBER+ " = '"+string_member_zenkoku+"';";

        db.execSQL(query1);


//      MainAcitivityに総計を送る
        String query2 = "update "+ UserContract.Users.TABLE_NAME
                +" set " + UserContract.Users.COL_TICKET+" = "+Integer.parseInt(kari_col_ticket.getText().toString())
                + " where " + UserContract.Users._ID + " = "+clicked_id+";";

        db.execSQL(query2);
    }
        }

    public void long_getView(AdapterView<?> arg0, View arg1, int position, long id){

        ListView listview=findViewById(R.id.zenkoku_list);
        MyAdapter adapter=(MyAdapter) listview.getAdapter();
        View childlist = listview.getChildAt(position);
//        メンバーを取得する
        TextView tv_delete_member = (TextView)childlist.findViewById(R.id.member_zenkoku);
        delete_member=tv_delete_member.getText().toString();

    }


    public static class ZenkokuDialogFragment extends TestDialogFragment
    {
        public void onTestDialogOKClick() {
            ZenkokuEvent activity=(ZenkokuEvent)getActivity();
            activity.deleteMember();
        }

    }
    public static class ZenkokuEventDialogFragment extends TestDialogFragment
    {
        public void onTestDialogOKClick() {
            ZenkokuEvent activity=(ZenkokuEvent)getActivity();
            activity.deleteEvent();
        }

    }




    public void deleteMember(){
        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        String sql="delete from "+UserContract.Zenkoku.TABLE_NAME+
                " where "+UserContract.Zenkoku.EVENT_ID+"="+clicked_id+
                " and "+UserContract.Zenkoku.MEMBER+"="+"'"+delete_member+"';";

        db.execSQL(sql);
//        リロードしたい

        reload();
    }

    public void showFragmentDialog(int id) {
        switch (id) {
            case MEMBERDIALOG:
                DialogFragment dialogFragment = new ZenkokuDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
            case EVENTDIALOG:
                    ZenkokuEventDialogFragment eventdialogFragment = new ZenkokuEventDialogFragment();
                    eventdialogFragment.show(getSupportFragmentManager(), "fragment_dialog");

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
    private void setTitle(){
        TextView package_title = findViewById(R.id.package_title);
        package_title.setText(R.string.package_more);
        RelativeLayout title=findViewById(R.id.title);
        title.setBackgroundResource(R.color.zenkokucolor);

    }
    private void setColTicket(){
        col_ticket = findViewById(R.id.col_ticket);
        col_ticket.setText(String.valueOf(sum_busuu));
        kari_col_ticket = findViewById(R.id.kari_col_ticket);
        kari_col_ticket.setVisibility(View.VISIBLE);
        TextView slash=findViewById(R.id.slash);
        slash.setVisibility(View.VISIBLE);
        kari_col_ticket.setVisibility(View.VISIBLE);
        kari_col_ticket.setText(String.valueOf(sum_busuu));
    }
    private void setNavigetion(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ImageView menu=findViewById(R.id.menu);
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
                        Intent intent1 =new Intent(ZenkokuEvent.this,UpdateEvent.class);
                        intent1.putExtra("clicked_id", clicked_id);
                        startActivity(intent1);
                        break;
                    case R.id. kobetsuaddEvent:
                        Intent intent2 =new Intent(ZenkokuEvent.this,ZenkokuAddEvent.class);
                        intent2.putExtra("clicked_id", clicked_id);
                        startActivity(intent2);
                        break;
                    case R.id. deleteEvent:
                        showFragmentDialog(EVENTDIALOG);
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
    private void deleteEvent(){
        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        String query2 = "delete from "+ UserContract.Users.TABLE_NAME
                + " where " + UserContract.Users._ID + " = "+clicked_id+";";

        db.execSQL(query2);

        String query3 = "delete from "+ UserContract.Zenkoku.TABLE_NAME
                + " where " + UserContract.Zenkoku.EVENT_ID + " = "+clicked_id+";";

        db.execSQL(query3);



        Intent intent3 =new Intent(ZenkokuEvent.this,MainActivity.class);
        startActivity(intent3);
    }




}












