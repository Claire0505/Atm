package claire.com;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

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

    private void parseJSON(String s) {
    }
}
