package com.yangzhiyan.cameraandqrcodedemo;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MulitPicActivity extends AppCompatActivity {
    @ViewInject(R.id.gvpics) private GridView gridView;
    private List<String> imageViewList;
    private MulitPicGridviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulit_pic);
        x.view().inject(this);
        loadData();

    }
    private void loadData(){
        imageViewList = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Log.i("uri","uri is" +uri);

        String dcimFolder = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM";
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri,filePathColumn,null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);

                if (picturePath.startsWith(dcimFolder)&&new File(picturePath).exists()){
                    imageViewList.add(0,picturePath);
                }
            }
            cursor.close();
            for (String s:imageViewList){
                Log.i("Tag","selected image : "+ s);
            }
        }
        if (imageViewList.size() == 0){
            Toast.makeText(this, "没有任何图片", Toast.LENGTH_SHORT).show();
            setResult(0);
            finish();
        }else {
            adapter = new MulitPicGridviewAdapter(this,imageViewList);
            gridView.setAdapter(adapter);
        }

    }
}
