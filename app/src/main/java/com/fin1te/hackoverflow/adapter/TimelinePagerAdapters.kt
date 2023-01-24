package com.fin1te.hackoverflow.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TimelinePagerAdapters(sFM: FragmentManager)  :
    FragmentPagerAdapter(sFM, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    /*set fragment Lists*/
    private val pFragmentList = ArrayList<Fragment>()
    private val pFragmentTitle=ArrayList<String>()
    override fun getCount(): Int = pFragmentList.size
    override fun getItem(position: Int): Fragment = pFragmentList[position]
    override fun getPageTitle(position: Int): CharSequence = pFragmentTitle[position]
    fun addFragment(fm:Fragment,title:String){
        pFragmentList.add(fm)
        pFragmentTitle.add(title)
    }
}