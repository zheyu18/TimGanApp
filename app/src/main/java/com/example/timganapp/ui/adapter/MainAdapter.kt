package com.example.timganapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MainAdapter(var data:List<Fragment> = ArrayList(),var titles:Array<String>,fm:FragmentManager):FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = data[position]

    override fun getCount(): Int = data.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]

}