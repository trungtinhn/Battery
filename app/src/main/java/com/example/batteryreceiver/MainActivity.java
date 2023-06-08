package com.example.batteryreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textViewBatteryPercentage;
    private TextView textViewBatteryStatus;
    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int percentage = Math.round((level * 100.0f) / scale);

            textViewBatteryPercentage.setText(percentage + "%");

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

            String statusText = "Battery is Unknown";
            if (isCharging) {
                statusText = "Battery is Charging";
            } else {
                statusText = "Battery is not Charging";
            }
            if (percentage >= 20) {
                getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            } else {
                getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
            }

            textViewBatteryStatus.setText(statusText);

//             Change the background color depending on the battery status
//            if (percentage >= 20) {
//                getWindow().getDecorView().setBackgroundColor();
//            } else {
//                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorBatteryOK));
//            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewBatteryPercentage = findViewById(R.id.textViewBatteryPercentage);
        textViewBatteryStatus = findViewById(R.id.textViewBatteryStatus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batteryReceiver);
    }
}