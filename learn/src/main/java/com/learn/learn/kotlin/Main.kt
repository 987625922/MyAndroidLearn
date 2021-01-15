package com.learn.learn.kotlin

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in  2020/12/22 17:27
 */
val array1 = intArrayOf(1, 2, 3)

var stringRepresentation: String = "1"
    get() = field
    set(value) {
        field += value // 解析字符串并赋值给其他属性
    }

fun main(args: Array<String>) {
    println("${array1.size}")
    stringRepresentation = "23"
    println(stringRepresentation)
    //扩展函数的使用
    val list = mutableListOf(1, 2, 3)
    list.swap(0, 2) // “swap()”内部的“this”会保存“list”的值
}

/**
 * @标签可以标志返回的位置
 * */
fun test1() {
    loop@ for (i in 1..100) {
        for (j in 1..100) {
            if (j == 1) {
                break@loop
            }
        }
    }
}

/**
 * 扩展函数
 */
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // “this”对应该列表
    this[index1] = this[index2] //这个 this 关键字在扩展函数内部对应到接收者对象（传过来的在点符号前的对象）
    this[index2] = tmp
}

/**
 * 扩展属性
 */
val <T> List<T>.lastIndex: Int
    get() = size - 1