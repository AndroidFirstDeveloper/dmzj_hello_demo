package com.example.myapplication;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JavaTest {
    private final String TAG = "JavaTest";

    public JavaTest() {
        testListSub();
        testMap();
        testDeleteRepeat();
        testStaticVarible();
        testDividerUsing();
    }

    /**
     * 测试分隔符的使用
     * 1、如果用“.”作为分隔的话,必须是如下写法,String.split("\\."),这样才能正确的分隔开,不能用String.split(".");
     * 2、如果用“|”作为分隔的话,必须是如下写法,String.split("\\|"),这样才能正确的分隔开,不能用String.split("|");
     */
    private void testDividerUsing() {
        String divider = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i + "").append("—").append(i + "").append(divider);
            divider = "|";
        }
        Log.e(TAG, "testDividerUsing: " + sb.toString());

        String divider2 = "";
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            sb2.append(divider2).append(i + "").append("—").append(i + "");
            divider2 = "|";
        }
        Log.e(TAG, "testDividerUsing: " + sb2.toString());

        if (!TextUtils.isEmpty(sb2.toString())) {
            String[] strArray = sb2.toString().split("\\|");
            Log.e(TAG, "testDividerUsing: " + new Gson().toJson(strArray));
        }
    }

    /**
     * 测试 修改了静态变量，但是在其它地方没有"起作用"的问题。这个问题已经解决，此处只是修改了非final类型变量，但是后期试图修改final常量，是不可能的，即使final常量由变量组成。
     */
    private void testStaticVarible() {
        if (true) {
            Constant.IS_RE_SERVE = true;
        } else {
            Constant.IS_RE_SERVE = false;
        }

        if (Constant.IS_RE_SERVE) {
            Constant.REQUEST_URL = Constant.RE_URL;
        } else {
            Constant.REQUEST_URL = Constant.URL;
        }

        Log.e(TAG, "testStaticVarible: " + Constant.REQUEST_URL);
    }

    private void testListSub() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        int itemCount = list.size();
        if (itemCount > 0) {
            if (itemCount % 2 == 0) {
                List<String> myList = list;
            } else {
                final int fromIndex = 0;
                final int toIndex = itemCount - 1;
                List<String> myList2 = list.subList(fromIndex, toIndex);//截取规则：截取的元素，包含索引fromIndex这个元素，不包含toIndex这个元素；
                Log.e(TAG, "testListSub: ");
            }
        }
    }

    private void testMap() {
        Map<String, String> map = new HashMap<>();
        map.put("1", null);
        map.put("2", "b");
        map.put("3", "c");
        map.put("4", "d");
        map.put("5", null);
        map.put("6", "");


      /*  Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String value = next.getValue();
            if (TextUtils.isEmpty(value)) {
                value = "";
            }
        }*/

        for (String key : map.keySet()) {
            if (TextUtils.isEmpty(map.get(key))) {
                map.put(key, "空值");
            }
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Log.e("DispatchActivity", "testMap: " + entry.getValue());
        }
    }

    /**
     * 测试HashSet去除重复元素的功能，去重关键点是重写equals、hashCode方法
     */
    private void testDeleteRepeat() {
        if (null instanceof ReadModel) {
            Log.e(TAG, "testDeleteRepeat: ");
        }
        List<ReadModel> list = new ArrayList<>();
        list.add(new ReadModel("0", "88", "航海王", 6));
        list.add(new ReadModel("0", "76", "今天当明星", 6));
        list.add(new ReadModel("0", "88", "航海王", 3));
        list.add(new ReadModel("1", "88", "航海王", 2));
        list.add(new ReadModel("1", "76", "今天当明星", 12));
        list.add(new ReadModel("2", "98", "一拳超人", 0));
        list.add(null);
        list.add(null);

        HashSet hashSet = new HashSet(list);
        list.clear();
        list.addAll(hashSet);
        for (ReadModel readModel : list) {
            Log.e(TAG, "testDeleteRepeat: " + new Gson().toJson(readModel));
        }
        Log.e(TAG, "testDeleteRepeat: ***************************************************");
        Iterator<ReadModel> iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            Log.e(TAG, "testDeleteRepeat: " + new Gson().toJson(iterator.next()));
        }
    }


    static class ReadModel {
        private String uid;
        private String bookId;
        private String bookName;
        private int currentPage;

        public ReadModel(String uid, String bookId, String bookName, int currentPage) {
            this.uid = uid;
            this.bookId = bookId;
            this.bookName = bookName;
            this.currentPage = currentPage;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (!(obj instanceof ReadModel)) {
                return false;
            }
            return TextUtils.equals(this.uid, ((ReadModel) obj).uid) && TextUtils.equals(this.bookId, ((ReadModel) obj).bookId);
        }

        @Override
        public int hashCode() {
            int code = 16;
            if (!TextUtils.isEmpty(uid)) {
                code = code + 31 * uid.hashCode();
            }
            if (!TextUtils.isEmpty(bookId)) {
                code = code + 31 * bookId.hashCode();
            }
            return code;
        }
    }
}
