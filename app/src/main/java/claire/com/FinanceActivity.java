package claire.com;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FinanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        ListView list = findViewById(R.id.list);
        MyDBHelper dbHelper = MyDBHelper.getInstance(this);
        Cursor c = dbHelper.getReadableDatabase().query(
                "exp", null, null,null, null, null, null);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.finance_row,  //使用自行設定的layout
                c,  //傳入所查詢的Cursor物件
                new String[]{"cdate","info","amount"}, //改顯示三個[cdate][info][amount]兩個欄位
                new int[] {R.id.item_cdate, R.id.item_info, R.id.item_amount}, //自訂layout的id
                0); //flags如果給「0」代表ListView在展示過程中資料庫中的記錄如果更動了，
        // ListView將不自動重新查詢並更動畫面中的資料。
        list.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FinanceActivity.this, AddActivity.class));
            }
        });


    }

}
