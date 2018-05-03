package site.kobatomo.akushukai;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/*public class MainActivity extends AppCompatActivity implements View.OnClickListener {*/
public class AddEvent extends Activity{
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
//        setContentView(R.layout.add_event);
        setContentView(R.layout.add_event2);
        setTitle();

        new GetZenkokuEventTask().execute();


//        ShowDate = findViewById(R.id.showDate);
//        ShowLoc = findViewById(R.id.showLoc);
//        ShowDate.setText(getNowDate());
//
//        ShowDate.setOnClickListener(this);
//        ShowLoc.setOnClickListener(this);


//        Button insertButton = (Button) findViewById(R.id.insertButton);
//        insertButton.setOnClickListener(this);

    }


//    }
//
//    public void onClick(View v) {
//
//        if (v.getId() == R.id.insertButton) {
//            inputedRead();
//            InsertDatabase();
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.showDate) {
//            DatePickerDialogFragment datePicker = new DatePickerDialogFragment();
//            datePicker.show(getSupportFragmentManager(), "datePicker");
//        }
//
//        if (v.getId() == R.id.showLoc) {
//            AddDialogFragment dialogFragment = new AddDialogFragment();
//            dialogFragment.show(getSupportFragmentManager(), "fragment_dialog");
//        }
//
//
//    }
//
//    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        selected_date = String.valueOf(year)  + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth);
//        ShowDate.setText(String.valueOf(year)  + "/ " + String.valueOf(monthOfYear + 1) + "/ " + String.valueOf(dayOfMonth));
//    }
//
//    public void inputedRead(){
//        /*ラジオボタン*/
//        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
//        int checkedId = radioGroup.getCheckedRadioButtonId();
//
//        if (checkedId != -1) {
//            // 選択されているラジオボタンの取得
//            RadioButton radioButton = (RadioButton) findViewById(checkedId);
//
//            // ラジオボタンのテキストを取得
//            seleceted_type = radioButton.getText().toString();
//
//            Log.v("checked", seleceted_type);
//        }
//        selected_date=ShowDate.getText().toString();
//        selected_loc=ShowLoc.getText().toString();
//    }
//
//    private void InsertDatabase(){
//        UserOpenHelper userOpenHelper = new UserOpenHelper(this);
//        SQLiteDatabase db = userOpenHelper.getWritableDatabase();
//
//        ContentValues newEvent = new ContentValues();
//        newEvent.put(UserContract.Users.COL_TYPE, seleceted_type);
//        newEvent.put(UserContract.Users.COL_DATE, selected_date);
//        newEvent.put(UserContract.Users.COL_LOC, selected_loc);
//        newEvent.put(UserContract.Users.COL_TICKET, "0");
//
//        long newId = db.insert(
//                UserContract.Users.TABLE_NAME,
//                null,
//                newEvent
//        );
//    }
//
    private void setTitle() {
        ImageView menu = findViewById(R.id.menu);
        menu.setVisibility(View.GONE);
        TextView package_title = findViewById(R.id.package_title);
        package_title.setText(R.string.package_add);
    }
//    public static String getNowDate() {
//        Date now = new Date(System.currentTimeMillis());
//        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        String nowText = formatter.format(now);
//        return nowText;
//    }
//
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode != KeyEvent.KEYCODE_BACK) {
//            return super.onKeyDown(keyCode, event);
//        } else {
//            Intent intent= new Intent(AddEvent.this,MainActivity.class);
//            startActivity(intent);
//            return true;
//        }
//    }


private class GetZenkokuEventTask extends AsyncTask<String,String,String> {

    //        APIに接続し、レスポンスを得る
    public String doInBackground(String... params) {
        Log.d("TAG", "doInBackground: ");
        String urlStr = "http://girly.lolitapunk.jp//keyakiakushukai/zenkoku.php";

        HttpURLConnection con = null;
        InputStream is = null;
        String result = null;

        try{
            URL url = new URL(urlStr);
            Log.d("URL",url.toString());
            con = (HttpURLConnection) url.openConnection();
//            con.setRequestProperty("User-Agent","Mozilla/5.0");
            Log.d("con",con.toString());
            con.connect();
            is = con.getInputStream();
            Log.d("is",is.toString());
            result = is2String(is);
            Log.d("result",result.toString());
        }catch (MalformedURLException ex){
        }
        catch(IOException ex) {
            Log.d("エラー",ex.getMessage());
        }

        finally {
            if(con != null) {
                con.disconnect();  // （10）
            }
            if(is != null) {
                try {
                    is.close();  // （11）
                }
                catch(IOException ex) {
                }
            }
        }
        return result;
    }

    public void onProgressUpdate(String... progress) {
    }

    //        受けとったjsonを加工する
    public void onPostExecute(String result) {
        Log.d("onPostExecute","onPostExecute");

        ArrayList zenkokuList =new ArrayList();

        try{
            Log.d("try","try");
            Log.d("result",result);

            try {
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    zenkokuList.add(jsonObject);
                }


            }catch (Exception ex){
                Log.d("エラー","エラー");

            }

        }catch (Exception ex){

        }

    }
    private String is2String(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        char[] b = new char[1024];
        int line;
        while(0 <= (line = reader.read(b))) {
            sb.append(b, 0, line);
        }
        return sb.toString();
    }



}




}






