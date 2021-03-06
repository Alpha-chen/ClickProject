package com.example.xpg.lbs.util;

import com.amap.api.services.core.PoiItem;
import com.example.xpg.lbs.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * POI 列表的adapter
 * Created by xupangen
 * on 2015/11/26.
 * e-mail: xupangen@ffrj.net
 */
public class POIListAdapter extends BaseAdapter {

    private String TAG = getClass().getSimpleName();
    private List<PoiItem> datas = null;
    private Context context;

    public POIListAdapter(Context context, List<PoiItem> data) {
        this.context = context;
        this.datas = data;
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler = null;
        viewHoler = new ViewHoler();
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.poi_list_item, null);
            viewHoler.address = (TextView) convertView.findViewById(R.id.address);
            viewHoler.addressShort = (TextView) convertView.findViewById(R.id.addressShort);
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        if (datas.get(position).getSnippet() == null) {
            viewHoler.addressShort.setText("");
        } else {
            viewHoler.addressShort.setText(datas.get(position).getSnippet());
        }
        if (datas.get(position).getTitle() == null) {
            viewHoler.address.setText("");
        } else {
            viewHoler.address.setText(datas.get(position).getTitle());
        }
        return convertView;
    }

    public class ViewHoler {
        private TextView address;
        private TextView addressShort;
    }


}
