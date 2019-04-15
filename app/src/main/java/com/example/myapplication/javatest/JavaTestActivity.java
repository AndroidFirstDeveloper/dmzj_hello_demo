package com.example.myapplication.javatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class JavaTestActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView activity_string_test_recycler_view;
    private Button activity_string_test_button;
    private List<String> list = new ArrayList<>();
    private TestAdapter testAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_test);
        findView();
        setListener();
        initData();
//        testString();
//        testString2();
//        testInteger();
//        testReference();
    }

    private void findView() {
        activity_string_test_button = findViewById(R.id.activity_string_test_button);
        activity_string_test_recycler_view = findViewById(R.id.activity_string_test_recycler_view);
        activity_string_test_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setListener() {
        activity_string_test_button.setOnClickListener(this);
    }

    private void initData() {
        System.out.println("JavaTestActivity\t\t\t\t\t\tlist=\t\t" + list.hashCode());
        for (int i = 0; i < 13; i++) {
            list.add("item\t\t" + i);
        }
        System.out.println("JavaTestActivity\t\t\t\t\t\tlist=\t\t" + list.hashCode());
        testAdapter = new TestAdapter(this, list);
        activity_string_test_recycler_view.setAdapter(testAdapter);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.activity_string_test_button) {
            int position = list.size();
            list.add("item\t\ttest");
//            testAdapter.notifyItemInserted(position);
//            testAdapter.notifyItemRangeInserted(position, 1);
            testAdapter.notifyDataSetChanged();
            System.out.println("JavaTestActivity\t\t\t\t\t\tlist=\t\t" + list.hashCode());
        }
    }

    private void testString() {
       /* String s1 = "HelloWorld";
        String s2 = "HelloWorld";
        String s3 = new String("HelloWorld");
        String s4 = "Hello";
        String s5 = "World";
        String s6 = "Hello" + "World";
        String s7 = s3 + s4;

        System.out.println(s1 == s2);//1:输出为true

        System.out.println(s1 == s3);//2：输出为false

        System.out.println(s1 == s6);//3：输出为true

        System.out.println(s1 == s7);//4：输出为false

        System.out.println(s1 == s7.intern());//5：输出为true

        System.out.println(s3 == s3.intern());//6：输出为false*/

       /* String s = new String("1");
        String s6 = s.intern();
        System.out.println("s == s6\t\t" + (s == s6));
        String s2 = "1";
        System.out.println("s == s2\t\t" + (s == s2));
        String s3 = new String("1") + new String("1");
        String s5 = s3.intern();
        System.out.println("s3 == s5\t\t" + (s3 == s5));
        String s4 = "11";
        System.out.println("s3 == s4\t\t" + (s3 == s4));*/

        /*
         * 总结：常量在代码编译的时候已经加到class文件中了，编译时还会对代码进行优化比如s="a"+"b"+"c";实际编译时会转为s="abc"再赋值，
         * 然后"abc"被放到字符串常量池中。
         * new String()创建字符串对象，创建的对象是存储在heap上，直接用引号创建的对象是存储在permanent generation space（永久代）上。
         * intern（）方法的作用是去字符串常量池中查找目标字符串，查到返回字符串引用，没有的话将字符串加到常量池中并返回引用。
         *  它遵循对于任何两个字符串 s 和 t，当且仅当 s.equals(t) 为 true 时，s.intern() == t.intern() 才为 true”，这是jdk文档原文注解。
         * */

       /* String s1 = "cui";
        String s2 = "zhenling";
        final String s3 = "cui";
        final String s4 = "zhenling";
        String s5 = "cuizhenling";
        String s6 = s1 + s2;
        String s7 = s3 + s4;
        String s8 = "cuizhenling1";
        String s9 = "cuizhenling" + 1;
        String s10 = new String("cui") + new String("zhenling");
        String s11 = new String("cuizhenling");
        String s12 = "cuizhengling6";
        String s13 = "cuizhengling6";

        System.out.println("s1==s3\t\t" + (s1 == s3));
        System.out.println("s5==s6\t\t" + (s5 == s6));
        System.out.println("s5==s6.intern\t\t" + (s5 == s6.intern()));
        System.out.println("s5==s7\t\t" + (s5 == s7));
        System.out.println("s8==s9\t\t" + (s8 == s9));
        System.out.println("s10==s11\t\t" + (s10 == s11));
        System.out.println("s5==s11\t\t" + (s5 == s11));
        System.out.println("s5==s11.intern\t\t" + (s5 == s11.intern()));
        System.out.println("s12==s13\t\t" + (s12 == s13));
        System.out.println("s5 hashCode\t\t" + s5.hashCode());
        System.out.println("s11 hashCode\t\t" + s11.hashCode());
        System.out.println("s11.intern hashCode\t\t" + s11.intern().hashCode());*/
    }

    private void testString2() {
        String s = "ren";
        System.out.println("s=" + s);
        changeString(s);
        System.out.println("s=" + s);
    }

    private void changeString(String str) {
        str = str + "wenling";
    }


    private void testInteger() {
        /*
         * 从jdk1.5开始Integer支持自动拆箱和自动装箱
         * */
        Integer i1 = 100;
        Integer i2 = 100;
        Integer i3 = new Integer(100);
        Integer i4 = 200;
        Integer i5 = 200;
        Integer i6 = new Integer(200);

        System.out.println("i1==i2\t\t" + (i1 == i2));//i1==i2		true
        System.out.println("i2==i3\t\t" + (i2 == i3));//i2==i3		false
        System.out.println("i4==i5\t\t" + (i4 == i5));//i4==i5		false
        System.out.println("i5==i6\t\t" + (i5 == i6));//i5==i6		false
    }

    private void testReference() {
        Person person = new Person();
        System.out.println("person=\t\t" + person);//person=		com.example.myapplication.javatest.JavaTestActivity$Person@195f3d16
        changePerson(person);
        System.out.println("person=\t\t" + person);//person=		com.example.myapplication.javatest.JavaTestActivity$Person@195f3d16
    }

    private void changePerson(Person person2) {
        person2 = new Person();
    }

    private static class Person {
        private String name;
        private String contact;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }
    }

}
