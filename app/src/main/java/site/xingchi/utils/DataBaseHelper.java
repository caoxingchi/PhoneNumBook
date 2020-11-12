package site.xingchi.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";
    private static final int VERSION = 1;
    //带全部参数的构造函数，此构造函数必不可少
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }
    //带两个参数的构造函数，调用的其实是带三个参数的构造函数
    public DataBaseHelper(Context context, String name) {
        this(context, name, VERSION);
    }
    //带三个参数的构造函数，调用的是带所有参数的构造函数
    public DataBaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: database");
        String sql="create table contacts(id Integer primary key autoincrement,name varchar(20) ,phoneNum varchar(30),info varchar(255))";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade: 更新成功");
    }
}
