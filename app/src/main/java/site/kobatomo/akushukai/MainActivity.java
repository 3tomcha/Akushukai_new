package site.kobatomo.akushukai;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.GestureDetector;
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


public class MainActivity extends FragmentActivity {

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


public void reload() {
    Intent intent = getIntent();
    overridePendingTransition(0, 0);
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    finish();
    overridePendingTransition(0, 0);
    startActivity(intent);
}



}


