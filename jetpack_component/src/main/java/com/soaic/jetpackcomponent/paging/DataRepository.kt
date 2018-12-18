package com.soaic.jetpackcomponent.paging

class DataRepository{
    private val data = ArrayList<DataBean>()

    init {
        for (i in 0..100) {
            val bean = DataBean(i.toLong(), "name $i")
            data.add(bean)
        }

    }

    fun loadData(size: Int): List<DataBean> {
        return data.subList(0, size)
    }

    fun loadData(index: Int, size: Int): List<DataBean>? {

        if (index >= data.size - 1 || index < 1) {
            return null
        }

        if (index + size > data.size) {
            return data.subList(index + 1, data.size)
        }
        return data.subList(index + 1, index + size)
    }

    fun loadPageData(page: Int, size: Int): List<DataBean>? {

        val totalPage = if (data.size % size == 0) {
            data.size / size
        } else {
            data.size / size + 1
        }

        if (page > totalPage || page < 1) {
            return null
        }

        if (page == totalPage) {
            return data.subList((page - 1) * size, data.size)
        }
        return data.subList((page - 1) * size, page * size)
    }

    fun add(){
        for (i in 101..200) {
            val bean = DataBean(i.toLong(), "name $i")
            data.add(bean)
        }
    }
}