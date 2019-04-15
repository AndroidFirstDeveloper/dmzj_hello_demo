package com.example.myapplication.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.refresh.RefreshLoadActivity;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment implements OnItemClickedListener {

    private View convertView;
    private final int FRAGMENT_REQUST_CODE = 0X1100;

    private TestAdapter testAdatper;
    private RecyclerView recyclerView;
    List<String> list = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.first_fragment_layout, container, false);
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list.clear();
        for (int i = 0; i < 20; i++) {
            list.add("item " + i);
        }
        recyclerView = convertView.findViewById(R.id.first_fragment_layout_recycler_view);
        testAdatper = new TestAdapter(getActivity(), list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(testAdatper);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private int mCurrentPos = 0;

    @Override
    public void onItemClicked(View view, int position, String content) {
        mCurrentPos = position;
        Intent intent = new Intent(getActivity(), SecondActivity.class);
        intent.putExtra("chapter", content);
        startActivityForResult(intent, FRAGMENT_REQUST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("FirstFragment", "onActivityResult" + "\t\trequestCode=" + requestCode + "\t\tresultCode=" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == FRAGMENT_REQUST_CODE) {
                if (data != null) {
                    String result = data.getStringExtra("result");
                    list.set(mCurrentPos, result);
                    testAdatper.notifyItemChanged(mCurrentPos);
                }
            }
        }
    }

    class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {

        private List<String> data;
        private Context context;
        private OnItemClickedListener onItemClickedListener;

        public TestAdapter(Context context, List<String> data, OnItemClickedListener onItemClickedListener) {
            this.context = context;
            this.onItemClickedListener = onItemClickedListener;
            this.data = data;
        }

        @NonNull
        @Override
        public TestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TestHolder(LayoutInflater.from(context).inflate(R.layout.test_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TestHolder holder, final int position) {
            holder.textView.setText(data.get(position));
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickedListener != null)
                        onItemClickedListener.onItemClicked(v, position, data.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        public class TestHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public TestHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.test_item_layout_tv);
            }
        }
    }
}
