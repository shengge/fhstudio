package com.fuhai.mobile.fuhai.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.provider.MediaStore.Images.Thumbnails;

import com.fuhai.mobile.fuhai.R;
import com.fuhai.mobile.fuhai.utils.ImageCommon;

import java.io.File;

/**
 * Created by liyusheng on 2017/5/18.
 */

public class GridViewAdapter extends BaseAdapter {
    private static final String TAG = "GridViewAdapter";
    private LayoutInflater mInflater;
    private String[] rowid;
    int flag=1; //sdcard
    Context c;
    String album;

    public GridViewAdapter(Context context, int f, String name, String[] rows)
    {
        mInflater = LayoutInflater.from(context);
        c=context;
        rowid = rows;
        flag=f;
        album=name;
    }

    public int getCount()
    {
        return rowid.length;
    }

    public Object getItem(int position)
    {
        return rowid[position];
    }

    public long getItemId(int position)
    {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;

        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.grid_row, null);
            imageView = (ImageView) convertView.findViewById(R.id.imageItem);
            convertView.setTag(imageView);
        }
        else
        {
            imageView = (ImageView) convertView.getTag();
        }

//        if(flag==0)
//        {
//            try
//            {
//                Bitmap image= ImageCommon.readBitmaps(album+"_"+rowid[position]);
//                if(image==null)
//                {
//                    image= BitmapFactory.decodeResource(c.getResources(), R.mipmap.ic_launcher);
//                }
//                imageView.setImageBitmap(image);
//            }
//            catch(Exception err)
//            {
//                err.printStackTrace();
//                Log.i(TAG, "get image by id had a unkonown error!");
//            }
//        }
//        else if(flag==1)  //sdcard
//        {
//            imageView.setImageBitmap(Thumbnails.getThumbnail(c.getContentResolver(),Integer.valueOf(rowid[position]),Thumbnails.MICRO_KIND,new BitmapFactory.Options()));
//        }
//        else  //usbhost
//        {
//
//        }
        Bitmap bitmap= ImageCommon.getFitSizePicture(new File(rowid[position]));
        if(bitmap==null)
        {
            Resources res=c.getResources();
            bitmap=BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
        }
        imageView.setImageBitmap(bitmap);
        //  holder.icon.setImageBitmap(imageInfo.get(position).icon);
        return convertView;
    }

}
