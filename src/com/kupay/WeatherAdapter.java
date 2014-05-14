package com.kupay;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherAdapter extends ArrayAdapter<OperacionRow>{

    Context context; 
    int layoutResourceId;    
    OperacionRow[] data = null;
    
    public WeatherAdapter(Context context, int layoutResourceId, OperacionRow[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new WeatherHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            
            
            row.setTag(holder);
        }
        else
        {
           holder = (WeatherHolder)row.getTag();
        }
        
        OperacionRow ventaw = data[position];
        holder.txtTitle.setText(ventaw.title);
        holder.imgIcon.setImageResource(ventaw.icon);
        
        return row;
    }
    
    static class WeatherHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}