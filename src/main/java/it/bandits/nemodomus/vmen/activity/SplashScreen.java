package it.bandits.nemodomus.vmen.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.bandits.nemodomus.vmen.R;
import it.bandits.nemodomus.vmen.service.BluetoothLeHmManager;

import static android.bluetooth.le.ScanSettings.SCAN_MODE_BALANCED;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = SplashScreen.class.getSimpleName();
    private Handler mHandler;
    private BluetoothDevice mDevice;
    private boolean mScanning = false;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            mDevice = result.getDevice();
            Log.d(TAG, "onScanResult: "+result.toString());
            if(mDevice!=null) {
                Intent intent = new Intent(SplashScreen.this, LoginActivty.class);
                intent.putExtra(BluetoothLeHmManager.EXTRAS_DEVICE_NAME, mDevice.getName());
                intent.putExtra(BluetoothLeHmManager.EXTRAS_DEVICE_ADDRESS, mDevice.getAddress());
                if (mScanning) {
                    mBluetoothLeScanner.stopScan(scanCallback);
                    mScanning = false;
                }
                startActivity(intent);
                finish();
            }
        }
    };

    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mHandler = new Handler();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothLeHmManager.
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final BluetoothManager bluetoothManager =
                        (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                mBluetoothAdapter = bluetoothManager.getAdapter();

                // Checks if Bluetooth is supported on the device.
                if (mBluetoothAdapter == null) {
                    Toast.makeText(SplashScreen.this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }

                if (mBluetoothAdapter.getState() == BluetoothAdapter.STATE_ON)
                    mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
                else{
                    if (!mBluetoothAdapter.isEnabled()) {
                        if (!mBluetoothAdapter.isEnabled()) {
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        }
                    }

                }

                if (!mBluetoothAdapter.isEnabled()) {
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    }
                }
                scanLeDevice(true);
            }
        }, 10000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult: BL_ACCPTED");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            Log.d(TAG, "scanLeDevice: Scan Start");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    //mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mBluetoothLeScanner.stopScan(scanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);
            mScanning = true;
            List<ScanFilter> scanFilters = new ArrayList<>();
            ScanFilter.Builder  builder = new ScanFilter.Builder();
            scanFilters.add(builder.setDeviceAddress("B4:99:4C:51:B6:BA").build());
            ScanSettings.Builder scanSettingsBuilder = new ScanSettings.Builder();

            mBluetoothLeScanner.startScan(scanFilters,scanSettingsBuilder.setScanMode(SCAN_MODE_BALANCED).build(),scanCallback);
        } else {
            mScanning = false;
            mBluetoothLeScanner.stopScan(scanCallback);
        }
        invalidateOptionsMenu();
    }

}
