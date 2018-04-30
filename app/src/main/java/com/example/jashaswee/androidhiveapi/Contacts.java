package com.example.jashaswee.androidhiveapi;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

/**
 * Created by jashaswee on 31/3/18.
 */

public class Contacts extends AbstractItem<Contacts, Contacts.ViewHolder> {
    private String name;
    private String email;
    private String mobile;

    public Contacts(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public Contacts(){ }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @NonNull
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.item_parent;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.list_item;
    }


    public class ViewHolder extends FastAdapter.ViewHolder<Contacts> {
        public TextView name;
        public TextView email;
        public TextView mobile;


        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            mobile = itemView.findViewById(R.id.mobile);
        }

        @Override
        public void bindView(Contacts item, List<Object> payloads) {
            name.setText(getName());
            email.setText(getEmail());
            mobile.setText(getMobile());
        }

        @Override
        public void unbindView(Contacts item) {
            name.setText(null);
            email.setText(null);
            mobile.setText(null);
        }


    }


}
