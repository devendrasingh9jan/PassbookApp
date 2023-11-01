package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.model.User;
import com.example.demoapp.repository.UserRepository;

public class SignupActivity extends AppCompatActivity {
    private EditText name, email, password, phoneEditText;
    private RadioButton male, female, others;
    private Button signup, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeFields();

        clear.setOnClickListener(view -> {
            name.setText("");
            email.setText("");
            password.setText("");
            male.setChecked(false);
            female.setChecked(false);
            Toast.makeText(this, " Cleared", Toast.LENGTH_SHORT).show();
        });


        signup.setOnClickListener( view -> {
            Boolean valid = true;
            User user = new User();
            Boolean isValid = getUser(user,valid);
            if (isValid) {
                UserRepository userRepository = new UserRepository(this);
                if (userRepository.insert(user)) {
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Boolean getUser(User user, Boolean valid) {

        if (name.getText().toString().equals("")) {
            name.setError("Enter name");
            valid = false;
        }
        user.setName(name.getText().toString().trim());
        if (email.getText().toString().equals("")) {
            email.setError("Enter email");
            valid = false;
        }
        user.setEmail(email.getText().toString().trim());
        if (phoneEditText.getText().toString().equals("")) {
            phoneEditText.setError("Enter phone");
            valid = false;
        }
        user.setPhone("+91"+phoneEditText.getText().toString().trim());
        if (password.getText().toString().equals("")) {
            password.setError("Enter password");
            valid = false;
        }
        user.setPassword(password.getText().toString().trim());
        if (male.isChecked()) {
            user.setGender(male.getText().toString().trim());
        } else if (female.isChecked()) {
            user.setGender(female.getText().toString().trim());
        } else if (others.isChecked()) {
            user.setGender(others.getText().toString().trim());
        }
        Log.d("User",user.toString());
        return valid;
    }

    private void initializeFields() {
        signup = findViewById(R.id.signup);
        clear = findViewById(R.id.clear);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneEditText = findViewById(R.id.phoneEditText);
        password = findViewById(R.id.password);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        others = findViewById(R.id.others);
    }


}