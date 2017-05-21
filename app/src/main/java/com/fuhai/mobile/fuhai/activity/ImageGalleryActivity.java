package com.fuhai.mobile.fuhai.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.fuhai.mobile.fuhai.R;
import com.fuhai.mobile.fuhai.view.RLoopRecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ImageGalleryActivity extends Activity {
    String[] paths=null;
    String path=null;
    List<String> mImagePathList;
    int mCurrentPosition = 0;
    RLoopRecyclerView recyclerView;
    MyAdapter myAdapter;
    int currentPosition=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery_view);
        getIntentData();
        initView();
        initData();
        initListener();
/**
 *          int position = myAdapter.getCurrentPosition();
 if(position!=0 && position>3){
 position-=3;
 }else {
 position=datas.size()-1;
 }
 Toast.makeText(MainActivity.this,"position="+position,Toast.LENGTH_SHORT).show();
 loopRecyclerView.scrollToPosition(position);
 //                loopRecyclerView.scrollToPosition(myAdapter.getCurrentPosition()+1);
 //                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
 //                        .setAction("Action", null).show();
 *
 */

    }

    private void getIntentData() {
        Intent intent = getIntent();
        paths = intent.getStringArrayExtra("data");
        if(paths==null)
        {
            return;
        }
        path=intent.getStringExtra("path");
        int position=intent.getIntExtra("id", 0);
        mCurrentPosition=position;
        mImagePathList=new ArrayList<String>();
            for(String str: paths)
            {
                mImagePathList.add(str);
            }
        String titlePath = path+ File.separator+"title"+File.separator+"welcome.txt";
        String strTitle =  getTitleStr(titlePath);
        if(!TextUtils.isEmpty(strTitle)){
           // tvBottomTitle.setText(strTitle);
        }
    }
    private String getTitleStr(String titlePath){
        String strTitle="";
        if(TextUtils.isEmpty(titlePath)){
            return strTitle;
        }
        try {
            File urlFile = new File(titlePath);
            if(!urlFile.exists()){
                return strTitle;
            }
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String mimeTypeLine = null ;
            while ((mimeTypeLine = br.readLine()) != null) {
                strTitle +=mimeTypeLine;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strTitle.trim();
    }
    private void initListener() {

    }

    private void initData() {
    }

    private void initView() {
        recyclerView = (RLoopRecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        myAdapter = new MyAdapter();
        myAdapter.setDatas(mImagePathList);
        recyclerView.setAdapter(myAdapter);


//         Display display = getWindowManager().getDefaultDisplay();
//         displayWidth=display.getWidth();
//         displayHeight=display.getHeight();
//
//
//          DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//         // Log.i("view_DisplayMetrics" , "height" +displayMetrics.heightPixels);
//         // Log.i("view_DisplayMetrics" , "width" +displayMetrics.widthPixels);
//         Animation load_anim= AnimationUtils.loadAnimation(ImageGalleryActivity.this,R.anim.load_photo);
//         anim=AnimationUtils.loadAnimation(ImageGalleryActivity.this,R.anim.to_large);
//
//         ivBG.startAnimation(load_anim);


    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    class MyAdapter extends RLoopRecyclerView.LoopAdapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(ImageGalleryActivity.this)
                    .inflate(R.layout.item_image_view, parent, false);
            return new MyViewHolder(inflate);
        }

        @Override
        public void onBindLoopViewHolder(MyViewHolder holder, int position) {
            ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.iv_view);
            imageView.setImageURI(Uri.parse(mImagePathList.get(position)));
        }

    }
    boolean isFirst =true;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(isFirst){
            currentPosition= recyclerView.getCurrentPosition();
            isFirst =false;
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:  //按下中间键

                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:  //按下下方向
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:  //按下左方向键
                currentPosition-=1;
                if(currentPosition<0){
                    currentPosition = myAdapter.getItemRawCount()-1;
                }
                recyclerView.scrollToPosition(currentPosition);
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:  //按下右方向键
                currentPosition+=1;
                if(currentPosition>myAdapter.getItemRawCount()-1){
                    currentPosition =0;
                }
                recyclerView.scrollToPosition(currentPosition);
                break;

            case KeyEvent.KEYCODE_DPAD_UP:  //按下上方向键
                break;
            case KeyEvent.KEYCODE_MENU:  //按下菜单键
                break;
            case KeyEvent.KEYCODE_BACK:
                this.finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
