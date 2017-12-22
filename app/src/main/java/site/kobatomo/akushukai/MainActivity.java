package site.kobatomo.akushukai;

import android.content.Intent;
import android.database.Cursor;
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


public class MainActivity extends FragmentActivity {

    private static ArrayList<Integer> id = new ArrayList<Integer>();
    private static ArrayList<String> type = new ArrayList<String>();
    private static ArrayList<String> date = new ArrayList<String>();
    private static ArrayList<String> location = new ArrayList<String>();
    private static ArrayList<String> position = new ArrayList<String>();
    private static ArrayList<String> ticket = new ArrayList<String>();
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


    public static ArrayList<String> getType() {
        return type;
    }

    public static ArrayList<String> getDate() {
        return date;
    }

    public static ArrayList<String> getLocation() {
        return location;
    }

    public static ArrayList<String> getTicket() {
        return ticket;
    }

    public static ArrayList<Integer> getId() {
        return id;
    }

    private GestureDetector mGestureDetector;
    private ListView listview;

    private Button ok;
    private int clicked_position;
    private String clicked_id;
    final int TEST_DIALOG = 0;
    private int ct=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inserturl();



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


/*データベースから取得*/
        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        Cursor c = null;
        c = db.query(
                UserContract.Users.TABLE_NAME,
                null, // fields
                null, // where
                null, // where arg
                null, // groupBy
                null, // having
                null // order by
        );
/*一旦リセットする*/
        id.clear();
        type.clear();
        date.clear();
        location.clear();
        ticket.clear();
/*一旦リセットする*/

        while (c.moveToNext()) {
            id.add(c.getInt(c.getColumnIndex(UserContract.Users._ID)));
            type.add(c.getString(c.getColumnIndex(UserContract.Users.COL_TYPE)));
            date.add(c.getString(c.getColumnIndex(UserContract.Users.COL_DATE)));
            location.add(c.getString(c.getColumnIndex(UserContract.Users.COL_LOC)));
            ticket.add(c.getString(c.getColumnIndex(UserContract.Users.COL_TICKET)));

        }


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
                KeyakiData keyaki = new KeyakiData();
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

