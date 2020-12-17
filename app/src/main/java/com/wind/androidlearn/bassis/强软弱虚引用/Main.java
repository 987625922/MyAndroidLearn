package com.wind.androidlearn.bassis.强软弱虚引用;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2020/12/17 11:01
 */
class Main {
    public static void main(String[] args) {
        //强引用
        String strongReference = new String("abc");
        //软引用 内存空间充足时，垃圾回收器就不会回收它
        String str = new String("abc");
        SoftReference<String> softReference = new SoftReference<>(str);
        //弱引用 不管当前内存空间足够与否，都会回收它的内存
        String str1 = new String("abc");
        WeakReference<String> weakReference = new WeakReference<>(str1);
        //虚引用
        String str2 = new String("abc");
        ReferenceQueue queue = new ReferenceQueue();
        // 创建虚引用，要求必须与一个引用队列关联
        PhantomReference pr = new PhantomReference(str2, queue);

        List list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Object o = new Object();
            list.add(o);
            o = null;
        }
        System.out.println(list.get(1));
    }
}
