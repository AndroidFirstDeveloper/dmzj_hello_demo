package com.example.myapplication.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

public class SampleDialog extends DialogFragment implements View.OnClickListener {

    private View convertView;
    private final String RESPONSE_VALUE = "response_value";
    private final int DIALOG_REQUEST_CODE = 0X1101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.sample_dialog_layout, container, false);
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView1 = convertView.findViewById(R.id.sample_dialog_layout_tv1);
        TextView textView2 = convertView.findViewById(R.id.sample_dialog_layout_tv2);
        TextView textView3 = convertView.findViewById(R.id.sample_dialog_layout_tv3);
        TextView textView4 = convertView.findViewById(R.id.sample_dialog_layout_tv4);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sample_dialog_layout_tv1:
            case R.id.sample_dialog_layout_tv2:
            case R.id.sample_dialog_layout_tv3:
            case R.id.sample_dialog_layout_tv4:
                setResult(((TextView) v).getText().toString());
                break;
        }
    }

    private void setResult(String content) {
        if (getTargetFragment() == null)
            return;
        Intent intent = new Intent();
        intent.putExtra(RESPONSE_VALUE, content);
        getTargetFragment().onActivityResult(DIALOG_REQUEST_CODE, getActivity().RESULT_OK, intent);
    }
}
