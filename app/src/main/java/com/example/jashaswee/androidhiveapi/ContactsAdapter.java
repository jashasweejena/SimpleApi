package com.example.jashaswee.androidhiveapi;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by jashaswee on 31/3/18.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private List<Contacts> contactsList;
    public static String TAG = ContactsAdapter.class.getSimpleName();
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contacts contacts = contactsList.get(position);
        Log.d(TAG, "onBindViewHolder: "+contacts.getName());
        holder.name.setText(contacts.getName());
        holder.email.setText(contacts.getEmail());
        holder.mobile.setText(contacts.getMobile());
    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + contactsList.size());
        return contactsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView email;
        public TextView mobile;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            mobile = itemView.findViewById(R.id.mobile);
        }
    }

    public ContactsAdapter(List<Contacts> contactsList)
    {
        this.contactsList = contactsList;
    }


}
