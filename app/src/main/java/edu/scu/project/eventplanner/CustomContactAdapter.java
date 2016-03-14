package edu.scu.project.eventplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
//import android.support.v7.widget.RecyclerView.ViewHolder;
import java.util.List;

/**
 * Created by Varada on 2/12/2016.
 */
public class CustomContactAdapter extends ArrayAdapter<Contact>{

    private Context context;
    private List<Contact> contactList = null;

    static class ViewHolder {
        TextView name;
        TextView number;
    }

    public CustomContactAdapter(Context context, int resource, List<Contact> contacts) {
        super(context, resource, contacts);
        this.contactList = contacts;
        this.context= context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = contactList.get(position);
        ViewHolder holder;
        View row;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_contact_list, null);

            holder = new ViewHolder();

            // Set the text
            holder.name = (TextView) row.findViewById(R.id.name);
            holder.number = (TextView) row.findViewById(R.id.ph);
            row.setTag(holder);
        }
        else
        { row = convertView;
            holder = (ViewHolder) row.getTag();}

        holder.name.setText(contact.getName());

        holder.number.setText(contact.getNumber());

        return row;
    }

}
