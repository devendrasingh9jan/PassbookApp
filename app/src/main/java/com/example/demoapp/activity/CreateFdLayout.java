package com.example.demoapp.activity;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demoapp.R;
import com.example.demoapp.model.FixedDeposit;
import com.example.demoapp.model.User;
import com.example.demoapp.repository.FdRepository;

public class CreateFdLayout extends AppCompatActivity {

    private Button create;
    private EditText fdNumber, fdAmount, fdRate, fdMaturityAmount, fdCreateDate, fdEndDate, fdBankAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_fd_layout);
        initializeFields();
        FixedDeposit fixedDeposit = new FixedDeposit();
        setDates(fixedDeposit);
        create.setOnClickListener(view -> {
            createFixedDeposit(fixedDeposit);
        });
    }

    private void createFixedDeposit(FixedDeposit fixedDeposit) {
        Intent intent = getIntent();
        // Check if the Intent contains the "loggedInUser" extra
        if (intent.hasExtra("loggedInUser")) {
            // Retrieve the user data from the Intent
            User loggedInUser = (User) intent.getSerializableExtra("loggedInUser");
            fixedDeposit.setUserId(loggedInUser.getId());
            if (fdNumber.getText().toString().equals("")) {
                fdNumber.setError("Enter Number");
                return;
            }
            fixedDeposit.setNumber(Long.valueOf(fdNumber.getText().toString().trim()));
            if (fdAmount.getText().toString().equals("")) {
                fdAmount.setError("Enter Amount");
                return;
            }
            fixedDeposit.setAmount(Double.valueOf(fdAmount.getText().toString().trim()));
            Long daysBetween = ChronoUnit.DAYS.between(fixedDeposit.getCreatedDate(), fixedDeposit.getEndDate());
            fixedDeposit.setTenure(Math.toIntExact(daysBetween));
            if (fdRate.getText().toString().equals("")) {
                fdRate.setError("Enter Rate");
                return;
            }
            fixedDeposit.setRate(Double.valueOf(fdRate.getText().toString().trim()));
            if (fdMaturityAmount.getText().toString().equals("")) {
                fdMaturityAmount.setError("Enter Maturity Amount");
                return;
            }
            if (fixedDeposit.getTenure()<0) {
                fdCreateDate.setError("Enter before End date");
                fdEndDate.setError("Enter after Created date");
                return;
            }
            LocalDate currentDate = LocalDate.now();
            /*if (fixedDeposit.getCreatedDate().isBefore(currentDate)) {
                fdCreateDate.setError("Future date can be selected.");
                return;
            }if (fixedDeposit.getEndDate().isBefore(currentDate)) {
                fdEndDate.setError("Enter after Created date");
                return;
            }*/
            fixedDeposit.setMaturityAmount(Double.valueOf(fdMaturityAmount.getText().toString().trim()));
            if (fdBankAddress.getText().toString().equals("")) {
                fdBankAddress.setError("Enter Bank Address");
                return;
            }
            fixedDeposit.setBankWithAddress(fdBankAddress.getText().toString().trim());

            Long daysLeft = ChronoUnit.DAYS.between(currentDate, fixedDeposit.getEndDate());
            fixedDeposit.setDaysLeft(Math.toIntExact(daysLeft));
            FdRepository fdRepository = new FdRepository(this);
            //checking duplicate Fd.
            List<FixedDeposit> fixedDepositList = fdRepository.getByNumber(fixedDeposit.getNumber());
            if (fixedDepositList.size()>0) {
                Toast.makeText(this,"Fd Already exists.",Toast.LENGTH_SHORT).show();
                return;
            }
            if (fdRepository.insert(fixedDeposit)) {
                Toast.makeText(this,"Fd Created",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
            } else {
                Toast.makeText(this,"Fd Creation failed.",Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this,"Some problem occurred.",Toast.LENGTH_SHORT).show();
        }
    }

    private void setDates(FixedDeposit fixedDeposit) {
        fdCreateDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog1 = new DatePickerDialog(
                    this,
                    (datePicker, createdYear, createdMonth, createdDay) -> {
                        LocalDate createdDate = LocalDate.of(createdYear, createdMonth + 1, createdDay);
                        fixedDeposit.setCreatedDate(createdDate);
                        fdCreateDate.setText(createdDate.toString());
                    },
                    LocalDate.now().getYear(),LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth()
            );
            datePickerDialog1.show();
        });
        fdEndDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog2 = new DatePickerDialog(
                    this,
                    (datePicker, endYear, endMonth, endDay) -> {
                        LocalDate endDate = LocalDate.of(endYear, endMonth + 1, endDay);
                        fixedDeposit.setEndDate(endDate);
                        fdEndDate.setText(endDate.toString());
                    },
                    LocalDate.now().getYear(),LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth()
            );
            datePickerDialog2.show();
        });
    }
    private void initializeFields() {
        fdNumber = findViewById(R.id.editTextNumber);
        create = findViewById(R.id.buttonCreateFd);
        fdAmount = findViewById(R.id.editTextAmount);
        fdRate = findViewById(R.id.editTextRate);
        fdMaturityAmount = findViewById(R.id.editTextMaturityAmount);
        fdCreateDate = findViewById(R.id.editTextCreatedDate);
        fdEndDate = findViewById(R.id.editTextEndDate);
        fdBankAddress = findViewById(R.id.editTextBankAddress);
    }


}