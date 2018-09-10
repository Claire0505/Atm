package claire.com;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //填入已儲存的帳號，讀取儲存設定中的「PREF_USERID」鍵值
        EditText edUserid = findViewById(R.id.ed_userid);
        SharedPreferences setting =
                getSharedPreferences("atm", MODE_PRIVATE);
        edUserid.setText(setting.getString("PREF_USERID", "")); //第二個參數為，前面沒有值時的預設字串

    }

    public void login(View v){
        EditText edUserid = findViewById(R.id.ed_userid);
        EditText edPassword = findViewById(R.id.ed_password);
        String uid = edUserid.getText().toString();
        String pw = edPassword.getText().toString();
        //測式帳密 jack/1234
        if (uid.equals("jack") && pw.equals("1234")){

            // 使用SharedPreference記住登入帳號
            // 呼叫getSharedPreferences()方法，產生一個檔名為atm.xml的設定儲存檔
            // MODE_PRIVATE 參數的值代表喜號設定只允許本應用程式內存取
            // 呼叫edit() 方法取得編輯器物件，將 uid 字串寫入設定檔，資料標籤為"PREF_USERID"
            SharedPreferences setting = getSharedPreferences("atm", MODE_PRIVATE);
            setting.edit()
                    .putString("PREF_USERID", uid)
                    .apply();

            Toast.makeText(this, "登入成功", Toast.LENGTH_SHORT).show();

            getIntent().putExtra("LOGIN_USERID", uid);
            getIntent().putExtra("LOGIN_PASSWD", pw);
            // 先將帳密放入Intent物件中，再呼叫Activity的setResult方法，設定這一個功能畫面的結果
            // 為RESULT_OK，並把Intent物件放入結果中，目的在結束後，回去MainActivity後仍可得到結果
            setResult(RESULT_OK, getIntent());

            finish(); //正確結束本活動，回到MainActivity畫面

        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Atm")
                    .setMessage("登入失敗")
                    .setPositiveButton("Ok", null)
                    .show();
        }

    }

    public void cancel(View v){

    }
}
