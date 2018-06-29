package com.soaic.toolsapp.ui.activity.check

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.soaic.libcommon.utils.ToastUtils
import com.soaic.toolsapp.R
import com.soaic.toolsapp.ui.activity.base.BasicActivity
import com.soaic.toolsapp.util.CheckUtil

class CheckActivity : BasicActivity() {
    private lateinit var checkBtn: Button
    private lateinit var checkCompany: EditText
    override val contentView: Int
        get() = R.layout.activity_check

    override fun initVariables(savedInstanceState: Bundle?) {

    }

    override fun initViews() {
        checkBtn = findViewById(R.id.check)
        checkCompany = findViewById(R.id.company_name)
    }

    override fun initEvents() {
        checkBtn.setOnClickListener {
            val company = checkCompany.text.toString()
            val cmp = CheckUtil().checkCompany(applicationContext, company)
            if (TextUtils.isEmpty(cmp)) {
                ToastUtils.showLongToast(applicationContext, "不是培训机构公司")
            } else {
                ToastUtils.showLongToast(applicationContext, "找到培训机构公司：$cmp")
            }
        }
    }

    override fun loadData() {

    }
}