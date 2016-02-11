package se.sorroz.daniel.homecctv;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DisplayImagesActivity extends AppCompatActivity {

    ListView list;
    int min;
    int max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_images);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        min = intent.getIntExtra("min",0);
        max = intent.getIntExtra("max",0);

        loadImages();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_display_images, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reloadImages:
                reloadImages();
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                break;
        }

        return true;
    }


    private void loadImages(){
        List<String> data = new ArrayList<>();
        List<Bitmap> images = new ArrayList<>();


        GetImagesTask git = new GetImagesTask(this, data,images,min,max);
        git.execute();

    }

    private void reloadImages(){
        ListView lv = (ListView) findViewById(R.id.list);
        Adapter a = lv.getAdapter();
        if(a != null){
            lv.setAdapter(null);
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            loadImages();
        }

    }



}
