package site.kobatomo.akushukai.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import site.kobatomo.akushukai.Adapter.KobetsuAdapter;
import site.kobatomo.akushukai.Member.KobetsuMember;
import site.kobatomo.akushukai.Model.KobetsuModel;
import site.kobatomo.akushukai.R;
import site.kobatomo.akushukai.UserContract;

/**
 * Created by bicpc on 2017/11/10.
 */

public class KobetsuEvent extends FragmentActivity {

    private ListView list_1bu;
    private ListView list_2bu;
    private ListView list_3bu;
    private ListView list_4bu;
    private ListView list_5bu;
    private ListView list_6bu;
    private static KobetsuEvent instance = null;

    public static KobetsuEvent getInstance() {
        return instance;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kobetsu_event);

        instance = this;

/*
データベースから、イベント情報を取得する
 */

        Intent intent = getIntent();
//        int eventId = intent.getIntExtra("eventId", 0);
        String eventId = intent.getStringExtra("eventId");
        KobetsuModel kobetsuModel = new KobetsuModel(KobetsuEvent.getInstance());

        Cursor eventCursor = kobetsuModel.searchEventData(eventId);
        eventCursor.moveToFirst();
        String year = eventCursor.getString(eventCursor.getColumnIndex(UserContract.Event.YEAR));
        String month = eventCursor.getString(eventCursor.getColumnIndex(UserContract.Event.MONTH));
        String day = eventCursor.getString(eventCursor.getColumnIndex(UserContract.Event.DAY));
        String type = eventCursor.getString(eventCursor.getColumnIndex(UserContract.Event.COL_TYPE));
        String place = eventCursor.getString(eventCursor.getColumnIndex(UserContract.Event.PLACE));


/*
データベースから、メンバー情報を取得する
 */

        Cursor memberCursor = kobetsuModel.searchMemberData(eventId);
        memberCursor.moveToFirst();

        List<String> urlList = new ArrayList<>();
        List<String> memberList = new ArrayList<>();
        List<String> nanbuList = new ArrayList<>();
        List<String> busuuList = new ArrayList<>();

        do{
            String url = memberCursor.getString(memberCursor.getColumnIndex(UserContract.Kobetsu.URL));
            String nanbu = memberCursor.getString(memberCursor.getColumnIndex(UserContract.Kobetsu.NANBU));
            String member = memberCursor.getString(memberCursor.getColumnIndex(UserContract.Kobetsu.MEMBER));
            String busuu = memberCursor.getString(memberCursor.getColumnIndex(UserContract.Kobetsu.BUSUU));
            urlList.add(url);
            nanbuList.add(nanbu);
            memberList.add(member);
            busuuList.add(member);

        }while(memberCursor.moveToNext());


/*
データ保持用のクラスにセットし、アダプターに当てはめることができるようにする
 */

        KobetsuMember kobetsuMember = new KobetsuMember();
        kobetsuMember.setUrl(urlList);
        kobetsuMember.setNanbu(nanbuList);
        kobetsuMember.setMember(memberList);
        kobetsuMember.setMaisuu(busuuList);


/*
イベント関連の見栄えをセットする
 */

        ((TextView) findViewById(R.id.date_event)).setText(year+"/"+month+"/"+day);
        ((TextView) findViewById(R.id.type_event)).setText(type);
        (findViewById(R.id.type_event)).setBackgroundResource(R.color.kobetsucolor);
        ((TextView) findViewById(R.id.location_event)).setText(place);



/*
メンバーの一覧表示処理
 */

//        list_1bu=findViewById(R.id.list_1bu);
//        list_2bu=findViewById(R.id.list_2bu);
//        list_3bu=findViewById(R.id.list_3bu);
//        list_4bu=findViewById(R.id.list_4bu);
//        list_5bu=findViewById(R.id.list_5bu);
//        list_6bu=findViewById(R.id.list_6bu);

