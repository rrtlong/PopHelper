package com.moli.module.net.manager

import com.blankj.utilcode.util.SPUtils
import com.moli.module.framework.utils.rx.subscribeOnIo
import com.moli.module.model.base.UserInfo
import com.moli.module.model.constant.EventConstant
import com.moli.module.model.constant.SPConstant
import com.moli.module.model.objectbox.BoxStoreUtils
import com.moli.module.net.http.ApiException
import io.objectbox.rx.RxQuery
import io.reactivex.Observable
import io.reactivex.Single
import org.simple.eventbus.EventBus
import timber.log.Timber

/**
 * 项目名称：Aletter-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/8/13 下午6:43
 * 修改人：yuliyan
 * 修改时间：2018/8/13 下午6:43
 * 修改备注：
 * @version
 */
object UserManager {

    private var selfUser: UserInfo? = null


    fun getSelf(): Single<UserInfo> {
        synchronized(UserManager::class) {
            if (selfUser != null) {
                return Single.just(selfUser!!).subscribeOnIo()
            }
        }
        return Single.unsafeCreate<UserInfo> {
            getSynSelf()
            if (selfUser != null) {
                it.onSuccess(selfUser)
            } else {
                it.onError(ApiException(-100, "selfUser is null"))
            }
        }.subscribeOnIo()

    }


    fun getSynSelf(): UserInfo? {
        if (selfUser != null) {
            return selfUser
        }
        val userId = SPUtils.getInstance().getLong(SPConstant.USER_ID, -1)
        if (userId == -1L)
            return null
        selfUser = BoxStoreUtils.getUserInfoBox()?.get(userId)
        selfUser?.let {
            initialization(it)
        }
        return selfUser
    }


    /**
     * 初始化用户相关的数据
     */
    private fun initialization(userInfo: UserInfo) {
        Timber.i("initialization")
//        CountUtils.onProfileSignIn(userInfo.name ?: "匿名", userInfo.id)
    }


    /**
     * 在调用这个方式的时候必须确保login(userId)方法调用过
     *  刷新当前登录用户的用户信息
     */
    fun refreshUserInfo(userInfo: UserInfo, hasAccessToken: Boolean) {
        getSynSelf()
        selfUser = userInfo
        var userId = SPUtils.getInstance().getLong(SPConstant.USER_ID, -1)
        if (userId != userInfo.id) {
            if (userInfo.id >= 0) {
                userId = userInfo.id
                SPUtils.getInstance().put(SPConstant.USER_ID, userId)
            }
        }
        if (userId > 0) {
            val box = BoxStoreUtils.getUserInfoBox() ?: return
            box.put(userInfo)
        }
    }

    /**
     * 监听用户信息变化数据
     */
    fun getUserInfoObservable(): Observable<List<UserInfo>> {
        val box = BoxStoreUtils.getUserInfoBox() ?: return Observable.empty()
        val query = box.query().build()
        return RxQuery.observable(query)
    }


    fun isLogin(): Boolean {
        val userId = SPUtils.getInstance().getLong(SPConstant.USER_ID, -1)
        return userId >= 0
    }

    fun isBind(): Boolean {
        val userId = SPUtils.getInstance().getLong(SPConstant.USER_ID, -1)
        return userId != 100000L
    }

    fun logout() {
        val userId = SPUtils.getInstance().getLong(SPConstant.USER_ID, -1)
        if (userId >= 0) {
            val box = BoxStoreUtils.getUserInfoBox() ?: return
            box.remove(userId)
            selfUser = null
            SPUtils.getInstance().remove(SPConstant.USER_ID)
            SPUtils.getInstance().remove(SPConstant.LOGIN_PHONE)
            EventBus.getDefault().post("", EventConstant.USER_LOGOUT)
        }
    }

    fun clearBeforLoginSuccessData() {
        SPUtils.getInstance().remove(SPConstant.SETTING_PASSWORD_VERIFICATION_REQUEST_PHONE)
        SPUtils.getInstance().remove(SPConstant.SETTING_PASSWORD_VERIFICATION_REQUEST_TIME)
        SPUtils.getInstance().remove(SPConstant.BIND_VERIFICATION_REQUEST_PHONE)
        SPUtils.getInstance().remove(SPConstant.BIND_VERIFICATION_REQUEST_TIME)
        SPUtils.getInstance().remove(SPConstant.FIND_BACK_PASSWORD_REQUEST_TIME)
        SPUtils.getInstance().remove(SPConstant.FIND_BACK_PASSWORD_REQUEST_PHONE)

    }

}
