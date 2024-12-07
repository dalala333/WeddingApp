package com.example.wedding_app;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    private ListView customerListView;
    private ArrayAdapter<String> customerAdapter;
    private List<String> customerList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_management);

        // Initialize UI components
        customerListView = findViewById(R.id.customerListView);
        Button viewListButton = findViewById(R.id.viewListButton);
        EditText filterInput = findViewById(R.id.filterInput);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize customer list
        customerList = new ArrayList<>();
        customerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customerList);
        customerListView.setAdapter(customerAdapter);

        // Fetch and display customers
        viewListButton.setOnClickListener(v -> {
            String filter = filterInput.getText().toString().trim();
            loadCustomerList(filter);
        });
    }

    // Inside CustomerActivity.java
    private void loadCustomerList(String filter) {
        customerList.clear(); // Clear the existing list
        List<String> fetchedCustomers = dbHelper.getCustomers(filter); // Fetch filtered customers

        if (fetchedCustomers != null && !fetchedCustomers.isEmpty()) {
            customerList.addAll(fetchedCustomers);
            customerAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No customers found", Toast.LENGTH_SHORT).show();
        }
    }
}