package com.soaic.libcommon.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.soaic.libcommon.database.DataBaseHelper;
import com.soaic.libcommon.database.entity.MessageModel;
import com.soaic.libcommon.database.table.MessageTable;

import java.util.ArrayList;
import java.util.List;

/**
 * message表DAO
 * Created by Soaic on 2017/11/24.
 */
public class MessageDAO {
    public static MessageDAO getInstance(){
        return SingleLoader.INSTANCE;
    }
    private static class SingleLoader {
        private final static MessageDAO INSTANCE = new MessageDAO();
    }

    public boolean add(Context context, String messageId, String messageTitle, int MessageContent) {
        ContentValues values = new ContentValues();
        values.put(MessageTable.MESSAGE_ID, messageId);
        values.put(MessageTable.MESSAGE_TITLE, messageTitle);
        values.put(MessageTable.MESSAGE_CONTENT, MessageContent);
        long result = DataBaseHelper.getDataBase(context).insert(MessageTable.TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean remove(Context context, int messageId) {
        int result = DataBaseHelper.getDataBase(context).delete(MessageTable.TABLE_NAME, MessageTable.MESSAGE_ID + " = ?", new String[]{ messageId + "" });
        return result != -1;
    }

    public List<MessageModel> query(Context context, int messageId) {
        Cursor cursor = DataBaseHelper.getDataBase(context).query(MessageTable.TABLE_NAME, null, MessageTable.MESSAGE_ID + " = ?", new String[]{ messageId + "" }, null, null, null);
        List<MessageModel> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            MessageModel bean = new MessageModel();
            bean.setId(cursor.getString(MessageTable.ID_MESSAGE_ID));
            bean.setContent(cursor.getString(MessageTable.ID_MESSAGE_CONTENT));
            list.add(bean);
        }
        cursor.close();
        return list;
    }

}
