package it.bandits.nemodomus.vmen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import it.bandits.nemodomus.vmen.R;
import it.bandits.nemodomus.vmen.service.BluetoothLeHmManager;

public class LoginActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

    }

    public void loginBtnClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(BluetoothLeHmManager.EXTRAS_DEVICE_NAME, getIntent().getStringExtra(BluetoothLeHmManager.EXTRAS_DEVICE_NAME));
        intent.putExtra(BluetoothLeHmManager.EXTRAS_DEVICE_ADDRESS, getIntent().getStringExtra(BluetoothLeHmManager.EXTRAS_DEVICE_ADDRESS));
        startActivity(intent);
        finish();
    }
}
