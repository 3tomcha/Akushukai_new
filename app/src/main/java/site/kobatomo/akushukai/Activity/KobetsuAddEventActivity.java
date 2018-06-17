package site.kobatomo.akushukai.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import site.kobatomo.akushukai.Member.AddEventMember;
import site.kobatomo.akushukai.Member.KobetsuMember;
import site.kobatomo.akushukai.Model.KobetsuModel;
import site.kobatomo.akushukai.Model.MemberToUrlAsyncModel;
import site.kobatomo.akushukai.R;


/**
 * Created by bicpc on 2017/11/12.
 */

public class KobetsuAddEventActivity extends Activity {

    private int current_num = 0;
    private TextView count;
    private String nanbu;
    private String selected_member1;
    private String selected_member2;
    private String selected_member3;
    public EditText member1;
    public EditText member2;
    public EditText member3;
    public View convertView;
    private List urlList;


    private static KobetsuAddEventActivity instance = null;
    private String eventId;

    public static KobetsuAddEventActivity getInstance() {
        return instance;
    }



/*
・新規追加
from AddEvent
インテントからイベント情報を取得し、データベース追加

・データ更新
from KobetsuEvent
インテントからeventIdを取得し、データベース更新
 */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.kobetsu_add_event);

        Serializable eventData = getIntent().getSerializableExtra("eventData");
        eventId = getIntent().getStringExtra("eventId");


        member1 = findViewById(R.id.member1);
        member2 = findViewById(R.id.member2);
        member3 = findViewById(R.id.member3);

        member1.setText("未選択");
        member2.setText("未選択");
        member3.setText("未選択");

        member1.setOnClickListener(new ClickListener_moreadd());
        member2.setOnClickListener(new ClickListener_moreadd());
        member3.setOnClickListener(new ClickListener_moreadd());

        setTitle();
        ticketCount();



/*
クリック時に、画像取得処理とデータベース処理を行う。
*/


        Button insertButton = findViewById(R.id.insertButton);
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*メンバー情報のうち、何部、メンバー、枚数をクラスに準備*/
                final KobetsuMember kmtemp = new KobetsuMember();
                KobetsuMember kobetsuMember = setNanbuMemberMaisuu(kmtemp);


                /*非同期処理を用いてURLの取得処理も行ったのちに、イベント情報、メンバー情報をデータベースに挿入する。*/
                final MemberToUrlAsyncModel memberToUrlAsyncModel = new MemberToUrlAsyncModel();
                memberToUrlAsyncModel.setOnCallBack(new MemberToUrlAsyncModel.CallBackTask() {
                    @Override
                    public void CallBack(String result) {
                        super.CallBack(result);

                        /*メンバー情報のURLをクラスに準備*/
                        List memberList = kobetsuMember.getMember();
                        urlList = memberToUrlAsyncModel.getUrlList(memberList);
                        kobetsuMember.setUrl(urlList);


                        /*
                        新規イベント追加時
                        */
                        Boolean NEWEVENT = eventId == null || eventId.isEmpty();

                        if (NEWEVENT) {

                            KobetsuModel kobetsuModel = new KobetsuModel(KobetsuAddEventActivity.getInstance());

                            /*イベント情報をデータベースに挿入*/
                            AddEventMember evt = (AddEventMember) eventData;
                            kobetsuModel.insertEvent(evt.getYear(), evt.getMonth(), evt.getDay(), evt.getPlace());

                            /*メンバー情報をデータベースに挿入*/
                            MA mA = new MA(kobetsuMember); //ただラップするだけのクラス
                            for (int i = 0; i < kobetsuMember.getMember().size(); i++) {
                                kobetsuModel.insertMember(mA.getNanbu(i), mA.getMember(i), mA.getUrl(i), mA.getMaisuu(i));
                            }

                            Intent intent = new Intent(KobetsuAddEventActivity.getInstance(), KobetsuEvent.class);
                            intent.putExtra("eventId", kobetsuModel.getEventId());

                            startActivity(intent);

                        } else{

                            KobetsuModel kobetsuModel = new KobetsuModel(KobetsuAddEventActivity.getInstance());

                            /*メンバー情報をデータベースに挿入*/
                            MA mA = new MA(kobetsuMember); //ただラップするだけのクラス
                            for (int i = 0; i < kobetsuMember.getMember().size(); i++) {
                                kobetsuModel.insertMember(mA.getNanbu(i), mA.getMember(i), mA.getUrl(i), mA.getMaisuu(i), eventId);
                            }

                            Intent intent = new Intent(KobetsuAddEventActivity.getInstance(), KobetsuEvent.class);
                            intent.putExtra("eventId", kobetsuModel.getEventId());

                            startActivity(intent);
                    }
/*
                                                  データ更新時
                                                   **/

//
//                                                  }else {
//                                                      for (int i = 0; i < kobetsuMember.getMember().size(); i++) {
//                                                          kobetsuModel.insertMember(kobetsuMember.getNanbu(), kobetsuMember.getMember().get(i).toString(),
//                                                                  kobetsuMember.getUrl().get(i).toString(), kobetsuMember.getMaisuu(), eventId
//                                                          );
//
//                                                      }
//                                                      Intent intent = new Intent(KobetsuAddEventActivity.getInstance(), KobetsuEvent.class);
//                                                      intent.putExtra("eventId",eventId);
//
//                                                      startActivity(intent);
//                                                  }



//                                          });


                    }
                });
                memberToUrlAsyncModel.execute();
                Log.d("OK", "OK");
            }
        });
    }


