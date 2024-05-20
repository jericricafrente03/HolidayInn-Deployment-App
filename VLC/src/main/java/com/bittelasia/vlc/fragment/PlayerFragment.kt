package com.bittelasia.vlc.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bittelasia.vlc.annotation.UpdateContents
import com.bittelasia.vlc.listener.OnChangeListener
import com.bittelasia.vlc.listener.OnFragmentListener

abstract class PlayerFragment : Fragment() {

    var onChangeListener: OnChangeListener?= null
//    var onFragmentListener: OnFragmentListener?= null
    var isHasControl = false
    var isRemoved = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeObject(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onChangeListener = context as OnChangeListener
//        onFragmentListener = context as OnFragmentListener
        isRemoved = false
    }

    override fun onDetach() {
        super.onDetach()
        onChangeListener = null
//        onFragmentListener = null
    }

    @Throws(Exception::class)
    private fun initializeObject(`object`: Any) {
        val clazz: Class<*> = `object`.javaClass
        for (method in clazz.declaredMethods) {
            if (method.isAnnotationPresent(UpdateContents::class.java)) {
                method.isAccessible = true
                method.invoke(`object`)
            }
        }
    }
}