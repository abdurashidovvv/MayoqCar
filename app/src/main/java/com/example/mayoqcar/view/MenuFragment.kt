package com.example.mayoqcar.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mayoqcar.R
import com.example.mayoqcar.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {


    private val binding by lazy { FragmentMenuBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return binding.root
    }
}