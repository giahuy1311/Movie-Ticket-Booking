package com.example.midtermproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SeatBookingActivity extends AppCompatActivity {
    private Movie movie;
    private Showtime showtime;
    private String theaterName;
    private String time, activity;
    private TextView movieNameTextView;
    private TextView theaterNameTextView;
    private TextView timeTextView;
    private TextView dayTextView;
    private ImageView movieImageView;
    private List<Seat> seatList;
    private TextView priceTextView;
    private TextView numberOfTicketsTextView;
    private List<Seat> bookedSeatList;
    private FloatingActionButton fabButton;
    private TableLayout tableLayout;
    private Booking booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_booking);

        movieNameTextView = findViewById(R.id.movieTitleTextView);
        theaterNameTextView = findViewById(R.id.theaterTextView);
        timeTextView = findViewById(R.id.timeTextView);
        dayTextView = findViewById(R.id.dayTextView);
        movieImageView = findViewById(R.id.imageView);
        priceTextView = findViewById(R.id.priceTextView);
        numberOfTicketsTextView = findViewById(R.id.numTicketTextView);
        fabButton = findViewById(R.id.fabButton);
        tableLayout = findViewById(R.id.seatTable);
        bookedSeatList = new ArrayList<>();
        // check if previous activity is MoviePage or BookingHistory
        activity = getIntent().getStringExtra("activity");
        Log.d("SeatBooking", "onCreate: " + activity);
        if (activity.equals("MoviePage")) {
            movie = (Movie) getIntent().getSerializableExtra("movie");
            showtime = (Showtime) getIntent().getSerializableExtra("showtime");
            theaterName = getIntent().getStringExtra("theaterName");
            time = getIntent().getStringExtra("time");
        }
        else {

            booking = (Booking) getIntent().getSerializableExtra("booking");
            movie = booking.getMovie();
            theaterName = booking.getTheaterName();
            time = booking.getTime();
            Log.d("SeatBooking", "onCreate: " + time + " " + theaterName + " " + movie.getTitle());
            showtime = new Showtime();
            showtime.setDate(booking.getDate());
            showtime.setShowtimeId(booking.getShowtimeId());
            bookedSeatList = booking.getSeatList();
            booking.reverseSeatList();
        }

        movieNameTextView.setText(movie.getTitle());
        theaterNameTextView.setText(theaterName);
        timeTextView.setText(time);
        dayTextView.setText(showtime.getDate() + ", " + showtime.getDayOfWeek());
        Picasso.get().load(movie.getPoster()).into(movieImageView);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        seatList = new ArrayList<>();
        // Get the seats from the theater in database
        loadSeatListData(seatList);

    }

    private void loadSeatListData(List<Seat> seatList) {
        Log.d("SeatBooking", "loadSeatListData: " + theaterName);
        DatabaseReference theatersRef = FirebaseDatabase.getInstance().getReference("theater");
        theatersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot theaterSnapshot : dataSnapshot.getChildren()) {
                    String theaterName1 = (String) theaterSnapshot.child("name").getValue();
                    String seatListString = (String) theaterSnapshot.child("seat").getValue();
                    String seatPrice = (String) theaterSnapshot.child("seatPrice").getValue();

                    if (theaterName1 != null && seatListString != null && theaterName1.equals(theaterName)) {
                        // Split the seatListString into an array of seat IDs
                        String[] seatIDs = seatListString.split(" ");

                        // Add each seat ID to the seatList
                        for (String seatID : seatIDs) {
                            seatList.add(new Seat(seatID, false, Double.parseDouble(seatPrice)));
                        }
                        // Create table seats
                        updateSeatList(seatList);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("SeatBooking", "onCancelled: " + databaseError.getMessage());
                // Handle error
            }
        });
    }

    private void createSeatTable(List<Seat> seatList) {

        int col = 8;

        int seatListSize = seatList.size();
        int row = 6;

        for (int i = 0; i < row; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT, 1.0f
            ));
            tableLayout.addView(tableRow);
            for (int j = 0; j < col; j++) {
                int seatIndex = i * col + j;
                if (seatIndex < seatListSize) {
                    Seat seat = seatList.get(seatIndex);
                    Button seatButton = new Button(this);

                    TableRow.LayoutParams params = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT, 1.0f
                    );
                    params.setMargins(5, 5, 5, 5);
                    seatButton.setLayoutParams(params);

                    Log.d("SeatBooking", "createSeatTable: " + seat.isBooked());
                    if (activity.equals("bookingHistory")) {
                        for (Seat bookedSeat : booking.getSeatList()) {
                            if (bookedSeat.getSeatId().equals(seat.getSeatId())) {
                                Log.d("SeatBooking", "setupBackground: " + seat.getSeatId());
                                seat.setIsSelected();
                            }
                        }
                    }
                    updateBookingInfo();
                    setupBackground(seat, seatButton);
                    seatButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Handle seat button click
                            if (seat.isBooked()) {
                                Toast.makeText(SeatBookingActivity.this, "This seat is already booked!", Toast.LENGTH_SHORT).show();
                            }
                            else if (seat.isSelected()) {
                                Log.d("SeatBooking", "onClick: " + seat.getSeatId());
                                seat.setUnSelected();
                                bookedSeatList.removeIf(seat1 -> seat1.getSeatId().equals(seat.getSeatId()));
                                Log.d("SeatBooking", "onClick: " + bookedSeatList.size());
                                updateBookingInfo();
                                // return back to original color
                                seatButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.seat_avail));
                            }
                            else {
                                seat.setIsSelected();
                                bookedSeatList.add(seat);
                                updateBookingInfo();
                                seatButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.seat_selected));
                            }
                        }
                    });
                    tableRow.addView(seatButton);
                }
            }
        }
    }

    private void setupBackground(Seat seat, Button seatButton) {
        if (seat.isBooked()) {
                seatButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.seat_booked));

        }
        else if (seat.isSelected()) {
            seatButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.seat_selected));
        }
        else {
            seatButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.seat_avail));

        }
    }

    private void updateSeatList(List<Seat> seatList){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("showtime");
        reference.child(movie.getTitle()).child(showtime.getShowtimeId()).child("bookedseat").child(time).child("seat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String seatListString = dataSnapshot.getValue(String.class);
                if (seatListString != null) {
                    String[] seatIDs = seatListString.split(" ");
                    for (String seatID : seatIDs) {
                        for (Seat seat : seatList) {
                            if (seat.getSeatId().equals(seatID)) {
                                seat.setIsBooked();
                            }
                        }
                    }
                }
                createSeatTable(seatList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("SeatBooking", "onCancelled: " + databaseError.getMessage());
                // Handle error
            }
        });
    }

    private void updateBookingInfo() {
        double totalPrice = 0;
        totalPrice = calculateTotalPrice();

        numberOfTicketsTextView.setText(bookedSeatList.size() + "");
        priceTextView.setText("Total: $" + totalPrice);

        double finalTotalPrice = totalPrice;
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date currentTime = new Date();

                // Định dạng thời gian theo 12 giờ
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
                String timeBooked = sdf.format(currentTime);

                if (!activity.equals("bookingHistory")) {
                    booking = new Booking(bookedSeatList, movie, theaterName, time, showtime.getDate(), finalTotalPrice, timeBooked, showtime.getShowtimeId());
                }
                // Handle fab button click);
                setUpSeatBooking(booking);
            }
        });
    }

    private void setUpSeatBooking(Booking booking) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Booking");
        builder.setMessage("Do you want to book " + bookedSeatList.size() + " seat(s) for a total of $" + calculateTotalPrice() + "?");

        // Add positive button (Yes action)
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform the booking action

                for (Seat seat : seatList) {
                    if (seat.isSelected()) {
                        seat.setIsBooked();
                    }
                }
                performBookingAction(booking);
                updateTableBackground();
                showDialogPurchase(booking);
            }
        });

        // Add negative button (Cancel action)
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User cancelled the booking
                dialog.dismiss();
            }
        });

        // Show the dialog
        builder.show();
    }

    private void showDialogPurchase(Booking booking) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Purchase information");
        builder.setMessage("Your total is $" + calculateTotalPrice() + ". Do you want to pay now?");

        // Add positive button (Yes action)
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform the booking action
                booking.setPaid();
                booking.updatePurchase();
                Toast.makeText(SeatBookingActivity.this, "Thank you! Wish you have a great time!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                // restart activity
                Intent i= new Intent(SeatBookingActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        builder.setNegativeButton("Pay later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform the booking action
                dialog.dismiss();
                Intent i= new Intent(SeatBookingActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        // Show the dialog
        builder.show();
    }

    private void updateTableBackground() {
        int col = 8;
        int seatListSize = seatList.size();
        int row = 6;

        for (int i = 0; i < row; i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < col; j++) {
                int seatIndex = i * col + j;
                if (seatIndex < seatListSize) {
                    Seat seat = seatList.get(seatIndex);
                    Button seatButton = (Button) tableRow.getChildAt(j);
                    setupBackground(seat, seatButton);
                }
            }
        }
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for (Seat seat : bookedSeatList) {
            totalPrice += seat.getPrice();
        }
        return totalPrice;
    }

    private void performBookingAction(Booking booking) {
        Toast.makeText(this, "Seats booked successfully!", Toast.LENGTH_SHORT).show();

        // Update seatList in firebase
        String newbookedSeatListString = "";
        for (Seat seat : seatList) {
            if (seat.isBooked()) {
                newbookedSeatListString += seat.getSeatId() + " ";
            }
        }
        Log.d("SeatBooking", "performBookingAction: " + newbookedSeatListString);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("showtime");
        reference.child(movie.getTitle())
                .child(showtime.getShowtimeId())
                .child("bookedseat")
                .child(time)
                .child("seat")
                .setValue(newbookedSeatListString)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // The value was successfully updated
                        Log.d("FirebaseUpdate", "Value updated successfully");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Log.e("FirebaseUpdate", "Error updating value", e);
                    }
                });
        updateBookingToFirebase(booking);
    }

    private void updateBookingToFirebase(Booking booking) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            String uid = user.getUid();
        }

        if (activity.equals("bookingHistory")) {
            booking.setSeatList(bookedSeatList);
            booking.setTimeBooked(booking.getTimeBooked());
            booking.setTotalPrice(calculateTotalPrice());

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("booking");
            reference.child(booking.getBookingId()).setValue(booking);
        }
        else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("booking");

            reference.child(booking.getBookingId()).setValue(booking, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        Log.d("FirebaseUpdate", "Booking added successfully");
                    } else {
                        Log.e("FirebaseUpdate", "Error adding booking", databaseError.toException());
                    }
                }
            });
        }
    }
}