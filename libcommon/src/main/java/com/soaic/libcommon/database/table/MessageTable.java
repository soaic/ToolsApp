package com.soaic.libcommon.database.table;

/**
 * 消息表
 * Created by Soaic on 2017/11/24.
 */
public class MessageTable {
    //表名
    public static final String TABLE_NAME = "MessageTable";
    //字段
    public static final String MESSAGE_ID = "id";
    public static final String MESSAGE_TITLE = "messageTitle";
    public static final String MESSAGE_CONTENT = "messageContent";
    //字段ID
    public static final int ID_MESSAGE_ID = 0;
    public static final int ID_MESSAGE_TITLE = 1;
    public static final int ID_MESSAGE_CONTENT = 2;
    //创建表
    public static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME + "(" +
            MESSAGE_ID + " text primary key, " +
            MESSAGE_TITLE + " text, " +
            MESSAGE_CONTENT + " text) ";
}
