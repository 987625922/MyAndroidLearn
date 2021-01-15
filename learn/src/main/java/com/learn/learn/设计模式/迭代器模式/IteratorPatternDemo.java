package com.learn.learn.设计模式.迭代器模式;

/**
 * @Author: LL
 * @Description:迭代器模式，在list和set中经常使用
 * @Date:Create：in 2020/12/21 16:11
 */
class IteratorPatternDemo {

    public static void main(String[] args) {
        Namerepository namerepository = new Namerepository();
        for (Iterator iterator = namerepository.getIterator(); iterator.hasNext();) {
            String name = (String) iterator.next();
            System.out.println("Name：" + name);
        }
    }
}
