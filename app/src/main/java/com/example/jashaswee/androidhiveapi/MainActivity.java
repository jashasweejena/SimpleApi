package com.example.jashaswee.androidhiveapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements TaskCompleted {
    private static String TAG = MainActivity.class.getSimpleName();
    private static String url = "https://api.androidhive.info/contacts/";
    public ContactsAdapter contactsAdapter;

    //URL TO GET JSON
    //    FastItemAdapter<Contacts> fastItemAdapter;
    Contacts contacts;
    ItemAdapter itemAdapter;
    FastAdapter fastAdapter;
    private ProgressDialog pDialog;
    private ListView lv;
        private ArrayList<Contacts> contactList = new ArrayList<>();
    private ArrayList<Contacts> contactList2;
    private RecyclerView recyclerView;
    FastItemAdapter<Contacts> fastItemAdapter;

    public void setList(ArrayList list) {
        contactList2 = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList2 = new ArrayList<>();
        new GetContacts(this).execute();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        fastItemAdapter = new FastItemAdapter<>();
        contacts = new Contacts();
        itemAdapter = new ItemAdapter();
        fastAdapter = FastAdapter.with(itemAdapter);
    }


    @Override
    public ArrayList onTaskComplete(ArrayList list) {
        contactList2 = list;
        itemAdapter.add(contactList2);
        for(int i = 0 ; i < list.size() ; i++)
        {
            Toast.makeText(MainActivity.this, " " + contactList2.get(i).getName(),Toast.LENGTH_SHORT).show();
        }
        return list;
    }

    public ArrayList<Contacts> parseJson(String jsonStr) {
        if (jsonStr != null) {
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


                    Contacts contacts1 = new Contacts(name, email, mobile);

                    //adding contact to contactlist
                    contactList.add(contacts1);
                    Log.d(TAG, "parseJson: " + contactList.get(i).getName());


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
        return contactList;
    }

    private class GetContacts extends AsyncTask<Void, Void, ArrayList> {
        private Context mContext;

        private TaskCompleted mCallback;

        public GetContacts(Context context) {
            mContext = context;
            mCallback = (TaskCompleted) context;
        }


        @Override
        protected void onPostExecute(ArrayList list) {
            super.onPostExecute(list);
            //Dismiss the progress dialog

            if (pDialog.isShowing())
                pDialog.dismiss();

            mCallback.onTaskComplete(list);
            new MainActivity().setList(list);
            Log.d(TAG, "CheckSize: " + contactList2.size());
            recyclerView.setAdapter(fastAdapter);

        }

        @Override
        protected ArrayList doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            //Getting  JSON data

            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url:" + jsonStr);
            contactList = parseJson(jsonStr);
            Log.d(TAG, "doInBackground: " + contactList.get(0).getName());


            return contactList;
        }

        ArrayList getContactList() {
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
}
