package tech.ducletran.travelgallery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import tech.ducletran.travelgallery.ImageData.ImageData;
import tech.ducletran.travelgallery.R;

import java.util.ArrayList;

public class PhotosAdapter extends ArrayAdapter<ImageData> {
    private Context context;

    public PhotosAdapter(Context context, ArrayList<ImageData> imageDataList) {
        super(context,0,imageDataList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.photo_item_view,parent,false);
            holder = new ViewHolder();
            holder.photoImageView = view.findViewById(R.id.photo_item_image_view);
            Glide.with(context).load(getItem(position).getThumbnail()).into(holder.photoImageView);
            view.setTag(holder);
        }
        return view;
    }

    static class ViewHolder {
        ImageView photoImageView;
    }
}

