package com.example.mayoqcar.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.fragment.findNavController
import com.example.mayoqcar.R
import com.example.mayoqcar.databinding.FragmentSplashScreenBinding
import com.example.mayoqcar.models.Worker
import com.example.mayoqcar.utils.MySharedPreference

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    private val binding by lazy { FragmentSplashScreenBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        findNavController().popBackStack()

        val handler = Handler(Looper.getMainLooper())
        var runnable = Runnable {
            MySharedPreference.init(binding.root.context)
            var worker: Worker? = null
            worker = MySharedPreference.getWorker()
            if (worker.login != null && worker.parol != null) {
                findNavController().navigate(R.id.mapFragment)
            } else {
                findNavController().navigate(R.id.signInFragment)
            }
        }
        handler.postDelayed(runnable, 3000)

            val animation = AnimationUtils.loadAnimation(context, R.anim.alpha_anim)
        binding.emergenix.startAnimation(animation)
        binding.info.startAnimation(animation)
        binding.splashImage.startAnimation(animation)


        return binding.root
    }
}