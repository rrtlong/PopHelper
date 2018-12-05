package com.moli.module.widget.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup
import com.moli.module.widget.adapter.util.FragmentLifecycle

import java.util.ArrayList

/**
 * 所有tab的adapter
 */

class TabAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var mCurrentFragment: FragmentLifecycle? = null
    private val fragments = ArrayList<Fragment>()
    private val titles = ArrayList<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }

    fun addFragment(fragment: Fragment, title: String, index: Int) {
        fragments.add(index, fragment)
        titles.add(index, title)
    }

    fun remove(index: Int) {
        if (index >= 0 && index < fragments.size) {
            fragments.removeAt(index)
            titles.removeAt(index)
        }
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {

        if (`object` is FragmentLifecycle) {
            if (getCurrentFragment() != `object`) {
                if (mCurrentFragment != null) {
                    mCurrentFragment!!.onFragmentPause()
                }
                mCurrentFragment = `object`
                mCurrentFragment?.onFragmentResume()
            }
        }
        super.setPrimaryItem(container, position, `object`)
    }

    fun getCurrentFragment(): FragmentLifecycle? {
        return mCurrentFragment
    }


    fun pauseAllFragment() {
        fragments.forEach {
            if (it is FragmentLifecycle) {
                it.onFragmentPause()
            }
        }
    }

    fun pauseOtherFragments(indexCurrent: Int){
        fragments.forEachIndexed { index, fragment ->
            if(index!=indexCurrent&&fragment is FragmentLifecycle){
                fragment.onFragmentPause()
            }
        }
    }

}
