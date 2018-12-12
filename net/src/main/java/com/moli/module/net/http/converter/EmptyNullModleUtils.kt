package com.moli.module.net.http.converter

import com.moli.module.model.base.VersionModel
import java.lang.reflect.Type

/**
 * 项目名称：Aletter-App
 * 类描述：接口请求注解类型空实现
 * 创建人：yuliyan
 * 创建时间：2018/8/24 上午11:07
 * 修改人：yuliyan
 * 修改时间：2018/8/24 上午11:07
 * 修改备注：
 * @version
 */
object EmptyNullModleUtils {

    fun getEmptyObject(type: Type): Any {
        return when (type) {
            String::class.java -> String()
            Any::class.java -> Any()
            Boolean::class.java -> false
            Int::class.java -> -1
            Long::class.java -> -1L
            VersionModel::class.java -> VersionModel()
            else -> {

            }
        }

    }
}
