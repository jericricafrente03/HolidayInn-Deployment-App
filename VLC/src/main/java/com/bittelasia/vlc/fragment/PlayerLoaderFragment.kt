package com.bittelasia.vlc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bittelasia.vlc.databinding.LoadingLayoutBinding

abstract class PlayerLoaderFragment : PlayerFragment() {

    private var _binding: LoadingLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoadingLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnKeyListener(null)
        isRemoved = false
    }

    override fun onDetach() {
        super.onDetach()
//        onFragmentListener?.onFragmentDetached(this)
//        onFragmentListener = null
        isRemoved = true
    }
    fun hideProgress(hide: Boolean) {
        binding.progressVisibility = hide
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}