/*
何部かを選択するラジオボタンのクリック処理。
nanbu:何部が選択されているかの最新状況
 */


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        String[] nanbu=getResources().getStringArray(R.array.nanbu);

        switch (view.getId()) {
            case R.id.more_add_radio1:
                if (checked)
                    this.nanbu = nanbu[0];
                break;
            case R.id.more_add_radio2:
                if (checked)
                    this.nanbu = nanbu[1];
                break;
            case R.id.more_add_radio3:
                if (checked)
                    this.nanbu = nanbu[2];
                break;
            case R.id.more_add_radio4:
                if (checked)
                    this.nanbu = nanbu[3];
                break;
            case R.id.more_add_radio5:
                if (checked)
                    this.nanbu = nanbu[4];
                break;
            case R.id.more_add_radio6:
                if (checked)
                    this.nanbu = nanbu[5];
                break;
        }
    }


    public class ClickListener_moreadd implements View.OnClickListener{


        private String[] mArray;
        private ListView dialog_list;
        private AlertDialog.Builder builder;
        private AlertDialog dialog;
        public View convertView;


//        ボタンのクリックリスナーを


        public void onClick(View v) {
            LayoutInflater inflater = (LayoutInflater) KobetsuAddEventActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(
                    R.layout.memberview, findViewById(R.id.dialog_custom));
            builder = new AlertDialog.Builder(KobetsuAddEventActivity.this);
            builder.setView(convertView);

            mArray = getResources().getStringArray(R.array.more_add_list);
            dialog_list = convertView.findViewById(R.id.dialog_list);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    KobetsuAddEventActivity.this, android.R.layout.simple_list_item_1);
            for (int i = 0; i < mArray.length; i++) {
                adapter.add(mArray[i]);
            }
            dialog_list.setAdapter(adapter);
            dialog = builder.create();
            dialog.show();

            
            Button hiragana = convertView.findViewById(R.id.hiragana);
            hiragana.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mArray = getResources().getStringArray(R.array.more_add_list2);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(KobetsuAddEventActivity.this, android.R.layout.simple_list_item_1);
                    for (int i = 0; i < mArray.length; i++) {
                        adapter.add(mArray[i]);
                    }
                    dialog_list.setAdapter(adapter);

                }
            });

//             ボタン処理（漢字）
            Button kanji = convertView.findViewById(R.id.kanji);
            kanji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mArray = getResources().getStringArray(R.array.more_add_list);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(KobetsuAddEventActivity.this, android.R.layout.simple_list_item_1);
                    for (int i = 0; i < mArray.length; i++) {
                        adapter.add(mArray[i]);
                    }
                    dialog_list.setAdapter(adapter);

                }
            });
            if (v == member1) {
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        selected_member1 = (String) dialog_list.getItemAtPosition(position);
                        dialog.dismiss();
                        member1.setText(selected_member1);
                    }
                });
            }

            if (v == member2) {
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        selected_member2 = (String) dialog_list.getItemAtPosition(position);
                        dialog.dismiss();
                        member2.setText(selected_member2);
                    }
                });
            }

            if (v == member3) {
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        selected_member3 = (String) dialog_list.getItemAtPosition(position);
                        dialog.dismiss();
                        member3.setText(selected_member3);
                    }
                });
            }
        }


    }
    private void setTitle() {
        ImageView menu = findViewById(R.id.menu);
        menu.setVisibility(View.GONE);
        TextView package_title = findViewById(R.id.package_title);
        package_title.setText(R.string.package_more_add);
        RelativeLayout title=findViewById(R.id.title);
        title.setBackgroundResource(R.color.kobetsucolor);
        LinearLayout plus=findViewById(R.id.plus_ticket_zenkoku);
        plus.setBackgroundResource(R.color.bgdeepcolor);
        LinearLayout minus=findViewById(R.id.minus_ticket_zenkoku);
        minus.setBackgroundResource(R.color.bgdeepcolor);


    }
    private void ticketCount(){
        count = findViewById(R.id.count);
        ImageView plus = findViewById(R.id.plus);
        plus.setOnClickListener(v -> {
            current_num += 1;
            count.setText(String.valueOf(current_num));
        });

        ImageView minus = findViewById(R.id.minus);
        minus.setOnClickListener(v -> {
            if(current_num>0) {
                current_num -= 1;
                count.setText(String.valueOf(current_num));
            }
        });
    }
    private KobetsuMember setNanbuMemberMaisuu(KobetsuMember km){

        List<String> nanbuTempList = new ArrayList<>();
        nanbuTempList.add(nanbu);
        km.setNanbu(nanbuTempList);

        List<String> memberTemplist = new ArrayList<>();
        Collections.addAll(memberTemplist, selected_member1, selected_member2, selected_member3);
        km.setMember(memberTemplist);

        List<String> maisuuList = new ArrayList<>();
        maisuuList.add(String.valueOf(current_num));
        km.setMaisuu(maisuuList);

        return km;
    }
    private String[] mA(KobetsuMember km, int i){
//        List<String> ml = new ArrayList<>();
        String[] ml = new String[4];
        ml[0]=km.getNanbu().get(0).toString();
        ml[1]=km.getMember().get(i).toString();
        ml[2]=km.getUrl().get(i).toString();
        ml[3]=km.getMaisuu().get(0).toString();
        return ml;
    }


    private class MA{
        private KobetsuMember km;

        MA(KobetsuMember km){
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




















