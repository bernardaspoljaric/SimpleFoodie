package hr.ferit.bspoljaric.simplefoodie.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "simpleFoodieDB.db";
    private static final int DB_VER = 1;
    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public void addToFavorites(String foodId){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO Favorites(FoodId) VALUES('%s');", foodId);
        db.execSQL(query);

    }
    public void removeFromFavorites(String foodId){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM Favorites WHERE FoodId = '%s';", foodId);
        db.execSQL(query);

    }
    public boolean isFavorite(String foodId){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT * FROM Favorites WHERE FoodId = '%s';", foodId);
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.getCount() <= 0){

            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

}
