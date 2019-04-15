package com.example.myapplication.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

public class SecondFragment extends Fragment {

    private final String TAG = "SecondFragment";
    private View convertView;
    private final int FRAGMENT_REQUST_CODE = 0X1100;
    private final int DIALOG_REQUEST_CODE = 0X1101;
    private final String RESPONSE_VALUE = "response_value";

    private String content;
    private Button button;
    private Button button2;
    private Button second_fragment_layout_check_btn;
    private String detail;//
    private String dialogSelectContent = "Fragment与FragmentDialog通信";
//    private  Button second_fragment_layout_remove_btn;
//    private OnItemClickedListener onItemClickedListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach()");
       /* if(getActivity()!=null){
            if(getActivity() instanceof OnItemClickedListener){
                onItemClickedListener=(OnItemClickedListener)getActivity();
            }else{
                throw new ClassCastException("activity haven't implements OnItemClickedListener");
            }
        }*/
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate()");
        setRetainInstance(true);
        Bundle bundle = getArguments();
        if (bundle != null) {
            content = bundle.getString("chapter");
            Intent intent = new Intent();
            intent.putExtra("result", content + "--SecondFragment");
            getActivity().setResult(getActivity().RESULT_OK, intent);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView()\t\tdetail=" + detail);
        convertView = inflater.inflate(R.layout.second_fragment_layout, container, false);
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated()");
        TextView textView = convertView.findViewById(R.id.second_fragment_layout_tv);
        textView.setText(content);

        button = convertView.findViewById(R.id.second_fragment_layout_btn);
        button2 = convertView.findViewById(R.id.second_fragment_layout_btn2);
        second_fragment_layout_check_btn = convertView.findViewById(R.id.second_fragment_layout_check_btn);
//        second_fragment_layout_remove_btn=convertView.findViewById(R.id.second_fragment_layout_remove_btn);

        button.setText(dialogSelectContent);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SampleDialog sampleDialog = new SampleDialog();
                sampleDialog.setTargetFragment(SecondFragment.this, DIALOG_REQUEST_CODE);
                sampleDialog.show(getFragmentManager(), "SampleDialog");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
                startActivity(intent);
            }
        });

        second_fragment_layout_check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail = "中华人民共和国合同法";
                Log.e(TAG, "onClick()\t\tdetail=" + detail);
            }
        });

      /*  second_fragment_layout_remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickedListener!=null)
                    onItemClickedListener.onItemClicked(v,0,"SecondFragment");
            }
        });*/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy()");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult()");
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == DIALOG_REQUEST_CODE) {
                if (data != null) {
                    String result = data.getStringExtra(RESPONSE_VALUE);
                    dialogSelectContent = result;
                    button.setText(result);
                }
            }
        }
    }

    public static SecondFragment getInstant(String content) {
        Bundle bundle = new Bundle();
        bundle.putString("chapter", content);
        SecondFragment secondFragment = new SecondFragment();
        secondFragment.setArguments(bundle);
        return secondFragment;
    }
}
