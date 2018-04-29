package site.kobatomo.akushukai;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import site.kobatomo.akushukai.Model.UserOpenHelper;


/**
 * Created by bicpc on 2017/11/12.
 */

public class ZenkokuAddEvent extends Activity {

    private String selected_member1;
    private String selected_member2;
    private String selected_member3;
    private String selected_member4;

    private Button member1;
    private Button member2;
    private Button member3;
    private Button member4;
    private int current_num;
    private TextView count;
    private String clicked_id;
    private CompoundButton switch1;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zenkoku_add_event);

        setTitle();
        ticketCount();

        Intent intent2 = getIntent();
        clicked_id = intent2.getStringExtra("clicked_id");


        Button button = findViewById(R.id.insertButton);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          insertDatabase();
                                          Intent intent = new Intent(ZenkokuAddEvent.this, ZenkokuEvent.class);
                                          intent.putExtra("clicked_id", clicked_id);
                                          startActivity(intent);
                                      }
                                  });




        switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    memberSetVisible();
                } else {
                    memberSetInvisible();

                }
            }
        });



    }
    public class Zenkoku_ClickListener_moreadd implements View.OnClickListener{

        private String uriImg = null;
        private Drawable drawImg = null;
        private Handler imgHandler = null;
        private int imageID;
        private android.database.sqlite.SQLiteDatabase sqLiteDatabase;
        private int current_num = 0;
        private TextView count;
        private String radio_value;
        private String clicked_id;
        private String[] kanji_name_resource;
        private String[] mArray2;
        private ListView dialog_list;
        private AlertDialog.Builder builder;
        private AlertDialog dialog;
        public View convertView;
        private Button hiragana;
        private Button kanji;





        public void onClick(View v) {
            LayoutInflater inflater = (LayoutInflater) ZenkokuAddEvent.this.getSystemService(LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(
                    R.layout.memberview, (ViewGroup) findViewById(R.id.dialog_custom));
            builder = new AlertDialog.Builder(ZenkokuAddEvent.this);
            builder.setView(convertView);

            kanji_name_resource = getResources().getStringArray(R.array.more_add_list);
            dialog_list = convertView.findViewById(R.id.dialog_list);


//           アダプター処理
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    ZenkokuAddEvent.this, android.R.layout.simple_list_item_1);
            for (int i = 0; i < kanji_name_resource.length; i++) {
                adapter.add(kanji_name_resource[i]);
            }

            dialog_list.setAdapter(adapter);

            dialog = builder.create();
            dialog.show();

            hiragana = convertView.findViewById(R.id.hiragana);
            kanji = convertView.findViewById(R.id.kanji);

            // ボタン処理（ひらがな）
            hiragana.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    hiragana.setBackgroundResource(R.color.bgcolor);
                    kanji.setBackgroundResource(R.color.bgdeepcolor);

                    kanji_name_resource = getResources().getStringArray(R.array.more_add_list2);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ZenkokuAddEvent.this, android.R.layout.simple_list_item_1);
                    for (int i = 0; i < kanji_name_resource.length; i++) {
                        adapter.add(kanji_name_resource[i]);
                    }
                    dialog_list.setAdapter(adapter);

                }
            });

            // ボタン処理（漢字）
            kanji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    hiragana.setBackgroundResource(R.color.bgdeepcolor);
                    kanji.setBackgroundResource(R.color.bgcolor);

                    kanji_name_resource = getResources().getStringArray(R.array.more_add_list);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ZenkokuAddEvent.this, android.R.layout.simple_list_item_1);
                    for (int i = 0; i < kanji_name_resource.length; i++) {
                        adapter.add(kanji_name_resource[i]);
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

            if (v == member4) {
                dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        selected_member4 = (String) dialog_list.getItemAtPosition(position);
                        dialog.dismiss();
                        member4.setText(selected_member4);
                    }
                });
            }
        }
        // ボタン処理（漢字）


    }

    // OKクリック時の処理
    private void insertDatabase() {
        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();


        if (selected_member1 != null && switch1.isChecked()) {
            ContentValues newEvent = new ContentValues();
            newEvent.put(UserContract.Zenkoku.EVENT_ID, clicked_id);
            newEvent.put(UserContract.Zenkoku.MEMBER, selected_member1);
            newEvent.put(UserContract.Zenkoku.BUSUU, current_num);

            long newId = db.insert(
                    UserContract.Zenkoku.TABLE_NAME,
                    null,
                    newEvent
            );
        }


        if (selected_member2 != null && switch1.isChecked()) {
            ContentValues newEvent = new ContentValues();
            newEvent.put(UserContract.Zenkoku.EVENT_ID, clicked_id);
            newEvent.put(UserContract.Zenkoku.MEMBER, selected_member2);
            newEvent.put(UserContract.Zenkoku.BUSUU, current_num);

            long newId = db.insert(
                    UserContract.Zenkoku.TABLE_NAME,
                    null,
                    newEvent
            );
        }
        if (selected_member3 != null && switch1.isChecked()) {
            ContentValues newEvent = new ContentValues();
            newEvent.put(UserContract.Zenkoku.EVENT_ID, clicked_id);
            newEvent.put(UserContract.Zenkoku.MEMBER, selected_member3);
            
            newEvent.put(UserContract.Zenkoku.BUSUU, current_num);

            long newId3 = db.insert(
                    UserContract.Zenkoku.TABLE_NAME,
                    null,
                    newEvent
            );
        }
        if (selected_member4 != null && switch1.isChecked()) {
            ContentValues newEvent = new ContentValues();
            newEvent.put(UserContract.Zenkoku.EVENT_ID, clicked_id);
            newEvent.put(UserContract.Zenkoku.MEMBER, selected_member4);

            newEvent.put(UserContract.Zenkoku.BUSUU, current_num);

            long newId4 = db.insert(
                    UserContract.Zenkoku.TABLE_NAME,
                    null,
                    newEvent
            );
        }
        if (!switch1.isChecked()){
            ContentValues newEvent = new ContentValues();
            newEvent.put(UserContract.Zenkoku.EVENT_ID, clicked_id);
            newEvent.put(UserContract.Zenkoku.MEMBER, "未定");
            newEvent.put(UserContract.Zenkoku.BUSUU, current_num);

            long newId4 = db.insert(
                    UserContract.Zenkoku.TABLE_NAME,
                    null,
                    newEvent
            );
        }


    }

    //戻るボタンの処理を変更
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        } else {
            finish();
            return true;
        }
    }


    private void setTitle() {
        ImageView menu = findViewById(R.id.menu);
        menu.setVisibility(View.GONE);
        TextView package_title = findViewById(R.id.package_title);
        package_title.setText(R.string.package_more_add);

        RelativeLayout title=findViewById(R.id.title);
        title.setBackgroundResource(R.color.zenkokucolor);
        LinearLayout plus=findViewById(R.id.plus_ticket_zenkoku);
        plus.setBackgroundResource(R.color.zenkokucolor);
        LinearLayout minus=findViewById(R.id.minus_ticket_zenkoku);
        minus.setBackgroundResource(R.color.zenkokucolor);





    }

    private void memberSetVisible(){
        member1=findViewById(R.id.member1);
        member2=findViewById(R.id.member2);
        member3=findViewById(R.id.member3);
        member4=findViewById(R.id.member4);
        member1.setVisibility(View.VISIBLE);
        member2.setVisibility(View.VISIBLE);
        member3.setVisibility(View.VISIBLE);
        member4.setVisibility(View.VISIBLE);
        member1.setOnClickListener(new Zenkoku_ClickListener_moreadd());
        member2.setOnClickListener(new Zenkoku_ClickListener_moreadd());
        member3.setOnClickListener(new Zenkoku_ClickListener_moreadd());
        member4.setOnClickListener(new Zenkoku_ClickListener_moreadd());
    }
    private void memberSetInvisible(){

        member1=findViewById(R.id.member1);
        member2=findViewById(R.id.member2);
        member3=findViewById(R.id.member3);
        member4=findViewById(R.id.member4);
        member1.setVisibility(View.GONE);
        member2.setVisibility(View.GONE);
        member3.setVisibility(View.GONE);
        member4.setVisibility(View.GONE);
    }

    private void ticketCount(){
        count = (TextView) findViewById(R.id.count);
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

    }























