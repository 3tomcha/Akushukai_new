package site.kobatomo.akushukai.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

import site.kobatomo.akushukai.Member.AddEventMember;
import site.kobatomo.akushukai.Model.ZenkokuModel;
import site.kobatomo.akushukai.R;


/**
 * Created by bicpc on 2017/11/12.
 */

public class ZenkokuAddPreActivity extends Activity {


    private Button member1;
    private Button member2;
    private Button member3;
    private Button member4;
    private int current_num;
    private static ZenkokuAddPreActivity instance = null;


    public static ZenkokuAddPreActivity getInstance() {
        return instance;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zenkoku_add_pre_event);
        instance = this;

        Intent intent = new Intent(ZenkokuAddPreActivity.getInstance(),ZenkokuEvent.class);

        //チケットの増減処理
        View plusTicket = findViewById(R.id.plus_ticket_zenkoku);
        View minusTicket = findViewById(R.id.minus_ticket_zenkoku);
        plusTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketCount();
            }
        });
        minusTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketCount();
            }
        });

        Button insertButton = findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                データベース処理に変更
                 */

                /*イベント情報をデータベースに挿入*/
                Intent gintent = getIntent();
                Serializable eventData = gintent.getSerializableExtra("eventData");
                AddEventMember evt = (AddEventMember) eventData;
                ZenkokuModel zenkokuModel = new ZenkokuModel(ZenkokuAddPreActivity.getInstance());
                zenkokuModel.insertEvent(evt.getYear(), evt.getMonth(), evt.getDay(), evt.getPlace(),"全国");

//                入力された合計枚数も次画面に送る必要がある
                String busuu =((TextView)findViewById(R.id.count)).getText().toString();
                intent.putExtra("busuu",busuu);
                intent.putExtra("eventId",zenkokuModel.getEventId());
                startActivity(intent);
            }
        });

    }

    private void memberSetVisible() {
        member1.setVisibility(View.VISIBLE);
        member2.setVisibility(View.VISIBLE);
        member3.setVisibility(View.VISIBLE);
        member4.setVisibility(View.VISIBLE);
    }

    private void memberSetInvisible() {
        member1.setVisibility(View.GONE);
        member2.setVisibility(View.GONE);
        member3.setVisibility(View.GONE);
        member4.setVisibility(View.GONE);
    }

    private void ticketCount(){
        final TextView count = (TextView) findViewById(R.id.count);
        ImageView plus = findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                current_num += 1;
                count.setText(String.valueOf(current_num));
            }
        });

        ImageView minus = findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(current_num>0) {
                    current_num -= 1;
                    count.setText(String.valueOf(current_num));
                }
            }
        });

    }

//メンバーボタンクリック時のダイアログ処理

    public class DialogListener implements View.OnClickListener {
        private ListView dialog_list;
        private String selected_member;

        public void onClick(View v) {
            LayoutInflater inflater = (LayoutInflater) ZenkokuAddPreActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
            View convertView = inflater.inflate(
                    R.layout.memberview, (ViewGroup) findViewById(R.id.dialog_custom));
            AlertDialog.Builder builder = new AlertDialog.Builder(ZenkokuAddPreActivity.this);
            builder.setView(convertView);

            final Button hiragana = convertView.findViewById(R.id.hiragana);
            final Button kanji = convertView.findViewById(R.id.kanji);

            final String[] kanjiNameResource = getResources().getStringArray(R.array.more_add_list);
            final String[] hiraganaNameResource = getResources().getStringArray(R.array.more_add_list2);

            dialog_list = convertView.findViewById(R.id.dialog_list);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    ZenkokuAddPreActivity.this, android.R.layout.simple_list_item_1);
            for (int i = 0; i < kanjiNameResource.length; i++) {
                adapter.add(kanjiNameResource[i]);
            }

            dialog_list.setAdapter(adapter);
            final AlertDialog dialog = builder.create();
            dialog.show();


// /ダイアログで漢字ボタンを押した際の、色、リソースの変更

            hiragana.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResource(hiragana,kanji,hiraganaNameResource);
                }
            });

            kanji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResource(kanji,hiragana,kanjiNameResource);
                }
            });

            if (v == member1 || v == member2 || v == member3 || v == member4) {
                final TextView textView =(TextView) v;
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selected_member = (String) dialog_list.getItemAtPosition(position);
                        dialog.dismiss();
                        textView.setText(selected_member);
                    }
                });
            }
        }

        private void setResource(Button button1, Button button2, String[] array){
                    button1.setBackgroundResource(R.color.bgcolor);
                    button2.setBackgroundResource(R.color.bgdeepcolor);
//                    kanji_name_resource = getResources().getStringArray(R.array.more_add_list2);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ZenkokuAddPreActivity.this, android.R.layout.simple_list_item_1);

                    for (int i = 0; i < array.length; i++) {
                        adapter.add(array[i]);
                    }
                    dialog_list.setAdapter(adapter);
        }
        private void setTitle() {
            findViewById(R.id.menu).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.package_title)).setText(R.string.package_more_add);
            findViewById(R.id.title).setBackgroundResource(R.color.zenkokucolor);
            findViewById(R.id.plus_ticket_zenkoku).setBackgroundResource(R.color.zenkokucolor);
            findViewById(R.id.minus_ticket_zenkoku).setBackgroundResource(R.color.zenkokucolor);
        }


    }




}


