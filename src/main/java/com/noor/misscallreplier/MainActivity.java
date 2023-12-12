package com.noor.misscallreplier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText ed1;
    Button b1,b2;
    String data;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = (EditText)findViewById(R.id.editText);
        b1 = (Button)findViewById(R.id.button);
        b2 = (Button)findViewById(R.id.button1);
        
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE) !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_CALL_LOG,Manifest.permission.SEND_SMS},1);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Incomingcallreceiver tm;
            tm = getSystemService(Incomingcallreceiver.class);
        }

    }


    public void FileInputter(View view) {
        data = ed1.getText().toString();
        try {
            FileOutputStream fout = openFileOutput("data.txt",MODE_PRIVATE);
            fout.write(data.getBytes());
            fout.close();
            Toast.makeText(getBaseContext(),"Message saved",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    public void FileInputter1(View view) {
        data = "";
        try {
            FileOutputStream fout = openFileOutput("data.txt",MODE_PRIVATE);
            fout.write(data.getBytes());
            fout.close();
            Toast.makeText(getBaseContext(),"Messages Cancelled",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {

            Intent intent = new Intent(this,SecondActivity.class);
            this.startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
