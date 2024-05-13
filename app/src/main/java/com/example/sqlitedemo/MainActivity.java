package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra("Student");

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
        });

        btnView.setOnClickListener(v -> {
            startActivity(StudentList.class);
        });

        if (student != null) {
            // populate your input fields with the student's information
            txtName.setText(student.getName());
            txtLocation.setText(student.getLocation());
            txtCourse.setText(student.getCourse());
        }
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class<?> cls){
        startActivity(new Intent(this, cls));
    }
}