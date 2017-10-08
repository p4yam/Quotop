package ir.kivee.quotop.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import ir.kivee.quotop.data.Quote;

/**
 * Created by payam on 9/23/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/ir.kivee.quotop/databases/";
    private static String DB_NAME = "database.db";
    private SQLiteDatabase myDataBase;
    private final Context context;
    private Quote quote;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //database.db does't exist yet.
        }

        if (checkDB != null)
            checkDB.close();

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0)
            myOutput.write(buffer, 0, length);

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase
                (myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database.db");
            }
        }
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        Cursor cursor = myDataBase.rawQuery("select * from categories", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            categories.add(cursor.getString(1));
            cursor.moveToNext();
        }
        return categories;
    }

    public List<Quote> getQuotes(int catId) {
        List<Quote> quotes = new ArrayList<>();
        //SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = myDataBase.
                rawQuery("select * from quotes where category_id=" + catId, null);

        while (cursor.moveToNext()) {
            quote = new Quote();
            quote.setId(cursor.getInt(0));
            quote.setCategory_id(cursor.getInt(1));
            quote.setQuote(cursor.getString(2));
            quote.setAuthor(cursor.getString(3));
            quote.setBook(cursor.getString(4));
            quote.setFavorite(cursor.getInt(6));
            quotes.add(quote);
        }
        return quotes;
    }

    public void setQuoteFavorite(int id,int faveValue){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("favorite", faveValue);
        db.update("quotes", contentValues, "id='" + id + "'", null);

    }

}
