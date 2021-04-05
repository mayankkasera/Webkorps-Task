package com.mayank.webkorps_task.ui.main.list

import java.text.FieldPosition

interface OnclickCallBack {
    fun onClick(position: Int)
    fun onEdit(position: Int)
    fun onDelete(position: Int)
}