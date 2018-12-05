package com.moli.module.widget.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import java.util.regex.Pattern

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/9/21 下午3:43
 * 修改人：yuliyan
 * 修改时间：2018/9/21 下午3:43
 * 修改备注：
 * @version
 */
object TextViewUtils {
    /**
     *  @param str1:关键字数组
     *  @param str2:高亮处理的字符串
     *  @param color:高亮字体颜色（"#0000000"）
     */
    fun setTextHighLights(str1: List<String>?, str2: String, color: String): SpannableString {
        val sp = SpannableString(str2)
        // 正则匹配
        if (str1 != null && str1.isNotEmpty()) {
            for (i in str1.indices) {
                val p = Pattern.compile(str1[i], Pattern.CASE_INSENSITIVE)
                val m = p.matcher(sp)
                // 查找下一个
                while (m.find()) {
                    // 字符开始下标
                    val start = m.start()
                    // 字符结束下标
                    val end = m.end()
                    // 设置高亮
                    sp.setSpan(ForegroundColorSpan(Color.parseColor(color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }

        return sp
    }

    fun setTextHightLogjts(str1: String, str2: String, color: String): SpannableString {
        val list = mutableListOf<String>()
        list.add(str1)
        return setTextHighLights(list, str2, color)
    }


}
