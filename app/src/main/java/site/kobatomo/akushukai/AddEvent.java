package site.kobatomo.akushukai;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import site.kobatomo.akushukai.Model.UserOpenHelper;


/*public class MainActivity extends AppCompatActivity implements View.OnClickListener {*/
public class AddEvent extends FragmentActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener {
    private EditText ShowDate;
  private String seleceted_type;
    private String selected_date;
    private String iitem;
    private List<String> lst;
    private EditText ShowLoc;
    private String selected_loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);
        setTitle();
        ShowDate = findViewById(R.id.showDate);
        ShowLoc = findViewById(R.id.showLoc);
        ShowDate.setText(getNowDate());

        ShowDate.setOnClickListener(this);
        ShowLoc.setOnClickListener(this);


        Button insertButton = (Button) findViewById(R.id.insertButton);
        insertButton.setOnClickListener(this);




    }

    public void onClick(View v) {

        if (v.getId() == R.id.insertButton) {
            inputedRead();
            InsertDatabase();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.showDate) {
            DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
            datePicker.show(getSupportFragmentManager(), "datePicker");
        }

        if (v.getId() == R.id.showLoc) {
            AddDialogFragment dialogFragment = new AddDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
        }


    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        selected_date = String.valueOf(year)  + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth);
        ShowDate.setText(String.valueOf(year)  + "/ " + String.valueOf(monthOfYear + 1) + "/ " + String.valueOf(dayOfMonth));
    }

    public void inputedRead(){
        /*ラジオボタン*/
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        int checkedId = radioGroup.getCheckedRadioButtonId();

        if (checkedId != -1) {
            // 選択されているラジオボタンの取得
            RadioButton radioButton = (RadioButton) findViewById(checkedId);

            // ラジオボタンのテキストを取得
            seleceted_type = radioButton.getText().toString();

            Log.v("checked", seleceted_type);
        }
        selected_date=ShowDate.getText().toString();
        selected_loc=ShowLoc.getText().toString();
    }

    private void InsertDatabase(){
        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
        SQLiteDatabase db = userOpenHelper.getWritableDatabase();

        ContentValues newEvent = new ContentValues();
        newEvent.put(UserContract.Users.COL_TYPE, seleceted_type);
        newEvent.put(UserContract.Users.COL_DATE, selected_date);
        newEvent.put(UserContract.Users.COL_LOC, selected_loc);
        newEvent.put(UserContract.Users.COL_TICKET, "0");

        long newId = db.insert(
                UserContract.Users.TABLE_NAME,
                null,
                newEvent
        );
    }

    private void setTitle() {
        ImageView menu = findViewById(R.id.menu);
        menu.setVisibility(View.GONE);
        TextView package_title = findViewById(R.id.package_title);
        package_title.setText(R.string.package_add);
    }
    public static String getNowDate() {
        Date now = new Date(System.currentTimeMillis());
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String nowText = formatter.format(now);
        return nowText;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        } else {
            Intent intent= new Intent(AddEvent.this,MainActivity.class);
            startActivity(intent);
            return true;
        }
    }





}