//        // ボタン処理（漢字）
//
//
//    }
//
//    // OKクリック時の処理
//    private void insertDatabase() {
//        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//
//
//        if (selected_member1 != null && switch1.isChecked()) {
//            ContentValues newEvent = new ContentValues();
//            newEvent.put(UserContract.Zenkoku.EVENT_DATE, clicked_id);
//            newEvent.put(UserContract.Zenkoku.MEMBER, selected_member1);
//            newEvent.put(UserContract.Zenkoku.BUSUU, current_num);
//
//            long newId = db.insert(
//                    UserContract.Zenkoku.TABLE_NAME,
//                    null,
//                    newEvent
//            );
//        }
//
//
//        if (selected_member2 != null && switch1.isChecked()) {
//            ContentValues newEvent = new ContentValues();
//            newEvent.put(UserContract.Zenkoku.EVENT_DATE, clicked_id);
//            newEvent.put(UserContract.Zenkoku.MEMBER, selected_member2);
//            newEvent.put(UserContract.Zenkoku.BUSUU, current_num);
//
//            long newId = db.insert(
//                    UserContract.Zenkoku.TABLE_NAME,
//                    null,
//                    newEvent
//            );
//        }
//        if (selected_member3 != null && switch1.isChecked()) {
//            ContentValues newEvent = new ContentValues();
//            newEvent.put(UserContract.Zenkoku.EVENT_DATE, clicked_id);
//            newEvent.put(UserContract.Zenkoku.MEMBER, selected_member3);
//
//            newEvent.put(UserContract.Zenkoku.BUSUU, current_num);
//
//            long newId3 = db.insert(
//                    UserContract.Zenkoku.TABLE_NAME,
//                    null,
//                    newEvent
//            );
//        }
//        if (selected_member4 != null && switch1.isChecked()) {
//            ContentValues newEvent = new ContentValues();
//            newEvent.put(UserContract.Zenkoku.EVENT_DATE, clicked_id);
//            newEvent.put(UserContract.Zenkoku.MEMBER, selected_member4);
//
//            newEvent.put(UserContract.Zenkoku.BUSUU, current_num);
//
//            long newId4 = db.insert(
//                    UserContract.Zenkoku.TABLE_NAME,
//                    null,
//                    newEvent
//            );
//        }
//        if (!switch1.isChecked()){
//            ContentValues newEvent = new ContentValues();
//            newEvent.put(UserContract.Zenkoku.EVENT_DATE, clicked_id);
//            newEvent.put(UserContract.Zenkoku.MEMBER, "未定");
//            newEvent.put(UserContract.Zenkoku.BUSUU, current_num);
//
//            long newId4 = db.insert(
//                    UserContract.Zenkoku.TABLE_NAME,
//                    null,
//                    newEvent
//            );
//        }
//
//
//    }
//
//    //戻るボタンの処理を変更
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode != KeyEvent.KEYCODE_BACK) {
//            return super.onKeyDown(keyCode, event);
//        } else {
//            finish();
//            return true;
//        }
//    }
//
//

//
//
//
//
//
//    }























