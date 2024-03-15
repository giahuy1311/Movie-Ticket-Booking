package com.example.midtermproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import com.example.midtermproject.R;
import com.example.midtermproject.adapter.BookingHistoryAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Booking> bookingList;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        Intent intent = getIntent();
        String serializedBookingList = intent.getStringExtra("bookingList");
        Gson gson = new Gson();
        Type bookingListType = new TypeToken<List<Booking>>(){}.getType();
        bookingList = gson.fromJson(serializedBookingList, bookingListType);


        // setup recycler view
        // if the list is empty, show a message
        if (bookingList.isEmpty()) {
            TextView emptyMessage = findViewById(R.id.empty_list);
            emptyMessage.setText("You have no booking history");
        }
        recyclerView = findViewById(R.id.recycler_booking_history);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        BookingHistoryAdapter adapter = new BookingHistoryAdapter(bookingList, this);
        recyclerView.setAdapter(adapter);

        btnBack = findViewById(R.id.backBtn);
        btnBack.setOnClickListener(view -> {
            finish();
        });

    }


}