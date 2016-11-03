package com.yangzhiyan.cameraandqrcodedemo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.ivpic)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
    }
    private final static int REQUESTCODE_GETPICFROMLIBRARY = 0;
    private final static int REQUESTCODE_GETMULITPICSFROMLIBRARY = 1;
    @Event(value = {R.id.btgetpicfromlibrary,R.id.btgetmultipicsfromlibrary},
            type = View.OnClickListener.class)
    private void Onclick(View view){
        switch (view.getId()){
            case R.id.btgetpicfromlibrary:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUESTCODE_GETPICFROMLIBRARY);
                break;
            case R.id.btgetmultipicsfromlibrary:
                Intent intent1 = new Intent();
                intent1.setClass(this,MulitPicActivity.class);
                startActivityForResult(intent1,REQUESTCODE_GETMULITPICSFROMLIBRARY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Tag","resultCode is "+ requestCode+", resultCode is "+resultCode);
        switch (requestCode){
            case REQUESTCODE_GETPICFROMLIBRARY:
                handleGetPicFromLibraryResult(resultCode,data);
                break;
        }

    }

    private void handleGetPicFromLibraryResult(int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            Uri selectedimage = data.getData();
            Log.i("Tag","Return Uri is "+selectedimage.toString());
            String[] filepathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedimage,filepathColumn,null,null,null);
            if (cursor!=null){
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filepathColumn[0]);
                Log.i("Tag","colmun is "+columnIndex+"");
                String picturePath = cursor.getString(columnIndex);
                Log.i("Tag","picturePath is "+picturePath+"");
                cursor.close();
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }
}
