package it.bandits.nemodomus.vmen.service;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Nemo Domus on 8/12/2017.
 */

public class BluetoothLeHmManager {
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    private static final String TAG = BluetoothLeHmManager.class.getSimpleName();
    private Context mContext;

    BluetoothLeHmResponseListner blhrl;

    private String mDeviceName;
    private String mDeviceAddress;

    private BluetoothLeService mBluetoothLeService;

    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                blhrl.BluetoothInitializeFailed();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
            blhrl.BluetoothConnectionSuccess();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
            Log.d(TAG, "onServiceDisconnected: ");
            blhrl.BluetoothConnectionFailure();
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                // updateConnectionState(R.string.connected);
                Log.d(TAG, "onReceive: Connected");
                Toast.makeText(context, "VMen Accessary is connected", Toast.LENGTH_SHORT).show();
                blhrl.BluetoothConnectionEstablished();

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                //updateConnectionState(R.string.disconnected);
                Toast.makeText(context, "VMen Accessary is disconnected", Toast.LENGTH_SHORT).show();
                blhrl.BluetoothConnectionDestroyed();

                //clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                mNotifyCharacteristic = breakGattServices(mBluetoothLeService.getSupportedGattServices());
                if(mNotifyCharacteristic != null) {
                    mBluetoothLeService.readCharacteristic(mNotifyCharacteristic);
                    mBluetoothLeService.setCharacteristicNotification(
                            mNotifyCharacteristic, true);
                }
                Log.d(TAG, "onReceive: Get Services");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                blhrl.BluetoothConnectionDataAvailable(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                Log.d(TAG, "onReceive: Data Available");
            }
        }
    };

    public BluetoothLeHmManager(Context mContext, String mDeviceName, String mDeviceAddress) {
        this.mContext = mContext;
        this.mDeviceName = mDeviceName;
        this.mDeviceAddress = mDeviceAddress;
    }

    public void setBluetoothLeHmResponseListner(BluetoothLeHmResponseListner blhrl){
        this.blhrl  = blhrl;
    }

    public ServiceConnection getServiceConnection() {
        return mServiceConnection;
    }

    private void displayData(String data) {
        if (data != null) {
            Log.d(TAG, "displayData: "+data);
        }
    }

    public void Register(){
        mContext.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    public void Unregister(){
        mContext.unregisterReceiver(mGattUpdateReceiver);
    }

    public void Destroy(){
        mContext.unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    private BluetoothGattCharacteristic breakGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return null;
        Log.d(TAG, "breakGattServices: servicesd exist");
        String uuid = null;

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            Log.d(TAG, "breakGattServices: length: "+gattServices.size());
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            Log.d(TAG, "breakGattServices: uuid: "+uuid);
            if(uuid.equals("0000ffe0-0000-1000-8000-00805f9b34fb")) {
                Log.d(TAG, "breakGattServices: HM-10 Service");
                List<BluetoothGattCharacteristic> gattCharacteristics =
                        gattService.getCharacteristics();

                // Loops through available Characteristics.
                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                    Log.d(TAG, "breakGattServices: chracteristics: "+gattCharacteristics.size());
                    uuid = gattCharacteristic.getUuid().toString();
                    if(uuid.equals(SampleGattAttributes.HM_10)) {
                        Log.d(TAG, "breakGattServices: FOUND CHARACTERISTIC");
                        Log.d(TAG, "displayGattServices: ");
                        return gattCharacteristic;
                    }
                }
            }
        }

        return null;
    }


    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
}
