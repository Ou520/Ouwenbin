package com.example.myapplication.ouwenbin.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//SQLite数据创建支持的数据类型： 整型数据，字符串类型，日期类型，二进制的数据类型
public class DatabaseHelper extends SQLiteOpenHelper {
  //定义常量，来存放数据库的东西
    public static final String DB_NAME="mydb";//数据库名
    public static final int DB_VERSION=1;//版本号//数据库版本号
    public static final String TABLE_USERS="users";//表名
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "userName";
    public static final String COL_PWD = "pwd";
    public static final String SQL = "CREATE TABLE " + TABLE_USERS + "(" +
            COL_ID + " integer primary key autoincrement," +
            COL_USERNAME + " text," +
            COL_PWD + " text" + ")";

  //结束
  private static DatabaseHelper databaseHelper;
  public DatabaseHelper( Context context) {
      //必须通过super调用父类当中的构造函数
      super(context, DB_NAME, null, DB_VERSION);
      //参数说明
      //context:上下文对象
      //name:数据库名称
      //param:factory
      //version:当前数据库的版本，值必须是整数并且是递增的状态
  }

  @Override
  //第一次创建数据库时执行的语句写在里面
  public void onCreate(SQLiteDatabase db)
  {
      db.execSQL(SQL);//execSQL用于执行SQL语句  //完成数据库的创建
  }
    /*数据库实际上是没有被创建或者打开的，直到getWritableDatabase()
    或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开 */

//作用：更新数据库表结构
//调用时机：数据库版本发生变化的时候回调（取决于数据库版本）
// 创建SQLiteOpenHelper子类对象的时候,必须传入一个version参数
//该参数就是当前数据库版本, 只要这个版本高于之前的版本, 就会触发这个onUpgrade()方法，如下面代码
  @Override
  //数据库升级时调用//当版本号发生改变时执行的语句写在里面
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//参数说明：
// db ： 数据库
//oldVersion ： 旧版本数据库
//newVersion ： 新版本数据库
//注意：这里的删除等操作必须要保证新的版本必须要比旧版本的版本号要大才行。[即 Version 2.0 > Version 1.0 ] 所以这边我们不需要对其进行操作。
  }


    public static synchronized DatabaseHelper getInstance(Context context){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }



