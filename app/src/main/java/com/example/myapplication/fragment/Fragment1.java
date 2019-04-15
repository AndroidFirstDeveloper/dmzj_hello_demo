package com.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

public class Fragment1 extends Fragment {

    private final String TAG = "Fragment1";
    private View convertView;
    private EditText editText;
    private TextView fragment1_layout_detail_tv;
    private Button fragment1_layout_btn;
    private Button fragment1_layout_contact_btn;
    private String detail = "";
    private OnItemClickedListener onItemClickedListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach\t\tfragment name：" + this.toString());
        if (getActivity() != null) {
            if (getActivity() instanceof OnItemClickedListener) {
                onItemClickedListener = (OnItemClickedListener) getActivity();
            } else {
                throw new ClassCastException("activity haven't implements OnItemClickedListener");
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView\t\tdetail=" + detail);
        convertView = inflater.inflate(R.layout.fragment1_layout, container, false);
        return convertView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated\t\tfragment name：" + this.toString());
        editText = convertView.findViewById(R.id.fragment1_layout_et);
        fragment1_layout_detail_tv = convertView.findViewById(R.id.fragment1_layout_detail_tv);
        fragment1_layout_btn = convertView.findViewById(R.id.fragment1_layout_btn);
        fragment1_layout_contact_btn = convertView.findViewById(R.id.fragment1_layout_contact_btn);

        fragment1_layout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail = editText.getText().toString().trim();
                fragment1_layout_detail_tv.setText(detail);
            }
        });

        fragment1_layout_contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickedListener != null)
                    onItemClickedListener.onItemClicked(v, 1, "fragment1");
            }
        });


//        editText.setText("fragment1");
//        runDelayTask(2000);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView");
        /*Bundle bundle = new Bundle();
        bundle.putString("title", editText.getText().toString().trim());
        onSaveInstanceState(bundle);*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach");
        onItemClickedListener = null;
    }

    private void runDelayTask(final long millis) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(millis);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editText.setText("First Fragment");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
