package com.example.myapplication.fragment3;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

public class FragmentFour extends Fragment {

    private String TAG = "FragmentFour";
    private TaskCallback taskCallback;
    private CallTask callTask;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach1: ");
        if (context instanceof TaskCallback) {
            taskCallback = (TaskCallback) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e(TAG, "onAttach2: ");
        if (activity instanceof TaskCallback) {
            taskCallback = (TaskCallback) activity;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        setRetainInstance(true);
        callTask = new CallTask(this);
        callTask.execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated: ");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach: ");
        taskCallback = null;
    }

    public void cancelTask() {
        if (callTask != null && !callTask.isCancelled()) {
            callTask.cancel(true);
        }
    }

    interface TaskCallback {
        void onPreExecute();

        void onPostExecute();

        void onProgressUpdate(Integer progress);

        void onCancelled();
    }

    static class CallTask extends AsyncTask<Void, Integer, Void> {

        private WeakReference<FragmentFour> weakReference;

        public CallTask(FragmentFour fragmentFour) {
            weakReference = new WeakReference<>(fragmentFour);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (weakReference.get().taskCallback != null)
                weakReference.get().taskCallback.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (weakReference.get().taskCallback != null)
                weakReference.get().taskCallback.onPostExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (weakReference.get().taskCallback != null)
                weakReference.get().taskCallback.onProgressUpdate(values[0]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (weakReference.get().taskCallback != null)
                weakReference.get().taskCallback.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i <= 100 && !isCancelled(); i++) {
                SystemClock.sleep(100);
                publishProgress(i);
            }
            return null;
        }
    }

}