package site.kobatomo.akushukai.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import site.kobatomo.akushukai.Adapter.ZenkokuAdapter;
import site.kobatomo.akushukai.Member.MainMember;
import site.kobatomo.akushukai.Model.MainModel;
import site.kobatomo.akushukai.Model.ZenkokuModel;
import site.kobatomo.akushukai.R;
import site.kobatomo.akushukai.TestDialogFragment;

//import site.kobatomo.akushukai.UpdateEvent;

/**
 * Created by tomoya on 2017/11/28.
 */

public class ZenkokuEvent extends BaseEvent {


    final int MEMBERDIALOG = 0;
    final int EVENTDIALOG = 1;
    private TextView kari_col_ticket;
    private TextView col_ticket;
    int sum_busuu;
    private int kari_busuu;
    private DrawerLayout drawer;
    private int ct=0;
    private ZenkokuAdapter zenkokuAdapter;

    private static ZenkokuEvent instance = null;
    private MainModel mainModel;
    private ZenkokuModel zenkokuModel;
    private String eventId;
    private RecyclerView.LayoutManager LayoutManager;
    private RecyclerView recyclerView;


    public static ZenkokuEvent getInstance() {
        return instance;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zenkoku_event);
        instance = this;
        Intent intent = new Intent(ZenkokuEvent.getInstance(), ZenkokuAddEventActivity.class);

        Intent gintent = getIntent();
        String bussu = gintent.getStringExtra("busuu");

/*
データベースからイベント情報を取得
 */

