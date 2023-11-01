package com.example.demoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.R;
import com.example.demoapp.model.User;
import com.example.demoapp.repository.UserRepository;

import java.util.Optional;

public class LoginActivity extends AppCompatActivity {

    private EditText emailOrPhone, password;
    private TextView forgotPassword, register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeFields();

        register.setOnClickListener( view -> {
            Intent intent = new Intent(this, SignupActivity.class);
            intent.putExtra("Welcome","Welcome");
            startActivity(intent);
        });

        forgotPassword.setOnClickListener( view -> {
            Intent intent = new Intent(this, ForgetPasswordActivity.class);
            startActivity(intent);
        });



        login.setOnClickListener(view -> {
            String userEmailOrPhone, userPassword;
            if (emailOrPhone.getText().toString().equals("")) {
                emailOrPhone.setError("Enter email or Phone");
                return;
            }
            userEmailOrPhone = emailOrPhone.getText().toString().trim();
            if (password.getText().toString().equals("")) {
                password.setError("Enter password");
                return;
            }
            userPassword = password.getText().toString().trim();
            UserRepository userRepository = new UserRepository(this);
            Optional<User> userOptional = null;
            if (isValidPhoneNumber("+91"+userEmailOrPhone)) {
                userOptional = userRepository.loginByPhone("+91"+userEmailOrPhone, userPassword);
            } else if (isValidEmailAddress(userEmailOrPhone)) {
                userOptional = userRepository.loginByEmail(userEmailOrPhone, userPassword);
            } else {
                System.out.println("Input is neither a phone number nor an email address.");
            }

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Toast.makeText(this,"Login successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("loggedInUser",user);
                startActivity(intent);
            } else {
                Toast.makeText(this,"Invalid Credentials", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void initializeFields() {
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        emailOrPhone = findViewById(R.id.emailOrPhone);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotPassword);
    }

    public static boolean isValidPhoneNumber(String input) {
        // Define a regular expression for a simple phone number (modify as needed)
        String phonePattern = "^[0-9-+()]+";

        return input.matches(phonePattern);
    }

    public static boolean isValidEmailAddress(String input) {
        // Define a regular expression for a simple email address (modify as needed)
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

        return input.matches(emailPattern);
    }


}