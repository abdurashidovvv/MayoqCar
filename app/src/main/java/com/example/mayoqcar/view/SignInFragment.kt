package com.example.mayoqcar.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mayoqcar.R
import com.example.mayoqcar.databinding.FragmentSignInBinding
import com.example.mayoqcar.models.Worker
import com.example.mayoqcar.repository.RegisterRepository
import com.example.mayoqcar.utils.MySharedPreference
import com.example.mayoqcar.viewmodels.RegisterViewModel
import com.example.mayoqcar.viewmodels.RegisterViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignInFragment : Fragment(), CoroutineScope {

    private val binding by lazy { FragmentSignInBinding.inflate(layoutInflater) }
    private val TAG = "SignInFragment"
    private lateinit var registerRepository: RegisterRepository
    private lateinit var registerViewModelFactory: RegisterViewModelFactory
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        registerRepository = RegisterRepository()
        registerViewModelFactory = RegisterViewModelFactory(registerRepository)
        registerViewModel =
            ViewModelProvider(this, registerViewModelFactory)[RegisterViewModel::class.java]


        if (MySharedPreference.getWorker().fullName != null) {
            findNavController().popBackStack()
        }

        val list = ArrayList<Worker>()
        launch {
            registerViewModel.getAllWorkers().collectLatest {
                list.addAll(it)
                Log.d(TAG, "onCreateView: $list")
            }
        }

        binding.nextBtn.setOnClickListener {
            for (user in list) {
                if (user.login == binding.edtLogin.text.toString() && user.parol == binding.edtPassword.text.toString()) {
                    MySharedPreference.init(binding.root.context)
                    MySharedPreference.setUser(user)
                    findNavController().navigate(R.id.menuFragment)
                }
            }
        }

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
        return binding.root
    }


    override val coroutineContext: CoroutineContext
        get() = Job()
}