package com.wyt.common.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * 软键盘管理类
 */
object KeybordUtil {

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    fun showSoftInput(context: Context, edit: EditText) {
        edit.isFocusable = true
        edit.isFocusableInTouchMode = true
        edit.requestFocus()
        val inputManager = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(edit, 0)
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    fun toggleSoftInput(context: Context, edit: EditText) {
        edit.isFocusable = true
        edit.isFocusableInTouchMode = true
        edit.requestFocus()
        val inputManager = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
    fun hideSoftInput(edit:EditText){
        val imm: InputMethodManager = edit?.context?.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(
                edit.applicationWindowToken, 0
            )
        }

    }

}