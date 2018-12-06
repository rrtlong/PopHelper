package com.moli.pophelper.module.home

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.blankj.utilcode.util.Utils
import com.moli.module.framework.mvp.BasePresenter
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.utils.FileDirUtils
import com.moli.module.framework.utils.writeFileFromIS
import com.moli.module.net.http.api.APIDownload
import com.moli.module.net.http.provider.APIService
import com.moli.pophelper.MainActivity
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.IOException

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/5 02:37
 * 修改人：lijilong
 * 修改时间：2018/12/5 02:37
 * 修改备注：
 * @version
 */
class MainActivityPresenter(iView: IView) : BasePresenter<IView>(iView) {
    @Autowired
    lateinit var api: APIService


}
