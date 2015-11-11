package com.example.administrator.weather_animation.adapter;

import com.example.administrator.weather_animation.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by xupangen
 * on 2015/11/10.
 * e-mail: xupangen@ffrj.net
 */
public class WeatherListAdapter extends BaseAdapter {
    private Context context=null;
    private List data=null;

    public WeatherListAdapter(Context context,List data){
        this.context=context;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler=null;
        if (null==convertView){
            viewHoler= new ViewHoler();
            convertView= LayoutInflater.from(context).inflate(R.layout.weather_list_item,null);
            viewHoler.weatherItem= (TextView) convertView.findViewById(R.id.item_weather);
            convertView.setTag(viewHoler);
        }else {
            viewHoler= (ViewHoler) convertView.getTag();
        }
            viewHoler.weatherItem.setText(data.get(position).toString());
        return convertView;
    }

}
class ViewHoler{
    TextView weatherItem;
}