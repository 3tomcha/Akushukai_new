package site.kobatomo.akushukai.Model;

import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by tomoya on 2018/05/03.
 */

public class AddEventModel extends AsyncTask<String, String, String> {

    private CallBackTask callbacktask;

    public ArrayList getZenkokuList() {
        return zenkokuList;
    }

    private ArrayList zenkokuList = new ArrayList();
    //        APIに接続し、レスポンスを得る

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    public String doInBackground(String... params) {
        Log.d("TAG", "doInBackground: ");
        String urlStr = "http://girly.lolitapunk.jp//keyakiakushukai/index.php";

        HttpURLConnection con = null;
        InputStream is = null;
        String result = null;

        try {
            URL url = new URL(urlStr);
            Log.d("URL", url.toString());
            con = (HttpURLConnection) url.openConnection();
//            con.setRequestProperty("User-Agent","Mozilla/5.0");
            Log.d("con", con.toString());
            con.connect();
            is = con.getInputStream();
            Log.d("is", is.toString());
            result = is2String(is);
            Log.d("result", result.toString());
        } catch (MalformedURLException ex) {
        } catch (IOException ex) {
            Log.d("エラー", ex.getMessage());
        } finally {
            if (con != null) {
                con.disconnect();  // （10）
            }
            if (is != null) {
                try {
                    is.close();  // （11）
                } catch (IOException ex) {
                }
            }
        }
        return result;
    }

    public void onProgressUpdate(String... progress) {
    }

    //        受けとったjsonを加工する
    public void onPostExecute(String result) {

        ArrayList List = new ArrayList();

        Log.d("onPostExecute", "onPostExecute");


        try {
            Log.d("try", "try");
            Log.d("result", result);

            try {
                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    List.add(jsonObject);
                }

                this.zenkokuList.addAll(List);

                Log.d("OK", "OK");


            } catch (Exception ex) {
                Log.d("エラー", "エラー");


            }

        } catch (Exception ex) {

        }
        callbacktask.CallBack(result);

    }

    private String is2String(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        char[] b = new char[1024];
        int line;
        while (0 <= (line = reader.read(b))) {
            sb.append(b, 0, line);
        }
        return sb.toString();
    }

    public static class CallBackTask {
        public void CallBack(String result) {
        }
    }
    public void setOnCallBack(CallBackTask _cbj) {
        callbacktask = _cbj;
    }


}

