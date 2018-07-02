package site.kobatomo.akushukai.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import site.kobatomo.akushukai.UserContract;

/**
 * Created by taguchi on 2015/05/13.
 */
public class UserOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "app.db";
    public static final int DB_VERSION = 126;
    public static final String CREATE_TABLE =
            "create table " + UserContract.Event.TABLE_NAME + " (" +
                    UserContract.Event._ID + " integer primary key, " +
                    UserContract.Event.EVENT_ID + " text," +
                    UserContract.Event.COL_TYPE + " text," +
//                    UserContract.Event.COL_DATE + " text," +
                    UserContract.Event.YEAR + " text," +
                    UserContract.Event.MONTH + " text," +
                    UserContract.Event.DAY + " text," +
                    UserContract.Event.PLACE + " text," +

//                    UserContract.Event.COL_LOC + " text," +
                    UserContract.Event.COL_TICKET + " text)";

    public static final String CREATE_TABLE_KOBETSU =
            "create table " + UserContract.Kobetsu.TABLE_NAME + " (" +
                    UserContract.Kobetsu._ID + " integer primary key, " +
                    UserContract.Kobetsu.EVENT_ID + " text," +
                    UserContract.Kobetsu.MEMBER + " text," +
                    UserContract.Kobetsu.NANBU + " text," +
                    UserContract.Kobetsu.URL + " text," +
                    UserContract.Kobetsu.BUSUU + " text)";

//    public static final String CREATE_TABLE_ZENKOKU =
//            "create table " + UserContract.Zenkoku.TABLE_NAME + " (" +
//                    UserContract.Zenkoku.YEAR + " text," +
//                    UserContract.Zenkoku.MONTH + " text," +
//                    UserContract.Zenkoku.DAY + " text," +
//                    UserContract.Zenkoku.PLACE + " text," +
//                    UserContract.Zenkoku.MEMBER + " text," +
//                    UserContract.Zenkoku.LANE + " text," +
//                    UserContract.Zenkoku.BUSUU + " text)";


    public static final String CREATE_TABLE_ZENKOKU =
            "create table " + UserContract.Zenkoku.TABLE_NAME + " (" +
                    UserContract.Zenkoku._ID + " integer primary key, " +
                    UserContract.Zenkoku.EVENT_ID + " text," +
                    UserContract.Zenkoku.MEMBER + " text," +
                    UserContract.Zenkoku.URL + " text," +
                    UserContract.Zenkoku.BUSUU + " text)";

//    public static final String CREATE_TABLE_ZENKOKU =
//            "create table " + UserContract.Zenkoku.TABLE_NAME + " (" +
////             ZENKOKUMEMBERテーブルと紐付けるID
//                    UserContract.Zenkoku.EVENT_ID + " text)";
////                    UserContract.Zenkoku.YEAR + " text," +
////                    UserContract.Zenkoku.MONTH + " text," +
////                    UserContract.Zenkoku.DAY + " text," +
////                    UserContract.Zenkoku.PLACE + " text)";


    public static final String CREATE_TABLE_ZENKOKU_MEMBER =
//            ZENKOKUテーブルと紐付けるID
            "create table " + UserContract.ZenkokuMember.TABLE_NAME + " (" +
                    UserContract.ZenkokuMember.EVENT_ID+" text," +
                    UserContract.ZenkokuMember.MEMBER + " text," +
                    UserContract.ZenkokuMember.LANE + " text," +
                    UserContract.ZenkokuMember.BUSUU + " text)";


    public static final String CREATE_TABLE_MEMBER =
            "create table " + UserContract.Member.TABLE_NAME + " (" +
                    UserContract.Member._ID + " integer primary key, " +
                    UserContract.Member.NAME + " text," +
                    UserContract.Member.URL + " text)";

