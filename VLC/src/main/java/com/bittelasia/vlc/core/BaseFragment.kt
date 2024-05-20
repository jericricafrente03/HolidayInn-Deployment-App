package com.bittelasia.vlc.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment<F : ViewDataBinding> : Fragment() {
    private var _binding : F?=null
    val binding : F get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        addContents()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
    abstract fun getLayout() : Int
    open fun addContents(){}
}