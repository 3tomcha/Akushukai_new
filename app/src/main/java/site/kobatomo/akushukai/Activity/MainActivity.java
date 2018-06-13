package site.kobatomo.akushukai.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import site.kobatomo.akushukai.MainAdapter;
import site.kobatomo.akushukai.Member.MainMember;
import site.kobatomo.akushukai.Model.MainModel;
import site.kobatomo.akushukai.R;
import site.kobatomo.akushukai.UserContract;


public class MainActivity extends FragmentActivity {

    private static MainActivity instance = null;
    private ListView listView;


    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        listView = findViewById(R.id.listview);

        MainModel mainModel = new MainModel(MainActivity.getInstance());
        Cursor c = mainModel.searchEventData();
        c.moveToFirst();

        List<MainMember> eventList = new ArrayList<>();

        do {
            MainMember mainMember = new MainMember();
/*
メソッドが重複するためラムダ式で管理
 */
            CursorInterface h = (cursor, id) -> cursor.getString(cursor.getColumnIndex(id));

            mainMember.setEventId(h.method(c,UserContract.Event.EVENT_ID));
            mainMember.setPlace(h.method(c,UserContract.Event.PLACE));
            mainMember.setType(h.method(c,UserContract.Event.COL_TYPE));
            mainMember.setYear(h.method(c,UserContract.Event.YEAR));
            mainMember.setMonth(h.method(c,UserContract.Event.MONTH));
            mainMember.setDay(h.method(c,UserContract.Event.DAY));
            mainMember.setBusuu(h.method(c,UserContract.Event.COL_TICKET));

            eventList.add(mainMember);

        } while (c.moveToNext());

        MainAdapter mainAdapter = new MainAdapter(MainActivity.getInstance(), eventList);
        listView.setAdapter(mainAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainMember target = (MainMember) parent.getItemAtPosition(position);
                Intent intent = (target.getType().equals("全国"))?
                new Intent(MainActivity.getInstance(),ZenkokuEvent.class):
                        new Intent(MainActivity.getInstance(),KobetsuEvent.class);
                intent.putExtra("eventId",target.getEventId());
                startActivity(intent);
            }
        });

        CursorInterface it = (name, n) -> {
            return "Hello " + name + n + "!";
        };



        FloatingActionButton addEvent = findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.getInstance(), AddEventActivity.class);
                startActivity(intent2);
            }
        });
    }






//
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//        title=findViewById(R.id.title);
//        final LinearLayout wrapper_listview = findViewById(R.id.wrapper_listview);
//
//        menu=findViewById(R.id.menu);
//        menu.setClickable(true);
//        menu.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if(ct%2==0) {
//                    drawer.openDrawer(GravityCompat.START);
//                    drawer.bringToFront();
//                    ct++;
//                }
//                else{
//                    drawer.closeDrawer(GravityCompat.START);
//                    wrapper_listview.bringToFront();
//                    ct++;
//                }
//
//            }
//        });
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id. nav_application:
//                        Intent intent =new Intent(MainActivity.this,MenuEvent.class);
//                        startActivity(intent);
//                        break;
//                        case R.id. addEvent:
//                            Intent intent2 =new Intent(MainActivity.this,AddEventActivity.class);
//                            startActivity(intent2);
//                            break;
//                    case R.id. reload:
//                        reload();
//                        break;
//
//
//
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
//
//
//
//
//
//
//
///*イベント追加ボタンの実装*/

//
//        MainModel mainModel = new MainModel();
//        mainModel.getall();
//
//
///*リストビュー*/
//
//        listview.setClickable(true);
//        listview.setLongClickable(true);
//
////        シングルクリック処理
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                TextView type=findViewById(R.id.type);
//                        ListView view3 = (ListView) adapterView;
//                String itemValue = (String)view3.getItemAtPosition(position);
//
//                ListView listView3 = (ListView) adapterView;
//                // クリックされたアイテムを取ます
//                clicked_position = listView3.getPositionForView(view);
//                MainModel keyaki = new MainModel();
//                clicked_id = keyaki.getId(clicked_position);
//
//
//                if(itemValue.equals("個別")){
//                            Intent intent4 = new Intent(MainActivity.this, KobetsuEvent.class);
//                            intent4.putExtra("clicked_id", clicked_id);
//                            startActivity(intent4);
//                        }
//
//                        if(itemValue.equals("全国")){
//                            Intent intent5 = new Intent(MainActivity.this, ZenkokuEvent.class);
//                            intent5.putExtra("clicked_id", clicked_id);
//                            startActivity(intent5);
//                        }
//            }
//        });






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


public void reload() {
    Intent intent = getIntent();
    overridePendingTransition(0, 0);
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    finish();
    overridePendingTransition(0, 0);
    startActivity(intent);
}


}
interface CursorInterface {
    // 抽象メソッド
    public String method(Cursor c, String EVENTID);
}


