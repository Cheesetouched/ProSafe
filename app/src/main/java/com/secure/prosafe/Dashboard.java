package com.secure.prosafe;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.secure.prosafe.Extras.ReadSMS;
import com.secure.prosafe.Extras.SmsListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.ghyeok.stickyswitch.widget.StickySwitch;

public class Dashboard extends AppCompatActivity {
    private StickySwitch stickySwitch;
    private Button status;
    private TinyDB db;
    private LinearLayout main;
    private final int PERMISSION_ALL = 1;
    private String[] PERMISSIONS = {
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        db = new TinyDB(getApplicationContext());
        main = findViewById(R.id.main);
        status = findViewById(R.id.status);
        stickySwitch = findViewById(R.id.sticky_switch);
        receivedSms();

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else {
            main.setVisibility(View.VISIBLE);
        }
        if (db.getBoolean("active")) {
            stickySwitch.setDirection(StickySwitch.Direction.RIGHT, true, true);
            status.setText("PROTECTION: ENABLED");
        } else {
            stickySwitch.setDirection(StickySwitch.Direction.LEFT, true, true);
            status.setText("PROTECTION: DISABLED");
        }

        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
                status.setText("PROTECTION: " + text);
                if (text.equalsIgnoreCase("enabled")) {
                    db.putBoolean("active", true);
                } else {
                    db.putBoolean("active", false);
                }
            }
        });
    }

    public void print(String message) {
        Toast.makeText(Dashboard.this, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void receivedSms() {
        ReadSMS.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                if (db.getBoolean("active")) {
                    String smsbody = "Hello! Greetings from ProSafe. Your phone is at SRM University, Vadapalani. Location Coordinates: (13.052040, 80.210292)";
                    SmsManager sms = SmsManager.getDefault();
                    ArrayList<String> parts = sms.divideMessage(smsbody);
                    sms.sendMultipartTextMessage(db.getString("number"), null, parts, null, null);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL: {
                if (!hasPermissions(this, PERMISSIONS)) {
                    status.setText("All the permissions are mandatory.");
                } else {
                    main.setVisibility(View.VISIBLE);
                }
            }
            default:
                return;
        }
    }

}
