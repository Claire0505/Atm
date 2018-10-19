package claire.com;

import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private EditText edDate, edInfo, edAmount;
    private  MyDBHelper helper;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initView();
        /**
         * Context context: this,即傳入activity本身
         * String name: 為資料庫檔案名稱 expense.db
         * CursorFactory factory: 在此使用null，代表以標準模示SQLiteCursor處理Cursor
         * int version: 應用程式目前資料庫版本 1
         */
         helper = MyDBHelper.getInstance(this);
    }

    private void initView() {
        //edDate = findViewById(R.id.ed_date);
        edInfo = findViewById(R.id.ed_info);
        edAmount = findViewById(R.id.ed_amount);
        datePicker = findViewById(R.id.datePicker);
    }

    public void add (View view){
        // 1.取得畫面上使用者輸入的資料
        //String cdate = edDate.getText().toString();
        //取得DatePicker並轉換字串
        int year = datePicker.getYear();
        int month = datePicker.getMonth()+1;
        int day = datePicker.getDayOfMonth();
        String cdate = year + "-" + month + "-" + day;
        String info = edInfo.getText().toString();
        int amount = Integer.parseInt(edAmount.getText().toString());

        // 2.收集一筆記錄資料
        //key: 欄位名稱 Value: 輸入的值
        ContentValues values = new ContentValues();
        values.put("cdate", cdate);
        values.put("info", info);
        values.put("amount", amount);

        // 3.取得資料庫物件後呼叫insert方法，傳入表格名稱與values集合物件以新增這筆記錄
        // 若成功會回傳新增記錄的id值
        // insert方法中第二個參數可填入一個欄位名稱，當第三個參數(values資料包)無任何資料時，會在該欄位上給予空值
        long id = helper.getWritableDatabase()
                .insert("exp", null, values);
        // 使用Log印出除錯訊息
        Log.d("ADD",id + "");

        startActivity(new Intent(this, FinanceActivity.class));
    }
}
