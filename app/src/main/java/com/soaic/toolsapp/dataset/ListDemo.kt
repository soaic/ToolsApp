package com.soaic.toolsapp.dataset

class ListDemo {

    //创建
    fun create() {
        var list1 = listOf<String>()
        var list2 = listOf(1, 2, "3")
        var list3 = mutableListOf<String>()
        var list4 = mutableListOf(1, 2, "3")
        var list5 = arrayListOf(1, 2, "3")
        var list6 = list1.toMutableList()  //转换为可变list
    }


    //遍历
    fun iter() {
        var list = listOf(1, 2, 3, 4, 5, 6)
        var iterator = list.iterator()
        while (iterator.hasNext()) {
            println(iterator.next())
        }
        list.forEach {
            println(it)
        }
        list.forEach(::println)
    }

    //操作
    fun operator() {
        var list = listOf(1, 2, 3, 4, 5, 6)
        var list2 = listOf(1, 3, 5, 7)
        var mlist = list.toMutableList()
        mlist.retainAll(list2) // 去两个集合交集
        mlist.first { it == 1 }
        mlist.firstOrNull { it > 8 }  //返回 null
        mlist.last()
        mlist.single()   //集合中只有一个元素时调用
        mlist.elementAt(1)  //没有找到会报错
        mlist.elementAtOrElse(7) { 0 }  //没找到返回 0
        mlist.elementAtOrNull(7) //没找到返回null
    }

    //计算
    fun calc() {
        var list = listOf(1, 2, 3, 4, 5, 6)
        list.any()  //是否至少有一个元素
        list.any { it > 5 }  //集合中是否有一个满足条件
        list.all { it > 0 }  //集合中全部满足条件
        list.none() //集合中没有元素
        list.none { it < 0 }  //集合中全部不满足条件
        list.count()
        list.count { it > 3 }  //满足条件的个数
        list.reduce { sum, next -> sum + next }  //累计运算 类似递归
        list.reduce { sum, next -> sum * next }  //累计运算 类似递归
        list.reduceRight { sum, next -> sum + next }  //从最后一项开始累计运算
        list.reduceIndexed { index, sum, next ->  if (index > 2) sum + next else sum }  //插入索引判断
        list.fold(100) { sum, next -> sum + next}  //带初始值的reduce
        list.max()
        list.min()
        true > false
        "a" > "b"
        list.maxBy { it * it }  //返回获取映射后返回最大值所对应的那个元素值
        list.minBy { it * it }  //返回获取映射后返回最小值所对应的那个元素值
        list.sumBy { it * it }  //返回获取映射值的总和  list类型必须为int
    }

    //过滤
    fun filter(){
        var list = listOf(1, 2, 3, 4, 5, 6)
        list.take(3)  //挑选集合前3个元素的子集合
        list.takeWhile { it > 3 } //挑选大于3的子集合, ["只要遇到任意一个元素不满足则返回"]
        list.takeLast(3) //挑选集合后3个元素的子集
        list.takeLastWhile { it > 3 }
        list.drop(3) //去除前三个元素返回剩下的元素的子集合
        list.dropWhile { it > 3 }
        list.dropLast(3)
        list.dropLastWhile { it > 3 }
        list.slice(0..2) //返回[1,2,3]
        list.slice(listOf(1,3,5))  //返回[2,4,6]

        var dest = mutableListOf<Int>()
        list.filterTo(dest) {it > 3} //过滤满足大于3的元素，并赋值给dest
        list.filter { it > 3 }
        list.filterNotNull()   //过滤掉null元素
        list.filterNot { it > 3 }  //过滤掉不满足条件的元素
    }

    //映射
    fun map(){
        var list = listOf(1, 2, 3, 4, 5, 6)
        list.map { it * it }  //返回集合中元素通过转换函数映射后的结果集合
        var dest = mutableListOf<Int>()
        list.mapTo(dest){ it * it }
        list.mapIndexed { index, i -> index * i}

        list.mapNotNull { it }
        list.flatMap { it -> listOf(it + 1) }
        list.map { it -> listOf(it + 1) }.flatten()  //等于上面的flatMap

    }

    //分组
    fun group(){
        var words = listOf<String>("a", "abc", "ab", "def", "abcd")
        words.groupBy { it.length }  //以每个元素的长度分组
        var programs = listOf("K&R" to "C", "Bjar" to "C++", "Linus" to "C", "James" to "Java")
        programs.groupBy ({ it.second }, { it.first })  //{C=[K&R,Linus], C++=[Bjar], Java=[James]}
        words.groupingBy { it.first() }.eachCount() //{a=4,d=1}
    }

    //排序
    fun sort(){
        var list = listOf(1, 2, 3, 4, 5, 6)
        list.reversed()  //倒序排列
        list.sorted()  //升序
        list.sortedDescending() //降序
        list.sortedBy { it * it }  //根据映射结果进行升序排序
        list.sortedByDescending { it * it }
    }

    //生产操作符
    fun product(){
        var list1 = listOf(1,2,3,4)
        var list2 = listOf("x", "y", "z")

        list1.zip(list2) // {[1,"x"],[2, "y"], [3,"z"]} 合并两个集合组成pair集合 ，取两个集合中最短
        list1.zip(list2) { t1, t2 -> t1 to t2} //相当于上面
        list1.zip(list2) { t1, t2 -> t2 + t1}

        var listPairs = listOf(Pair(1,2), Pair(3,4), Pair(5,6))
        listPairs.unzip()  //{[1,3,4],[2,4,6]}
        list1.partition { it > 2 }  //([3,4],[1,2])  //根据条件拆分成两个子集合组成的Pair

        list1.plus(list2)
        list1 + list2

        list1.plus(5)
        list1.plusElement(5)
        list1 + 5
    }

}