package in.ac.iitp.hdi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by satish on 4/1/2017.
 */

public class InfoDataBaseAdapter
{
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+"STATE  text,GENDER text,STARTYEAR text,ENDYEAR text,VALUE text); ";
    // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public  InfoDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  InfoDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String state,String gender,String startYear,String endYear,String value)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("STATE", state);
        newValues.put("GENDER", gender);
        newValues.put("STARTYEAR", startYear);
        newValues.put("ENDYEAR", endYear);
        newValues.put("VALUE", value);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
		/*public int deleteEntry(String UserName)
		{
			//String id=String.valueOf(ID);
		    String where="USERNAME=?";
		    int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
	       // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
	        return numberOFEntriesDeleted;
		}	*/
		public String getSingleEntry(String state,String gender,String birthYear)
		{
            int startYear=Integer.valueOf(birthYear);
            if(startYear<=2005 && startYear>=2001){
                birthYear="2001";
            }
            else if(startYear<=2010 && startYear>=2006){
                birthYear="2006";
            }
            else if(startYear<=2015 && startYear>=2011){
                birthYear="2011";
            }
            else if(startYear<=2020 && startYear>=2016){
                birthYear="2016";
            }
            else if(startYear<=2025 && startYear>=2021){
                birthYear="2021";
            }
            System.out.println("Value of birthYear:"+birthYear);
			Cursor cursor=db.query("LOGIN", null, " STATE=? AND GENDER=? AND STARTYEAR=?", new String[]{state,gender,birthYear}, null, null, null);
            //Cursor c = db.rawQuery("SELECT * FROM tbl1 WHERE TRIM(STATE) = '"+state.trim()+"'", null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String value= cursor.getString(cursor.getColumnIndex("VALUE"));
			cursor.close();
			return value;
		}
		/*public void  updateEntry(String userName,String password)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("USERNAME", userName);
			updatedValues.put("PASSWORD",password);

	        String where="USERNAME = ?";
		    db.update("LOGIN",updatedValues, where, new String[]{userName});
		}	*/
}
