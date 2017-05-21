package com.fuhai.mobile.fuhai.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.storage.StorageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.fuhai.mobile.fuhai.R;
import com.fuhai.mobile.fuhai.adapter.GridViewAdapter;
import com.fuhai.mobile.fuhai.utils.SubstringUtils;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomeActivity extends Activity {
    private GridView gView;
    private String rPath="";
    String[] albums=null;
    int flag =2;
    private LinkedList<String> extens=new LinkedList<String>();
    private List<String> picPathList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        gView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id)
            {
                //Log.i("GridView.setOnItemClickListener", "position="+position);
                //String path=images.get(position).path;
                //Log.i("ImageListView_onListItemClick", "the path="+path);

                ArrayList<String> pathArray=new ArrayList<String>();
                for(int i=0; i<albums.length; i++)
                {
                    if(flag==2)
                    {
                        pathArray.add(albums[i]);
                    }
                    else
                    {
                        String absolutePath=albums[i].split("&")[1];
                        pathArray.add(absolutePath);
                    }
                }

                Intent intent = new Intent();
                intent.setClass(HomeActivity.this, ImageGalleryActivity.class);
                intent.putExtra("path", rPath);
                intent.putExtra("id", position);
                intent.putExtra("data", (String[])pathArray.toArray(new String[pathArray.size()]));
                HomeActivity.this.startActivity(intent);
            }
        });
    }

    private void initData() {
        rPath = getRootPicPath();
        String name = "fuhai";
        getAllUsbHostImages(rPath);
        albums= (String[])picPathList.toArray(new String[picPathList.size()]);
        gView.setAdapter(new GridViewAdapter(this,flag, name, getNames(flag, albums)));
    }

    private void initView() {
        gView = (GridView) this.findViewById(R.id.gridview);
    }

    private String[] getNames(int flag, String[] albums)
    {
        if(flag==0)
        {
            String[] paths=new String[albums.length];
            String path=null;
            String name=null;
            for(int i=0; i<albums.length; i++)
            {
                path=albums[i].split("&")[1];
                name=path.substring(path.lastIndexOf("/")+1);
                paths[i]=name;
            }
            return paths;
        }
        else if(flag==1)
        {
            String[] ids=new String[albums.length];
            for(int i=0; i<albums.length; i++)
            {
                String id=albums[i].split("&")[0];
                ids[i]=id;
            }
            return ids;
        }
        else
            return albums;
    }
    private void getAllUsbHostImages(String path) {
        // TODO Auto-generated method stub
        extens=new LinkedList<String>();
        getExtens();
        File file=new File(path);
        getAllUsbHostImageFile(file);
    }
    public void getAllUsbHostImageFile(File file){

        file.listFiles(new FileFilter(){
            public boolean accept(File file) {
                String name = file.getName();
                int i = name.lastIndexOf('.');
                if(i != -1){
                    name = name.substring(i).toUpperCase();
                    if(extens.contains(name)){
                        savePicture(file);
                        return true;
                    }
                }else if(file.isDirectory()){
                    getAllUsbHostImageFile(file);
                }
                return false;
            }

            private void savePicture(File file) {
                String path=file.getAbsolutePath();
                if(!TextUtils.isEmpty(path)){
                    String temp =  SubstringUtils.substringAfter(path,"fuhai/");
                    if(!temp.contains("._")){
                        picPathList.add(path);
                    }
                }
            }
        });
    }
    private String[] getFilePath() {
        String[] result = null;
        StorageManager storageManager = (StorageManager)getSystemService(Context.STORAGE_SERVICE);
        try {
            Method method = StorageManager.class.getMethod("getVolumePaths");
            method.setAccessible(true);
            try {
                result =(String[])method.invoke(storageManager);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private String getRootPicPath() {
        String rootPath ="";
        String[] paths = getFilePath();
        for(int i=0;i<paths.length;i++){
            String pth = paths[i];
            rootPath=pth+"/fuhai";
            File file = new File(rootPath);
            if(file.exists())
                return rootPath;
        }
        return rootPath;
    }
    private void getExtens()
    {
        extens.add(".JPEG");
        extens.add(".JPG");
        extens.add(".PNG");
        extens.add(".GIF");
        extens.add(".BMP");
    }
}