        KeyakiData keyaki = new KeyakiData();


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

private void inserturl(){
    UserOpenHelper userOpenHelper = new UserOpenHelper(this);
    SQLiteDatabase db = userOpenHelper.getWritableDatabase();

    String sql=
            "insert into member (name, url) values " +
                    "('石森 虹花','http://cdn.keyakizaka46.com/images/14/b73/9d17d9445c63ffe04548fe14559c7/400_320_102400.jpg'),"+
                    "('今泉 佑唯','http://cdn.keyakizaka46.com/images/14/514/2fe0c906fce3bf0869c19c764b345/400_320_102400.jpg'),"+
                    "('上村 莉菜','http://cdn.keyakizaka46.com/images/14/1b0/5ce96a28b9735269eef3a59664836/400_320_102400.jpg'),"+
                    "('尾関 梨香','http://cdn.keyakizaka46.com/images/14/0c0/71a66d1735d641922e5c70ee8e23c/400_320_102400.jpg'),"+
                    "('織田 奈那','http://cdn.keyakizaka46.com/images/14/3b5/90f1f562d722acad704949e8d99a3/400_320_102400.jpg'),"+
                    "('小池 美波','http://cdn.keyakizaka46.com/images/14/c6f/f17b468961ce10adbf62ed3f93387/400_320_102400.jpg'),"+
                    "('小林 由依','http://cdn.keyakizaka46.com/images/14/f52/b99b43f53c66f566d2d49ca17e47e/400_320_102400.jpg'),"+
                    "('齋藤 冬優花','http://cdn.keyakizaka46.com/images/14/557/5508e16189be2899fbdd7a3b7972b/400_320_102400.jpg'),"+
                    "('佐藤 詩織','http://cdn.keyakizaka46.com/images/14/ea6/0f77ddfeedade54512313e3c64c16/400_320_102400.jpg'),"+
                    "('志田 愛佳','http://cdn.keyakizaka46.com/images/14/02f/bad0138f8e8d9e1f270d0b9c01933/400_320_102400.jpg'),"+
                    "('菅井 友香','http://cdn.keyakizaka46.com/images/14/605/36078236b2fd56c893495324e9b5b/400_320_102400.jpg'),"+
                    "('鈴本 美愉','http://cdn.keyakizaka46.com/images/14/435/736cb31e2e76cf7c44ff019c87a61/400_320_102400.jpg'),"+
                    "('長沢 菜々香','http://cdn.keyakizaka46.com/images/14/1a5/3514149db8409daf3644ef869e8e1/400_320_102400.jpg'),"+
                    "('長濱 ねる','http://cdn.keyakizaka46.com/images/14/d2d/51c8eed9384bce8a7e55174b04d6e/400_320_102400.jpg'),"+
                    "('土生 瑞穂','http://cdn.keyakizaka46.com/images/14/776/d652723f04eabb2c364c55efae36d/400_320_102400.jpg'),"+
                    "('原田 葵','http://cdn.keyakizaka46.com/images/14/f0c/7ba24b8bc92d67990a7122c88519b/400_320_102400.jpg'),"+
                    "('平手 友梨奈','http://cdn.keyakizaka46.com/images/14/bd8/e46f9c4686d1767cde04cd30c4ec8/400_320_102400.jpg'),"+
                    "('守屋 茜','http://cdn.keyakizaka46.com/images/14/71e/0743e5b22fcb7a02d3ba4f799b5c3/400_320_102400.jpg'),"+
                    "('米谷 奈々未','http://cdn.keyakizaka46.com/images/14/2e1/67354e8215e4f64a71118f6560e04/400_320_102400.jpg'),"+
                    "('渡辺 梨加','http://cdn.keyakizaka46.com/images/14/161/a9757839b899cf1f91876fe1f7d05/400_320_102400.jpg'),"+
                    "('渡邉 理佐','http://cdn.keyakizaka46.com/images/14/094/eae807fe9e36d79d672b1364d8fdc/400_320_102400.jpg'),"+
                    "('井口 眞緒','http://cdn.keyakizaka46.com/images/14/04c/14dacd30f2b6a7ad017a66c8c1222/400_320_102400.jpg'),"+
                    "('潮 紗理菜','http://cdn.keyakizaka46.com/images/14/1c2/06ad8785a584e4e2cebf35c2ef36b/400_320_102400.jpg'),"+
                    "('柿崎 芽実','http://cdn.keyakizaka46.com/images/14/e43/ef65cde1edc50b5cc8dd5d6aefb77/400_320_102400.jpg'),"+
                    "('影山 優佳','http://cdn.keyakizaka46.com/images/14/fdc/dd24d840f117af6b3f07ac626d673/400_320_102400.jpg'),"+
                    "('加藤 史帆','http://cdn.keyakizaka46.com/images/14/d5a/eff6a3b75c9e64505228eef211dcb/400_320_102400.jpg'),"+
                    "('齊藤 京子','http://cdn.keyakizaka46.com/images/14/385/a304de67244d04fdd8b715c77fc0e/400_320_102400.jpg'),"+
                    "('佐々木 久美','http://cdn.keyakizaka46.com/images/14/03b/9bf55f7bae5fd014bc685da912aef/400_320_102400.jpg'),"+
                    "('佐々木 美玲','http://cdn.keyakizaka46.com/images/14/849/c4bd22af347348079827766d69e2b/400_320_102400.jpg'),"+
                    "('高瀬 愛奈','http://cdn.keyakizaka46.com/images/14/8c9/6cd50d9624fa417e1258443fd69b7/400_320_102400.jpg'),"+
                    "('高本 彩花','http://cdn.keyakizaka46.com/images/14/b9e/157345429736f21d921182ffa9c44/400_320_102400.jpg'),"+
                    "('東村 芽依','http://cdn.keyakizaka46.com/images/14/2ca/e19b0091acdc6529dea8aa7a1cbad/400_320_102400.jpg'),"+
                    "('金村 美玖','http://cdn.keyakizaka46.com/images/14/920/176d0228e3a3d492eb7f5d126ff72/400_320_102400.jpg'),"+
                    "('河田 陽菜','http://cdn.keyakizaka46.com/images/14/66b/97b189aec9b786ef4efc7057824d4/400_320_102400.jpg'),"+
                    "('小坂 菜緒','http://cdn.keyakizaka46.com/images/14/451/b7f11152b13ac995924cc0bf9e7cf/400_320_102400.jpg'),"+
                    "('富田 鈴花','http://cdn.keyakizaka46.com/images/14/ff1/63721810b007df1c0fd3caac8ec27/400_320_102400.jpg'),"+
                    "('丹生 明里','http://cdn.keyakizaka46.com/images/14/3b4/7e8818bad76a74acafca75980f68d/400_320_102400.jpg'),"+
                    "('濱岸 ひより','http://cdn.keyakizaka46.com/images/14/94c/432894fe7fadee21722a7e6c716ac/400_320_102400.jpg'),"+
                    "('松田 好花','http://cdn.keyakizaka46.com/images/14/f3f/a327766895ecd1161d6fbbc51a487/400_320_102400.jpg'),"+
                    "('宮田 愛萌','http://cdn.keyakizaka46.com/images/14/b2f/5a4a20677b782f2e364c5cfb6bb57/400_320_102400.jpg'),"+
                    "('渡邉 美穂','http://cdn.keyakizaka46.com/images/14/714/72cbf1aea6598bb3397d088744015/400_320_102400.jpg')";

    db.execSQL(sql);
}









}


