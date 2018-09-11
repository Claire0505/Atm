package claire.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int RC_LOGIN = 1;
    boolean logon = false;
    String[] func = {"餘額查詢","交易明細", "最新消息", "投資理財", "離開"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //清單 ListView
        ListView list = findViewById(R.id.list);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_expandable_list_item_1, func);

        list.setAdapter(adapter);

        //使用Spinner
        Spinner notify = findViewById(R.id.spinner);
        //因為陣列是放在res專案資源中，可以使用ArrayAdapter的類別方法createFromResource，
        //直接產生一個ArrayAdapter<CharSequence>的物件
        final ArrayAdapter<CharSequence> nAdapter =
                ArrayAdapter.createFromResource(this,
                        R.array.notify_array,
                        android.R.layout.simple_spinner_dropdown_item);
        notify.setAdapter(nAdapter);
        //Spinner項目選擇時的傾聽器
        notify.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,
                        nAdapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 測試TestActivity
        // startActivity(new Intent(this, TestActivity.class));

        if (!logon) //如果末登入，則開啟LoginActivity
        {
            Intent intent = new Intent(this, LoginActivity.class);

            // startActivity(intent); // 使用這個方式，按返回鍵一樣可以不用登入回到主畫面

            // 1.定義代表功能的常數
            // 2.startActivityForResult 是到另一個畫面中並取得結果(資料)
            // 3.在功能畫面結束之前設定返回值 (LoginActivity)
            // 4.回到主功能onActivityResult方法 (LoginActivity)
            startActivityForResult(intent, RC_LOGIN);

        }
    }

    // LoginActivity 回到主功能，覆寫OnActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_LOGIN){
            //第二與第三個參數是由3.結束前所設定的RESULT_OK與Intent物件 (resultCode與data)
            if (resultCode == RESULT_OK){
                String uid = data.getStringExtra("LOGIN_USERID");
                String pw = data.getStringExtra("LOGIN_PASSWD");
                Log.d("RESULT", uid + "/" + pw);
            }
        }else {
            finish();
        }
    }

    // 選單menu設定
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_help:
                Toast.makeText(this, "Menu Help", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_setting:
                Toast.makeText(this, "Menu Setting", Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
