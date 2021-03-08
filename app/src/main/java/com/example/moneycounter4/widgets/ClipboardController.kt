package com.example.moneycounter4.widgets

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.example.moneycounter4.extensions.toast

/**
 *@author zhangzhe
 *@date 2021/1/3
 *@description
 */

object ClipboardController {
    fun copyText(context: Context, content: String) {
        val cm: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText("", content)
        cm.setPrimaryClip(mClipData)
        context.toast("已复制到剪切板")
    }
}