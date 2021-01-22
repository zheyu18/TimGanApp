/*
 * Created by 动脑科技-Tim on 17-6-20 下午10:37
 * Copyright (c) 2017. All rights reserved
 *
 * Last modified 17-6-20 下午10:37
 */

package com.example.timganapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class MainAdapter (var data:List<Fragment> = ArrayList(), var titles: Array<String>, fm: FragmentManager):
FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return data[position]
    }

    override fun getCount(): Int {

        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }


}