//    public boolean add(Users users)
//    {
//        SQLiteDatabase db=this.getReadableDatabase();// 调用getReadableDatabase()方法创建或打开一个可以读的数据库
//        long flag=0;//定义长整型变量
//        ContentValues values=new ContentValues();// 创建ContentValues对象
////        values.put(COL_USERNAME,useName);
////        values.put(COL_PWD,pwd);
//        // 向该对象中插入键值
//        values.put(COL_USERNAME,users.getUserName());
//        values.put(COL_PWD,users.getUserPwd());
//        //其中，key代表列名，value代表该列要插入的值
//        //注：ContentValues内部实现就是HashMap，但是两者还是有差别的
//        //ContenValues Key只能是String类型，Value只能存储基本类型数据，不能存储对象
//
//        //--------插入数据到表中------------
//        //做异常处理
//        try {//没有异常时执行下面语句
//            flag=db.insert(TABLE_USERS,null,values);// 调用insert()方法将数据插入到数据库当中
//                                                                    // 第一个参数：要操作的表名称
//                                                                    // 第二个参数：SQl不允许一个空列，如果ContentValues是空的，那么这一列被明确的指明为NULL值
//                                                                    // 第三个参数：ContentValues对象
//            ////db.execSQL("insert into user (id,name) values (1,'张三')")  也可以
//        }catch (Exception e)//有异常时执行下面语句
//        {
//            Log.v("error",e.getLocalizedMessage());
//            return false;
//        }
//        if (flag>0){
//            return true;
//        }
//        return false;
//    }
//    //-----------查询数据---------
//    public ArrayList<Users> getAllUsers()
//    {
//        SQLiteDatabase db=this.getReadableDatabase();//打开可读的数据库。  getReadableDatabase()：创建或打开可读的数据库
//        ArrayList<Users> list=new ArrayList<>();//定义动态数组//将Cursor中的数据转为 ArrayList<Users> 类型数据
//        try {
//            Cursor cursor=null;//定义游标
//            //方法-（最简单）
//            //将所有的SQL语句都组织到一个字符串中，使用占位符代替实际参数（selectionArgs）
//            cursor = db.rawQuery("select * from " + TABLE_USERS ,null);//给游标赋值进行数据库查询
//
//
//            if (cursor !=null && cursor.moveToNext()){  //判断游标是否为空并且向下移动游标
//                Users users=new Users(); //new一个Users模型
//                //遍历Cursor
//                do {
//                    //输入第一列数据
//                    users.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
//                    users.setUserName(cursor.getString(cursor.getColumnIndex(COL_USERNAME)));
//                    users.setUserPwd(cursor.getString(cursor.getColumnIndex(COL_PWD)));
//                    list.add(users);
//                }while (cursor.moveToNext());// 将光标移动到下一行，从而判断该结果集是否还有下一条数据
//                                            //如果有则返回true，没有则返回false
//            }
//            if (cursor !=null)
//            {
//                cursor.close();//关闭游标
//            }
//        }catch (Exception e){
//            Log.v("mydb",e.getMessage());//打印日志
//        }
//        return list;
//    }
//      //------------方法二-------------------------------
//    public Users getUsersByUserName(String userName)
//    {
//        SQLiteDatabase db=this.getReadableDatabase();//打开可读的数据库。  getReadableDatabase()：创建或打开可读的数据库
//        Users users=new Users(); //new一个Users模型
//        Cursor cursor=null;//定义游标
//        try {
//            cursor=db.query(TABLE_USERS,new String[]{COL_ID,COL_USERNAME,COL_PWD},COL_USERNAME + " =? ",new String[]{userName},null,null,null);
//            //cursor= db.query("user", new String[] { "id",  "name" }, "id=?", new String[] { "1" }, null, null, null);
//            // 第一个参数String：表名
//            // 第二个参数String[]:要查询的列名
//            // 第三个参数String：查询条件
//            // 第四个参数String[]：查询条件的参数
//            // 第五个参数String:对查询的结果进行分组
//            // 第六个参数String：对分组的结果进行限制
//            // 第七个参数String：对查询的结果进行排序
//      /*    db.query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy);
//          db.query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit);
//          db.query(String distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit);
//       */
//            //参数说明
//            //table：要操作的表明
//            //columns：查询的列所有名称集
//            //selection：WHERE之后的条件语句，可以使用占位符
//            //groupBy：指定分组的列名
//            //having指定分组条件，配合groupBy使用
//            //orderBy指定排序的列名
//            //limit指定分页参数
//            //distinct可以指定“true”或“false”表示要不要过滤重复值
//            //所有方法将返回一个Cursor对象，代表数据集的游标
//            if (cursor!=null && cursor.moveToFirst())
//            {
//                users.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
//                users.setUserName(cursor.getString(cursor.getColumnIndex(COL_USERNAME)));
//                users.setUserPwd(cursor.getString(cursor.getColumnIndex(COL_PWD)));
//            }
//        }catch (Exception e)
//        {
//            Log.v("mydb",e.getMessage());
//        }
//        return users;
//    }
//      //-----------修改（更新）数据---------
//      public boolean updateUsersByid(Users users)
//      {
//          SQLiteDatabase db=this.getReadableDatabase();//打开可读的数据库。  getReadableDatabase()：创建或打开可读的数据库
//          int flag =0;
//          ContentValues values=new ContentValues();// 创建ContentValues对象
//          // 向该对象中插入键值
//          values.put(COL_USERNAME,users.getUserName());
//          values.put(COL_PWD,users.getUserPwd());
//          //其中，key代表列名，value代表该列要插入的值
//          //注：ContentValues内部实现就是HashMap，但是两者还是有差别的
//          //ContenValues Key只能是String类型，Value只能存储基本类型数据，不能存储对象
//          //做异常处理
//          try {//没有异常时执行下面语句
//              // 调用update方法修改数据库
//              flag=db.update(TABLE_USERS,values,COL_ID  + " =? ",new String[]{String.valueOf(values.getAsInteger(COL_ID))});
//              // 第一个参数String：表名
//              // 第二个参数ContentValues：ContentValues对象（需要修改的）
//              // 第三个参数String：WHERE表达式，where选择语句, 选择那些行进行数据的更新, 如果该参数为 null, 就会修改所有行;？号是占位符
//              // 第四个参数String[]：where选择语句的参数, 逐个替换 whereClause 中的占位符;
//          }catch (Exception e)//有异常时执行下面语句
//          {
//              Log.v("error",e.getLocalizedMessage());
//              return false;
//          }
//          if (flag>0){
//              return true;
//          }
//          return false;
//      }
//      //-----------删除数据---------
//     public boolean deleteById(int id)
//     {
//         SQLiteDatabase db=this.getReadableDatabase();//打开可读的数据库。  getReadableDatabase()：创建或打开可读的数据库
//         int flag =0;
//         try {//没有异常时执行下面语句
//             //调用delete方法进行删除操作
//             flag=db.delete(TABLE_USERS,COL_ID + " =? ",new String[]{String.valueOf(id)});
//             //第一个参数String：需要操作的表名
//             //第二个参数String：where选择语句, 选择哪些行要被删除, 如果为null, 就删除所有行;
//             //第三个参数String[]： where语句的参数, 逐个替换where语句中的 "?" 占位符;
//         }catch (Exception e)//有异常时执行下面语句
//         {
//             Log.v("mydb",e.getLocalizedMessage());
//         }
//         if (flag>0){
//             return true;
//         }
//         return false;
//     }
}
