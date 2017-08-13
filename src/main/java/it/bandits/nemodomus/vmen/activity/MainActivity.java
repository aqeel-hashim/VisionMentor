package it.bandits.nemodomus.vmen.activity;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;

import it.bandits.nemodomus.vmen.R;
import it.bandits.nemodomus.vmen.service.BluetoothLeHmManager;
import it.bandits.nemodomus.vmen.service.BluetoothLeHmResponseListner;
import it.bandits.nemodomus.vmen.service.BluetoothLeService;
import it.bandits.nemodomus.vmen.service.SampleGattAttributes;

public class MainActivity extends AppCompatActivity implements
        OnChartValueSelectedListener, BluetoothLeHmResponseListner {


    private static final String TAG = MainActivity.class.getSimpleName();


    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };
    private PieChart mChart;

    private BluetoothLeHmManager bluetoothLeHmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        final Intent intent = getIntent();
        String mDeviceName = intent.getStringExtra(BluetoothLeHmManager.EXTRAS_DEVICE_NAME);
        String mDeviceAddress = intent.getStringExtra(BluetoothLeHmManager.EXTRAS_DEVICE_ADDRESS);

        bluetoothLeHmManager = new BluetoothLeHmManager(this,mDeviceName,mDeviceAddress);

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, bluetoothLeHmManager.getServiceConnection(), BIND_AUTO_CREATE);


        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        setData(4, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        mChart.getLegend().setEnabled(false);

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


    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    mParties[i % mParties.length],
                    null));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Build Error\nper 100 Builds");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 11, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 11, s.length() - 7, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 11, s.length() - 7, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 11, s.length() - 7, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 6, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 6, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    @Override
    public void BluetoothConnectionSuccess() {
        Log.d(TAG, "BluetoothConnectionSuccess: ");
    }

    @Override
    public void BluetoothConnectionFailure() {
        Log.d(TAG, "BluetoothConnectionFailure: ");
    }

    @Override
    public void BluetoothInitializeFailed() {
        finish();
    }

    @Override
    public void BluetoothConnectionEstablished() {
        Log.d(TAG, "BluetoothConnectionEstablished: ");
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
