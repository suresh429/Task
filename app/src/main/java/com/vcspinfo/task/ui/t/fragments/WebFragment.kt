package com.vcspinfo.task.ui.t.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.location.LocationServices
import com.vcspinfo.task.R
import com.vcspinfo.task.databinding.FragmentAddLocationBinding
import com.vcspinfo.task.databinding.FragmentWebBinding


class WebFragment : Fragment() {
    lateinit var binding: FragmentWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebBinding.inflate(inflater, container, false)
        binding.webView.loadUrl("file:///android_asset/aboutusfile.html")
       return binding.root

    }

}