        String eventId = gintent.getStringExtra("eventId");
        ZenkokuModel zenkokuModel = new ZenkokuModel(ZenkokuEvent.getInstance());
        MainMember eventInformation = eventInformationfromModel(zenkokuModel,eventId);

/*
イベント関連の見栄えをセットする
 */
        setEventRelatedLook(eventInformation,R.color.zenkokucolor);
        ((TextView)findViewById(R.id.col_ticket)).setText(bussu);


/*
画面遷移
 */
        intent.putExtra("eventId", eventId);
        floatingButtonClickIntent(intent);
        mvButtonClickIntent(intent);



//
//
//        Serializable intentDate = intent.getSerializableExtra("intentDate");
//        final List<String> memberData = intent.getStringArrayListExtra("memberData");
//
//        final AddEventMember eventDate = (AddEventMember) intentDate;
//
//        if(memberData!=null) {
//            recyclerView = findViewById(R.id.recyclerview);
//            recyclerView.setHasFixedSize(true);
//            LayoutManager = new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(LayoutManager);
//
//            zenkokuAdapter = new ZenkokuAdapter(memberData);
//            recyclerView.setAdapter(zenkokuAdapter);
//        }
//
//        ItemTouchHelper mIth = new ItemTouchHelper(
//                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
//                        ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
//                    @Override
//                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                        final int fromPos = viewHolder.getAdapterPosition();
//                        final int toPos = target.getAdapterPosition();
//                        zenkokuAdapter.notifyItemMoved(fromPos,toPos);
//
//                        return true;
//                    }
//
//                    @Override
//                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                        final int fromPos = viewHolder.getAdapterPosition();
//                        memberData.remove(fromPos);
//                        zenkokuAdapter.notifyItemRemoved(fromPos);
//
//                    }
//                }
//        );
//        mIth.attachToRecyclerView(recyclerView);
//
//
////        zenkoku_list = (ListView) findViewById(R.id.zenkoku_list);
////        zenkokuAdapter = new ZenkokuAdapter(memberData);
////        zenkoku_list.setAdapter(zenkokuAdapter);
//
//
//        setTitle();
//
//        String year = eventDate.getYear();
//        String month = eventDate.getMonth();
//        String day= eventDate.getDay();
//        ((TextView)findViewById(R.id.date_event)).setText(year+"/"+month+"/"+day);
//        ((TextView)findViewById(R.id.location_event)).setText(eventDate.getPlace());
//        ((TextView)findViewById(R.id.col_ticket)).setText(eventDate.getBusuu());
//
//        findViewById(R.id.type_event).setBackgroundResource(R.color.zenkokucolor);
//        ((TextView)findViewById(R.id.type_event)).setText("全国");
//        findViewById(R.id.typecolor).setBackgroundResource(R.color.zenkokucolor);
//
//
//        //        /*イベント追加ボタンの実装*/
//        FloatingActionButton addEvent = findViewById(R.id.addEvent);
//        addEvent.setOnClickListener(new View.OnClickListener() {
//                                        public void onClick(View v) {
//                                            Intent intent2 = new Intent(ZenkokuEvent.getInstance(), ZenkokuAddEventActivity.class);
//                                            intent2.putExtra("eventDate", eventDate);
////                                            saveData();
//                                            startActivity(intent2);
//                                        }
//                                    });
//
////      同様の処理をリストビューをクリックしてもできるようにする
//        LinearLayout et_zenkoku_event = findViewById(R.id.mv_event);
//
//        et_zenkoku_event.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    Intent intent2 = new Intent(ZenkokuEvent.getInstance(), ZenkokuAddEventActivity.class);
////                    intent2.putExtra("clicked_id", clicked_id);
////                    saveData();
//                    startActivity(intent2);
//                }
//                });
//
//
//        /*
//        ZenkokuAddEventAcitivityで入力された情報を取り込み、MVに表示する
//        eventId:２つのデータベースをつなぐ値
//        */
//
////            try {
////                cursor.moveToFirst();
////                String year = cursor.method(cursor.getColumnIndex(UserContract.Zenkoku.YEAR));
////                String month = cursor.method(cursor.getColumnIndex(UserContract.Zenkoku.MONTH));
////                String day = cursor.method(cursor.getColumnIndex(UserContract.Zenkoku.DAY));
////
////                ((TextView)findViewById(R.id.date_event)).setText(year+"/"+month+"/"+day);
////
////                String place = cursor.method(cursor.getColumnIndex(UserContract.Zenkoku.PLACE));
////                ((TextView)findViewById(R.id.location_event)).setText(place);
////                col_ticket = findViewById(R.id.col_ticket);
////                eventId = cursor.method(cursor.getColumnIndex(UserContract.Zenkoku.EVENT_ID));
////
////                kari_col_ticket = findViewById(R.id.kari_col_ticket);
////                kari_col_ticket.setVisibility(View.VISIBLE);
//////                kari_col_ticket.setText(busuu);
////                TextView slash=findViewById(R.id.slash);
////                slash.setVisibility(View.VISIBLE);
////
////            } catch (Exception e) {
////                Log.d("エラー", e.getMessage());
////            }
//
////            ここから下は編集中
//
//
////        Cursor membercursor = zenkokuModel.searchMemberInfomation(eventId);
////        ArrayList<MemberInfomation> memberInfomations = new ArrayList<MemberInfomation>();
//
//        /*
//        イベントに紐づいたメンバーの検索処理
//        メンバーが存在しない場合は、次のifの処理はスルーする
//         */
//
////        if (membercursor.moveToFirst()) {
////            do {
////                String added_member = membercursor.method(membercursor.getColumnIndex(UserContract.ZenkokuMember.MEMBER));
////                String added_busuu = membercursor.method(membercursor.getColumnIndex(UserContract.ZenkokuMember.BUSUU));
////                if(added_member.equals("未定")) {
////                    memberInfomations.add(new MemberInfomation(added_member,added_busuu));
////                }
////                else{
////                    String added_url = zenkokuModel.search_Memberimg(added_member);
////                    memberInfomations.add(new MemberInfomation(added_member, added_busuu, added_url));
////                }
////                sum_busuu += Integer.parseInt(added_busuu);
////
////            } while (membercursor.moveToNext());
////        } else {
////            Log.v("nanntoka", "nantoka");
////        }
////
//
////
////        kari_col_ticket.setVisibility(View.VISIBLE);
////        kari_col_ticket.setText(String.valueOf(sum_busuu));
//
//
//        //
////        mainModel = new MainModel(ZenkokuEvent.getInstance());
////        zenkokuModel = new ZenkokuModel(ZenkokuEvent.getInstance());
////        setTitle();
////        setNavigetion();
//
////
////
////
////        /*イベント追加ボタンの実装*/
////        FloatingActionButton addEvent = findViewById(R.id.addEvent);
////        addEvent.setOnClickListener(new View.OnClickListener() {
////                                        public void onClick(View v) {
////                                            Intent intent2 = new Intent(ZenkokuEvent.getInstance(), ZenkokuAddEventActivity.class);
////                                            intent2.putExtra("clicked_id", clicked_id);
////                                            saveData();
////                                            startActivity(intent2);
////                                        }
////                                    });
////
//////      同様の処理をリストビューをクリックしてもできるようにする
////        LinearLayout et_zenkoku_event = findViewById(R.id.mv_event);
////
////        et_zenkoku_event.setOnClickListener(new View.OnClickListener() {
////                public void onClick(View v) {
////                    Intent intent2 = new Intent(ZenkokuEvent.getInstance(), ZenkokuAddEventActivity.class);
////                    intent2.putExtra("clicked_id", clicked_id);
////                    saveData();
////                    startActivity(intent2);
////                }
////                });
////
////
////
//////メンバーの登録情報をデータベースから探し、アレイリストに入れる
//
//////アレイリストをアダプターにセットし、それをリストビューに表示する
//
////
////
//////        zenkoku_list.setLongClickable(true);
//////        zenkoku_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//////            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
//////
//////                long_getView(arg0,arg1,position,id);
//////                showFragmentDialog(MEMBERDIALOG);
//////
//////                return true;
//////            }
//////        });
////
////        setColTicket();


    }



