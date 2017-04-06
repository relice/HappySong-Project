package com.happysong.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.happysong.app.bean.RndomInfo;

import java.util.ArrayList;
import java.util.List;



/**
 * 数据库操作
 */
public class DBHelper extends BaseDBHelper {
    public final int OBJ_ID = 1;
    public final int IMG_ID = 2;
    public static final String RANDOM_TABLE_NAME = "random_table";

    private static final String RANDOM_OBJ_ID = "random_obj_id";
    private static final String RANDOM_IMG_ID = "random_img_id";
    private static final String RANDOM_IMG_URL = "random_img_url";
    private static final String RANDOM_IMG_DES = "random_img_des";
    private static final String RANDOM_IMG_TITLE = "random_img_title";
    private static final String RANDOM_IMG_TYPE = "random_img_type";
    //是否被收藏
    private static final String RANDOM_IS_LIKED = "random_is_liked";
    //relice寄语
    private static final String RANDOM_RELICE_SAY = "random_relice_say";

    private Context mCont;

    public DBHelper(Context context) {
        super(context);
        mCont = context;
    }


    /**
     * 保存随机图
     *
     * @param info
     * @return
     */
    public boolean saveRandomImg(RndomInfo info) {
        StringBuffer sql = new StringBuffer();
        sql.append("insert into "
                + RANDOM_TABLE_NAME + "("
                + RANDOM_OBJ_ID + ","
                + RANDOM_IMG_ID + ","
                + RANDOM_IMG_URL + ","
                + RANDOM_IMG_DES + ","
                + RANDOM_IMG_TITLE + ","
                + RANDOM_IMG_TYPE + ","
                + RANDOM_IS_LIKED + ","
                + RANDOM_RELICE_SAY +
                ") ");
        db.beginTransaction();
        try {
//            if (cursor.getCount() > 0) {
//                db.delete(RANDOM_TABLE_NAME, RANDOM_OBJ_ID + "=?",
//                        new String[]{info.getObjectId()});
//                cursor.close();
//            }

            sql.append("values ('"
                    + info.getObjectId() + "','"
                    + info.getImg_id() + "','"
                    + info.getImg_url() + "','"
                    + info.getImg_des() + "','"
                    + info.getImg_title() + "','"
                    + info.getImg_type() + "','"
                    + info.is_liked() + "','"
                    + info.getRelice_say()
                    + "')");//结尾还有个 ' 不可以忘记了
            db.execSQL(sql.toString());
            db.setTransactionSuccessful();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 删除随机图中某一条数据
     */
    public void deleteScanHistory(RndomInfo info) {
        db.delete(RANDOM_TABLE_NAME,
                RANDOM_OBJ_ID + "=?", new String[]{info.getObjectId() + ""});
    }

    /**
     * 查询商品浏览本地保存记录
     */
    public Cursor queryRandom() {
        String sql;
        sql = "select * from " + RANDOM_TABLE_NAME
                + " order by id desc";
        Cursor cursor;
        cursor = db.rawQuery(sql, null);
        return cursor;
    }

    /**
     * 查询图片集合
     */
    public List<RndomInfo> queryRandomList() {
        DBHelper helper = new DBHelper(mCont);
        Cursor cursor = helper.queryRandom();

        ArrayList<RndomInfo> infos = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            infos = new ArrayList<>();
            do {
                //查询数据库不可以从index 0 开始查找,index 0 默认是数据库的序号
                RndomInfo info = new RndomInfo();
                info.setObjectId(cursor.getString(1));
                info.setImg_id(cursor.getString(2));
                info.setImg_url(cursor.getString(3));
                info.setImg_des(cursor.getString(4));
                info.setImg_title(cursor.getString(5));
                info.setImg_type(cursor.getString(6));
                info.setIs_liked(Boolean.parseBoolean(cursor.getString(7)));//浏览记录ID
                info.setRelice_say(cursor.getString(8));
                infos.add(info);
            } while (cursor.moveToNext());
        }

        cursor.close();
        helper.close();
        return infos;
    }

    /**
     * 删除数据库的表
     */
    public void deleteRandomTable() {
        String sql = "DELETE FROM " + RANDOM_TABLE_NAME + ";";
        db.execSQL(sql);
//        db.close();
    }

    /**
     * 查询某条数据是否存在
     *
     * @param id or imgID
     * @return
     */
    public RndomInfo queryRandomByID(String id, int queryType) {
        Cursor cursor = null;
        if (queryType == OBJ_ID) {
            cursor = db.rawQuery("select * from "
                    + RANDOM_TABLE_NAME + " where " + RANDOM_OBJ_ID
                    + " = '" + id + "'", null);
        } else if (queryType == IMG_ID) {
            cursor = db.rawQuery("select * from "
                    + RANDOM_TABLE_NAME + " where " + RANDOM_IMG_ID
                    + " = '" + id + "'", null);
        }

        RndomInfo info = new RndomInfo();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            //查询数据库不可以从index 0 开始查找,index 0 默认是数据库的序号
            info.setObjectId(cursor.getString(1));
            info.setImg_id(cursor.getString(2));
            info.setImg_url(cursor.getString(3));
            info.setImg_des(cursor.getString(4));
            info.setImg_title(cursor.getString(5));
            info.setImg_type(cursor.getString(6));
            info.setIs_liked(Boolean.parseBoolean(cursor.getString(7)));//浏览记录ID
            info.setRelice_say(cursor.getString(8));
        }

        cursor.close();
        return info;
    }


    @Override
    public void onDataBaseCreat(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + RANDOM_TABLE_NAME
                + " ( id integer primary key autoincrement,"
                + RANDOM_OBJ_ID + " varchar,"
                + RANDOM_IMG_ID + " varchar, "
                + RANDOM_IMG_URL + " varchar, "
                + RANDOM_IMG_DES + " varchar, "
                + RANDOM_IMG_TITLE + " varchar, "
                + RANDOM_IMG_TYPE + " varchar, "
                + RANDOM_IS_LIKED + " varchar, "
                + RANDOM_RELICE_SAY + " varchar)");
    }

    @Override
    public void onDataBaseUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("drop table if exists " + RANDOM_TABLE_NAME);
    }
}
