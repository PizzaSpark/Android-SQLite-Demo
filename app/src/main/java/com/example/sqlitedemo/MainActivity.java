package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtName, txtLocation, txtCourse;
    Button btnSave, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtName = findViewById(R.id.txtName);
        txtLocation = findViewById(R.id.txtLocation);
        txtCourse = findViewById(R.id.txtCourse);
        btnSave = findViewById(R.id.btnSave);
        btnView = findViewById(R.id.btnView);

        btnSave.setOnClickListener(v -> {
            String name = txtName.getText().toString();
            String location = txtLocation.getText().toString();
            String course = txtCourse.getText().toString();

            if(name.isEmpty()){
                toastMessage("Name is required");
                return;
            }

            if(location.isEmpty()){
                toastMessage("Location is required");
                return;
            }

            if(course.isEmpty()){
                toastMessage("Course is required");
                return;
            }

            Student student = new Student(name, location, course);
            DBHandler dbHandler = new DBHandler(this);
            dbHandler.addStudent(student);
            toastMessage("Student added successfully");
        });


    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}