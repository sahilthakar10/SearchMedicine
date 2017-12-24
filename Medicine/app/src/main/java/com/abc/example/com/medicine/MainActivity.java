package com.abc.example.com.medicine;

import android.app.ProgressDialog;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RequestQueue queue;
    EditText e1;
    ListView l1;
    TextView t1;
    datalist datalist;
    ProgressBar progressBar;
    View id1 , id2;
    ImageView search1 , search2 , back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id1 = findViewById(R.id.id1);
        id2 = findViewById(R.id.id2);

        search1 = (ImageView)id1.findViewById(R.id.search1);
        search2 = (ImageView)id2.findViewById(R.id.search2);
        back = (ImageView)id2.findViewById(R.id.backbuuton);

        search1.setOnClickListener(this);
        back.setOnClickListener(this);

        id2.setVisibility(View.GONE);
        e1 = (EditText)id2.findViewById(R.id.e1);
        l1 = (ListView)findViewById(R.id.l1);
        t1 = (TextView)findViewById(R.id.t1);
        progressBar = (ProgressBar)findViewById(R.id.p1);

        t1.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        getname();
        e1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                progressBar.setVisibility(View.VISIBLE);
                getname();

            }
        });

    }

    public void getname()
    {
        Log.e("Edit" , e1.getText().toString());
        if (queue == null) {
            queue = Volley.newRequestQueue(getApplicationContext());
        }
        String url = "http://192.168.43.142/medicine.php?name="+ Uri.encode(e1.getText().toString());
        Log.e("url" , url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("responce" , response);



                        if (response.equals("[]"))
                        {
                            t1.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            t1.setVisibility(View.INVISIBLE);
                        }
                        try {
                            JSONArray ar = new JSONArray(response);
                            String medicine[] = new String[ar.length()];
                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject out = ar.getJSONObject(i);
                                medicine[i] = out.getString("medicine");
                                Log.e("medicine" , medicine[i]);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                            datalist = new datalist(MainActivity.this , medicine);
                            l1.setAdapter(datalist);
                        } catch (JSONException ex) {
                            Log.e("json" , ex.getMessage());
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                t1.setVisibility(View.VISIBLE);
            }
        });
        queue.add(stringRequest);

    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.search1)
        {
            id1.setVisibility(View.GONE);
            id2.setVisibility(View.VISIBLE);
        }
       else if (view.getId() == R.id.backbuuton)
        {
            id1.setVisibility(View.VISIBLE);
            id2.setVisibility(View.GONE);
        }
        else if (view.getId() == R.id.search2)
        {
            getname();
        }

    }
}
