package se.sorroz.daniel.homecctv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CameraSettingsActivity extends AppCompatActivity {

    public static final String SOCKET_IP = "socketip";
    public static final String SOCKET_PORT = "socketport";
    public static final String URL_IP = "urlip";
    public static final String HTTP_USER = "httpuser";
    public static final String HTTP_PASSWORD = "httppassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new CameraSettingsFragment())
                .commit();
    }
}
