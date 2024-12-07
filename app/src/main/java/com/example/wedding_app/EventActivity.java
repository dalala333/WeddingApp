package com.example.wedding_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private EditText itemNameInput, itemDetailsInput;
    private ListView itemListView;
    private Button addButton, updateButton, deleteButton;
    private List<String> itemList;
    private ItemAdapter itemAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_management);

        // Initialize UI components
        itemNameInput = findViewById(R.id.itemName);
        itemDetailsInput = findViewById(R.id.itemDetail);
        itemListView = findViewById(R.id.itemListView);  // Ensure this matches the ID in the layout

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize item list and adapter
        itemList = new ArrayList<>();
        itemAdapter = new ItemAdapter(this, R.layout.list_item, itemList);
        itemListView.setAdapter(itemAdapter);  // Set adapter after initializing ListView

        // Add item
        addButton = findViewById(R.id.addItemButton);
        addButton.setOnClickListener(v -> {
            String itemName = itemNameInput.getText().toString().trim();
            String itemDetails = itemDetailsInput.getText().toString().trim();

            if (!itemName.isEmpty() && !itemDetails.isEmpty()) {
                addItem(itemName, itemDetails);
            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            }
        });

        // Update item (implementation should be added as needed)
        updateButton = findViewById(R.id.updateItemButton);
        updateButton.setOnClickListener(v -> {
            // Logic for updating an item
        });

        // Delete item (implementation should be added as needed)
        deleteButton = findViewById(R.id.deleteItemButton);
        deleteButton.setOnClickListener(v -> {
            // Logic for deleting an item
        });

        // Load initial item list from database
        loadItemList();
    }

    private void addItem(String itemName, String itemDetails) {
        boolean isAdded = dbHelper.addItemToEvent(1, itemName, itemDetails); // Replace 1 with actual eventId
        if (isAdded) {
            Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
            loadItemList(); // Refresh the list after adding an item
        } else {
            Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadItemList() {
        itemList.clear();
        // Fetch items from DB and add to itemList
        List<String> fetchedItems = dbHelper.getItemsForEvent(1); // Fetch items for event ID 1, replace with actual eventId
        if (fetchedItems != null && !fetchedItems.isEmpty()) {
            itemList.addAll(fetchedItems);
            itemAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No items found", Toast.LENGTH_SHORT).show();
        }
    }
}
