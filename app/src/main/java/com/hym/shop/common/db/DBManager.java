package com.hym.shop.common.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hym.shop.bean.HotWares;

import java.util.ArrayList;
import java.util.List;

/**
 * db工具类
 * @author Mika.
 * @created 2020/10/12 19:00.
 */
public class DBManager {
    public static SQLiteDatabase database;

    public static void initDB(Context context){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        database = dbOpenHelper.getWritableDatabase();
    }


    /**
     * 獲取數據庫當中全部行的内容，存儲到list中
     * @return
     */
    public static List<HotWares.WaresBean> getAllWaresList(){
        ArrayList<HotWares.WaresBean> list = new ArrayList<>();
        Cursor cursor = database.query("wares", null, null, null, null, null, "id desc");
        while (cursor.moveToNext()) {
            HotWares.WaresBean waresBean = new HotWares.WaresBean();
            waresBean.setId(cursor.getInt(cursor.getColumnIndex("waresId")));
            waresBean.setName(cursor.getString(cursor.getColumnIndex("title")));
            waresBean.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
            waresBean.setImgUrl(cursor.getString(cursor.getColumnIndex("img")));
            waresBean.setCount(cursor.getInt(cursor.getColumnIndex("count")));
            waresBean.setCheck(cursor.getInt(cursor.getColumnIndex("isCheck")) == 1);
            list.add(waresBean);
        }
        return list;
    }


    /**
     * 修改數據庫當中的行信息當中的選中記錄
     * @param waresId
     * @param isCheck
     */
    public static void updateWaresIsCheck(int waresId,int isCheck){
            ContentValues values = new ContentValues();
            values.put("isCheck",isCheck);
            database.update("wares",values,"waresId=?",new String[]{String.valueOf(waresId)});
    }


    /**
     * 修改數據庫當中商品數量
     * @param waresId
     * @param count
     */
    public static void updateWaresCount(int waresId,int count){
        ContentValues values = new ContentValues();
        values.put("count",count);
        database.update("wares",values,"waresId=?",new String[]{String.valueOf(waresId)});
    }


    /**
     * 修改數據庫當中商品數量+1
     * @param waresId
     */
    public static void updateWaresCount(int waresId){
        int count = 0;
        Cursor cursor = database.query("wares", null, "waresId="+waresId, null, null, null, "id desc");
        while (cursor.moveToNext()) {
            count = cursor.getInt(cursor.getColumnIndex("count"));
        }
        cursor.close();
        ContentValues values = new ContentValues();
        count++;
        values.put("count",count);
        database.update("wares",values,"waresId=?",new String[]{String.valueOf(waresId)});
    }

    /**
     * 向数据库中插入一条记录
     * @param waresBean
     */
    public static void insertWares(HotWares.WaresBean waresBean){
        if (getWares(waresBean.getId())) {
            updateWaresCount(waresBean.getId());
        }else {
            ContentValues values = new ContentValues();
            values.put("waresId",waresBean.getId());
            values.put("title",waresBean.getName());
            values.put("price",waresBean.getPrice());
            values.put("img",waresBean.getImgUrl());
            values.put("count",waresBean.getCount());
            values.put("isCheck",waresBean.isCheck()? 1 : 0);
            database.insert("wares",null,values);
        }

    }

    /**
     * 清空数据库中搜索历史记录表中的数据
     */
    public static void DeleteAllWares(){
        database.execSQL("delete from " + "wares");
    }

    /**
     * 删除数据库中一条记录
     * @param waresBean
     */
    public static void DeleteWares(HotWares.WaresBean waresBean){
        database.delete("wares","waresId=?",new String[]{String.valueOf(waresBean.getId())});
    }

    /**
     * 獲取數據庫中的一條數據
     * @param waresId
     * @return
     */
    public static boolean getWares(int waresId){
        Cursor cursor = database.query("wares", null, "waresId="+waresId, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count>0?true:false;

    }

    /**
     * 獲取數據庫中所有選中的商品
     * @return
     */
    public static  List<HotWares.WaresBean> getAllCheckedWares(){
        ArrayList<HotWares.WaresBean> list = new ArrayList<>();
        Cursor cursor = database.query("wares", null, "isCheck=" + 1, null, null, null, null);
        while (cursor.moveToNext()) {
            HotWares.WaresBean waresBean = new HotWares.WaresBean();
            waresBean.setId(cursor.getInt(cursor.getColumnIndex("waresId")));
            waresBean.setName(cursor.getString(cursor.getColumnIndex("title")));
            waresBean.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
            waresBean.setImgUrl(cursor.getString(cursor.getColumnIndex("img")));
            waresBean.setCount(cursor.getInt(cursor.getColumnIndex("count")));
            waresBean.setCheck(cursor.getInt(cursor.getColumnIndex("isCheck")) == 1);
            list.add(waresBean);
        }
        return list;
    }
}
