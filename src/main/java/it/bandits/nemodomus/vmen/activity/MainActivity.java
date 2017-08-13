package it.bandits.nemodomus.vmen.activity;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import it.bandits.nemodomus.vmen.R;
import it.bandits.nemodomus.vmen.service.BluetoothLeHmManager;
import it.bandits.nemodomus.vmen.service.BluetoothLeHmResponseListner;
import it.bandits.nemodomus.vmen.service.BluetoothLeService;
public class MainActivity extends AppCompatActivity implements BluetoothLeHmResponseListner {


    private static final String TAG = MainActivity.class.getSimpleName();

    private BluetoothLeHmManager bluetoothLeHmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        final Intent intent = getIntent();
        String mDeviceName = intent.getStringExtra(BluetoothLeHmManager.EXTRAS_DEVICE_NAME);
        String mDeviceAddress = intent.getStringExtra(BluetoothLeHmManager.EXTRAS_DEVICE_ADDRESS);
        Log.d(TAG, "onCreate: "+mDeviceAddress);
        bluetoothLeHmManager = new BluetoothLeHmManager(this,mDeviceName,mDeviceAddress);
        bluetoothLeHmManager.setBluetoothLeHmResponseListner(this);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, bluetoothLeHmManager.getServiceConnection(), BIND_AUTO_CREATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        bluetoothLeHmManager.Register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bluetoothLeHmManager.Unregister();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothLeHmManager.Destroy();
    }

    @Override
    public void BluetoothConnectionSuccess() {
        Log.d(TAG, "BluetoothConnectionSuccess: ");
        TextView text = (TextView) findViewById(R.id.ConnectionString);
        text.setText("Connecting");
    }

    @Override
    public void BluetoothConnectionFailure() {
        Log.d(TAG, "BluetoothConnectionFailure: ");
    }

    @Override
    public void BluetoothInitializeFailed() {
        Log.d(TAG, "BluetoothInitializeFailed: ");
        finish();
    }

    @Override
    public void BluetoothConnectionEstablished() {
        Log.d(TAG, "BluetoothConnectionEstablished: ");
        TextView text = (TextView) findViewById(R.id.ConnectionString);
        text.setText("Connected");
    }

    @Override
    public void BluetoothConnectionDestroyed() {
        Log.d(TAG, "BluetoothConnectionDestroyed: ");
    }

    @Override
    public void BluetoothConnectionDataAvailable(String data) {
        Log.d(TAG, "BluetoothConnectionDataAvailable: "+data);
    }
}
