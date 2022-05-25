package com.example.practice_fitbit.MainScreens.dropdownforprofile;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.practice_fitbit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Dropdownactivity extends AppCompatActivity {

    Button btnadd;
    Spinner spinner;
    DatabaseReference dbref;

    ValueEventListener listener;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner = (Spinner) findViewById(R.id.spinnerdata);
        btnadd = (Button) findViewById(R.id.btnadd);
        dbref = FirebaseDatabase.getInstance().getReference("spinnerdata");

        list=new ArrayList<String>();
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);

        fetchdata();
    }


    public void fetchdata()
    {
        listener=dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mydata : snapshot.getChildren())
                    list.add(Objects.requireNonNull(mydata.getValue()).toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.example.practice_fitbit.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class Dropdownactivity extends AppCompatActivity {
//
//    Button btnadd;
//    Spinner spinner;
//    DatabaseReference dbref;
//
//    ValueEventListener listener;
//    ArrayList<String> list;
//    ArrayAdapter<String> adapter;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        spinner = findViewById(R.id.spinnerdata);
//        btnadd = findViewById(R.id.btnadd);
//        dbref = FirebaseDatabase.getInstance().getReference("spinnerdata");
//
//        list=new ArrayList<String>();
//        adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,list);
//        spinner.setAdapter(adapter);
//
//        fetchdata();
//    }
//
//
//
//    public void fetchdata()
//    {
//        listener=dbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot mydata : snapshot.getChildren())
//                    list.add(mydata.getValue().toString());
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
//}
