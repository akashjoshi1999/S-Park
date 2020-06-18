package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Objects;

public class HistoryOwnerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<PaymentOwner> list;
    MyAdapterForOwner myAdapterForOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_owner);
        Objects.requireNonNull(getSupportActionBar()).hide();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewHistoryOwner);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<PaymentOwner>();

        FirebaseDatabase.getInstance().getReference("data")
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("history")
            .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String key = dataSnapshot1.getKey();
                        PaymentOwner p = dataSnapshot.child(key).getValue(PaymentOwner.class);
                        list.add(p);
                        myAdapterForOwner = new MyAdapterForOwner(HistoryOwnerActivity.this, list);
                        recyclerView.setAdapter(myAdapterForOwner);
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
         });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.v("a12", "BACK");
        Intent i = new Intent(HistoryOwnerActivity.this, OwnerActivity.class);
        startActivity(i);
    }
}
