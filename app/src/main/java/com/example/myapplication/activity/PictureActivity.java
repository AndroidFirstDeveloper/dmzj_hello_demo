package com.example.myapplication.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.PhotoClipperUtil;
import com.example.myapplication.R;
import com.example.myapplication.album.AlbumActivity;
import com.example.myapplication.album.AlbumFile;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class PictureActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "AlbumActivity";
    private TextView activity_album_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        findView();
        initData();
    }


    private void findView() {
        View activity_picture_toolbar = findViewById(R.id.activity_picture_toolbar);
        View title_bar_layout_status_view = activity_picture_toolbar.findViewById(R.id.title_bar_layout_status_view);
        ImmersionBar.with(this).statusBarView(title_bar_layout_status_view).init();

        Button activity_album_btn1 = findViewById(R.id.activity_album_btn1);
        Button activity_album_btn2 = findViewById(R.id.activity_album_btn2);
        Button activity_album_btn3 = findViewById(R.id.activity_album_btn3);
        Button activity_album_three_btn = findViewById(R.id.activity_album_three_btn);
        activity_album_tv = findViewById(R.id.activity_album_tv);

        activity_album_btn1.setOnClickListener(this);
        activity_album_btn2.setOnClickListener(this);
        activity_album_btn3.setOnClickListener(this);
        activity_album_three_btn.setOnClickListener(this);
    }


    private void initData() {
        activity_album_tv.setText("使用 Intent.ACTION_PICK 方式，选择图片后返回结果 content://media/external/images/media/5858\n"
                + "使用 Intent.ACTION_GET_CONTENT 方式，选择图片后返回结果 content://com.android.providers.media.documents/document/image%3A5869\n"
                + "使用 Intent.ACTION_OPEN_DOCUMENT 方式，选择图片后返回结果 content://com.android.providers.media.documents/document/image%3A5866\n"
                + "经过对返回结果解析，得到的路径格式 /storage/emulated/0/Pictures/Screenshots/Screenshot_20190425-163505.jpg\n"
        );
    }

    private final int action_pick_request_code = 1001;
    private final int action_content_request_code = 1002;
    private final int action_document_request_code = 1003;

    private List<AlbumFile> list = new ArrayList<>();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_album_btn1: {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, action_pick_request_code);
            }
            break;
            case R.id.activity_album_btn2: {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, action_content_request_code);
            }
            break;
            case R.id.activity_album_btn3: {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, action_document_request_code);
            }
            break;
            case R.id.activity_album_three_btn: {
                Intent intent = new Intent(PictureActivity.this, AlbumActivity.class);
                intent.putExtra(AlbumActivity.PICTURE_MAX_SELECTED_SIZE, 20);
                intent.putParcelableArrayListExtra(AlbumActivity.PICTURE_SELECTED_LIST, (ArrayList<? extends Parcelable>) list);
                intent.putExtra(AlbumActivity.REQUEST_CLASS_NAME, "com.example.myapplication.activity.PictureActivity");
                startActivityForResult(intent, 2000);
            }
            break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    Log.e(TAG, "onActivityResult: " + uri);
                    String path = PhotoClipperUtil.getPath(this.getApplicationContext(), uri);
                    Log.e(TAG, "onActivityResult: " + path);
                }

                switch (requestCode) {
                    case action_pick_request_code:
                        break;
                    case action_content_request_code:
                        break;
                    case action_document_request_code:
                        break;
                }
            }
        } else if (resultCode == AlbumActivity.PICTURE_SELECTED_RESULT_CODE) {
            List<AlbumFile> list = data.getParcelableArrayListExtra(AlbumActivity.PICTURE_SELECTED_LIST);
            if (list != null) {
                Log.e("PictureActivity", new Gson().toJson(list));
            }
        }
    }

    @Override
    protected void onDestroy() {
        ImmersionBar.with(this).destroy();
        super.onDestroy();
    }
}
