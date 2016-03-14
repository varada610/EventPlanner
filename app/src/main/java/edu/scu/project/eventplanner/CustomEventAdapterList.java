package edu.scu.project.eventplanner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Varada on 2/12/2016.
 */
public class CustomEventAdapterList extends ArrayAdapter<Event> {

    private final List<Event> events;
    private Context context = null;

    static class ViewHolder {
        ImageView image;
        TextView textView;
    }

    public CustomEventAdapterList(Context context, int resource, List<Event> events) {
        super(context, resource, events);
        this.events = events;
        this.context= context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event = events.get(position);
        ViewHolder holder;
        View row;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_event_list, null);

            holder = new ViewHolder();

            // Set the text
            holder.textView = (TextView) row.findViewById(R.id.eventName);
            holder.image = (ImageView) row.findViewById(R.id.imageIcon);
            row.setTag(holder);
        }
        else
        { row = convertView;
            holder = (ViewHolder) row.getTag();}

        holder.textView.setText(event.getEventName());

        try {
            InputStream ins = this.context.getAssets().open(event.getImageName()+".jpg");
            Drawable drawable = Drawable.createFromStream(ins, null);
            holder.image.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return row;
    }
}
