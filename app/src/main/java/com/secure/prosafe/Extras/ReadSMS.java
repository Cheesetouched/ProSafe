package com.secure.prosafe.Extras;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.secure.prosafe.Extras.SmsListener;
import com.secure.prosafe.TinyDB;

public class ReadSMS extends BroadcastReceiver {
    private static SmsListener mListener;
    private TinyDB db;

    @Override
    public void onReceive(Context context, Intent intent) {
        db = new TinyDB(context);
        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = smsMessage.getDisplayOriginatingAddress();
            if (sender.contains(db.getString("number"))) {
                String messageBody = smsMessage.getMessageBody();
                if (messageBody.contains(db.getString("code"))) {
                    mListener.messageReceived(messageBody);
                }
            }
        }
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

}
