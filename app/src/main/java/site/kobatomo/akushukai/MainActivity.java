package site.kobatomo.akushukai;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import site.kobatomo.akushukai.Model.MainModel;
import site.kobatomo.akushukai.Model.UserOpenHelper;


public class MainActivity extends FragmentActivity {
//
//    private static ArrayList<Integer> id = new ArrayList<Integer>();
//    private static ArrayList<String> type = new ArrayList<String>();
//    private static ArrayList<String> date = new ArrayList<String>();
//    private static ArrayList<String> location = new ArrayList<String>();
//    private static ArrayList<String> position = new ArrayList<String>();
//    private static ArrayList<String> ticket = new ArrayList<String>();
    private Drawable savedtypecolor;
    private CharSequence savedpackage_title;
    private Drawable savedtitle;
    private Drawable savedlistview;
    private Drawable savedclick_over_inner;
    private ArrayList<String> param = new ArrayList<String>();
    private View typecolor;
    private LinearLayout click_over_inner;
    private ImageView delete;
    private TextView package_title;
    private RelativeLayout title;
    private ImageView edit;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private TextView mTextView;
    private DrawerLayout drawer;
    private ImageView menu;
    // Y軸最低スワイプ距離
    private int SWIPE_MIN_DISTANCE = 50;
    // Y軸最低スワイプスピード
    private int SWIPE_THRESHOLD_VELOCITY = 200;
    // X軸の移動距離 これ以上なら縦移動を判定しない
    private int SWIPE_MAX_OFF_PATH = 200;
//    private LinearLayout wrapper_listview;


//    public static ArrayList<String> getType() {
//        return type;
//    }
//
//    public static ArrayList<String> getDate() {
//        return date;
//    }
//
//    public static ArrayList<String> getLocation() {
//        return location;
//    }
//
//    public static ArrayList<String> getTicket() {
//        return ticket;
//    }
//
//    public static ArrayList<Integer> getId() {
//        return id;
//    }

    private GestureDetector mGestureDetector;
    private ListView listview;

    private Button ok;
    private int clicked_position;
    private String clicked_id;
    final int TEST_DIALOG = 0;
    private int ct=0;
    private static MainActivity instance = null;


    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        title=findViewById(R.id.title);
        final LinearLayout wrapper_listview = findViewById(R.id.wrapper_listview);

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
                    case R.id. nav_application:
                        Intent intent =new Intent(MainActivity.this,MenuEvent.class);
                        startActivity(intent);
                        break;
                        case R.id. addEvent:
                            Intent intent2 =new Intent(MainActivity.this,AddEvent.class);
                            startActivity(intent2);
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







/*イベント追加ボタンの実装*/
        FloatingActionButton addEvent = findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, AddEvent.class);
                startActivity(intent2);
            }
        });

        MainModel mainModel = new MainModel();
        mainModel.getall();


/////*データベースから取得*/
//        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//
//        Cursor c = null;
//        c = db.query(
//                UserContract.Users.TABLE_NAME,
//                null, // fields
//                null, // where
//                null, // where arg
//                null, // groupBy
//                null, // having
//                null // order by
//        );
///*一旦リセットする*/
//        id.clear();
//        type.clear();
//        date.clear();
//        location.clear();
//        ticket.clear();
///*一旦リセットする*/
//
//        while (c.moveToNext()) {
//            id.add(c.getInt(c.getColumnIndex(UserContract.Users._ID)));
//            type.add(c.getString(c.getColumnIndex(UserContract.Users.COL_TYPE)));
//            date.add(c.getString(c.getColumnIndex(UserContract.Users.COL_DATE)));
//            location.add(c.getString(c.getColumnIndex(UserContract.Users.COL_LOC)));
//            ticket.add(c.getString(c.getColumnIndex(UserContract.Users.COL_TICKET)));
//
//        }


/*リストビュー*/
        listview = findViewById(R.id.listview);
        KeyakiAdapter adapter = new KeyakiAdapter(this);
        listview.setAdapter(adapter);

        listview.setClickable(true);
        listview.setLongClickable(true);

//        シングルクリック処理
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView type=findViewById(R.id.type);
                        ListView view3 = (ListView) adapterView;
                String itemValue = (String)view3.getItemAtPosition(position);

                ListView listView3 = (ListView) adapterView;
                // クリックされたアイテムを取ます
                clicked_position = listView3.getPositionForView(view);
                MainModel keyaki = new MainModel();
                clicked_id = keyaki.getId(clicked_position);


                if(itemValue.equals("個別")){
                            Intent intent4 = new Intent(MainActivity.this, KobetsuEvent.class);
                            intent4.putExtra("clicked_id", clicked_id);
                            startActivity(intent4);
                        }

                        if(itemValue.equals("全国")){
                            Intent intent5 = new Intent(MainActivity.this, ZenkokuEvent.class);
                            intent5.putExtra("clicked_id", clicked_id);
                            startActivity(intent5);
                        }
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * フラグメントダイアログを表示する。
     */

    public void showFragmentDialog(int id) {
        switch (id) {
            case TEST_DIALOG:
                DialogFragment dialogFragment = new MainDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
                        }
    }

    public void long_getView(AdapterView<?> arg0, View arg1, int position, long id){
        ListView view2 = (ListView) arg0;
        clicked_position = view2.getPositionForView(arg1);
        typecolor = listview.getChildAt(position).findViewById(R.id.typecolor);
        click_over_inner = listview.getChildAt(position).findViewById(R.id.click_over_inner);
        delete = findViewById(R.id.delete);
        package_title = findViewById(R.id.package_title);
        title = findViewById(R.id.title);
        edit = findViewById(R.id.edit);
    }


    /**
     * OKボタンが押されたことを感知する。
     */


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode != KeyEvent.KEYCODE_BACK){return super.onKeyDown(keyCode, event);
        }else{return false;}
    }


    public static class MainDialogFragment extends TestDialogFragment
    {
        public void onTestDialogOKClick() {
            MainActivity activity=(MainActivity)getActivity();
            activity.deleteEvent();
        }

    }


    public void deleteEvent(){
        /*データベースの削除処理を行いたい*/
        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        MainModel keyaki = new MainModel();


        for (int i = 0; i < param.size(); i++) {
            int clicked_position = Integer.parseInt(param.get(i));
            clicked_id = keyaki.getId(clicked_position);
            int deletedCount = db.delete(UserContract.Users.TABLE_NAME, UserContract.Users._ID + " =? ", new String[]{clicked_id}
            );
        }
        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }

public void reload() {
    Intent intent = getIntent();
    overridePendingTransition(0, 0);
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    finish();
    overridePendingTransition(0, 0);
    startActivity(intent);
}



}


