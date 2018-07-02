package site.kobatomo.akushukai.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import site.kobatomo.akushukai.Member.ZenkokuMember;
import site.kobatomo.akushukai.Model.MemberToUrlAsyncModel;
import site.kobatomo.akushukai.Model.ZenkokuModel;
import site.kobatomo.akushukai.R;


/**
 * Created by bicpc on 2017/11/12.
 */

public class ZenkokuAddEventActivity extends Activity {


    private Button member1;
    private Button member2;
    private Button member3;
    private Button member4;
    private int current_num;
    private static ZenkokuAddEventActivity instance = null;
    private String eventId;


    public static ZenkokuAddEventActivity getInstance() {
        return instance;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zenkoku_add_event);
//
        instance = this;

        eventId = getIntent().getStringExtra("eventId");

        ArrayList<Integer> memberIdlist = new ArrayList<>();

        for (int i=1; i<=4; i++) {
            int viewId = getResources().getIdentifier("member" + i, "id", getPackageName());
            findViewById(viewId).setOnClickListener(new DialogListener());
            memberIdlist.add(viewId);
        }

        member1 = findViewById(memberIdlist.get(0));
        member2 = findViewById(memberIdlist.get(1));
        member3 = findViewById(memberIdlist.get(2));
        member4 = findViewById(memberIdlist.get(3));

        findViewById(R.id.plus_ticket_zenkoku).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketCount();
            }
        });
        findViewById(R.id.minus_ticket_zenkoku).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketCount();
            }
        });
//
        Button insertButton = findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ZenkokuMember zenkokuMember = new ZenkokuMember();

                /*メンバーを取得しクラスにセットする*/
                List<String> memberTempList = new ArrayList<>();
                if(isSelected(member1)) memberTempList.add(member1.getText().toString());
                if(isSelected(member2)) memberTempList.add(member2.getText().toString());
                if(isSelected(member3)) memberTempList.add(member3.getText().toString());
                if(isSelected(member4)) memberTempList.add(member4.getText().toString());
                zenkokuMember.setMember(memberTempList);

                /*枚数を取得しクラスにセットする*/
                int count = Integer.parseInt(((TextView) findViewById(R.id.count)).getText().toString());
                List<String> maisuuList = new ArrayList<>();
                maisuuList.add(String.valueOf(count));
                zenkokuMember.setMaisuu(maisuuList);


                /*非同期処理を用いてURLの取得処理も行ったのちに、イベント情報に対応するメンバー情報をデータベースに挿入する。*/
                final MemberToUrlAsyncModel memberToUrlAsyncModel = new MemberToUrlAsyncModel();
                memberToUrlAsyncModel.setOnCallBack(new MemberToUrlAsyncModel.CallBackTask() {
                    @Override
                    public void CallBack(String result) {
                        super.CallBack(result);


                        /*メンバー情報のURLをクラスに準備*/
                        List memberList = zenkokuMember.getMember();
                        List urlList = memberToUrlAsyncModel.getUrlList(memberList);
                        zenkokuMember.setUrl(urlList);


                        ZenkokuModel zenkokuModel = new ZenkokuModel(ZenkokuAddEventActivity.getInstance());
//                        zenkokuModel.insertMember();

                        /*メンバー情報をデータベースに挿入*/
                        MA mA = new MA(zenkokuMember);
                        for (int i = 0; i < zenkokuMember.getMember().size(); i++) {
                            zenkokuModel.insertMember(mA.getMember(i), mA.getUrl(i), mA.getMaisuu(i));
                        }

                    }
                });
                memberToUrlAsyncModel.execute();
                Log.d("OK", "OK");


//
//
//
////                デートとプレイスをaddEventから持ってくる必要あり
//                Intent intent = getIntent();
//                Serializable eventDate = intent.getSerializableExtra("eventDate");
////                Log.d("a","b");
//                AddEventMember Date = (AddEventMember) eventDate;
//
//                Intent newintent = new Intent(ZenkokuAddEventActivity.getInstance(),ZenkokuEvent.class);
//                newintent.putExtra("intentDate",eventDate);
//                newintent.putExtra("memberData",memberList);
//                startActivity(newintent);
//            }
//        });
            }
        });
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
            LayoutInflater inflater = (LayoutInflater) ZenkokuAddEventActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
            View convertView = inflater.inflate(
                    R.layout.memberview, (ViewGroup) findViewById(R.id.dialog_custom));
            AlertDialog.Builder builder = new AlertDialog.Builder(ZenkokuAddEventActivity.this);
            builder.setView(convertView);

            final Button hiragana = convertView.findViewById(R.id.hiragana);
            final Button kanji = convertView.findViewById(R.id.kanji);

            final String[] kanjiNameResource = getResources().getStringArray(R.array.more_add_list);
            final String[] hiraganaNameResource = getResources().getStringArray(R.array.more_add_list2);

            dialog_list = convertView.findViewById(R.id.dialog_list);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    ZenkokuAddEventActivity.this, android.R.layout.simple_list_item_1);
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

//            if (v == member1 || v == member2 || v == member3 || v == member4) {
            if (v == member1|| v == member2 || v == member3 || v == member4) {
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ZenkokuAddEventActivity.this, android.R.layout.simple_list_item_1);

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

    private boolean isSelected(Button button){
        return button.getText().toString()!="未選択";
    }

    private class MA{
        private ZenkokuMember km;

        MA(ZenkokuMember km){
            this.km=km;
        }
        public String getNanbu(int i){
            return km.getNanbu().get(0).toString();

        }
        public String getMember(int i) {
            return km.getMember().get(i).toString();
        }

        public String getUrl(int i) {
            return km.getUrl().get(i).toString();
        }

        public String getMaisuu(int i) {
            return km.getMaisuu().get(0).toString();
        }
    }


}

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























