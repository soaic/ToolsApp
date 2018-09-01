package com.soaic.toolsapp.dataset


class SetDemo {

    //Set中元素不可重复

    fun create(){
        var set = setOf<Int>()    //使用的LinkedHashSet构造函数
        var set0 = emptySet<Int>()
        var set1 = setOf(1, 2, 3, 4)
        var set2 = mutableSetOf<Int>()//使用的LinkedHashSet构造函数

        var hs = hashSetOf<Int>() //使用的HashSet构造函数
        var lh = linkedSetOf<Int>()//使用的LinkedHashSet构造函数
        var ss = sortedSetOf<Int>()//使用的TreeSet构造函数

    }

    fun plusOrMinus(){
        var set = mutableSetOf(1,2,3,4,5)
        set + 10
        set - 10
        set + listOf(6,7)
        set - listOf(6,7)
    }
}