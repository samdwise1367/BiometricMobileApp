package samdwise.justjava.com.biometricmobile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CSCSBiometricsAppTable";

    // Contacts table name
    private static final String TABLE_Investors = "Investors";



    // Contacts Table Columns names
    private static final String UserId = "userId";
    private static final String Fname = "fname";
    private static final String Mname = "mname";
    private static final String Lname = "lname";
    private static final String Dob = "dob";
    private static final String HomeAdress = "homeAdd";
    private static final String Gender = "gender";

    private static final String Latitude = "latitude";
    private static final String Longitude = "longitude";

    private static final String ContactAddress = "contactAdd";
    private static final String PhoneNumber = "phoneNumber";
    private static final String Email = "email";
    private static final String Blood = "bloodgroup";
    private static final String Marital = "maritalstatus";

    private static final String NumberChildren = "nochildren";
    private static final String Disability = "disability";
    private static final String SchoolStatus = "schholStatus";
    private static final String Education = "education";
    private static final String HouseholdType = "householdType";
    private static final String SizeHousehold = "sizeHousehold";

    private static final String ImageUrl = "imageUrl";
    private static final String DateAdded = "dateAdded";
    private static final String UniqueId = "uniqueId";
    private static final String AccountType = "accountType";

    ////////////////////////////////////////////////////////////////

    // Fingerprints table name
    private static final String TABLE_Fingerprints = "Fingerprints";
    // Fingerprints Table Columns names
    private static final String FRecordId = "FRecordId";
    private static final String FUserId = "FUserId";
    private static final String FUniqueId = "FUniqueId";
    private static final String FFingerPosition = "FFingerPosition";
    private static final String FImageUrl1 = "FImageUrl1";
    private static final String FImageUrl2 = "FImageUrl2";
    private static final String FImageUrl3 = "FImageUrl3";

    //Location table name
    private static final String TABLE_Locations = "Locations";
    private static final String LocationId = "LocationId";
    private static final String LocationAddress = "LocationAddress";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_Investors + "("
                + UserId + " INTEGER PRIMARY KEY AUTOINCREMENT," + Fname
                + " TEXT," + Mname + " TEXT, " + Lname + " TEXT, " + Dob + " TEXT, "
                + HomeAdress + " TEXT, " + Gender + " TEXT, "+ Latitude+ " TEXT, "+ Longitude+ " TEXT, "+ContactAddress+ " TEXT, " + PhoneNumber
                + " TEXT, " + Email + " TEXT, " + Blood + " TEXT, "
                + Marital + " TEXT, " + NumberChildren + " TEXT, " + Disability + " TEXT, "
                + SchoolStatus + " TEXT, " + Education + " TEXT, " + HouseholdType
                + " TEXT, "+SizeHousehold+ " TEXT," + ImageUrl + " TEXT, " + DateAdded + " TEXT, " + UniqueId
                + " TEXT, " + AccountType + " TEXT " + ")";
        /////////////////////////////////////////////////////////////////////////
        String CREATE_FINGERPRINTS_TABLE = "CREATE TABLE " + TABLE_Fingerprints + "("
                + FRecordId + " INTEGER PRIMARY KEY AUTOINCREMENT," + FUserId
                + " TEXT," + FUniqueId + " TEXT, " + FFingerPosition + " TEXT, "
                + FImageUrl1 + " TEXT, " +
                FImageUrl2 + " TEXT, " +
                FImageUrl3 + " TEXT " + ")";
        //////////////////////////////////////////////////////////////////////////
        String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_Locations + "("
                + LocationId + " INTEGER PRIMARY KEY AUTOINCREMENT," + LocationAddress
                + " TEXT " + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_FINGERPRINTS_TABLE);
        db.execSQL(CREATE_LOCATION_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Investors);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Fingerprints);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Locations);

        // Create tables again
        onCreate(db);
    }

    public int addContact(InvestorPpty investor) {
        int id= -1;
        SQLiteDatabase db = null;

        ContentValues values = new ContentValues();
        //values.put(UserId, investor.get_userId());
        values.put(Fname, investor.get_fname());
        values.put(Mname, investor.get_mname());
        values.put(Lname, investor.get_lname());
        values.put(Dob, investor.get_dob());
        values.put(HomeAdress, investor.get_homeAddress());
        values.put(Gender, investor.get_gender());
        values.put(Latitude, investor.get_latitude());
        values.put(Longitude, investor.get_longitude());
        values.put(ContactAddress, investor.get_contactAddress());
        values.put(PhoneNumber, investor.get_phoneNumber());
        values.put(Email, investor.get_email());
        values.put(Blood, investor.get_blood());
        values.put(Marital, investor.get_marital());

        values.put(NumberChildren, investor.get_children());
        values.put(Disability, investor.get_disablity());
        values.put(SchoolStatus, investor.get_schoolStatus());
        values.put(Education, investor.get_education());
        values.put(HouseholdType, investor.get_houseHoldType());
        values.put(SizeHousehold, investor.get_sizeHousehold());

        values.put(ImageUrl, investor.get_imageUrl());
        values.put(UniqueId, investor.get_uniqueId());
        values.put(AccountType, investor.get_accountType());
        values.put(DateAdded, investor.get_dateAdded());
        try {
            db = this.getWritableDatabase();

            // Inserting Row
            id=(int) db.insert(TABLE_Investors, null, values);
            System.out.println("id......"+id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {

            db.close();
        }
        return id;

    }

    public int addAddress(SaveLocation locate) {
        int id=-1;
        SQLiteDatabase db = null;
        //Toast.makeText, String.valueOf(locate.getAddress()),Toast.LENGTH_SHORT).show();
        ContentValues values = new ContentValues();
        // values.put(UserId, investor.get_userId());
        String newAddress = locate.get_address().replace("null","").trim();
        values.put(LocationAddress, newAddress);

        try {
            db = this.getWritableDatabase();

            // Inserting Row
            id=(int) db.insert(TABLE_Locations, null, values);
            System.out.println("id......"+id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {

            db.close();
        }
        return id;

    }


    public long addFinger(FingerprintPpty finger) {
        long id = -1;
        SQLiteDatabase db = null;

        ContentValues values = new ContentValues();
        // values.put(UserId, investor.get_userId());
        values.put(FUserId, finger.get_fUserId());
        values.put(FUniqueId, finger.get_fUniqueId());
        values.put(FFingerPosition, finger.get_fFingerPosition());
        values.put(FImageUrl1, finger.get_fFingerImageUrl1());
        values.put(FImageUrl2, finger.get_fFingerImageUrl2());
        values.put(FImageUrl3, finger.get_fFingerImageUrl3());
        try {
            db = this.getWritableDatabase();
            id=(int) db.insert(TABLE_Fingerprints, null, values);
            System.out.println("id......"+id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {

            db.close();
        }
        return id;

    }

    // Getting single contact
    InvestorPpty getInvestor(int id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        InvestorPpty contact = null;
        try {
            db = this.getReadableDatabase();

            cursor = db
                    .query(TABLE_Investors, new String[] { UserId, Fname,
                                    Mname, Lname, Dob, HomeAdress, Gender,Latitude,Longitude,ContactAddress,
                                    PhoneNumber, Email, Blood,  Marital,
                                    NumberChildren, Disability, SchoolStatus, Education, HouseholdType,SizeHousehold,
                                    ImageUrl, DateAdded, UniqueId, AccountType, },
                            UserId + "=?", new String[] { String.valueOf(id) },
                            null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            contact = new InvestorPpty(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8),
                    cursor.getString(9), cursor.getString(10),
                    cursor.getString(11), cursor.getString(12),
                    cursor.getString(13), cursor.getString(14),
                    cursor.getString(15), cursor.getString(16),
                    cursor.getString(17), cursor.getString(18),
                    cursor.getString(19),cursor.getString(20),
                    cursor.getString(21),cursor.getString(22),cursor.getString(23));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }

        // return contact
        return contact;
    }
    FingerprintPpty getFinger(int id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        FingerprintPpty contact = null;
        try {
            db = this.getReadableDatabase();

            cursor = db
                    .query(TABLE_Fingerprints, new String[] { FRecordId, FUserId,
                                    FUniqueId, FFingerPosition, FImageUrl1,
                                    FImageUrl2, FImageUrl3, },
                            FRecordId + "=?", new String[] { String.valueOf(id) },
                            null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            contact = new FingerprintPpty(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5)
                    , cursor.getString(6));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }

        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<InvestorPpty> getAllContacts() {
        List<InvestorPpty> contactList = new ArrayList<InvestorPpty>();
        // Select All Query
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String selectQuery = "SELECT  * FROM " + TABLE_Investors;

        try {
            db = this.getWritableDatabase();
            cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    InvestorPpty contact = new InvestorPpty();
                    contact.set_userId(Integer.parseInt(cursor.getString(0)));
                    contact.set_fname((cursor.getString(1)));
                    contact.set_mname(cursor.getString(2));
                    contact.set_lname((cursor.getString(3)));
                    contact.set_dob((cursor.getString(4)));
                    contact.set_homeAddress((cursor.getString(5)));
                    contact.set_gender((cursor.getString(6)));
                    contact.set_latitude((cursor.getString(7)));
                    contact.set_longitude((cursor.getString(8)));
                    contact.set_contactAddress((cursor.getString(9)));

                    contact.set_phoneNumber((cursor.getString(10)));
                    contact.set_email((cursor.getString(11)));
                    contact.set_blood((cursor.getString(12)));
                    contact.set_marital((cursor.getString(13)));

                    contact.set_children(cursor.getString(14));
                    contact.set_disablity(cursor.getString(15));
                    contact.set_schoolStatus(cursor.getString(16));
                    contact.set_education(cursor.getString(17));
                    contact.set_houseHoldType(cursor.getString(18));
                    contact.set_sizeHousehold(cursor.getString(19));


                    contact.set_imageUrl((cursor.getString(20)));
                    contact.set_dateAdded((cursor.getString(21)));
                    contact.set_uniqueId((cursor.getString(22)));
                    contact.set_accountType((cursor.getString(23)));
                    // Adding contact to list
                    contactList.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }

        // return contact list
        return contactList;
    }


    public List<SaveLocation> getAllAddress() {
        List<SaveLocation> addressList = new ArrayList<SaveLocation>();
        // Select All Query
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String selectQuery = "SELECT  * FROM " + TABLE_Locations;

        try {
            db = this.getWritableDatabase();
            cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SaveLocation add = new SaveLocation();
                    add.set_address(cursor.getString(1));

                    // Adding contact to list
                    addressList.add(add);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }

        // return contact list
        return addressList;
    }

    public List<FingerprintPpty> getAllFingers() {
        List<FingerprintPpty> fingerList = new ArrayList<FingerprintPpty>();
        // Select All Query
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String selectQuery = "SELECT  * FROM " + TABLE_Fingerprints;

        try {
            db = this.getWritableDatabase();
            cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    FingerprintPpty contact = new FingerprintPpty();
                    contact.set_fRecordId(Integer.parseInt(cursor.getString(0)));
                    contact.set_fUserId((cursor.getString(1)));
                    contact.set_fUniqueId(cursor.getString(2));
                    contact.set_fFingerPosition((cursor.getString(3)));
                    contact.set_fFingerImageUrl1((cursor.getString(4)));
                    contact.set_fFingerImageUrl2((cursor.getString(5)));
                    contact.set_fFingerImageUrl3((cursor.getString(6)));

                    // Adding contact to list
                    fingerList.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }

        // return contact list
        return fingerList;
    }

    // Updating single contact
    public int updateContact(InvestorPpty investor) {
        int count = 0;
        SQLiteDatabase db = null;

        ContentValues values = new ContentValues();

        values.put(Fname, investor.get_fname());
        values.put(Mname, investor.get_mname());
        values.put(Lname, investor.get_lname());
        values.put(Dob, investor.get_dob());
        values.put(HomeAdress, investor.get_homeAddress());
        values.put(Gender, investor.get_gender());
        values.put(Latitude, investor.get_latitude());
        values.put(Longitude, investor.get_longitude());

        values.put(ContactAddress, investor.get_contactAddress());
        values.put(PhoneNumber, investor.get_phoneNumber());
        values.put(Email, investor.get_email());
        values.put(Blood, investor.get_blood());
        values.put(Marital, investor.get_marital());


        values.put(NumberChildren, investor.get_children());
        values.put(Disability, investor.get_disablity());
        values.put(SchoolStatus, investor.get_schoolStatus());
        values.put(Education, investor.get_education());
        values.put(HouseholdType, investor.get_houseHoldType());
        values.put(SizeHousehold, investor.get_sizeHousehold());

        values.put(ImageUrl, investor.get_imageUrl());
        values.put(DateAdded, investor.get_dateAdded());
        values.put(UniqueId, investor.get_uniqueId());
        values.put(AccountType, investor.get_accountType());

        try {
            db = this.getWritableDatabase();
            db.update(TABLE_Investors, values, UserId + " = ?",
                    new String[] { String.valueOf(investor.get_userId()) });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            db.close();
        }
        // updating row

        return investor.get_userId();
    }
    public int updateFinger(FingerprintPpty finger) {
        int count = 0;
        SQLiteDatabase db = null;

        ContentValues values = new ContentValues();

        values.put(FUserId, finger.get_fUserId());
        values.put(FUniqueId, finger.get_fUniqueId());
        values.put(FFingerPosition, finger.get_fFingerPosition());
        values.put(FImageUrl1, finger.get_fFingerImageUrl1());
        values.put(FImageUrl2, finger.get_fFingerImageUrl2());
        values.put(FImageUrl3, finger.get_fFingerImageUrl3());

        try {
            db = this.getWritableDatabase();
            db.update(TABLE_Fingerprints, values, FRecordId + " = ?",
                    new String[] { String.valueOf(finger.get_fRecordId()) });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            db.close();
        }
        // updating row
        return count;
    }
    // Deleting single contact
    public void deleteContact(InvestorPpty investor) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.delete(TABLE_Investors, UserId + " = ?",
                    new String[] { String.valueOf(investor.get_userId()) });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            db.close();
        }

    }
    public void deleteFingers(FingerprintPpty finger) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.delete(TABLE_Fingerprints, FRecordId + " = ?",
                    new String[] { String.valueOf(finger.get_fUserId()) });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            db.close();
        }

    }

    // Getting contacts Count
    public int getContactsCount() {
        int count = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String countQuery = "SELECT  * FROM " + TABLE_Investors;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(countQuery, null);
            count = cursor.getCount();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }

        // return count
        return count;
    }

    public int getLocationCount() {
        int count = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String countQuery = "SELECT  * FROM " + TABLE_Locations;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery(countQuery, null);
            count = cursor.getCount();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cursor.close();
            db.close();
        }

        // return count
        return count;
    }

    public int getLastId() {

        int id = 0;
        SQLiteDatabase db = null;
        Cursor mCursor = null;
        final String MY_QUERY = "SELECT MAX(" + UserId + ") AS _id FROM "
                + TABLE_Investors;

        try {
            db = this.getReadableDatabase();
            mCursor = db.rawQuery(MY_QUERY, null);
            if (mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                id = mCursor.getInt(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            mCursor.close();
            db.close();
        }
        return id;

    }

    public int getLastFinger() {

        int id = 0;
        SQLiteDatabase db = null;
        Cursor mCursor = null;

        String MY_QUERY = "SELECT "+FRecordId+" from "+TABLE_Fingerprints+
                " order by "+FRecordId+" DESC limit 1";
        try {
            db = this.getReadableDatabase();
            mCursor = db.rawQuery(MY_QUERY, null);
            if (mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                id = mCursor.getInt(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            mCursor.close();
            db.close();
        }
        return id;

    }

}
