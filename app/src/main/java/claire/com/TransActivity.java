package claire.com;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TransActivity extends AppCompatActivity {
    //使用OkHttp第三方函式庫
    OkHttpClient client = new OkHttpClient();
    private static final String AtmHistoryURL = "http://atm201605.appspot.com/h";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);

        //OKHttp,使用Request.Builder設定一個連線必要的資訊，此時尚未連線至主機
        Request request = new Request.Builder()
                .url(AtmHistoryURL)
                .build();
        //使用OkHttpClient的newCall方法建立一個呼叫物件，此時尚未連線至主機
        Call call = client.newCall(request);
        //呼叫Call類別的enqueue方法，進行排程連線，此時才實際連至主機
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //告知使用者連線失數
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //取得伺服器的回應字串
                String json = response.body().string();
                Log.d("OKHTTP", json);
                //解析JSON
                parseJSON(json);

            }
        });

        //new TransTask().execute("http://atm201605.appspot.com/h");
    }

    //使用[AsyncTask]類別設計HTTP連線工作
    class TransTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = in.readLine();
                while (line != null){
                    Log.d("HTTP", line);
                    sb.append(line);
                    line = in.readLine();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        //接收doInBackground最後return回值的字串，進行後續解析工作
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("JSON", s);
            parseJSON(s);
        }
    }

    //使用Android中已內建的 JSON.org解析，JSONObject類別可解析JSON物件, 而JSONArray類別可用在JSON陣列
    //JSON陣列-用來表示多筆資料，含有一筆以上的資料，資料間用逗號分隔[物件1,物件2,物件3]
    //JSON物件-用來表示(一筆)資料，資料中每個欄位名稱以冒號表示它的值，並以大括號集合所有欄位成為一個物件
    // {欄位名稱:欄位值,欄位名稱:欄位值,欄位名稱:欄位值}
    private void parseJSON(String s) {
        //實作解析JSON資料並將資料收集在Java的ArrayList類別中
        //先準備ArrayList集合物件trans，裡面只能存放Transaction物件
        ArrayList<Transaction> trans = new ArrayList<>();
        try {
            //將傳入的字串s交給JSONArray的建構子，產生array物件
            JSONArray array = new JSONArray(s);
            //以迴圈依序取出交易記錄
            for (int i = 0; i < array.length(); i++){
                //以索引值取得JSONObject物件obj
                JSONObject obj = array.getJSONObject(i);
                //呼叫JSONObject類的getXX方法取得各個屬性值
                String account = obj.getString("account");
                String date = obj.getString("date");
                int amount = obj.getInt("amount");
                int type = obj.getInt("type");

                Log.d("JSON: ", account + "/" + date + "/" + amount + "/" + type);
                //產生Transaction物件，代表一筆交易記錄
                Transaction t = new Transaction(account, date, amount, type);
                //將物件加入到集合中
                trans.add(t);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
