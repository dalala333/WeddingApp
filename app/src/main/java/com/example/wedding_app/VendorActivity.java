
package com.example.wedding_app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VendorActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText itemIdInput, itemNameInput, itemDetailInput, eventIdInput;
    private TextView itemDisplay;
    private LinearLayout modifyLayout;
    private ScrollView viewListScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_management);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Initialize UI Components
        itemIdInput = findViewById(R.id.itemId);
        itemNameInput = findViewById(R.id.itemName);
        itemDetailInput = findViewById(R.id.itemDetail);
        eventIdInput = findViewById(R.id.eventId);
        itemDisplay = findViewById(R.id.itemDisplay);
        modifyLayout = findViewById(R.id.modifyLayout);
        viewListScroll = findViewById(R.id.viewListScroll);

        Button viewListButton = findViewById(R.id.viewListButton);
        Button modifyListButton = findViewById(R.id.modifyListButton);
        Button addButton = findViewById(R.id.addItemButton);
        Button editButton = findViewById(R.id.updateItemButton);
        Button deleteButton = findViewById(R.id.deleteItemButton);

        // Set Button Listeners
        viewListButton.setOnClickListener(v -> viewItemList());
        modifyListButton.setOnClickListener(v -> toggleModifyList(true));
        addButton.setOnClickListener(v -> addItem());
        editButton.setOnClickListener(v -> editItem());
        deleteButton.setOnClickListener(v -> deleteItem());

        // Initially hide modify layout
        toggleModifyList(false);
    }

    private void toggleModifyList(boolean show) {
        if (show) {
            modifyLayout.setVisibility(View.VISIBLE);
            viewListScroll.setVisibility(View.GONE);
        } else {
            modifyLayout.setVisibility(View.GONE);
            viewListScroll.setVisibility(View.VISIBLE);
        }
    }

    private void viewItemList() {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM VendorActivity WHERE event_id = ?";
            Cursor cursor = db.rawQuery(query, new String[]{eventIdInput.getText().toString()});

            StringBuilder builder = new StringBuilder();
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String detail = cursor.getString(2);

                    builder.append("ID: ").append(id).append("\n")
                            .append("Name: ").append(name).append("\n")
                            .append("Detail: ").append(detail).append("\n\n");
                } while (cursor.moveToNext());
            } else {
                builder.append("No items found for this event.");
            }
            itemDisplay.setText(builder.toString());
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error loading items: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addItem() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id", itemIdInput.getText().toString());
            values.put("name", itemNameInput.getText().toString());
            values.put("detail", itemDetailInput.getText().toString());
            values.put("event_id", eventIdInput.getText().toString());

            long result = db.insert("VendorActivity", null, values);
            if (result == -1) {
                Toast.makeText(this, "Error adding item.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Item added successfully!", Toast.LENGTH_SHORT).show();
                viewItemList();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void editItem() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", itemNameInput.getText().toString());
            values.put("detail", itemDetailInput.getText().toString());

            int rows = db.update("VendorActivity", values, "id = ?", new String[]{itemIdInput.getText().toString()});
            if (rows > 0) {
                Toast.makeText(this, "Item updated successfully!", Toast.LENGTH_SHORT).show();
                viewItemList();
            } else {
                Toast.makeText(this, "No item found with this ID.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteItem() {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int rows = db.delete("VendorActivity", "id = ?", new String[]{itemIdInput.getText().toString()});
            if (rows > 0) {
                Toast.makeText(this, "Item deleted successfully!", Toast.LENGTH_SHORT).show();
                viewItemList();
            } else {
                Toast.makeText(this, "No item found with this ID.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
