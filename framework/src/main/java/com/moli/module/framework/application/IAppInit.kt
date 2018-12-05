package com.moli.module.framework.application

import android.app.Application

/**
 * 项目名称：Aletter
 * 类描述：用于统一封装初始化实现操作
 * 创建人：yuliyan
 * 创建时间：2018/7/30 下午7:50
 * 修改人：yuliyan
 * 修改时间：2018/7/30 下午7:50
 * 修改备注：
 * @version
 */
interface IAppInit {
    fun init(application: Application)
}
