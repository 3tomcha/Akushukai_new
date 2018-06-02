//package site.kobatomo.akushukai;
//
//
//import android.app.DatePickerDialog;
//import android.content.ContentValues;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import java.util.List;
//
//import site.kobatomo.akushukai.Activity.MainActivity;
//import site.kobatomo.akushukai.Model.UserOpenHelper;
//
//import static site.kobatomo.akushukai.R.id.showDate;
//
//
///*public class MainActivity extends AppCompatActivity implements View.OnClickListener {*/
//public class UpdateEvent extends FragmentActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener {
//    private EditText ShowDate;
//    private EditText ShowLoc;
//    private String update_text;
//    private String update_date;
//    private String update_loc;
//    private List<String> lst;
//    private String clicked_id;
//
//    private String selected_type;
//    private String selected_date;
//    private String selected_loc;
//
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_addevent);
//
//        setTitle();
//
//        LinearLayout update_erase=findViewById(R.id.update_erase);
//        update_erase.setVisibility(View.GONE);
//
//
//        /*calender機能*/
//        ShowDate = findViewById(R.id.showDate);
//        ShowDate.setOnClickListener(this);
//
//        ShowLoc = findViewById(R.id.showLoc);
//        ShowLoc.setOnClickListener(this);
//         /*calender機能*/
//
//
//         /*値を受け取る*/
////        Intent intent = getIntent();
////        int clicked_position = intent.getIntExtra("clicked_position", 0);
////        KeyakiData keyaki = new KeyakiData();
////        clicked_id = keyaki.getId(clicked_position);
//        Intent intent = getIntent();
//      clicked_id=intent.getStringExtra("clicked_id");
//
//
//        /*データの検索*/
//
//        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
//        SQLiteDatabase db = userOpenHelper.getReadableDatabase();
//
//        String query = "select * from " + UserContract.Event.TABLE_NAME + " where " + UserContract.Event._ID +" = " + clicked_id;
//
//        Cursor c = db.rawQuery(query, null);
//        if (c.moveToFirst()) {
////            selected_type = c.getString(c.getColumnIndex(UserContract.Event.COL_TYPE));
//            selected_date=c.getString(c.getColumnIndex(UserContract.Event.COL_DATE));
//            selected_loc=c.getString(c.getColumnIndex(UserContract.Event.COL_LOC));
//        }
//
//        /*日付をセットする*/
//
//        ShowDate.setText(selected_date);
//        ShowLoc.setText(selected_loc);
//
//
//
//        StringBuilder selected_date_string=new StringBuilder(selected_date);
//
//        int lastslash =selected_date_string.lastIndexOf("/");
//        Log.d("lastslash",String.valueOf(lastslash));
//
//
//        if(lastslash==7){
//            int year = Integer.parseInt(selected_date_string.substring(0, 4));
//
//            int monthOfYear = Integer.parseInt(selected_date_string.substring(5, 7));
//
//            int dayOfMonth = Integer.parseInt(selected_date_string.substring(8, 10));
//
//            ShowDate.setText(String.valueOf(year)  + "/ " + String.valueOf(monthOfYear) + "/ " + String.valueOf(dayOfMonth));
//
//        }
//        else if(lastslash==6){
//            int year = Integer.parseInt(selected_date_string.substring(0, 4));
//
//            int monthOfYear = Integer.parseInt(selected_date_string.substring(5));
//
//            int dayOfMonth = Integer.parseInt(selected_date_string.substring(7, 9));
//            ShowDate.setText(String.valueOf(year)  + "/ " + String.valueOf(monthOfYear) + "/ " + String.valueOf(dayOfMonth));
//            }
//
//
//
//    /*インサートボタン*/
//        Button insertButton = (Button) findViewById(R.id.insertButton);
//        insertButton.setOnClickListener(this);
//        /*インサートボタン*/
//
//    }
//
//    public void onClick(View v) {
//        /*データベースへ挿入*/
//        if (v.getId() == R.id.insertButton) {
//            update_loc=ShowLoc.getText().toString();
//
//            UserOpenHelper userOpenHelper = new UserOpenHelper(this);
//            SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//
//            ContentValues newEvent = new ContentValues();
//
//            if(update_date!=null) {
//                newEvent.put(UserContract.Event.COL_DATE, update_date);
//            }
//            if(update_loc!=null) {
//                newEvent.put(UserContract.Event.COL_LOC, update_loc);
//            }
//
//            if(update_date==null && update_loc==null) {
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//            }
//            else{
//                int update = db.update(UserContract.Event.TABLE_NAME, newEvent, UserContract.Event._ID + " =? ", new String[]{clicked_id});
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//            }
//
//        }
//        if (v.getId() == R.id.showLoc) {
//            AddDialogFragment dialogFragment = new AddDialogFragment();
//            dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
//        }
//        if (v.getId() == showDate) {
//            DatePickerDialogFragment2 datePicker = new DatePickerDialogFragment2();
//
//            datePicker.show(getSupportFragmentManager(), "datePicker");
//        }
//
//    }
//
//    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        update_date = String.valueOf(year) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth);
//        ShowDate.setText(String.valueOf(year) + "/ " + String.valueOf(monthOfYear + 1) + "/ " + String.valueOf(dayOfMonth));
//    }
//
//    private void setTitle(){
//        ImageView menu = findViewById(R.id.menu);
//        menu.setVisibility(View.GONE);
//            TextView package_title = findViewById(R.id.package_title);
//            package_title.setText(R.string.package_update);
//
//    }
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode != KeyEvent.KEYCODE_BACK) {
//            return super.onKeyDown(keyCode, event);
//        } else {
//            Intent intent= new Intent(UpdateEvent.this,MainActivity.class);
//            startActivity(intent);
//            return true;
//        }
//    }
//
//}
//
//
//
//
//
