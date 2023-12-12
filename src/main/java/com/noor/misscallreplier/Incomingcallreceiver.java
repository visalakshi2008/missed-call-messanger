package com.noor.misscallreplier;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;


public class Incomingcallreceiver extends BroadcastReceiver {
    static boolean ring = false;
    static boolean receive = false;
    static String callerPhoneNumber;
    String msg="";

    @Override
    public void onReceive(Context context, Intent intent){
        callerPhoneNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING))
        {
            ring = true;
           // Bundle bundle = intent.getExtras();
           // callerPhoneNumber = bundle.getString("incoming_number");
            if (callerPhoneNumber != null)
                showToast(context,"Incoming call from  "+callerPhoneNumber);
        }
        else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
        {
            receive = true;
        }
        else  if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE))
        {
            if (ring==true && receive == false)
                if (callerPhoneNumber != null) {

                    showToast(context, "Missed call from " + callerPhoneNumber);
                    try {
                        Inputter(context);
                        SmsManager smsManager=SmsManager.getDefault();
                        if(msg!=""){
                            smsManager.sendTextMessage(callerPhoneNumber,null,msg,null,null);
                            showToast(context,"Message Sent");
                        }

                    }catch (Exception e)
                    {
                        showToast(context,e.toString());
                    }
                }
        }
    }

    void showToast(Context context,String message)
    {
        Toast toast=Toast.makeText(context,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    void Inputter(Context context){
        try{
            FileInputStream fin = context.openFileInput("data.txt");
            int c;
            while((c=(fin.read()))!=-1)
            {
                msg = msg+Character.toString((char)c);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
}