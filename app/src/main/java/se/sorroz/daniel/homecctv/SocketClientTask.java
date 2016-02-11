package se.sorroz.daniel.homecctv;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by daniel on 2016-01-21.
 */
public class SocketClientTask extends AsyncTask<Void, Void, Void> {

    TextView outputView;
    String dstAddress;
    int dstPort;
    JSONArray data;
    String response;

    SocketClientTask(Activity context, String[] command, TextView outputView) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        dstAddress = preferences.getString(CameraSettingsActivity.SOCKET_IP, "DEFAULT");
        dstPort = Integer.parseInt(preferences.getString(CameraSettingsActivity.SOCKET_PORT, "DEFAULT"));
        data = jsonate(command);
        this.outputView = outputView;
    }

    public JSONArray jsonate(String[] command) {
        JSONArray ja = new JSONArray();
        for (String c : command) {
            ja.put(c);
        }
        return ja;

    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;


        try {
            InetAddress serverAddr = InetAddress.getByName(dstAddress);
            socket = new Socket();
            socket.connect(new InetSocketAddress(dstAddress, dstPort), 5000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.print(data);
            out.flush();

            response = in.readLine();


        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block

        } catch (IOException e) {
            // TODO Auto-generated catch block
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block

                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        updateOutput();

        //outputView.setText("Snapshot taken.");
    }

    private void updateOutput() {
        if (response != null) {
            if (response.equals("snapshot_ok")) {
                outputView.setText("Snapshot taken");
            } else if (response.equals("motion_off")) {
                outputView.setText("Motion sensor disabled");
            } else if (response.equals("motion_on")) {
                outputView.setText("Motion sensor enabled");
            } else {
                outputView.setText("Something went wrong");
            }
        } else {
            outputView.setText("Could not connect to server");
        }

    }

}