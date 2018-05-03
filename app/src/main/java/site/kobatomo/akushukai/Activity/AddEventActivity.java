package site.kobatomo.akushukai.Activity;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import site.kobatomo.akushukai.Model.AddEventModel;
import site.kobatomo.akushukai.R;


/*public class MainActivity extends AppCompatActivity implements View.OnClickListener {*/
public class AddEventActivity extends Activity{
    private EditText ShowDate;
  private String seleceted_type;
    private String selected_date;
    private String iitem;
    private List<String> lst;
    private EditText ShowLoc;
    private String selected_loc;
    private static AddEventActivity instance = null;


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

        AddEventModel addEventModel =new AddEventModel();

//        ExecutorService service = Executors.newCachedThreadPool();
//        Future<String> future = service.submit(addEventModel.execute(););
//


        ArrayList zenkokuList = addEventModel.getZenkokuList();

        Log.d("OK","OK");
    }


//
    private void setTitle() {
        ImageView menu = findViewById(R.id.menu);
        menu.setVisibility(View.GONE);
        TextView package_title = findViewById(R.id.package_title);
        package_title.setText(R.string.package_add);
    }








}