//    public static final String INSERT_TABLE_MEMBER =
//    "insert into member (name, url) values " +
//            "('石森 虹花','http://cdn.keyakizaka46.com/images/14/b73/9d17d9445c63ffe04548fe14559c7/400_320_102400.jpg'),"+
//            "('今泉 佑唯','http://cdn.keyakizaka46.com/images/14/514/2fe0c906fce3bf0869c19c764b345/400_320_102400.jpg'),"+
//            "('上村 莉菜','http://cdn.keyakizaka46.com/images/14/1b0/5ce96a28b9735269eef3a59664836/400_320_102400.jpg'),"+
//            "('尾関 梨香','http://cdn.keyakizaka46.com/images/14/0c0/71a66d1735d641922e5c70ee8e23c/400_320_102400.jpg'),"+
//            "('織田 奈那','http://cdn.keyakizaka46.com/images/14/3b5/90f1f562d722acad704949e8d99a3/400_320_102400.jpg'),"+
//            "('小池 美波','http://cdn.keyakizaka46.com/images/14/c6f/f17b468961ce10adbf62ed3f93387/400_320_102400.jpg'),"+
//            "('小林 由依','http://cdn.keyakizaka46.com/images/14/f52/b99b43f53c66f566d2d49ca17e47e/400_320_102400.jpg'),"+
//            "('齋藤 冬優花','http://cdn.keyakizaka46.com/images/14/557/5508e16189be2899fbdd7a3b7972b/400_320_102400.jpg'),"+
//            "('佐藤 詩織','http://cdn.keyakizaka46.com/images/14/ea6/0f77ddfeedade54512313e3c64c16/400_320_102400.jpg'),"+
//            "('志田 愛佳','http://cdn.keyakizaka46.com/images/14/02f/bad0138f8e8d9e1f270d0b9c01933/400_320_102400.jpg'),"+
//            "('菅井 友香','http://cdn.keyakizaka46.com/images/14/605/36078236b2fd56c893495324e9b5b/400_320_102400.jpg'),"+
//            "('鈴本 美愉','http://cdn.keyakizaka46.com/images/14/435/736cb31e2e76cf7c44ff019c87a61/400_320_102400.jpg'),"+
//            "('長沢 菜々香','http://cdn.keyakizaka46.com/images/14/1a5/3514149db8409daf3644ef869e8e1/400_320_102400.jpg'),"+
//            "('長濱 ねる','http://cdn.keyakizaka46.com/images/14/d2d/51c8eed9384bce8a7e55174b04d6e/400_320_102400.jpg'),"+
//            "('土生 瑞穂','http://cdn.keyakizaka46.com/images/14/776/d652723f04eabb2c364c55efae36d/400_320_102400.jpg'),"+
//            "('原田 葵','http://cdn.keyakizaka46.com/images/14/f0c/7ba24b8bc92d67990a7122c88519b/400_320_102400.jpg'),"+
//            "('平手 友梨奈','http://cdn.keyakizaka46.com/images/14/bd8/e46f9c4686d1767cde04cd30c4ec8/400_320_102400.jpg'),"+
//            "('守屋 茜','http://cdn.keyakizaka46.com/images/14/71e/0743e5b22fcb7a02d3ba4f799b5c3/400_320_102400.jpg'),"+
//            "('米谷 奈々未','http://cdn.keyakizaka46.com/images/14/2e1/67354e8215e4f64a71118f6560e04/400_320_102400.jpg'),"+
//            "('渡辺 梨加','http://cdn.keyakizaka46.com/images/14/161/a9757839b899cf1f91876fe1f7d05/400_320_102400.jpg'),"+
//            "('渡邉 理佐','http://cdn.keyakizaka46.com/images/14/094/eae807fe9e36d79d672b1364d8fdc/400_320_102400.jpg'),"+
//            "('井口 眞緒','http://cdn.keyakizaka46.com/images/14/04c/14dacd30f2b6a7ad017a66c8c1222/400_320_102400.jpg'),"+
//            "('潮 紗理菜','http://cdn.keyakizaka46.com/images/14/1c2/06ad8785a584e4e2cebf35c2ef36b/400_320_102400.jpg'),"+
//            "('柿崎 芽実','http://cdn.keyakizaka46.com/images/14/e43/ef65cde1edc50b5cc8dd5d6aefb77/400_320_102400.jpg'),"+
//            "('影山 優佳','http://cdn.keyakizaka46.com/images/14/fdc/dd24d840f117af6b3f07ac626d673/400_320_102400.jpg'),"+
//            "('加藤 史帆','http://cdn.keyakizaka46.com/images/14/d5a/eff6a3b75c9e64505228eef211dcb/400_320_102400.jpg'),"+
//            "('齊藤 京子','http://cdn.keyakizaka46.com/images/14/385/a304de67244d04fdd8b715c77fc0e/400_320_102400.jpg'),"+
//            "('佐々木 久美','http://cdn.keyakizaka46.com/images/14/03b/9bf55f7bae5fd014bc685da912aef/400_320_102400.jpg'),"+
//            "('佐々木 美玲','http://cdn.keyakizaka46.com/images/14/849/c4bd22af347348079827766d69e2b/400_320_102400.jpg'),"+
//            "('高瀬 愛奈','http://cdn.keyakizaka46.com/images/14/8c9/6cd50d9624fa417e1258443fd69b7/400_320_102400.jpg'),"+
//            "('高本 彩花','http://cdn.keyakizaka46.com/images/14/b9e/157345429736f21d921182ffa9c44/400_320_102400.jpg'),"+
//            "('東村 芽依','http://cdn.keyakizaka46.com/images/14/2ca/e19b0091acdc6529dea8aa7a1cbad/400_320_102400.jpg'),"+
//            "('金村 美玖','http://cdn.keyakizaka46.com/images/14/920/176d0228e3a3d492eb7f5d126ff72/400_320_102400.jpg'),"+
//            "('河田 陽菜','http://cdn.keyakizaka46.com/images/14/66b/97b189aec9b786ef4efc7057824d4/400_320_102400.jpg'),"+
//            "('小坂 菜緒','http://cdn.keyakizaka46.com/images/14/451/b7f11152b13ac995924cc0bf9e7cf/400_320_102400.jpg'),"+
//            "('富田 鈴花','http://cdn.keyakizaka46.com/images/14/ff1/63721810b007df1c0fd3caac8ec27/400_320_102400.jpg'),"+
//            "('丹生 明里','http://cdn.keyakizaka46.com/images/14/3b4/7e8818bad76a74acafca75980f68d/400_320_102400.jpg'),"+
//            "('濱岸 ひより','http://cdn.keyakizaka46.com/images/14/94c/432894fe7fadee21722a7e6c716ac/400_320_102400.jpg'),"+
//            "('松田 好花','http://cdn.keyakizaka46.com/images/14/f3f/a327766895ecd1161d6fbbc51a487/400_320_102400.jpg'),"+
//            "('宮田 愛萌','http://cdn.keyakizaka46.com/images/14/b2f/5a4a20677b782f2e364c5cfb6bb57/400_320_102400.jpg'),"+
//            "('渡邉 美穂','http://cdn.keyakizaka46.com/images/14/714/72cbf1aea6598bb3397d088744015/400_320_102400.jpg')";



    public static final String DROP_TABLE =
            "drop table if exists event";

    public static final String DROP_TABLE_KOBETSU =
            "drop table if exists kobetsu";
    public static final String DROP_TABLE_ZENKOKU =
            "drop table if exists zenkoku";
    public static final String DROP_TABLE_MEMBER =
            "drop table if exists "+UserContract.Member.TABLE_NAME;
    public static final String DROP_TABLE_ZENKOKU_MEMBER =
            "drop table if exists "+UserContract.ZenkokuMember.TABLE_NAME;


    /*public static final String UPDATE_ID ="ALTER TABLE users AUTO_INCREMENT = 1;";*/

    public UserOpenHelper(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create table

        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_KOBETSU);
        sqLiteDatabase.execSQL(CREATE_TABLE_ZENKOKU);
        sqLiteDatabase.execSQL(CREATE_TABLE_ZENKOKU_MEMBER);
        sqLiteDatabase.execSQL(CREATE_TABLE_MEMBER);
//        sqLiteDatabase.execSQL(INSERT_TABLE_MEMBER);
//        sqLiteDatabase.execSQL(INIT_TABLE_MEMBER);

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // drop table
        sqLiteDatabase.execSQL(DROP_TABLE);
        sqLiteDatabase.execSQL(DROP_TABLE_KOBETSU);
        sqLiteDatabase.execSQL(DROP_TABLE_ZENKOKU);
        sqLiteDatabase.execSQL(DROP_TABLE_MEMBER);
        sqLiteDatabase.execSQL(DROP_TABLE_ZENKOKU_MEMBER);

        // onCreate
        onCreate(sqLiteDatabase);
    }
}