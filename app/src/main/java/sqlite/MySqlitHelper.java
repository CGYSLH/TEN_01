package sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by 暗示语 on 2016/11/17.
 */

public class MySqlitHelper extends SQLiteOpenHelper {

    public MySqlitHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqlitHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    public MySqlitHelper(Context context)
    {
        super(context,"saveurl.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists table_share (_id" +
                " integer primary key autoincrement, url text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists table_share");
        onCreate(db);
    }
    public void addUrl(String url) {
        SQLiteDatabase db=getReadableDatabase();
        db.execSQL("insert into table_share (url) values(?)",new Object[]{url});
    }
    public ArrayList<String> getAllUrl()
    {
        SQLiteDatabase db=getReadableDatabase();
      Cursor cursor= db.rawQuery("select * from table_share",null);
        ArrayList<String> list=new ArrayList<>();
        while (cursor.moveToNext()) {
            String url=cursor.getString(cursor.getColumnIndex("url"));
            list.add(url);
        }
        db.close();
        return list;
    }
}
