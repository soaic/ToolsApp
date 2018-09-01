package com.soaic.toolsapp.dataset

class MapDemo {

    fun create(){
        var map1 = mapOf<String, Int>()
        var map2 = mapOf(1 to "x", 2 to "y")
        var map3 = emptyMap<String, Int>()
        var map4 = mutableMapOf<String, Int>()
        var map5 = hashMapOf<String, Int>()
        var map6 = linkedMapOf<String, Int>()
        var map7 = sortedMapOf(Pair(1 , "x"), 2 to "y")

    }

    fun operator(){
        var map = mutableMapOf<String, Int>()
        map.entries.forEach {
            it.component1()  //key
            it.component2()  //value
            it.toPair()  //pair
        }

        map.getOrElse("1") { 1 }   //如果不存在key，返回默认值
        map.getOrPut("1") { 1 }  //如果不存在就添加这个key到map

        for((k,v) in map) {
            println(k)
            println(v)
        }

        map.mapKeys { it.key + it.value }
        map.mapValues { it.value + it.value }
        map.filterKeys { it == "" }
        map.filterValues { it == 1 }
        map.filter { it.key == "" && it.value == 1 }

        var list = listOf(Pair(1,2))
        list.toMap()

        var map1 = mapOf<String, Int>()
        var mmap = map1.toMutableMap()
        mmap.put("2", 2)
        mmap["2"] = 2
        mmap.remove("2")
        mmap.clear()
    }

}