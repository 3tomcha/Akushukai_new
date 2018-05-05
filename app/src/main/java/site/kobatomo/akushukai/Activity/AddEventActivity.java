package site.kobatomo.akushukai.Activity;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import site.kobatomo.akushukai.Adapter.AddEventAdapter;
import site.kobatomo.akushukai.Model.AddEventModel;
import site.kobatomo.akushukai.R;


public class AddEventActivity extends Activity{
    private EditText ShowDate;
  private String seleceted_type;
    private String selected_date;
    private String iitem;
    private List<String> lst;
    private EditText ShowLoc;
    private String selected_loc;
    private static AddEventActivity instance = null;
    private AddEventModel addEventModel;
    private ArrayList zenkokuList;


    public static AddEventActivity getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_addevent);
        setContentView(R.layout.activity_addevent);
        setTitle();
        instance = this;

        final AddEventModel addEventModel = new AddEventModel();
        addEventModel.setOnCallBack(new AddEventModel.CallBackTask(){
            @Override
            public void CallBack(String result) {
                super.CallBack(result);
                zenkokuList = addEventModel.getZenkokuList();

                ListView list_addevent = findViewById(R.id.list_addevent);
                AddEventAdapter addEventAdapter = new AddEventAdapter(AddEventActivity.getInstance(),R.layout.list_addevent,zenkokuList);
                list_addevent.setAdapter(addEventAdapter);
                Log.i("AsyncTaskCallback", "非同期処理が終了しました。");
            }
        });
        // AsyncTaskの実行
        addEventModel.execute();
        Log.d("OK","OK");


    }

    public void onFinished() {
        Log.v("TAG", "finished");
    }

    private void setTitle() {
        ImageView menu = findViewById(R.id.menu);
        menu.setVisibility(View.GONE);
        TextView package_title = findViewById(R.id.package_title);
        package_title.setText(R.string.package_add);
    }

}






