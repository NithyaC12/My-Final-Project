package com.example.lenovo.bustracking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.model.BusScheduleModel;
import com.example.lenovo.model.BusStopModel;
import com.example.lenovo.model.RouteModel;

import java.util.ArrayList;

public class BusScheduleAdapter extends BaseAdapter {

    Context context;
    ArrayList<RouteModel> routeList;
    ArrayList<BusScheduleModel> scheduleList;

    public BusScheduleAdapter(Context context, ArrayList<RouteModel> routeList,ArrayList<BusScheduleModel> scheduleList) {
        this.context = context;
        this.routeList = routeList;
        this.scheduleList=scheduleList;
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
            gridView=inflator.inflate(R.layout.schedule_adapter, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        TextView tv1=(TextView)gridView.findViewById(R.id.rname);
        TextView tv2=(TextView)gridView.findViewById(R.id.rnumber);
        TextView tv3=(TextView)gridView.findViewById(R.id.stime);
        TextView tv4=(TextView)gridView.findViewById(R.id.etime);
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        RouteModel model=routeList.get(position);
        BusScheduleModel sModel=scheduleList.get(position);
        tv1.setText(model.getRoute_name());
        tv2.setText(model.getRoute_number());
        tv3.setText(sModel.getStart_time());
        tv4.setText(sModel.getEnd_time());

        return gridView;
    }
}

