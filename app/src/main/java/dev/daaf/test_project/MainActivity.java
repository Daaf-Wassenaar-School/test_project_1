package dev.daaf.test_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        make_hello_world();
        show_hello_world();
    }

    private void make_hello_world() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference message = database.getReference("message");

        message.setValue(Calendar.getInstance().getTime().toString());

        Toast.makeText(getApplicationContext(), "Tijd opgeslagen in database", Toast.LENGTH_SHORT).show();
    }

    public void refresh(View view) {
        make_hello_world();
    }

    private void show_hello_world() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference message = database.getReference("message");
        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        };
        message.addValueEventListener(messageListener);
    }

    public void woorden_lijst(View view) {
        DatabaseReference databaseReference;
        ListView listView;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter;

        setContentView(R.layout.activity_main);
        databaseReference=FirebaseDatabase.getInstance().getReference("woorden");
        listView=(ListView)findViewById(R.id.listView);
        arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value=snapshot.getValue(Woorden.class).toString();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}