        /*
        * */
        list_1bu=findViewById(R.id.list_1bu);
        list_1bu.setAdapter(new KobetsuAdapter(this, kobetsuMember));

/*
データ保持用のクラスをアダプタにセットする
 */

//        switch (kobetsuMember.getNanbu()) {
//            case("1部"):
//                (findViewById(R.id.TV1bu_kobetsu)).setVisibility(View.VISIBLE);
//                list_1bu.setAdapter(new KobetsuAdapter(this, kobetsuMember));
//                break;
//            case("2部"):
//                (findViewById(R.id.TV2bu_kobetsu)).setVisibility(View.VISIBLE);
//                list_2bu.setAdapter(new KobetsuAdapter(this, kobetsuMember));
//                break;
//            case("3部"):
//                (findViewById(R.id.TV3bu_kobetsu)).setVisibility(View.VISIBLE);
//                list_3bu.setAdapter(new KobetsuAdapter(this, kobetsuMember));
//                break;
//            case("4部"):
//                (findViewById(R.id.TV4bu_kobetsu)).setVisibility(View.VISIBLE);
//                list_4bu.setAdapter(new KobetsuAdapter(this, kobetsuMember));
//                break;
//            case("5部"):
//                (findViewById(R.id.TV5bu_kobetsu)).setVisibility(View.VISIBLE);
//                list_5bu.setAdapter(new KobetsuAdapter(this, kobetsuMember));
//                break;
//            case("6部"):
//                (findViewById(R.id.TV6bu_kobetsu)).setVisibility(View.VISIBLE);
//                list_6bu.setAdapter(new KobetsuAdapter(this, kobetsuMember));
//                break;
//
//        }


/*
合計枚数をセット
 */

        Cursor sumMaisuCursor = kobetsuModel.calcSumMaisu(eventId);
        sumMaisuCursor.moveToFirst();
        ((TextView)findViewById(R.id.col_ticket)).setText(sumMaisuCursor.getString(0));
        kobetsuModel.updateMaisu(eventId,sumMaisuCursor.getString(0));


