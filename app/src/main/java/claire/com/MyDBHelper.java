package claire.com;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {

    //SQLiteOpenHelper的Singleton設計
    //1.將MyDBHelper設計成單一物件，利用[static]的特性，確保整個應用程式都使用同一個MyDBHelper物件
    private static MyDBHelper instance;
    //2.再設計一個公開的getInstance()方法，以取得MyDBHelper物件
    public  static MyDBHelper getInstance(Context ctx){
        if (instance == null){
            instance = new MyDBHelper(ctx,"expense.db", null, 1);
        }

        return  instance;
    }
    //3.最後在將原本的public改為private
    //4.完成後再到FinanceActivity和AddActivity將原來產生的MyDBHelper改成為 MyDBHelper helper = MyDBHelper.getInstance(this)
    //經過Singleton後的MyDBHelper與Activity類別，能夠確保同一時間只有一個MyDBHelper實例在運行，可避免執行緒存取資料庫問題。
    private MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE exp " +
                    "(_id INTEGER PRIMARY KEY NOT NULL," +
                    "cdate DATETIME NOT NULL," +
                    "info VARCHAR, " +
                    "amount INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
