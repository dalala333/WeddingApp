package com.example.wedding_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "event_management.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // No table creation needed since prepopulated DB is used
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle DB upgrade logic if needed
    }

    // Query for upcoming events
    public Cursor getUpcomingEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT event_id, event_name, event_date, event_start_time FROM Events WHERE event_date >= DATE('now') ORDER BY event_date ASC", null);
    }

    // Query for total costs of events
    public Cursor getEventTotalCosts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT event_id, event_name, SUM(vendor_cost) AS total_cost FROM Events LEFT JOIN Vendors ON Events.event_id = Vendors.event_id GROUP BY Events.event_id", null);
    }

    // Query for vendors of an activity
    public Cursor getVendorsForActivity(int eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT vendor_name, activity_name FROM Vendors WHERE event_id = ?", new String[]{String.valueOf(eventId)});
    }

    // Query for guest meal preferences
    public Cursor getGuestMeals(int eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT meal_choice, COUNT(*) AS total FROM Guests WHERE event_id = ? GROUP BY meal_choice", new String[]{String.valueOf(eventId)});
    }

    // Query for incomplete payments
    public Cursor getIncompletePayments() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT customer_id, payment_due, vendor_id FROM Payments WHERE payment_status = 'Incomplete'", null);
    }

    // Query to fetch customers by name or ID
    public List<String> getCustomers(String filter) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> customers = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT customer_id, first_name, last_name FROM Customers WHERE customer_id LIKE ? OR first_name LIKE ? OR last_name LIKE ?",
                new String[]{"%" + filter + "%", "%" + filter + "%", "%" + filter + "%"});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String customer = cursor.getInt(0) + " - " + cursor.getString(1) + " " + cursor.getString(2); // Format: ID - FirstName LastName
                customers.add(customer);
            }
            cursor.close();
        }
        return customers;
    }

    public boolean addItemToEvent(int eventId, String itemName, String itemDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("event_id", eventId);
        values.put("item_name", itemName);
        values.put("item_details", itemDetails);

        long result = db.insert("EventItems", null, values); // Replace "EventItems" with your table name
        return result != -1; // Return true if insert is successful
    }

    public boolean updateEventItem(int itemId, String newItemName, String newItemDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("item_name", newItemName);
        values.put("item_details", newItemDetails);

        int rowsUpdated = db.update("EventItems", values, "item_id=?", new String[]{String.valueOf(itemId)});
        return rowsUpdated > 0; // Return true if update is successful
    }

    public boolean deleteEventItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("EventItems", "item_id=?", new String[]{String.valueOf(itemId)});
        return rowsDeleted > 0; // Return true if delete is successful
    }

    public List<String> getItemsForEvent(int eventId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> items = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT item_name FROM EventItems WHERE event_id = ?", new String[]{String.valueOf(eventId)});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String itemName = cursor.getString(cursor.getColumnIndex("item_name"));
                items.add(itemName);
            }
            cursor.close();
        }
        return items;
    }


}
