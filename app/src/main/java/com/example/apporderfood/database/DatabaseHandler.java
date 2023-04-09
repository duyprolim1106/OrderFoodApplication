package com.example.apporderfood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.apporderfood.model.Food;
import com.example.apporderfood.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "myDatabase.db";

    // Table names
    private static final String TABLE_FOOD = "food";
    private static final String TABLE_USER = "user";

    // Food table column names
    private static final String KEY_FOOD_IMAGE = "image";
    private static final String KEY_FOOD_NAME = "foodname";
    private static final String KEY_FOOD_MONEY = "money";
    private static final String KEY_FOOD_QUANTITY = "quantity";

    // User table column names
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_USERNAME = "username";
    private static final String KEY_USER_PASSWORD = "password";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Food table
        String CREATE_FOOD_TABLE = "CREATE TABLE " + TABLE_FOOD + "("
                + KEY_FOOD_IMAGE + " INTEGER PRIMARY KEY,"
                + KEY_FOOD_NAME + " TEXT,"
                + KEY_FOOD_MONEY + " INTEGER,"
                + KEY_FOOD_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_FOOD_TABLE);

        // Create the User table
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USER_USERNAME + " TEXT,"
                + KEY_USER_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    //Adding new food item
    public void addFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_IMAGE, food.getImage());
        values.put(KEY_FOOD_NAME, food.getFoodName());
        values.put(KEY_FOOD_MONEY, food.getMoney());
        values.put(KEY_FOOD_QUANTITY, food.getQuantity());
        db.insert(TABLE_FOOD, null, values);
        db.close();
    }

    // Updating single food item
    public int updateFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_IMAGE, food.getImage());
        values.put(KEY_FOOD_NAME, food.getFoodName());
        values.put(KEY_FOOD_MONEY, food.getMoney());
        values.put(KEY_FOOD_QUANTITY, food.getQuantity());
        return db.update(TABLE_FOOD, values, KEY_FOOD_IMAGE + " = ?",
                new String[] { String.valueOf(food.getImage()) });
    }

    public void updateFoodQuantity(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_QUANTITY, food.getQuantity());
        db.update(TABLE_FOOD, values, KEY_FOOD_IMAGE + "=?", new String[]{String.valueOf(food.getImage())});
        db.close();
    }

    // Delete a food from the database
    public void deleteFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOOD, KEY_FOOD_IMAGE + " = ?", new String[] { String.valueOf(food.getImage()) });
        db.close();
    }

    public List<Food> getAllFoodItems() {
        List<Food> foodList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FOOD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setImage(Integer.parseInt(cursor.getString(0)));
                food.setFoodName(cursor.getString(1));
                food.setMoney(Integer.parseInt(cursor.getString(2)));
                food.setQuantity(Integer.parseInt(cursor.getString(3)));
                foodList.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodList;
    }

    public boolean checkIdExists(Food food) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_FOOD_IMAGE}; // Replace with the actual column names of your table
        String selection = KEY_FOOD_IMAGE + " = ?"; // Replace with the actual column name for ID
        String[] selectionArgs = {String.valueOf(food.getImage())}; // Replace with the actual value of the ID to check
        String limit = "1"; // Limit the result to 1 row since we're only checking for existence

        Cursor cursor = db.query(TABLE_FOOD, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = cursor.getCount() > 0;
        cursor.close();

        return exists;
    }

    public void clearAllFoodData() {
        SQLiteDatabase db = getWritableDatabase(); // Replace with your own database reference

        // Delete all rows from the "Food" table
        int rowsDeleted = db.delete(TABLE_FOOD, null, null);

        if (rowsDeleted > 0) {
            // Data cleared successfully, do something
        } else {
            // Failed to clear data, do something else
        }

        db.close();
    }


    // Add a new user to the database
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME, user.getUsername());
        values.put(KEY_USER_PASSWORD, user.getPassword());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    // Update an password user in the database
    public void updatePasswordByUsername(String username, String newPassword) {
        SQLiteDatabase db = getWritableDatabase(); // Replace with your own database reference
        ContentValues values = new ContentValues();
        values.put(KEY_USER_PASSWORD, newPassword); // Replace "password" with the actual column name for password in your User table

        // Update the User table with the new password based on the username
        int rowsAffected = db.update(TABLE_USER, values, KEY_USER_USERNAME + " = ?", new String[]{username});

        if (rowsAffected > 0) {
            // Update successful, do something
        } else {
            // Update failed, do something else
        }

        db.close();
    }


    // Delete a user from the database
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_USER_ID + " = ?", new String[] { String.valueOf(user.getId()) });
        db.close();
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { KEY_USER_ID };
        String selection = KEY_USER_USERNAME + " = ?" + " AND " + KEY_USER_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count > 0;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { KEY_USER_ID };
        String selection = KEY_USER_USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count > 0;
    }


}
