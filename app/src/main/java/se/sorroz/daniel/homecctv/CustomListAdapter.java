package se.sorroz.daniel.homecctv;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by daniel on 2016-01-28.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> data;
    private final List<Bitmap> images;

    public CustomListAdapter(Activity context,List<String> data, List<Bitmap> images) {
        super(context, R.layout.item_listimage, data);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.data=data;
        this.images=images;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View item=inflater.inflate(R.layout.item_listimage, null, true);

        TextView idTV = (TextView) item.findViewById(R.id.idTextViewI);
        TextView typeTV = (TextView) item.findViewById(R.id.typeTextViewI);
        TextView dateTV = (TextView) item.findViewById(R.id.dateTextViewI);
        ImageView imageView = (ImageView) item.findViewById(R.id.imageViewI);

        String id = "Id: ";
        String type = "Type: ";
        String date = "Date: ";

        try{
            JSONObject jsonData = new JSONObject(data.get(position));
            id += jsonData.getString("id");
            type += jsonData.getString("type");
            date += jsonData.getString("timestamp");
        }catch (Exception e){
            id += "No data";
            type += "No data";
            date += "No data";
        }

        idTV.setText(id);
        typeTV.setText(type);
        dateTV.setText(date);

        imageView.setImageBitmap(images.get(position));
        return item;

    };
}