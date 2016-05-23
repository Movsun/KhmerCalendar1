package com.example.movsun.khmercalendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * Created by Movsun on 5/18/2016.
 */
public class CalendarAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<JSONObject> data;
    private Integer[] monthId;

    public CalendarAdapter(Context c, ArrayList<JSONObject> data, Integer[] monthId){
        mContext = c;
        this.data = data;
        this.monthId = monthId;
    }

    @Override
    public int getCount() {
        if (data == null){
            return 0;
        } else {
            return data.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout relativeLayout = null;
//        ImageView imageView;
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            relativeLayout = (RelativeLayout) inflater.inflate(R.layout.calendar_layout, null);

        } else {
            relativeLayout = (RelativeLayout) convertView;
        }
//        TextView textView = (TextView) relativeLayout.findViewById(R.id.textView);
//        textView.setText(position + "");
//        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.imageView);

//        imageView.setImageResource(mThumbIds[position]);
        TextView textDay = (TextView) relativeLayout.findViewById(R.id.textDay);
        TextView khmerDate = (TextView) relativeLayout.findViewById(R.id.khmerDate);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.imageView);

        try {
//            JSONObject jsonObject = jsonArray.getJSONObject(position);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
//            System.out.println(data.get(position).getString("date"));
//            System.out.println(format.parse(data.get(position).getString("date")));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(data.get(position).getString("date")));
//            System.out.println(calendar.toString());
//            System.out.println(calendar.get(Calendar.DATE));
//            System.out.println(data.get(position).getBoolean("is_a_holy_day"));
            textDay.setText(calendar.get(Calendar.DATE) + "");

            int kh_day = data.get(position).getInt("kh_day");
            if (kh_day >15){
                khmerDate.setText((kh_day - 15) + " រោច");
            } else {
                khmerDate.setText(kh_day + " កើត");
            }

            if (calendar.get(Calendar.MONTH) != monthId[0]){
                relativeLayout.setAlpha((float) 0.5);
            }
            if (data.get(position).getInt("is_a_holy_day") == 1){
                imageView.setImageResource(R.drawable.holy_day);
            }




        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeLayout;
//        return imageView;
    }


}
