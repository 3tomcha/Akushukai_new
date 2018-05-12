package site.kobatomo.akushukai.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import site.kobatomo.akushukai.Adapter.AddEventAdapter;
import site.kobatomo.akushukai.Model.AddEventModel;
import site.kobatomo.akushukai.Model.ZenkokuAddModel;
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
                AddEventAdapter addEventAdapter = null;

                try {
                    addEventAdapter = new AddEventAdapter(AddEventActivity.getInstance(), R.layout.list_addevent,zenkokuList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list_addevent.setAdapter(addEventAdapter);
                Log.i("AsyncTaskCallback", "非同期処理が終了しました。");

//                リストビューひとつひとつにクリック処理
                list_addevent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView date = view.findViewById(R.id.date);
                        date.getText();
                        TextView place = view.findViewById(R.id.place);
                        place.getText();

                        ZenkokuAddModel zenkokuAddModel = new ZenkokuAddModel(AddEventActivity.getInstance());
                        zenkokuAddModel.insert(date.getText().toString(),place.getText().toString(),"","","");
                        Intent intent = new Intent(AddEventActivity.getInstance(),ZenkokuAddEventActivity.class);
                        startActivity(intent);
                    }
                });
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






