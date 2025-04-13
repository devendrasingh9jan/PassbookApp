package com.example.demoapp.service;

import static android.app.PendingIntent.getActivity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.demoapp.R;
import com.example.demoapp.activity.MainActivity;
import com.example.demoapp.model.FixedDeposit;
import com.example.demoapp.model.User;
import com.example.demoapp.repository.FdRepository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FDNotificationService extends Service {

    private static final String CHANNEL_ID = "fd_notification_channel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra("loggedInUser")) {
            // Retrieve the user data from the Intent
            User loggedInUser = (User) intent.getSerializableExtra("loggedInUser");
            FdRepository fdRepository = new FdRepository(getApplication());
            List<FixedDeposit> fixedDeposits = fdRepository.getAllFixedDeposits(loggedInUser.getId());
            checkFdEndDates(fixedDeposits);
            stopSelf();
        }
        return START_NOT_STICKY;
    }

    private void checkFdEndDates(List<FixedDeposit> fixedDeposits) {
        for (FixedDeposit fd : fixedDeposits) {
            if (isEndDateReached(fd.getEndDate())) {
                displayNotification(fd);
            }
        }
    }

    private boolean isEndDateReached(LocalDate endDate) {
        LocalDate today = LocalDate.now();
        return !today.isBefore(endDate);
    }


    private void displayNotification(FixedDeposit fd) {
        createNotificationChannel();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification) // Replace with your notification icon
                .setContentTitle("FD Maturity Alert")
                .setContentText("Your FD with number " + fd.getNumber() + " has reached its maturity date.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "FD Notifications";
            String description = "Notifications for FD maturity dates";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}