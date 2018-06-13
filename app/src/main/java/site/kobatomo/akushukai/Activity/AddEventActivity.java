package site.kobatomo.akushukai.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import site.kobatomo.akushukai.Adapter.AddEventAdapter;
import site.kobatomo.akushukai.Member.AddEventMember;
import site.kobatomo.akushukai.Model.AddEventModel;
import site.kobatomo.akushukai.R;


public class AddEventActivity extends Activity{
    private static AddEventActivity instance = null;
    private ArrayList zenkokuList;
    private AddEventAdapter addEventAdapter;


    public static AddEventActivity getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);
        setTitle();
        instance = this;

/*
リスト用のデータ取得処理
APIの処理手順の関係でコールバックを用いる
 */
        final AddEventModel addEventModel = new AddEventModel();
        addEventModel.setOnCallBack(new AddEventModel.CallBackTask(){
            @Override
            public void CallBack(String result) {
                super.CallBack(result);
                zenkokuList = addEventModel.getEventList();

                ListView list_addevent = findViewById(R.id.list_addevent);
                addEventAdapter = null;

                try {
                    addEventAdapter = new AddEventAdapter(AddEventActivity.getInstance(), R.layout.list_addevent,zenkokuList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                list_addevent.setAdapter(addEventAdapter);
                Log.i("AsyncTaskCallback", "非同期処理が終了しました。");

/*
リストビューひとつひとつにクリック処理をつける
全国だったら全国の追加処理、個別だったら個別の追加処理にインテント
「タイプ」「部数」「年」「月」「日」「場所」の情報も送る
*/
                list_addevent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        AddEventMember eventData = (AddEventMember) addEventAdapter.getItem(position);
                        Intent intent = ((TextView)view.findViewById(R.id.type)).getText().toString().equals("全国")?
                                new Intent(AddEventActivity.getInstance(), ZenkokuAddPreActivity.class) : new Intent(AddEventActivity.getInstance(), KobetsuAddEventActivity.class);
                        intent.putExtra("eventData",eventData);
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






