package com.example.lenovo.bustracking;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.model.BusModel;
import com.example.lenovo.model.BusStopModel;
import com.example.lenovo.model.StopAllocationModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StopAllocationAdapter extends BaseAdapter {

    SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
    Context context;
    ArrayList<StopAllocationModel> allocationList;
    ArrayList<BusStopModel> stopList;
    BusModel busModel;
    Date startTime=null;

    public StopAllocationAdapter(Context context, ArrayList<StopAllocationModel> allocationList,ArrayList<BusStopModel> stopList,BusModel busModel) {
        this.context = context;
        this.allocationList = allocationList;
        this.stopList = stopList;
        this.busModel = busModel;
        try {
            startTime=sdf.parse(DriverRoutes.selectedSchedule.getStart_time());
        }catch (Exception e){}
    }

    @Override
    public int getCount() {
        return stopList.size();
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
            gridView=inflator.inflate(R.layout.stop_allocation_adapter, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        TextView tv1=(TextView)gridView.findViewById(R.id.name);
        TextView tv2=(TextView)gridView.findViewById(R.id.time);
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);

        BusStopModel model=stopList.get(position);
        StopAllocationModel sModel=allocationList.get(position);
        tv1.setText(model.getLocation());
        if(busModel.getBus_type().equalsIgnoreCase("ordinary")){

            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(startTime);
                cal.add(Calendar.MINUTE, Integer.parseInt(sModel.getOrdinary()));
                startTime=cal.getTime();
                String newTime = sdf.format(cal.getTime());
                tv2.setText(newTime);
            }catch (Exception e){
                Log.d("===============",e.toString());
            }
        }
        else if(busModel.getBus_type().equalsIgnoreCase("limited stop")){
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(startTime);
                cal.add(Calendar.MINUTE, Integer.parseInt(sModel.getOrdinary()));
                startTime=cal.getTime();
                String newTime = sdf.format(cal.getTime());
                tv2.setText(newTime);
            }catch (Exception e){
                Log.d("===============",e.toString());
            }
        }
        else if(busModel.getBus_type().equalsIgnoreCase("fast passenger")){
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(startTime);
                cal.add(Calendar.MINUTE, Integer.parseInt(sModel.getOrdinary()));
                startTime=cal.getTime();
                String newTime = sdf.format(cal.getTime());
                tv2.setText(newTime);
            }catch (Exception e){
                Log.d("===============",e.toString());
            }
        }
        else if(busModel.getBus_type().equalsIgnoreCase("super fast")){
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(startTime);
                cal.add(Calendar.MINUTE, Integer.parseInt(sModel.getOrdinary()));
                startTime=cal.getTime();
                String newTime = sdf.format(cal.getTime());
                tv2.setText(newTime);
            }catch (Exception e){
                Log.d("===============",e.toString());
            }
        }
        else if(busModel.getBus_type().equalsIgnoreCase("super express")){
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(startTime);
                cal.add(Calendar.MINUTE, Integer.parseInt(sModel.getOrdinary()));
                startTime=cal.getTime();
                String newTime = sdf.format(cal.getTime());
                tv2.setText(newTime);
            }catch (Exception e){
                Log.d("===============",e.toString());
            }
        }
        else if(busModel.getBus_type().equalsIgnoreCase("super delux")){
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(startTime);
                cal.add(Calendar.MINUTE, Integer.parseInt(sModel.getOrdinary()));
                startTime=cal.getTime();
                String newTime = sdf.format(cal.getTime());
                tv2.setText(newTime);
            }catch (Exception e){
                Log.d("===============",e.toString());
            }
        }

        return gridView;
    }
}