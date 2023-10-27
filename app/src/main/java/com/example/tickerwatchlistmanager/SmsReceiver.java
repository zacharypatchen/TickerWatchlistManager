package com.example.tickerwatchlistmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SmsReceiver extends BroadcastReceiver {
    boolean charError;
    private TickerListFragment tickerListFragment;
    public SmsReceiver(TickerListFragment tickerListFragment) {
        this.tickerListFragment = tickerListFragment;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    for (Object pdu : pdus) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String messageBody = smsMessage.getMessageBody().toUpperCase();
                        for(char s : messageBody.toCharArray()){
                            if(!Character.isLetter(s)){
                                charError = true;
                            }
                        }
                        if(!charError){
                            extractTickerSymbols(messageBody);
                        }else{
                            Toast.makeText(context, "Not a ticker", Toast.LENGTH_SHORT).show();
                            charError = false;
                        }

                    }
                }
            }
        }

    }

    private void extractTickerSymbols(String messageBody) {
        List<String> extractedTickers = extractTickersFromMessage(messageBody);
        tickerListFragment.updateTickers(extractedTickers);
    }

    private List<String> extractTickersFromMessage(String messageBody) {
        List<String> tickers = new ArrayList<>();
        String regex = "\\b[A-Z]{2,6}\\b";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(messageBody);

        while (matcher.find()) {
            String ticker = matcher.group();
            tickers.add(ticker);
        }

        return tickers;
    }

}