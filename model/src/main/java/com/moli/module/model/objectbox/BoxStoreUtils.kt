package com.moli.module.model.objectbox

import android.util.SparseArray
import com.moli.module.model.base.MyObjectBox
import com.moli.module.model.base.UserInfo
import com.moli.module.model.constant.SPConstant
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import io.objectbox.Box
import io.objectbox.BoxStore
import org.jetbrains.anko.collections.forEach
import timber.log.Timber

/**
 * 项目名称：Aletter-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/8/2 下午8:26
 * 修改人：yuliyan
 * 修改时间：2018/8/2 下午8:26
 * 修改备注：
 * @version
 */
object BoxStoreUtils {

    private val boxStores = SparseArray<BoxStore>()


    fun getBoxStore(): BoxStore? {
        val userId = SPUtils.getInstance().getLong(SPConstant.USER_ID, 0)
        val storeName = if (userId == 0L) "visitor" else userId.toString()
        val boxStore = boxStores[userId.toInt()]

        return if (boxStore == null) {
            boxStores.forEach { _, box ->
                box.closeThreadResources()
            }
            var store: BoxStore? = null
            try {
                store = MyObjectBox.builder().androidContext(Utils.getApp()).name(storeName).build()
                boxStores.put(userId.toInt(),store)
            } catch (e: Exception) {
                Timber.e(e)
            }
            store
        } else {
            boxStore
        }
    }


    inline fun <reified T> getBox(): Box<T>? {
        return getBoxStore()?.boxFor(T::class.java)
    }

    fun <T> getEntityBox(clazz: Class<T>): Box<T>?{
        return getBoxStore()?.boxFor(clazz)
    }


    fun getUserInfoBox():Box<UserInfo>?{
        return getBox()
    }


}
