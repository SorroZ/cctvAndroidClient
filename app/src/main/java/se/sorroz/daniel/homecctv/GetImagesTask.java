package se.sorroz.daniel.homecctv;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;

/**
 * Created by daniel on 2016-01-21.
 */
public class GetImagesTask extends AsyncTask<Void, Void, Void> {
    List<String> data;
    List<Bitmap> images;
    int start;
    int stop;
    Activity context;
    String domainname;


    public GetImagesTask(Activity context, List<String> data, List<Bitmap> images, int start, int stop) {
        this.data = data;
        this.images = images;
        this.start = start;
        this.stop = stop;
        this.context = context;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        domainname = preferences.getString(CameraSettingsActivity.URL_IP, "DEFAULT");
        final String httpu = preferences.getString(CameraSettingsActivity.HTTP_USER, "DEFAULT");
        final String httpp = preferences.getString(CameraSettingsActivity.HTTP_PASSWORD, "DEFAULT");
        // Set the user and password for web connection
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(httpu, httpp.toCharArray());
            }
        });
    }

    protected Void doInBackground(Void... p) {
        try {
            for(int id = start; id<=stop;id++){

                String imageURL = "http://" + domainname + "/getPicture.php?pic="+ id;
                String imageDataURL = "http://" + domainname + "/getPictureData.php?pic="+ id;

                //Get image from URL
                InputStream in = new java.net.URL(imageURL).openStream();
                Bitmap image = BitmapFactory.decodeStream(in);

                //get imageData
                InputStream inData = new java.net.URL(imageDataURL).openStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(inData));
                String imageData = r.readLine();


                if(image != null && imageData != null){
                    images.add(image);
                    data.add(imageData);
                }

            }


        } catch (Exception e) {
            Log.e("Error", e.getMessage());

        }

        return null;
    }

    protected void onPostExecute(Void res) {
        context.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        if(!data.isEmpty() && !images.isEmpty()) {

            CustomListAdapter adapter = new CustomListAdapter(context, data, images);
            ListView list = (ListView) context.findViewById(R.id.list);
            list.setAdapter(adapter);
        }
    }

    public static Bitmap scaleBitmap(Bitmap bitmapToScale, float newWidth, float newHeight) {
        if (bitmapToScale == null)
            return null;
        //get the original width and height
        int width = bitmapToScale.getWidth();
        int height = bitmapToScale.getHeight();
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(newWidth / width, newHeight / height);

        // recreate the new Bitmap and set it back
        return Bitmap.createBitmap(bitmapToScale, 0, 0, bitmapToScale.getWidth(), bitmapToScale.getHeight(), matrix, true);
    }
}