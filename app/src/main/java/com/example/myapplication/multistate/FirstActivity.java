package com.example.myapplication.multistate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import java.util.zip.Inflater;

public class FirstActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        setToolbar();
    }

    private void setToolbar(){

        toolbar.setTitleTextAppearance(this,R.style.Toolbar_TitleTextSize);
        toolbar.setBackgroundResource(R.color.colorPrimary);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.holo_red_dark));
        toolbar.setPopupTheme(R.style.Toolbar_PopupTheme);
        toolbar.setNavigationIcon(R.drawable.vector_drawable_back);
    }

    @Override
    public void onClick(View v) {
        multiStateView.showLoadingNormal();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.net_error_item:
                multiStateView.showNetError();
                break;
            case R.id.loading_data_item:
                multiStateView.showLoadingNormal();
                break;
            case R.id.loading_error_item:
                multiStateView.showLoadingError();
                break;
            case R.id.empty_item:
                multiStateView.showEmpty();
                break;
            case R.id.loading_success_item:
                multiStateView.showSuccess();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
