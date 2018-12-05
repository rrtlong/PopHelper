package com.moli.module.framework.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.IntDef
import android.support.annotation.IntRange
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import com.moli.module.framework.R
import com.qmuiteam.qmui.util.QMUIDeviceHelper

/**
 * 项目名称：Platformer
 * 类描述：
 * 创建人：liujun
 * 创建时间：2018/1/4 12:24
 * 修改人：liujun
 * 修改时间：2018/1/4 12:24
 * 修改备注：
 * @version
 */
object StatusBarUtils {
    private const val STATUSBAR_TYPE_DEFAULT = 0
    private const val STATUSBAR_TYPE_MIUI = 1
    private const val STATUSBAR_TYPE_FLYME = 2
    private const val STATUSBAR_TYPE_ANDROID6 = 3 // Android 6.0

    const val DEFAULT_STATUS_BAR_ALPHA = 112
    private val FAKE_STATUS_BAR_VIEW_ID = R.id.statusbarutil_fake_status_bar_view
    private val FAKE_TRANSLUCENT_VIEW_ID = R.id.statusbarutil_translucent_view
    private const val TAG_KEY_HAVE_SET_OFFSET = -123

    @StatusBarType
    private var statusBarType = STATUSBAR_TYPE_DEFAULT

    /**
     * 设置状态栏颜色
     *
     * @param window         需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    fun setColor(fragment: Fragment, window: Window, color: Int, statusBarAlpha: Int) {
        if (fragment.activity == null) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = calculateStatusColor(color, statusBarAlpha)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 生成一个状态栏大小的矩形
            val statusView = createStatusBarView(fragment.activity!!, color, statusBarAlpha)
            // 添加 statusView 到布局中
            val decorView = window.decorView as ViewGroup
            decorView.addView(statusView)
            setRootView(fragment)
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    fun setColor(activity: Activity, @ColorInt color: Int) {
        setColor(activity, color, DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */

