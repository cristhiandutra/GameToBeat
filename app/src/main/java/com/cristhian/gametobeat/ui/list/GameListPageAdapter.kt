package com.cristhian.gametobeat.ui.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*

class GameListPageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val fragmentList: ArrayList<GameListFragment> = arrayListOf()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentList[position].getTitle()
    }

    fun addFragment(fragment: GameListFragment) {
        fragmentList?.add(fragment)
    }
}