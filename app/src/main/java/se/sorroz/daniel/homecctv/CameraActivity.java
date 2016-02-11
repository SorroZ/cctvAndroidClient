package se.sorroz.daniel.homecctv;

import android.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.net.Authenticator;

import java.net.PasswordAuthentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CameraActivity extends AppCompatActivity {

    int numberOfValues = 100;
    int stepLength = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);



        String[] displayValues = new String[numberOfValues];
        for(int i = 0; i< numberOfValues;i++){
            displayValues[i] = Integer.toString(stepLength * i);
        }

        NumberPicker np = (NumberPicker) findViewById(R.id.numberPickerMin);
        np.setMaxValue(displayValues.length - 1);
        np.setMinValue(0);
        np.setValue(0);
        np.setDisplayedValues(displayValues);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.numberPickerMax);
        np2.setMaxValue(displayValues.length - 1);
        np2.setMinValue(0);
        np2.setValue(2);
        np2.setDisplayedValues(displayValues);

        ToggleButton tb = (ToggleButton)findViewById(R.id.motionToggle) ;
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                motionToggleHandler(isChecked);
            }
        }) ;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_camera, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                loadSettings();
                break;

            default:
                break;
        }

        return true;
    }

    // BUTTON METHOD
    public void loadLatestPicture(View view) {

        loadImages(0,0);
    }

    // BUTTON METHOD
    public void loadSearchPictures(View view) {
        NumberPicker pnMin = (NumberPicker) findViewById(R.id.numberPickerMin);
        NumberPicker pnMax = (NumberPicker) findViewById(R.id.numberPickerMax);
        int min = pnMin.getValue()  * stepLength;
        int max = pnMax.getValue() * stepLength;
        if(max<min){
            max = min;
        }
        loadImages(min,max);
    }

    // BUTTON METHOD
    public void snapPicture(View view){
        snap();
    }

    private void snap(){
        TextView tv = (TextView) findViewById(R.id.cameraTextOutput);
        tv.setText("Taking snapshot...");

        String[] c = {"command", "snapshot", "remote"};
        SocketClientTask sct = new SocketClientTask(this, c, tv);
        sct.execute();


    }

    public void loadImages(int min, int max){
        Intent intent = new Intent(this, DisplayImagesActivity.class);
        intent.putExtra("min",min);
        intent.putExtra("max",max);
        startActivity(intent);
    }

    private void loadSettings(){
        Intent intent = new Intent(this, CameraSettingsActivity.class);
        startActivity(intent);
    }

    private void motionToggleHandler(boolean status){
        TextView tv = (TextView) findViewById(R.id.cameraTextOutput);
        String[] c = new String[3];
        if(status){
            tv.setText("Enabling Motion sensor...");
            c[0]="command";
            c[1]="motion";
            c[2]="on";
        }else{
            tv.setText("Disabling Motion sensor...");
            c[0]="command";
            c[1]="motion";
            c[2]="off";
        }
        SocketClientTask sct = new SocketClientTask(this, c, tv);
        sct.execute();
    }

}
