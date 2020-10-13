package com.hym.shop.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * db工具类
 * @author Mika.
 * @created 2020/10/12 19:00.
 */
public class DBOpenHelper extends SQLiteOpenHelper {


    /**
     * 新建數據庫
     * @param context
     */
    public DBOpenHelper(Context context) {
        super(context, "shoppingCar.db", null, 1);
    }


    /**
     * 新建表
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table wares(id integer primary key,waresId integer UNIQUE ,title text not null,price REAL,img varchar(20),count intger,isCheck intger)";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
