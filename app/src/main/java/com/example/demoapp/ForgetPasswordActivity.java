package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.model.User;
import com.example.demoapp.repository.UserRepository;

import java.util.Optional;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText emailReset, passwordReset;
    private TextView forgotPassword, register;
    private Button updatePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initializeFields();

        updatePassword.setOnClickListener(view -> {
            String userEmail, userPassword;
            if (emailReset.getText().toString().equals("")) {
                emailReset.setError("Enter email");
                return;
            }
            userEmail = emailReset.getText().toString().trim();
            if (passwordReset.getText().toString().equals("")) {
                passwordReset.setError("Enter password");
                return;
            }
            userPassword = passwordReset.getText().toString().trim();
            UserRepository userRepository = new UserRepository(this);
            Optional<User> userOptional = userRepository.findByEmailId(userEmail);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                userRepository.updatePassword(user.getId(), userPassword);
                Toast.makeText(this,"Updated successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("Welcome",user);
                startActivity(intent);
            } else {
                Toast.makeText(this,"Email doesn't exists", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void initializeFields() {
        emailReset = findViewById(R.id.emailReset);
        passwordReset = findViewById(R.id.passwordReset);
        updatePassword = findViewById(R.id.updatePassword);
    }
}