        setTitle();


//        /*イベント追加ボタンの実装*/
        FloatingActionButton addEvent = findViewById(R.id.addEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(KobetsuEvent.getInstance(), KobetsuAddEventActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
            }
        });

    }



        public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        } else {
            Intent intent = new Intent(KobetsuEvent.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
    }
        private void setTitle() {
        TextView package_title = findViewById(R.id.package_title);
        package_title.setText(R.string.package_more);
        RelativeLayout title = findViewById(R.id.title);
        title.setBackgroundResource(R.color.kobetsucolor);
        TextView col_ticket=findViewById(R.id.col_ticket);
    }


//
//        loadDate();
//        loadDatabase();
//        setNavigetion();
//
//
//
//
//
//        LinearLayout et_kobetsu_event = findViewById(R.id.mv_event);
//
//        et_kobetsu_event.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent2 = new Intent(KobetsuEvent.this, KobetsuAddEventActivity.class);
//                intent2.putExtra("clicked_id", clicked_id);
//                startActivity(intent2);
//            }
//        });
//
////        LinearLayout mvevent=findViewById(R.id.mv_event);
////        mvevent.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent2 = new Intent(KobetsuEvent.this, UpdateEvent.class);
////                intent2.putExtra("clicked_id", clicked_id);
////                startActivity(intent2);
////            }
////        });
//
//        setColTicket();
//
//
////        削除処理
//        list_1bu.setLongClickable(true);
//        list_2bu.setLongClickable(true);
//        list_3bu.setLongClickable(true);
//        list_4bu.setLongClickable(true);
//        list_5bu.setLongClickable(true);
//
//
//        list_1bu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                long_getView(parent,view,position,id);
//                showFragmentDialog();
//
//                return false;
//            }
//        });
//        list_2bu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                long_getView(parent,view,position,id);
//                showFragmentDialog();
//
//                return false;
//            }
//        });
//        list_3bu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                long_getView(parent,view,position,id);
//                showFragmentDialog();
//
//                return false;
//            }
//        });
//        list_4bu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                long_getView(parent,view,position,id);
//                showFragmentDialog();
//
//                return false;
//            }
//        });
//        list_5bu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                long_getView(parent,view,position,id);
//                showFragmentDialog();
//
//                return false;
//            }
//        });




//

//
//
//
//

//
//    public void deleteItem() {
//
//        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//
//        String sql="delete from "+UserContract.Kobetsu.TABLE_NAME+" where "+
//                UserContract.Kobetsu.EVENT_ID+" = "+clicked_id+" and "+
//                UserContract.Kobetsu.NANBU+" = "+"'"+delete_nanbu+"'"+" and "+
//                UserContract.Kobetsu.MEMBER+" = "+"'"+delete_member+"'"+";";
//
//        db.execSQL(sql);
//        reload();
//
//    }
//    public void long_getView(AdapterView<?> arg0, View arg1, int position, long id){
//
//        View childlist = arg0.getChildAt(position);
//
//        TextView tv_delete_member = (TextView)childlist.findViewById(R.id.member_kobetsu);
//        delete_member=tv_delete_member.getText().toString();
//
//        String[] nanbuView=getResources().getStringArray(R.array.nanbuView);
//
//        switch (arg0.getId()) {
//            case R.id.list_1bu:
//                delete_nanbu =nanbuView[0];
//                break;
//            case R.id.list_2bu:
//                delete_nanbu =nanbuView[1];
//                break;
//            case R.id.list_3bu:
//                delete_nanbu =nanbuView[2];
//                break;
//            case R.id.list_4bu:
//                delete_nanbu =nanbuView[3];
//                break;
//            case R.id.list_5bu:
//                delete_nanbu =nanbuView[4];
//                break;
//        }
//    }
//    public void reload() {
//        Intent intent = getIntent();
//        overridePendingTransition(0, 0);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(intent);
//    }
//
//    public static class KobetsuDialogFragment extends TestDialogFragment
//    {
//        public void onTestDialogOKClick() {
//            KobetsuEvent activity=(KobetsuEvent)getActivity();
//            activity.deleteItem();
//        }
//
//    }
//    public static class KobetsuEventDialogFragment extends TestDialogFragment
//    {
//        public void onTestDialogOKClick() {
//            KobetsuEvent activity=(KobetsuEvent)getActivity();
//            activity.deleteEvent();
//
//        Intent intent3 =new Intent(activity,MainActivity.class);
//        startActivity(intent3);
//        }
//
//    }
//
//
//    private void showFragmentDialog() {
//                DialogFragment dialogFragment = new KobetsuDialogFragment();
//                dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
//    }
//
//    private void showFragmentDialog(int EVENTDIALOG) {
//        KobetsuEventDialogFragment dialogFragment2 = new KobetsuEventDialogFragment();
//        dialogFragment2.show(getSupportFragmentManager(), "fragment_dialog");
//    }
//
//
//
//    private void setNavigetion(){
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
//                    View wrapper_listview=findViewById(R.id.wrapper_listview);
//                    wrapper_listview.bringToFront();
//                    ct++;
//                }
//
//            }
//        });
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.updateEvent:
////                        Intent intent1 =new Intent(KobetsuEvent.this,UpdateEvent.class);
////                        intent1.putExtra("clicked_id", clicked_id);
////                        startActivity(intent1);
//                        break;
//                    case R.id. kobetsuaddEvent:
//                        Intent intent2 =new Intent(KobetsuEvent.this,KobetsuAddEventActivity.class);
//                        intent2.putExtra("clicked_id", clicked_id);
//                        startActivity(intent2);
//                        break;
//                    case R.id. deleteEvent:
//
//                        showFragmentDialog(EVENTDIALOG);
//                        deleteEvent();
//                        break;
//
//                    case R.id. reload:
//                        reload();
//                        break;
//
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
//
//
//    }
//    private void saveDate(){
//        //      MainAcitivityに総計を送る
//
//        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//
//        String query2 = "update "+ UserContract.Event.TABLE_NAME
//                +" set " + UserContract.Event.COL_TICKET+" = "+Integer.parseInt(col_ticket.getText().toString())
//                + " where " + UserContract.Event._ID + " = "+clicked_id+";";
//
//        db.execSQL(query2);
//
//    }
//    private void deleteEvent(){
//        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//
//        String query2 = "delete from "+ UserContract.Event.TABLE_NAME
//                + " where " + UserContract.Event._ID + " = "+clicked_id+";";
//
//        db.execSQL(query2);
//
//        String query3 = "delete from "+ UserContract.Kobetsu.TABLE_NAME
//                + " where " + UserContract.Kobetsu.EVENT_ID + " = "+clicked_id+";";
//
//        db.execSQL(query3);
//    }


}




