    fun setColor(activity: Activity, @ColorInt color: Int, @IntRange(from = 0,
            to = 255) statusBarAlpha: Int) {
        val window = activity.window ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = calculateStatusColor(color, statusBarAlpha)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val decorView = window.decorView as ViewGroup
            val fakeStatusBarView = decorView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
            } else {
                decorView.addView(createStatusBarView(activity, color, statusBarAlpha))
            }
            setRootView(activity)
        }
    }

    /**
     * 为滑动返回界面设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    fun setColorForSwipeBack(activity: Activity, color: Int) {
        setColorForSwipeBack(activity, color, DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 为滑动返回界面设置状态栏颜色
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    fun setColorForSwipeBack(activity: Activity, @ColorInt color: Int, @IntRange(from = 0,
            to = 255) statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            val contentView = activity.findViewById(android.R.id.content) as ViewGroup
            val rootView = contentView.getChildAt(0)
            val statusBarHeight = getStatusBarHeight(activity)
            if (rootView != null && rootView is CoordinatorLayout) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    rootView.setFitsSystemWindows(false)
                    contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
                    val isNeedRequestLayout = contentView.paddingTop < statusBarHeight
                    if (isNeedRequestLayout) {
                        contentView.setPadding(0, statusBarHeight, 0, 0)
                        rootView.post({ rootView.requestLayout() })
                    }
                } else {
                    rootView.setStatusBarBackgroundColor(
                            calculateStatusColor(color, statusBarAlpha))
                }
            } else {
                contentView.setPadding(0, statusBarHeight, 0, 0)
                contentView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha))
            }
            setTransparentForWindow(activity.window)
        }
    }

    /**
     * 设置状态栏纯色 不加半透明效果
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    fun setColorNoTranslucent(activity: Activity, @ColorInt color: Int) {
        setColor(activity, color, 0)
    }

    /**
     * 设置状态栏颜色(5.0以下无半透明效果,不建议使用)
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     */
    @Deprecated("")
    fun setColorDiff(activity: Activity, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        transparentStatusBar(activity)
        val contentView = activity.findViewById(android.R.id.content) as ViewGroup
        // 移除半透明矩形,以免叠加
        val fakeStatusBarView = contentView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.visibility == View.GONE) {
                fakeStatusBarView.visibility = View.VISIBLE
            }
            fakeStatusBarView.setBackgroundColor(color)
        } else {
            contentView.addView(createStatusBarView(activity, color))
        }
        setRootView(activity)
    }

    /**
     * 使状态栏半透明
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    fun setTranslucent(activity: Activity) {
        setTranslucent(activity, DEFAULT_STATUS_BAR_ALPHA)
    }

    fun setTranslucent(fragment: Fragment, window: Window) {
        setTranslucent(fragment, window, DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 使状态栏半透明
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     */
    fun setTranslucent(activity: Activity, @android.support.annotation.IntRange(from = 0,
            to = 255) statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparent(activity)
        addTranslucentView(activity, statusBarAlpha)
    }

    fun setTranslucent(fragment: Fragment, window: Window, @android.support.annotation.IntRange(from = 0,
            to = 255) statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparent(fragment, window)
        addTranslucentView(window.context, window, statusBarAlpha)
    }

    /**
     * 针对根布局是 CoordinatorLayout, 使状态栏半透明
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     */
    fun setTranslucentForCoordinatorLayout(activity: Activity, @android.support.annotation.IntRange(
            from = 0, to = 255) statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        transparentStatusBar(activity)
        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    fun setTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        transparentStatusBar(activity)
        setRootView(activity)
    }

    fun setTransparent(fragment: Fragment, window: Window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        transparentStatusBar(window)
        setRootView(fragment)
    }

    /**
     * 使状态栏透明(5.0以上半透明效果,不建议使用)
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    @Deprecated("")
    fun setTranslucentDiff(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            setRootView(activity)
        }
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    fun setColorForDrawerLayout(activity: Activity,
                                drawerLayout: DrawerLayout, @ColorInt color: Int) {
        setColorForDrawerLayout(activity, drawerLayout, color, DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 为DrawerLayout 布局设置状态栏颜色,纯色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    fun setColorNoTranslucentForDrawerLayout(activity: Activity,
                                             drawerLayout: DrawerLayout, @ColorInt color: Int) {
        setColorForDrawerLayout(activity, drawerLayout, color, 0)
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity       需要设置的activity
     * @param drawerLayout   DrawerLayout
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    fun setColorForDrawerLayout(activity: Activity,
                                drawerLayout: DrawerLayout, @ColorInt color: Int,
                                @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        // 生成一个状态栏大小的矩形
        // 添加 statusBarView 到布局中
        val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
        val fakeStatusBarView = contentLayout.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.visibility == View.GONE) {
                fakeStatusBarView.visibility = View.VISIBLE
            }
            fakeStatusBarView.setBackgroundColor(color)
        } else {
            contentLayout.addView(createStatusBarView(activity, color), 0)
        }
        // 内容布局不是 LinearLayout 时,设置padding top
        if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1)
                    .setPadding(contentLayout.paddingLeft,
                            getStatusBarHeight(activity) + contentLayout.paddingTop,
                            contentLayout.paddingRight, contentLayout.paddingBottom)
        }
        // 设置属性
        setDrawerLayoutProperty(drawerLayout, contentLayout)
        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 设置 DrawerLayout 属性
     *
     * @param drawerLayout              DrawerLayout
     * @param drawerLayoutContentLayout DrawerLayout 的内容布局
     */
    private fun setDrawerLayoutProperty(drawerLayout: DrawerLayout,
                                        drawerLayoutContentLayout: ViewGroup) {
        val drawer = drawerLayout.getChildAt(1) as ViewGroup
        drawerLayout.fitsSystemWindows = false
        drawerLayoutContentLayout.fitsSystemWindows = false
        drawerLayoutContentLayout.clipToPadding = true
        drawer.fitsSystemWindows = false
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色(5.0以下无半透明效果,不建议使用)
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    @Deprecated("")
    fun setColorForDrawerLayoutDiff(activity: Activity,
                                    drawerLayout: DrawerLayout, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 生成一个状态栏大小的矩形
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            val fakeStatusBarView = contentLayout.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(
                        calculateStatusColor(color, DEFAULT_STATUS_BAR_ALPHA))
            } else {
                // 添加 statusBarView 到布局中
                contentLayout.addView(createStatusBarView(activity, color), 0)
            }
            // 内容布局不是 LinearLayout 时,设置padding top
            if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0)
            }
            // 设置属性
            setDrawerLayoutProperty(drawerLayout, contentLayout)
        }
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    fun setTranslucentForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout) {
        setTranslucentForDrawerLayout(activity, drawerLayout, DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    fun setTranslucentForDrawerLayout(
            activity: Activity,
            drawerLayout: DrawerLayout,
            @IntRange(from = 0, to = 255) statusBarAlpha: Int
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparentForDrawerLayout(activity, drawerLayout)
        addTranslucentView(activity, statusBarAlpha)
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    fun setTransparentForDrawerLayout(activity: Activity, drawerLayout: DrawerLayout) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
        // 内容布局不是 LinearLayout 时,设置padding top
        if (contentLayout !is LinearLayout && contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0)
        }

        // 设置属性
        setDrawerLayoutProperty(drawerLayout, contentLayout)
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明(5.0以上半透明效果,不建议使用)
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    @Deprecated("")
    fun setTranslucentForDrawerLayoutDiff(activity: Activity, drawerLayout: DrawerLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 设置内容布局属性
            val contentLayout = drawerLayout.getChildAt(0) as ViewGroup
            contentLayout.fitsSystemWindows = true
            contentLayout.clipToPadding = true
            // 设置抽屉布局属性
            val vg = drawerLayout.getChildAt(1) as ViewGroup
            vg.fitsSystemWindows = false
            // 设置 DrawerLayout 属性
            drawerLayout.fitsSystemWindows = false
        }
    }

    /**
     * 为头部是 ImageView 的界面设置状态栏全透明
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTransparentForImageView(activity: Activity, needOffsetView: View?) {
        setTranslucentForImageView(activity, 0, needOffsetView)
    }

    /**
     * 为头部是 ImageView 的界面设置状态栏全透明
     *
     * @param window       需要设置的window
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTransparentForImageView(window: Window, needOffsetView: View?) {
        setTranslucentForImageView(window, 0, needOffsetView)
    }

    /**
     * 为头部是 ImageView 的界面设置状态栏透明(使用默认透明度)
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTranslucentForImageView(activity: Activity, needOffsetView: View?) {
        setTranslucentForImageView(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView)
    }

    /**
     * 为头部是 ImageView 的界面设置状态栏透明(使用默认透明度)
     *
     * @param window       需要设置的window
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTranslucentForImageView(window: Window, needOffsetView: View?) {
        setTranslucentForImageView(window, DEFAULT_STATUS_BAR_ALPHA, needOffsetView)
    }

    /**
     * 为头部是 ImageView 的界面设置状态栏透明
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTranslucentForImageView(
            activity: Activity,
            @IntRange(from = 0, to = 255) statusBarAlpha: Int,
            needOffsetView: View?
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparentForWindow(activity.window)
        addTranslucentView(activity, statusBarAlpha)
        if (needOffsetView != null) {
            val haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET)
            if (haveSetOffset != null && haveSetOffset == true) {
                return
            }
            val layoutParams = needOffsetView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(layoutParams.leftMargin,
                    layoutParams.topMargin + getStatusBarHeight(activity),
                    layoutParams.rightMargin, layoutParams.bottomMargin)
            needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true)
        }
    }

    /**
     * 为头部是 ImageView 的界面设置状态栏透明
     *
     * @param window       需要设置的window
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTranslucentForImageView(
            window: Window,
            @IntRange(from = 0, to = 255) statusBarAlpha: Int,
            needOffsetView: View?
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return
        }
        setTransparentForWindow(window)
        addTranslucentView(window.context, window, statusBarAlpha)
        if (needOffsetView != null) {
            val haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET)
            if (haveSetOffset != null && haveSetOffset == true) {
                return
            }
            val layoutParams = needOffsetView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(layoutParams.leftMargin,
                    layoutParams.topMargin + getStatusBarHeight(window.context),
                    layoutParams.rightMargin, layoutParams.bottomMargin)
            needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true)
        }
    }

    /**
     * 为 fragment 头部是 ImageView 的设置状态栏透明
     *
     * @param activity       fragment 对应的 activity
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTranslucentForImageViewInFragment(activity: Activity, needOffsetView: View) {
        setTranslucentForImageViewInFragment(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView)
    }

    /**
     * 为 fragment 头部是 ImageView 的设置状态栏透明
     *
     * @param activity       fragment 对应的 activity
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTransparentForImageViewInFragment(activity: Activity, needOffsetView: View) {
        setTranslucentForImageViewInFragment(activity, 0, needOffsetView)
    }

    /**
     * 为 fragment 头部是 ImageView 的设置状态栏透明
     *
     * @param activity       fragment 对应的 activity
     * @param statusBarAlpha 状态栏透明度
     * @param needOffsetView 需要向下偏移的 View
     */
    fun setTranslucentForImageViewInFragment(
            activity: Activity,
            @IntRange(from = 0, to = 255) statusBarAlpha: Int,
            needOffsetView: View
    ) {
        setTranslucentForImageView(activity, statusBarAlpha, needOffsetView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            clearPreviousSetting(activity)
        }
    }

    /**
     * 隐藏伪状态栏 View
     *
     * @param activity 调用的 Activity
     */
    fun hideFakeStatusBarView(activity: Activity) {
        val decorView = activity.window.decorView as ViewGroup
        val fakeStatusBarView = decorView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if (fakeStatusBarView != null) {
            fakeStatusBarView.visibility = View.GONE
        }
        val fakeTranslucentView = decorView.findViewById<View>(FAKE_TRANSLUCENT_VIEW_ID)
        if (fakeTranslucentView != null) {
            fakeTranslucentView.visibility = View.GONE
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun clearPreviousSetting(activity: Activity) {
        val decorView = activity.window.decorView as ViewGroup
        val fakeStatusBarView = decorView.findViewById<View>(FAKE_STATUS_BAR_VIEW_ID)
        if (fakeStatusBarView != null) {
            decorView.removeView(fakeStatusBarView)
            val rootView = (activity.findViewById(android.R.id.content) as ViewGroup).getChildAt(
                    0) as ViewGroup
            rootView.setPadding(0, 0, 0, 0)
        }
    }

    /**
     * 添加半透明矩形条
     *
     * @param activity       需要设置的 activity
     * @param statusBarAlpha 透明值
     */
    private fun addTranslucentView(activity: Activity, @IntRange(from = 0,
            to = 255) statusBarAlpha: Int) {
        val contentView = activity.findViewById(android.R.id.content) as ViewGroup
        val fakeTranslucentView = contentView.findViewById<View>(FAKE_TRANSLUCENT_VIEW_ID)
        if (fakeTranslucentView != null) {
            if (fakeTranslucentView.visibility == View.GONE) {
                fakeTranslucentView.visibility = View.VISIBLE
            }
            fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0))
        } else {
            contentView.addView(createTranslucentStatusBarView(activity, statusBarAlpha))
        }
    }

    /**
     * 添加半透明矩形条
     *
     * @param window       需要设置的 window
     * @param statusBarAlpha 透明值
     */
    private fun addTranslucentView(context: Context, window: Window, @IntRange(from = 0,
            to = 255) statusBarAlpha: Int) {
        val contentView = window.findViewById(android.R.id.content) as ViewGroup
        val fakeTranslucentView = contentView.findViewById<View>(FAKE_TRANSLUCENT_VIEW_ID)
        if (fakeTranslucentView != null) {
            if (fakeTranslucentView.visibility == View.GONE) {
                fakeTranslucentView.visibility = View.VISIBLE
            }
            fakeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0))
        } else {
            contentView.addView(createTranslucentStatusBarView(context, statusBarAlpha))
        }
    }

    /**
     * 生成一个和状态栏大小相同的彩色矩形条
     *
     * @param activity 需要设置的 activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private fun createStatusBarView(activity: Activity, @ColorInt color: Int): View {
        return createStatusBarView(activity, color, 0)
    }

    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    透明值
     * @return 状态栏矩形条
     */
    private fun createStatusBarView(activity: Activity, @ColorInt color: Int, alpha: Int): View {
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = View(activity)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity))
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha))
        statusBarView.id = FAKE_STATUS_BAR_VIEW_ID
        return statusBarView
    }

    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param context context
     * @param color 状态栏颜色值
     * @param alpha 透明值
     * @return 状态栏矩形条
     */
    private fun createStatusBarView(context: Context, color: Int, alpha: Int): View {
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = View(context)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(context))
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha))
        return statusBarView
    }

    /**
     * 设置根布局参数
     */
    private fun setRootView(activity: Activity) {
        val parent = activity.findViewById(android.R.id.content) as ViewGroup
        var i = 0
        val count = parent.childCount
        while (i < count) {
            val childView = parent.getChildAt(i)
            if (childView is ViewGroup) {
                childView.setFitsSystemWindows(true)
                childView.clipToPadding = true
            }
            i++
        }
    }

    /**
     * 设置根布局参数
     */
    private fun setRootView(fragment: Fragment) {
        val view = fragment.view
        if (view != null) {
            view.fitsSystemWindows = true
            if (view is ViewGroup) {
                view.clipToPadding = true
            }
        }
    }

    /**
     * 设置透明
     */
    private fun setTransparentForWindow(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 使状态栏透明
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun transparentStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            activity.window.statusBarColor = Color.TRANSPARENT
        } else {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun transparentStatusBar(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.statusBarColor = Color.TRANSPARENT
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }

    /**
     * 创建半透明矩形 View
     *
     * @param alpha 透明值
     * @return 半透明 View
     */
    private fun createTranslucentStatusBarView(activity: Context, alpha: Int): View {
        // 绘制一个和状态栏一样高的矩形
        val statusBarView = View(activity)
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity))
        statusBarView.layoutParams = params
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
        statusBarView.id = FAKE_TRANSLUCENT_VIEW_ID
        return statusBarView
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private fun calculateStatusColor(@ColorInt color: Int, alpha: Int): Int {
        if (alpha == 0) {
            return color
        }
        val a = 1 - alpha / 255f
        var red = color shr 16 and 0xff
        var green = color shr 8 and 0xff
        var blue = color and 0xff
        red = (red * a + 0.5).toInt()
        green = (green * a + 0.5).toInt()
        blue = (blue * a + 0.5).toInt()
        return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
    }


    /**
     * 设置状态栏黑色字体图标，
     * 支持 4.4 以上版本 MIUI 和 Flyme，以及 6.0 以上版本的其他 Android
     *
     * @param activity 需要被处理的 Activity
     */
    fun setStatusBarLightMode(activity: Activity?): Boolean {
        if (activity == null) return false
        // 无语系列：ZTK C2016只能时间和电池图标变色。。。。
        if (QMUIDeviceHelper.isZTKC2016()) {
            return false
        }

        if (statusBarType != STATUSBAR_TYPE_DEFAULT) {
            return setStatusBarLightMode(activity, statusBarType)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isMIUICustomStatusBarLightModeImpl() && MIUISetStatusBarLightMode(activity.window, true)) {
                statusBarType = STATUSBAR_TYPE_MIUI
                return true
            } else if (FlymeSetStatusBarLightMode(activity.window, true)) {
                statusBarType = STATUSBAR_TYPE_FLYME
                return true
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Android6SetStatusBarLightMode(activity.window, true)
                statusBarType = STATUSBAR_TYPE_ANDROID6
                return true
            }
        }
        return false
    }

    fun setStatusBarLightMode(window: Window?): Boolean {
        if (window == null) return false
        // 无语系列：ZTK C2016只能时间和电池图标变色。。。。
        if (QMUIDeviceHelper.isZTKC2016()) {
            return false
        }

        if (statusBarType != STATUSBAR_TYPE_DEFAULT) {
            return setStatusBarLightMode(window, statusBarType)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isMIUICustomStatusBarLightModeImpl() && MIUISetStatusBarLightMode(window, true)) {
                statusBarType = STATUSBAR_TYPE_MIUI
                return true
            } else if (FlymeSetStatusBarLightMode(window, true)) {
                statusBarType = STATUSBAR_TYPE_FLYME
                return true
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Android6SetStatusBarLightMode(window, true)
                statusBarType = STATUSBAR_TYPE_ANDROID6
                return true
            }
        }
        return false
    }

    /**
     * 已知系统类型时，设置状态栏黑色字体图标。
     * 支持 4.4 以上版本 MIUI 和 Flyme，以及 6.0 以上版本的其他 Android
     *
     * @param activity 需要被处理的 Activity
     * @param type     StatusBar 类型，对应不同的系统
     */
    private fun setStatusBarLightMode(activity: Activity, @StatusBarType type: Int): Boolean {
        if (type == STATUSBAR_TYPE_MIUI) {
            return MIUISetStatusBarLightMode(activity.window, true)
        } else if (type == STATUSBAR_TYPE_FLYME) {
            return FlymeSetStatusBarLightMode(activity.window, true)
        } else if (type == STATUSBAR_TYPE_ANDROID6) {
            return Android6SetStatusBarLightMode(activity.window, true)
        }
        return false
    }

    private fun setStatusBarLightMode(window: Window, @StatusBarType type: Int): Boolean {
        if (type == STATUSBAR_TYPE_MIUI) {
            return MIUISetStatusBarLightMode(window, true)
        } else if (type == STATUSBAR_TYPE_FLYME) {
            return FlymeSetStatusBarLightMode(window, true)
        } else if (type == STATUSBAR_TYPE_ANDROID6) {
            return Android6SetStatusBarLightMode(window, true)
        }
        return false
    }


    /**
     * 设置状态栏白色字体图标
     * 支持 4.4 以上版本 MIUI 和 Flyme，以及 6.0 以上版本的其他 Android
     */
    fun setStatusBarDarkMode(activity: Activity?): Boolean {
        if (activity == null) return false
        if (statusBarType == STATUSBAR_TYPE_DEFAULT) {
            // 默认状态，不需要处理
            return true
        }

        if (statusBarType == STATUSBAR_TYPE_MIUI) {
            return MIUISetStatusBarLightMode(activity.window, false)
        } else if (statusBarType == STATUSBAR_TYPE_FLYME) {
            return FlymeSetStatusBarLightMode(activity.window, false)
        } else if (statusBarType == STATUSBAR_TYPE_ANDROID6) {
            return Android6SetStatusBarLightMode(activity.window, false)
        }
        return true
    }


    fun setStatusBarDarkMode(window: Window?): Boolean {
        if (window == null) return false
        if (statusBarType == STATUSBAR_TYPE_DEFAULT) {
            // 默认状态，不需要处理
            return true
        }

        if (statusBarType == STATUSBAR_TYPE_MIUI) {
            return MIUISetStatusBarLightMode(window, false)
        } else if (statusBarType == STATUSBAR_TYPE_FLYME) {
            return FlymeSetStatusBarLightMode(window, false)
        } else if (statusBarType == STATUSBAR_TYPE_ANDROID6) {
            return Android6SetStatusBarLightMode(window, false)
        }
        return true
    }

    /**
     * 设置状态栏字体图标为深色，Android 6
     *
     * @param window 需要设置的窗口
     * @param light  是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    @TargetApi(23)
    private fun Android6SetStatusBarLightMode(window: Window, light: Boolean): Boolean {
        val decorView = window.decorView
        var systemUi = if (light) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        systemUi = changeStatusBarModeRetainFlag(window, systemUi)
        decorView.systemUiVisibility = systemUi
        if (QMUIDeviceHelper.isMIUIV9()) {
            // MIUI 9 低于 6.0 版本依旧只能回退到以前的方案
            // https://github.com/QMUI/QMUI_Android/issues/160
            MIUISetStatusBarLightMode(window, light)
        }
        return true
    }

    @SuppressLint("PrivateApi")
    /**
     * 设置状态栏字体图标为深色，需要 MIUIV6 以上
     *
     * @param window 需要设置的窗口
     * @param light   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回 true
     */
    fun MIUISetStatusBarLightMode(window: Window?, light: Boolean): Boolean {
        var result = false
        if (window != null) {
            val clazz = window.javaClass
            try {
                val darkModeFlag: Int
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                if (light) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag) //状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag) //清除黑色字体
                }
                result = true
            } catch (ignored: Exception) {
            }

        }
        return result
    }

    /**
     * 更改状态栏图标、文字颜色的方案是否是MIUI自家的， MIUI9 && Android 6 之后用回Android原生实现
     * 见小米开发文档说明：https://dev.mi.com/console/doc/detail?pId=1159
     */
    private fun isMIUICustomStatusBarLightModeImpl(): Boolean {
        return if (QMUIDeviceHelper.isMIUIV9() && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            true
        } else QMUIDeviceHelper.isMIUIV5() || QMUIDeviceHelper.isMIUIV6() ||
                QMUIDeviceHelper.isMIUIV7() || QMUIDeviceHelper.isMIUIV8()
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为 Flyme 用户
     *
     * @param window 需要设置的窗口
     * @param light   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    fun FlymeSetStatusBarLightMode(window: Window?, light: Boolean): Boolean {
        if (window == null) return false
        // flyme 在 6.2.0.0A 支持了 Android 官方的实现方案，旧的方案失效
        Android6SetStatusBarLightMode(window, light)

        var result = false
        try {
            val lp = window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            if (light) {
                value = value or bit
            } else {
                value = value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            window.attributes = lp
            result = true
        } catch (ignored: Exception) {
        }
        return result
    }

    /**
     * 获取是否全屏
     *
     * @return 是否全屏
     */
    fun isFullScreen(activity: Activity): Boolean {
        var ret = false
        try {
            val attrs = activity.window.attributes
            ret = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN != 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret
    }

    @TargetApi(23)
    private fun changeStatusBarModeRetainFlag(window: Window, out: Int): Int {
        var flag = out
        flag = retainSystemUiFlag(window, flag, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        flag = retainSystemUiFlag(window, flag, View.SYSTEM_UI_FLAG_FULLSCREEN)
        flag = retainSystemUiFlag(window, flag, View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        flag = retainSystemUiFlag(window, flag, View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        flag = retainSystemUiFlag(window, flag, View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        flag = retainSystemUiFlag(window, flag, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        return flag
    }

    fun retainSystemUiFlag(window: Window, out: Int, type: Int): Int {
        var flag = out
        val now = window.decorView.systemUiVisibility
        if (now and type == type) {
            flag = flag or type
        }
        return flag
    }

    @IntDef(STATUSBAR_TYPE_DEFAULT, STATUSBAR_TYPE_MIUI, STATUSBAR_TYPE_FLYME,
            STATUSBAR_TYPE_ANDROID6)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    private annotation class StatusBarType
}
