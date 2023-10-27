package com.example.tickerwatchlistmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SmsReceiver smsReceiver;
    private TickerListFragment tickerListFragment;
    private InfoWebFragment infoWebFragment;
    private static final int SMS_PERMISSION_REQUEST_CODE = 123;
    private void loadLandscapeFragment() {
        tickerListFragment = new TickerListFragment();
        infoWebFragment = new InfoWebFragment();// Assign to class variable
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerTickerList, tickerListFragment) // Use the class variable here
                .replace(R.id.containerInfoWeb, infoWebFragment)
                .commit();

    }
    private void loadPortraitFragment() {
        tickerListFragment = new TickerListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerTickerList, tickerListFragment).commit();
    }

    /*
    creates ui, objects, and assigns an intent
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//creates last instance
        setContentView(R.layout.activity_main);//creates ui
        if (!isSmsPermissionGranted()) {
            requestSmsPermission();}
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            loadLandscapeFragment();
        } else {
            loadPortraitFragment();
        }
        smsReceiver = new SmsReceiver(tickerListFragment);//creates obj
        IntentFilter intentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);//creates obj
        registerReceiver(smsReceiver, intentFilter);//registers the receiver to intent
        String tickerSymbolsString = getResources().getString(R.string.ticker_symbols);
        List<String> tickerSymbolsList = Arrays.asList(tickerSymbolsString.split(","));
        tickerListFragment.initiateTickers(tickerSymbolsList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
    }

    private boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "SMS permission denied. Some features may not work properly.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
Loads fragment with ticker parameter
@param String
 */
    public void loadInfoWebFragment(String ticker) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://seekingalpha.com/symbol/" + ticker));
            startActivity(intent);
        }else{
            InfoWebFragment infoWebFragment = (InfoWebFragment) getSupportFragmentManager().findFragmentById(R.id.containerInfoWeb); //creates obj
            if (infoWebFragment == null || !infoWebFragment.isVisible()) {
                infoWebFragment = new InfoWebFragment();
            }//qc
            infoWebFragment.loadWebsiteForTicker(ticker);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerInfoWeb, infoWebFragment)
                    .commit();
        }
    }
}
