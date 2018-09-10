package claire.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public static final int RC_LOGIN = 1;
    boolean logon = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
