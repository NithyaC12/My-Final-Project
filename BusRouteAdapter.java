package com.example.lenovo.bustracking;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.model.RouteModel;

import java.util.ArrayList;

public class BusRouteAdapter extends BaseAdapter {

    Context context;
    ArrayList<RouteModel> routeList;

    public BusRouteAdapter(Context context, ArrayList<RouteModel> routeList) {
        this.context = context;
        this.routeList = routeList;
    }

    @Override
    public int getCount() {
        return routeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(context);
            gridView=inflator.inflate(R.layout.route_adapter, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        TextView tv1=(TextView)gridView.findViewById(R.id.textView14);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView16);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView17);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView19);
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);

        RouteModel model=routeList.get(position);
        tv1.setText(model.getRoute_name());
        tv2.setText(model.getRoute_number());
        tv3.setText(model.getRoute_from());
        tv4.setText(model.getRoute_to());

        return gridView;
    }
}
