package com.example.lenovo.bustracking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.model.BusStopModel;
import java.util.ArrayList;

public class BusStopAdapter extends BaseAdapter {

    Context context;
    ArrayList<BusStopModel> routeList;

    public BusStopAdapter(Context context, ArrayList<BusStopModel> routeList) {
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
            gridView=inflator.inflate(R.layout.bus_stop_adapter, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        TextView tv1=(TextView)gridView.findViewById(R.id.textView14);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView16);
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);

        BusStopModel model=routeList.get(position);
        tv1.setText(model.getLocation());
        tv2.setText(model.getStop_type());

        return gridView;
    }
}
