package samdwise.justjava.com.biometricmobile;

/**
 * Created by Akinrinde on 3/20/2016.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "enrollment";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";

    public static final String ENROLL_COLUMN_EMAIL = "semail";
    public static final String ENROLL_COLUMN_FNAME = "sfName";
    public static final String ENROLL_COLUMN_MNAME = "smName";
    public static final String ENROLL_COLUMN_LNAME = "slName";
    public static final String ENROLL_COLUMN_MAIDENNAME = "smothMaidenName";
    public static final String ENROLL_COLUMN_GENDER = "sgender";
    public static final String ENROLL_COLUMN_DOB = "sdob";

    public static final String ENROLL_COLUMN_STEL = "sTel";


    public static final String ENROLL_COLUMN_SOO = "sStateOfOrigin";


    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table enrollment " +
                        "(id integer primary key,semail text, sfName text,smName text,slName text, smothMaidenName text, sgender text, sdob text,sTel text," +
                        "sNationality text,sStateOfOrigin text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS enrollment");
        onCreate(db);
    }

    public boolean insertEnrollment(String semail,String sfName, String smName, String slName, String smothMaidenName,String sgender, String sdob)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("semail" ,semail);
        contentValues.put("sfName", sfName);
        contentValues.put("smName", smName);
        contentValues.put("slName", slName);
        contentValues.put("smothMaidenName", smothMaidenName);
        contentValues.put("sgender", sgender);
        contentValues.put("sdob", sdob);
        db.insert("enrollment", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from enrollment where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateEnroll1 (String sEmail, String sTel, String sNationality, String sStateOfOrigin)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("sEmail", sEmail);
        contentValues.put("sTel", sTel);
        contentValues.put("sNationality", sNationality);
        contentValues.put("sStateOfOrigin", sStateOfOrigin);
        //contentValues.put("place", place);
        db.update("enrollment", contentValues, "sEmail = ? ", new String[] { sEmail } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
