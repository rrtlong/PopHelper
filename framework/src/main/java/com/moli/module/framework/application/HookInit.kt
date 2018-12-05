package com.moli.module.framework.application

import android.app.Application
import android.os.Process
import com.moli.module.framework.utils.CheckHook
import com.moli.module.framework.utils.EmulatorDetector
import com.moli.module.widget.widget.longToast
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * 项目名称：Aletter
 * 类描述：防止虚拟机盗刷app
 * 创建人：yuliyan
 * 创建时间：2018/7/30 下午7:55
 * 修改人：yuliyan
 * 修改时间：2018/7/30 下午7:55
 * 修改备注：
 * @version
 */
class HookInit : IAppInit {

    override fun init(application: Application) {
        if (EmulatorDetector.isEmulator()) {
            application.baseContext.longToast("请勿使用模拟器运行本应用")
            delaykillProcess()
        } else if (CheckHook.isHook(application)) {
            application.baseContext.longToast("请勿在安装了 xposed 的手机上使用本应用")
            delaykillProcess()
        }

    }

    private fun delaykillProcess() {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(object : Observer<Long> {

            override fun onComplete() {
            }

            override fun onError(e: Throwable) {
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: Long) {
                Process.killProcess(Process.myPid())
            }

        }
        )
    }
}


