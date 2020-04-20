package com.example.myapplication.ouwenbin.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.myapplication.ouwenbin.model.bean.Users;

import java.util.ArrayList;

public class UsersService {
    DatabaseHelper helper;
    SQLiteDatabase db;
    public UsersService(Context context) {
        helper = DatabaseHelper.getInstance(context);
        db  = helper.getReadableDatabase();
    }

    public boolean add(Users users){

        long flag = 0;
        ContentValues  values = new ContentValues();
        values.put(DatabaseHelper.COL_USERNAME,users.getUserName());
        values.put(DatabaseHelper.COL_PWD,users.getUserPwd());
        //map:key-value
        /*
        Map<String,String> map = new HashMap<>();
        map.put("key1","1");
        map.put("key2","2");

        map.get("key1");
        */
        try{
            flag = db.insert(DatabaseHelper.TABLE_USERS,null,values);
        }catch (Exception e){
            Log.v("error",e.getMessage());
            return false;
        }

        if(flag > 0){
            return true;
        }

        return false;
    }
    public ArrayList<Users> getAllUsers(){

        ArrayList<Users> list = new ArrayList<>();
        try{
            Cursor cursor = null;
            cursor = db.rawQuery("select * from  " + DatabaseHelper.TABLE_USERS ,null);

            if(cursor != null && cursor.moveToFirst()){
                do{
                    Users users = new Users();
                    users.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
                    users.setUserName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME)));
                    users.setUserPwd(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PWD)));
                    list.add(users);
                }while(cursor.moveToNext());
            }
            if (cursor!=null){
                cursor.close();
            }
        }catch (Exception e){
            Log.v("mydb",e.getMessage());
        }
        return list;
    }

    public Users getUsersByUserName(String userName){

        Users users = new Users();
        Cursor cursor = null;
        try{
            //cursor = db.query(TB_USERS,new String[]{COL_ID,COL_USERNAME,COL_PWD},COL_USERNAME +
            // " = " + userName,null,null,null,null);
            cursor = db.query(DatabaseHelper.TABLE_USERS,new String[]{DatabaseHelper.COL_ID,
                    DatabaseHelper.COL_USERNAME,DatabaseHelper.COL_PWD},DatabaseHelper.COL_USERNAME +
                    " = ? ",new String[]{userName},null,null,null);
            if(cursor!=null && cursor.moveToFirst()){
//                users.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID)));
                users.setUserName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_USERNAME)));
                users.setUserPwd(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PWD)));
            }
        }catch (Exception e){
            Log.v("mydb",e.getMessage());
        }
        if(cursor != null){
            cursor.close();
        }
        return users;
    }
    public boolean updateUsers(Users users){

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_ID,users.getId());
        values.put(DatabaseHelper.COL_USERNAME,users.getUserName());
        values.put(DatabaseHelper.COL_PWD,users.getUserPwd());
        int flag = 0;
        try{
            flag=db.update(DatabaseHelper.TABLE_USERS,values,DatabaseHelper.COL_ID + " = ? ",
                    new String[]{String.valueOf(values.getAsInteger(DatabaseHelper.COL_ID))});

        }catch (Exception e){
            Log.v("mydb",e.getMessage());
        }
        if(flag > 0 ) return true;
        return false;
    }
    public boolean deleteById(int id){

        int flag = 0;
        try{
            flag=db.delete(DatabaseHelper.TABLE_USERS,DatabaseHelper.COL_ID + " = ? ",new String[]{String.valueOf(id)});
        }catch (Exception e){
            Log.v("mydb",e.getMessage());
        }
        if(flag > 0 ) return true;
        return false;
    }
}


