package com.moli.module.widget.adapter.util

/**
 * 项目名称：Reward-App
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2018/9/15 下午6:08
 * 修改人：yuliyan
 * 修改时间：2018/9/15 下午6:08
 * 修改备注：
 * @version
 */
interface FragmentLifecycle {

      fun onFragmentPause()
      fun onFragmentResume()
      fun onBackPressed()
      fun onActivityPause()
      fun onActivityResume()
      fun onActivityDestroy()
}
