package com.example.timganapp.util

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.timganapp.ui.fragment.ProgressFragment
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

fun Activity.toast(msg: String) {
    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showProgress() {
    val dialog = ProgressFragment.newInstance()
    dialog.show(this.supportFragmentManager, ProgressFragment::class.java.simpleName)
}

fun AppCompatActivity.dimssProgress() {
    (supportFragmentManager.findFragmentByTag(ProgressFragment::class.java.simpleName) as ProgressFragment).dismiss()
}

fun <T : Fragment> FragmentManager.showFragment(
    className: KClass<T>,
    layoutId: Int,
    fragmentTransaction: FragmentTransaction
): T? {
    var willShowFragment = this.findFragmentByTag(className.simpleName)
    if (willShowFragment == null) {
        willShowFragment = className.createInstance()
        fragmentTransaction.add(layoutId, willShowFragment, className.simpleName)

    } else {
        fragmentTransaction.show(willShowFragment)
    }
    return willShowFragment as T?

}


