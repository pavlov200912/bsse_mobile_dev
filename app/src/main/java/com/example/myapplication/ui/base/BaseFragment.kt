package com.example.myapplication.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.BuildConfig
import com.example.myapplication.logFragmentHierarchy
import timber.log.Timber

open class BaseFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onStart() {
        super.onStart()
        if (BuildConfig.DEBUG) {
            val logTag = "NavigationInfo"
            logFragmentHierarchy(logTag)
            try {
                findNavController().logBackstack(logTag)
            } catch (error: IllegalStateException) {
                Timber.e(error)
            }
        }
    }
}

fun NavController.logBackstack(tag: String? = null) {
    val timber =
        if (tag == null) Timber.asTree()
        else Timber.tag(tag)
    timber.d(backQueue.joinToString(" -> ", "Backstack: ") { it.destination.displayName })
}