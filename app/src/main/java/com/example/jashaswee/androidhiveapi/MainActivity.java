package com.example.jashaswee.androidhiveapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskCompleted{
    private static String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;

//URL TO GET JSON

    private static String url = "https://api.androidhive.info/contacts/";
    private List<Contacts> contactList = new ArrayList<>();
    private List<Contacts> contactList2;
    public ContactsAdapter contactsAdapter;
    private RecyclerView recyclerView;

    public void setList(List list)
    {
        contactList2 = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList2 = new ArrayList<Contacts>();
        new GetContacts(this).execute();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        contactsAdapter = new ContactsAdapter(contactList2);
        Log.d(TAG, "CheckSize: "+contactList2.size());
        recyclerView.setAdapter(contactsAdapter);
//        Log.d(TAG, "onCreate: FUCK YOU!!!!!!!!!!!!!!!");
    }



    @Override
    public List onTaskComplete(List list) {
        contactList2 = list;
        if (list.size() != 0){
            Toast.makeText(this, "LOOOL!" + " " + list.size(), Toast.LENGTH_SHORT).show();
        }
        return list;
    }


    private class GetContacts extends AsyncTask<Void, Void, List> {
        private Context mContext;

        private TaskCompleted mCallback;

        public GetContacts(Context context)
        {
            mContext = context;
            mCallback = (TaskCompleted)context;
        }


        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            //Dismiss the progress dialog

            if (pDialog.isShowing())
                pDialog.dismiss();

            //Updating parsed json data into listview
/*
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    contactList,
                    R.layout.list_item,
                    new String[]{"name", "email", "mobile"}, new int[]{R.id.name, R.id.email, R.id.mobile}
            );
            lv.setAdapter(adapter);*/
//            Log.d(TAG, "onPostExecute: "+contactList2.get(0));
            mCallback.onTaskComplete(list);
        new MainActivity().setList(list);}

        @Override
        protected List doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            //Getting  JSON data

            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url:" + jsonStr);
            contactList = parseJson(jsonStr);
            Log.d(TAG, "doInBackground: "+contactList.get(0).getName());

            /*if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray contacts = jsonObject.getJSONArray("contacts");

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        //Phone node is JSONObject

                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        //Tmp hash map for single contact

                        HashMap<String, String> contact = new HashMap<>();


                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);


                        Contacts contacts1 = new Contacts(name,email,mobile);

                        //adding contact to contactlist
                        contactList.add(contacts1);


                    }
                } catch (final JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json Parsing Error " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();

                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }*/
            return contactList;
        }

        List getContactList(){
            return contactList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //SHowing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please Wait");
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }

    public List<Contacts> parseJson(String jsonStr)
    {if (jsonStr != null) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray contacts = jsonObject.getJSONArray("contacts");

            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                String id = c.getString("id");
                String name = c.getString("name");
                String email = c.getString("email");
                String address = c.getString("address");
                String gender = c.getString("gender");

                //Phone node is JSONObject

                JSONObject phone = c.getJSONObject("phone");
                String mobile = phone.getString("mobile");
                String home = phone.getString("home");
                String office = phone.getString("office");

                //Tmp hash map for single contact

                HashMap<String, String> contact = new HashMap<>();


                contact.put("id", id);
                contact.put("name", name);
                contact.put("email", email);
                contact.put("mobile", mobile);


                Contacts contacts1 = new Contacts(name,email,mobile);

                //adding contact to contactlist
                contactList.add(contacts1);
                Log.d(TAG, "parseJson: "+contactList.get(i).getName());


            }
        } catch (final JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Json parsing error: " + e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Json Parsing Error " + e.getMessage(),
                            Toast.LENGTH_LONG).show();

                }
            });
        }
    } else {
        Log.e(TAG, "Couldn't get json from server.");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),
                        "Couldn't get json from server.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    return contactList;}
}
