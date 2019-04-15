package com.example.myapplication.singleton;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

public class MyFragment extends Fragment {
    private TextView myfragment_layout_singleton_content1, myfragment_layout_singleton_content2,myfragment_layout_singleton_content3, myfragment_layout_singleton_content4;
    private View convertView;

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
        if (convertView == null)
            convertView = inflater.inflate(R.layout.myfragment_layout, container, false);
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myfragment_layout_singleton_content1 = convertView.findViewById(R.id.myfragment_layout_singleton_content1);
        myfragment_layout_singleton_content2 = convertView.findViewById(R.id.myfragment_layout_singleton_content2);
        myfragment_layout_singleton_content3 = convertView.findViewById(R.id.myfragment_layout_singleton_content3);
        myfragment_layout_singleton_content4 = convertView.findViewById(R.id.myfragment_layout_singleton_content4);
        myfragment_layout_singleton_content1.setText(getEhanContent());
        myfragment_layout_singleton_content2.setText(getLanhanContent());
        myfragment_layout_singleton_content3.setText(getStaticNestedClassContent());
        myfragment_layout_singleton_content4.setText(getEnumContent());
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private String getEhanContent() {
        return "public class Singleton {\n" +
                "    private static final Singleton INSTANCE = new Singleton();\n" +
                "    // 私有化构造函数\n" +
                "    private Singleton(){}\n" +
                "\n" +
                "    public static Singleton getInstance(){\n" +
                "        return INSTANCE;\n" +
                "    }\n" +
                "}";
    }

    private String getLanhanContent(){
        return "public class Singleton {\n" +
                "    private volatile static Singleton INSTANCE; //声明成 volatile\n" +
                "    private Singleton (){}\n" +
                "\n" +
                "    public static Singleton getSingleton() {\n" +
                "        if (INSTANCE == null) {                         \n" +
                "            synchronized (Singleton.class) {\n" +
                "                if (INSTANCE == null) {       \n" +
                "                    INSTANCE = new Singleton();\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "        return INSTANCE;\n" +
                "    }\n" +
                "\n" +
                "}";
    }


    private String getStaticNestedClassContent(){
        return "public class Singleton { \n" +
                "    /** \n" +
                "     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例没有绑定关系， \n" +
                "     * 而且只有被调用到才会装载，从而实现了延迟加载 \n" +
                "     */ \n" +
                "    private static class SingletonHolder{ \n" +
                "        /** \n" +
                "         * 静态初始化器，由JVM来保证线程安全 \n" +
                "         */ \n" +
                "        private static final Singleton instance = new Singleton(); \n" +
                "    } \n" +
                "    /** \n" +
                "     * 私有化构造方法 \n" +
                "     */ \n" +
                "    private Singleton(){ \n" +
                "    } \n" +
                "\n" +
                "    public static  Singleton getInstance(){ \n" +
                "        return SingletonHolder.instance; \n" +
                "    } \n" +
                "}";
    }

    private String getEnumContent(){
        return "public enum Singleton{\n" +
                "    INSTANCE;\n" +
                "}";
    }
}
