package site.kobatomo.akushukai.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import site.kobatomo.akushukai.Adapter.KobetsuAdapter;
import site.kobatomo.akushukai.Member.KobetsuMember;
import site.kobatomo.akushukai.Member.MainMember;
import site.kobatomo.akushukai.Model.KobetsuModel;
import site.kobatomo.akushukai.R;
import site.kobatomo.akushukai.UserContract;

/**
 * Created by bicpc on 2017/11/10.
 */

public class KobetsuEvent extends BaseEvent {

    private ListView list_1bu;
    private static KobetsuEvent instance = null;
    private String nanbu;

    public static KobetsuEvent getInstance() {
        return instance;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kobetsu_event);

        instance = this;
        Intent intent = new Intent(KobetsuEvent.getInstance(), KobetsuAddEventActivity.class);
/*
データベースから、イベント情報を取得する
 */

        Intent gintent = getIntent();
        String eventId = gintent.getStringExtra("eventId");
        KobetsuModel kobetsuModel = new KobetsuModel(KobetsuEvent.getInstance());
        MainMember eventInformation = eventInformationfromModel(kobetsuModel,eventId);


/*
データベースから、メンバーの登録情報を取得する
 */

        Cursor memberCursor = kobetsuModel.searchMemberData(eventId);
        memberCursor.moveToFirst();

        List<String> urlList = new ArrayList<>();
        List<String> memberList = new ArrayList<>();
        List<String> nanbuList = new ArrayList<>();
        List<String> busuuList = new ArrayList<>();


/*
2部,1部といったヘッダーを加えるために、区切り用のデータを挿入する
 */

        nanbu="0";
        final String DEVIDELABEL = "0";
        do{
            if(!nanbu.equals(memberCursor.getString(memberCursor.getColumnIndex(UserContract.Kobetsu.NANBU)))){
                urlList.add(DEVIDELABEL);
                nanbu = memberCursor.getString(memberCursor.getColumnIndex(UserContract.Kobetsu.NANBU));
                nanbuList.add(nanbu);
                memberList.add("a");
                busuuList.add("a");
            }
                String busuu = memberCursor.getString(memberCursor.getColumnIndex(UserContract.Kobetsu.BUSUU));
                String url = memberCursor.getString(memberCursor.getColumnIndex(UserContract.Kobetsu.URL));
                nanbu = memberCursor.getString(memberCursor.getColumnIndex(UserContract.Kobetsu.NANBU));
                String member = memberCursor.getString(memberCursor.getColumnIndex(UserContract.Kobetsu.MEMBER));
                urlList.add(url);
                nanbuList.add(nanbu);
                memberList.add(member);
                busuuList.add(busuu);


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

        setEventRelatedLook(eventInformation,R.color.zenkokucolor);
//        ((TextView) findViewById(R.id.date_event)).setText(evt.getYear()+"/"+evt.getMonth()+"/"+evt.getDay());
//        ((TextView) findViewById(R.id.type_event)).setText(evt.getType());
//        (findViewById(R.id.type_event)).setBackgroundResource(R.color.kobetsucolor);
//        ((TextView) findViewById(R.id.location_event)).setText(evt.getPlace());

        /*
        * */
        list_1bu=findViewById(R.id.list_1bu);
        list_1bu.setAdapter(new KobetsuAdapter(this, kobetsuMember));


/*
合計枚数をセット
 */

        Cursor sumMaisuCursor = kobetsuModel.calcSumMaisu(eventId);
        sumMaisuCursor.moveToFirst();
        ((TextView)findViewById(R.id.col_ticket)).setText(sumMaisuCursor.getString(0));
        kobetsuModel.updateMaisu(eventId,sumMaisuCursor.getString(0));


        setTitle(R.color.kobetsucolor,R.id.package_title,R.id.title,R.id.typecolor);

//        ((TextView)findViewById(R.id.package_title)).setText(R.string.package_more);
//        findViewById(R.id.title).setBackgroundResource(R.color.kobetsucolor);
//        findViewById(R.id.typecolor).setBackgroundResource(R.color.kobetsucolor);;

/*
画面遷移
 */

        intent.putExtra("eventId", eventId);
        floatingButtonClickIntent(intent);
        mvButtonClickIntent(intent);

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
//        private void setTitle() {
//        ((TextView)findViewById(R.id.package_title)).setText(R.string.package_more);
//        findViewById(R.id.title).setBackgroundResource(R.color.kobetsucolor);
//        findViewById(R.id.typecolor).setBackgroundResource(R.color.kobetsucolor);;
//    }


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




























