package edu.scu.project.eventplanner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class CustomAdapter extends ArrayAdapter<Task> {
    private final List<Task> tasks;

    public CustomAdapter(Context context, int resource, List<Task> tasks) {
        super(context, resource, tasks);
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getTaskView(position, convertView, parent);
    }

    // getDropDownView returns the view for the dropdown. We use the same view
    // between getView and getDropDownView.
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getTaskView(position, convertView, parent);
    }


    public View getTaskView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_row, null);

        TextView textView = (TextView) row.findViewById(R.id.rowText);
        textView.setText(tasks.get(position).getTask());

        TextView textView2 = (TextView) row.findViewById(R.id.rowText2);
        textView2.setText(tasks.get(position).getPerson());
       /* String person = tasks.get(position).getPerson();*/

       /* try {
            ImageView imageView = (ImageView) row.findViewById(R.id.rowImage);
            String filename = tasks.get(position).getPerson();
            InputStream inputStream = getContext().getAssets().open(filename);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return row;


    }

}
