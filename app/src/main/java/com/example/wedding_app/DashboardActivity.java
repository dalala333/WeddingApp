package com.example.wedding_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        // Manager Features
        Button viewUpcomingEventsButton = findViewById(R.id.viewUpcomingEventsButton);
        Button viewTotalCostsButton = findViewById(R.id.viewTotalCostsButton);
        Button viewVendorsButton = findViewById(R.id.viewVendorsButton);
        Button viewGuestsMealsButton = findViewById(R.id.viewGuestsMealsButton);
        Button viewIncompletePaymentsButton = findViewById(R.id.viewIncompletePaymentsButton);

        // Navigate to activities
        viewUpcomingEventsButton.setOnClickListener(v -> startActivity(new Intent(this, EventActivity.class)));
        viewTotalCostsButton.setOnClickListener(v -> startActivity(new Intent(this, PaymentActivity.class)));
        viewVendorsButton.setOnClickListener(v -> startActivity(new Intent(this, VendorActivity.class)));
        viewGuestsMealsButton.setOnClickListener(v -> startActivity(new Intent(this, GuestActivity.class)));
        viewIncompletePaymentsButton.setOnClickListener(v -> startActivity(new Intent(this, PaymentActivity.class)));

        // Customer Features
        Button customerViewEventsButton = findViewById(R.id.customerViewEventsButton);
        Button customerPaymentStatusButton = findViewById(R.id.customerPaymentStatusButton);

        customerViewEventsButton.setOnClickListener(v -> startActivity(new Intent(this, ViewEventsActivity.class)));
        customerPaymentStatusButton.setOnClickListener(v -> startActivity(new Intent(this, PaymentActivity.class)));
    }
}
