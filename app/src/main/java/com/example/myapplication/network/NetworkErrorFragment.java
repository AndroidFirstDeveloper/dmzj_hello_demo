package com.example.myapplication.network;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.fragment.OnItemClickedListener;

public class NetworkErrorFragment extends Fragment {

    private final String TAG = "NetworkErrorFragment";

    private View convertView;
    private ImageView network_error_fragment_layout_iv;
    private OnItemClickedListener onItemClickedListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            if (context instanceof OnItemClickedListener) {
                onItemClickedListener = (OnItemClickedListener) context;
            } else {
                throw new ClassCastException("context not implements OnItemClickedListener");
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            if (activity instanceof OnItemClickedListener) {
                onItemClickedListener = (OnItemClickedListener) activity;
            } else {
                throw new ClassCastException("activity not implements OnItemClickedListener");
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.network_error_fragment_layout, container, false);
        }
        return convertView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        network_error_fragment_layout_iv = convertView.findViewById(R.id.network_error_fragment_layout_iv);
        network_error_fragment_layout_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkSingleton.getInstance().isConnected()) {
                    if (onItemClickedListener != null) {
                        onItemClickedListener.onItemClicked(v, 0, "网络正常");
                    }
                } else {//没有可用网络
                    DialogSingleton.getInstance().showWifiDlg(getActivity());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
