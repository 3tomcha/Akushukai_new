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

import site.kobatomo.akushukai.Member.AddEventMember;

/**
 * Created by tomoya on 2018/05/03.
 */


/*
イベント追加の際にリスト表示するためのデータを
API経由で取得するためのクラス
 */

public class AddEventModel extends AsyncTask<String, String, String> {

    private CallBackTask callbacktask;

    public ArrayList getEventList() {
        return eventList;
    }

    private ArrayList eventList = new ArrayList();
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

        ArrayList members = new ArrayList();

        Log.d("onPostExecute", "onPostExecute");


        try {
            Log.d("try", "try");
            Log.d("result", result);

            try {
                result = result.trim();
//                JSONArray jsonArray = new JSONArray(result);

                JSONObject jsonResponse = new JSONObject(result);
                Log.d("なんとか","かんとか");
                JSONArray zenkoku = jsonResponse.getJSONArray("zenkoku");
                JSONArray kobetsu = jsonResponse.getJSONArray("kobetsu");

//                この時点でjsonからクラスに変換する
//                日付は逆順にしたい


                for (int i = 0; i < kobetsu.length(); i++) {

                    AddEventMember addEventMember = new AddEventMember();

//                    タイプも判別できるようにしたい
                    String type = "全国";
                    String year = kobetsu.getJSONObject(i).getString("year");
                    String month = kobetsu.getJSONObject(i).getString("month");
                    String day = kobetsu.getJSONObject(i).getString("day");
                    String location = kobetsu.getJSONObject(i).getString("location");
                    String place = kobetsu.getJSONObject(i).getString("place");

                    addEventMember.setType(type);
                    addEventMember.setYear(year);
                    addEventMember.setMonth(month);
                    addEventMember.setDay(day);
                    addEventMember.setLocation(location);
                    addEventMember.setPlace(place);

                    members.add(addEventMember);

                    Log.d("感とか","さんとか");
                }

                for (int i = 0; i < kobetsu.length(); i++) {

                    AddEventMember addEventMember = new AddEventMember();

//                    タイプも判別できるようにしたい
                    String type = "個別";
                    String year = kobetsu.getJSONObject(i).getString("year");
                    String month = kobetsu.getJSONObject(i).getString("month");
                    String day = kobetsu.getJSONObject(i).getString("day");
                    String location = kobetsu.getJSONObject(i).getString("location");
                    String place = kobetsu.getJSONObject(i).getString("place");

                    addEventMember.setType(type);
                    addEventMember.setYear(year);
                    addEventMember.setMonth(month);
                    addEventMember.setDay(day);
                    addEventMember.setLocation(location);
                    addEventMember.setPlace(place);

                    members.add(addEventMember);

                    Log.d("感とか","さんとか");
                }






//
//
//
//                List.add(jsonResponse);
                this.eventList.addAll(members);

                Log.d("OK", "OK");


            } catch (Exception ex) {
                Log.d("エラー", ex.getMessage());


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

