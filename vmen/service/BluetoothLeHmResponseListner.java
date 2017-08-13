package it.bandits.nemodomus.vmen.service;

/**
 * Created by Nemo Domus on 8/12/2017.
 */

public interface BluetoothLeHmResponseListner {

    public void BluetoothConnectionSuccess();

    public void BluetoothConnectionFailure();

    public void BluetoothInitializeFailed();

    public void BluetoothConnectionEstablished();

    public void BluetoothConnectionDestroyed();

    public void BluetoothConnectionDataAvailable(String data);


}