//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode != KeyEvent.KEYCODE_BACK) {
//            return super.onKeyDown(keyCode, event);
//        } else {
//            Intent intent= new Intent(ZenkokuEvent.this,MainActivity.class);
////            saveData();
//
//            ZenkokuModel zenkokuModel = new ZenkokuModel(ZenkokuEvent.getInstance());
//
//            startActivity(intent);
//            return true;
//        }
//    }


//    数量を変更した時のデータベース処理
//private void saveData() {
//    String string_member_zenkoku="未定";
//    ListView listview=findViewById(R.id.zenkoku_list);
//    ZenkokuAdapter adapter=(ZenkokuAdapter) listview.getAdapter();
//
//    for (int i=0; i<adapter.getCount(); i++) {
//        View childlist = listview.getChildAt(i);
////        TextView member_zenkoku = childlist.findViewById(R.id.member_zenkoku);
////        TextView busuu_zenkoku = childlist.findViewById(R.id.busuu_zenkoku);
//
////        zenkokuModel.update(busuu_zenkoku.getText().toString(),clicked_id,member_zenkoku.getText().toString());
//        mainModel.update(clicked_id,kari_col_ticket.getText().toString());
//    }
//    }

//    public void long_getView(AdapterView<?> arg0, View arg1, int position, long id){
//
//        ListView listview=findViewById(R.id.zenkoku_list);
//        ZenkokuAdapter adapter=(ZenkokuAdapter) listview.getAdapter();
//        View childlist = listview.getChildAt(position);
////        メンバーを取得する
//        TextView tv_delete_member = (TextView)childlist.findViewById(R.id.member_zenkoku);
//        delete_member=tv_delete_member.getText().toString();
//
//    }


    public static class ZenkokuDialogFragment extends TestDialogFragment
    {
        public void onTestDialogOKClick() {
            ZenkokuEvent activity=(ZenkokuEvent)getActivity();
//            activity.deleteMember();
        }

    }
    public static class ZenkokuEventDialogFragment extends TestDialogFragment
    {
        public void onTestDialogOKClick() {
            ZenkokuEvent activity=(ZenkokuEvent)getActivity();
//            activity.deleteEvent();
        }

    }


//    public void deleteMember(){
//        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//
//        String sql="delete from "+UserContract.Zenkoku.TABLE_NAME+
//                " where "+UserContract.Zenkoku.EVENT_DATE +"="+clicked_id+
//                " and "+UserContract.Zenkoku.MEMBER+"="+"'"+delete_member+"';";
//
//        db.execSQL(sql);
////        リロードしたい
//
//        reload();
//    }

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
//                        Intent intent1 =new Intent(ZenkokuEvent.this,UpdateEvent.class);
//                        intent1.putExtra("clicked_id", clicked_id);
//                        startActivity(intent1);
                        break;
                    case R.id. kobetsuaddEvent:
                        Intent intent2 =new Intent(ZenkokuEvent.this,ZenkokuAddEventActivity.class);
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
//    private void deleteEvent(){
//        mainModel.delete(clicked_id);
////        zenkokuModel.delete(clicked_id);
//        Intent intent3 =new Intent(ZenkokuEvent.this,MainActivity.class);
//        startActivity(intent3);
//    }

//    メンバー登録情報の保持用クラス
//public class MemberInfomation {
//        private String member = null;
//        private String busuu = null;
//        private String url = null;
//        private long id = 0;
//
//        public MemberInfomation(String member, String busuu, String url) {
//            super();
//            this.member = member;
//            this.busuu = busuu;
//            this.url = url;
//            /*画像に関しても加える*/
//
//            this.id = new Date().getTime();
//        }
//        public MemberInfomation(String member,String busuu) {
//            super();
//            this.member = member;
//            this.busuu = busuu;
//            /*画像に関しても加える*/
//            this.id = new Date().getTime();
//        }
//        public long getId() {
//            return id;
//        }
//        public String getmember() {
//            return member;
//        }
//        public String geturl() {
//            return url;
//        }
//        public String getbusuu() {
//            return busuu;
//        }
//
//    }

    public void abstractMethod(int num, String str) {

    }
}












