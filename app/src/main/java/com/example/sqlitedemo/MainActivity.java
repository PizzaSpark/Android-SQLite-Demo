package com.example.sqlitedemo;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtName, txtLocation, txtCourse;
    Button btnSave, btnView, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtName = findViewById(R.id.txtName);
        txtLocation = findViewById(R.id.txtLocation);
        txtCourse = findViewById(R.id.txtCourse);
        btnSave = findViewById(R.id.btnSave);
        btnView = findViewById(R.id.btnView);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra("Student");

        if (student != null) {
            // populate your input fields with the student's information
            txtName.setText(student.getName());
            txtLocation.setText(student.getLocation());
            txtCourse.setText(student.getCourse());

            // change the text of the save button to update
            btnSave.setText("Update");

            // show the delete button
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            // change the text of the save button to save
            btnSave.setText("Save");

            // hide the delete button
            btnDelete.setVisibility(View.GONE);
        }

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

            DBHandler dbHandler = new DBHandler(this);
            if (student != null) {
                // update existing student
                student.setName(name);
                student.setLocation(location);
                student.setCourse(course);
                dbHandler.updateStudent(student);
                toastMessage("Student updated successfully");
            } else {
                // add new student
                Student newStudent = new Student(name, location, course);
                dbHandler.addStudent(newStudent);
                toastMessage("Student added successfully");
            }

            // clear input fields
            txtName.setText("");
            txtLocation.setText("");
            txtCourse.setText("");
        });

        btnView.setOnClickListener(v -> {
            startActivity(StudentList.class);
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Student")
                    .setMessage("Are you sure you want to delete this student?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        // Continue with delete operation
                        DBHandler dbHandler = new DBHandler(this);
                        dbHandler.deleteStudent(student);
                        toastMessage("Student deleted successfully");
                        startActivity(StudentList.class);
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });

        //detect back press
        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        dispatcher.addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // alert dialog to confirm exit
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Exit")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // exit the app
                            finish();
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class<?> cls){
        startActivity(new Intent(this, cls));
        finish();
    }
}