package com.moli.pophelper.module.login

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import com.alibaba.android.arouter.facade.annotation.Route
import com.moli.module.framework.base.BaseMVPActivity
import com.moli.module.framework.mvp.IView
import com.moli.module.framework.mvp.MVPMessage
import com.moli.module.framework.utils.rx.RxCountDown
import com.moli.module.framework.utils.rx.clicksThrottle
import com.moli.module.framework.utils.rx.observeOnMain
import com.moli.module.model.constant.RegularConstant
import com.moli.module.widget.widget.dialog.LoadingDialog2
import com.moli.pophelper.R
import com.moli.pophelper.constant.HelperArouter
import com.moli.pophelper.constant.WebConstant
import com.moli.pophelper.utils.PageSkipUtils
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButtonDrawable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/6 03:45
 * 修改人：lijilong
 * 修改时间：2018/12/6 03:45
 * 修改备注：
 * @version
 */
@Route(path = HelperArouter.Activity.LoginActivity.PATH)
class LoginActivity : BaseMVPActivity<LoginActivityPresenter>(), IView {
    var countDisposable: Disposable? = null
    private val loadingDialog by lazy { LoadingDialog2(this) }
    var count_time = 60 * 1000L
    var isTiming = false //是否正在倒计时
    override val layoutResId: Int
        get() = R.layout.activity_login

    override fun initData(savedInstanceState: Bundle?) {
        QMUIStatusBarHelper.translucent(this)
        ivClose.clicksThrottle().subscribe { finish() }
        tvProtocol.clicksThrottle().subscribe { PageSkipUtils.skipGenderWeb(WebConstant.USER_PROTOCOL, "用户协议", true) }
        etPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!isTiming) {
                    if (s.toString().length == 11) {
                        rbGetCode.isEnabled = true
                        (rbGetCode.background as QMUIRoundButtonDrawable).setColor(
                            ContextCompat.getColor(
                                this@LoginActivity,
                                R.color.color_f93672
                            )
                        )
                        rbGetCode.setTextColor(
                            ContextCompat.getColor(
                                this@LoginActivity,
                                R.color.white
                            )
                        )
                    } else {
                        rbGetCode.isEnabled = false
                        (rbGetCode.background as QMUIRoundButtonDrawable).setColor(
                            ContextCompat.getColor(
                                this@LoginActivity,
                                R.color.color_dddddd
                            )
                        )
                        rbGetCode.setTextColor(
                            ContextCompat.getColor(
                                this@LoginActivity,
                                R.color.white
                            )
                        )
                    }
                }
                checkEnableLogin()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        etCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkEnableLogin()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        rbGetCode.clicksThrottle().subscribe {
            var phone = etPhone.text.toString()
            val errorInfo = when {
                phone.isNullOrEmpty() -> {
                    "请输入手机号"
                }
                !Pattern.compile(RegularConstant.PHONE_REGULAR).matcher(phone).matches() -> {
                    "输入的手机号格式有误"
                }
                else -> {
                    null
                }
            }
            if (errorInfo != null) {
                showMessage(errorInfo)
                return@subscribe
            }
            presenter?.getCode(phone)
        }
        tvLogin.clicksThrottle().subscribe {
            var phone = etPhone.text.toString()
            var code = etCode.text.toString()

            val errorInfo = when {
                phone.isNullOrEmpty() -> {
                    "请输入手机号"
                }
                !Pattern.compile(RegularConstant.PHONE_REGULAR).matcher(phone).matches() -> {
                    "输入的手机号格式有误"
                }
                code.isNullOrEmpty() -> {
                    "请输入验证码"
                }
                else -> {
                    null
                }
            }
            if (errorInfo != null) {
                showMessage(errorInfo)
                return@subscribe
            }

            loadingDialog.show()
            presenter?.login(phone, code)
        }
    }

    override fun createPresenter(): LoginActivityPresenter? {
        return LoginActivityPresenter(this)
    }

    override fun handleMessage(message: MVPMessage) {
        super.handleMessage(message)
        when (message.what) {
            1 -> {
                //请求验证码成功
                timeCountDown(count_time)
            }
            2 -> {
                //登录成功
                dismiss()
                finish()
            }
            3 -> {
                //登录失败
                dismiss()

            }
        }
    }

    private fun timeCountDown(time: Long) {
        isTiming = true
        countDisposable?.dispose()
        countDisposable = RxCountDown.countdown((time / 1000).toInt()).observeOnMain().subscribe {
            if (it > 0) {
                rbGetCode.isEnabled = false
                (rbGetCode.background as QMUIRoundButtonDrawable).setColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
                rbGetCode.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_f93672
                    )
                )
                rbGetCode.text = "%d秒".format(it)
            } else {
                isTiming = false
                rbGetCode.isEnabled = true
                (rbGetCode.background as QMUIRoundButtonDrawable).setColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_f93672
                    )
                )
                rbGetCode.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
                rbGetCode.text = "获取验证码"
            }
        }
    }

    override fun onBackPressed() {
        if (dismiss()) {
            return
        }
        super.onBackPressed()
    }

    fun dismiss(): Boolean {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
            return true
        }
        return false
    }

    fun checkEnableLogin() {
        if (etPhone.text.toString().length == 11 && etCode.text.toString().length >= 4) {
            tvLogin.setBackgroundResource(R.drawable.bg_login)
        } else {
            tvLogin.setBackgroundResource(R.drawable.shape_black20_radius5dp)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.bottom_out)
    }


}
