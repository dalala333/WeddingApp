
package com.example.wedding_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_events);

        // Initialize UI Components
        TextView eventsDisplay = findViewById(R.id.eventsDisplay);

        // Sample logic for displaying events
        String sampleEvents = "Event 1: Wedding Ceremony\nEvent 2: Reception\nEvent 3: After Party";
        eventsDisplay.setText(sampleEvents);
    }
}
