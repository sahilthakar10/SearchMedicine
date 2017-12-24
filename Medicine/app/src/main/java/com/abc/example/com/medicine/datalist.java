package com.abc.example.com.medicine;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by ABC on 24-12-2017.
 */

public class datalist extends ArrayAdapter<String> {

    private  String[] name;
    private Activity context;
    int b = 0 ;
    public datalist(Activity context ,  String[] name) {
        super(context, R.layout.datalist,name);
        this.context = context;
        this.name = name;
    }
    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            View listViewItem = inflater.inflate(R.layout.datalist, null, true);
            TextView textViewContact = (TextView) listViewItem.findViewById(R.id.t1);
            textViewContact.setText(name[position]);
            return listViewItem;


    }

}
