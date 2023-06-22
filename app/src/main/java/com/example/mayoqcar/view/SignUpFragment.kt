package com.example.mayoqcar.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mayoqcar.R
import com.example.mayoqcar.databinding.FragmentSignUpBinding
import com.example.mayoqcar.models.Worker
import com.example.mayoqcar.repository.RegisterRepository
import com.example.mayoqcar.utils.MyData
import com.example.mayoqcar.utils.MySharedPreference
import com.example.mayoqcar.viewmodels.RegisterViewModel
import com.example.mayoqcar.viewmodels.RegisterViewModelFactory
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class SignUpFragment : Fragment(), CoroutineScope {

    private val binding by lazy { FragmentSignUpBinding.inflate(layoutInflater) }
    private lateinit var registerRepository: RegisterRepository
    private lateinit var registerViewModelFactory: RegisterViewModelFactory
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var reference: StorageReference
    private var photoUri = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        registerRepository = RegisterRepository()
        registerViewModelFactory = RegisterViewModelFactory(registerRepository)
        registerViewModel =
            ViewModelProvider(this, registerViewModelFactory)[RegisterViewModel::class.java]

        firebaseStorage = FirebaseStorage.getInstance()
        reference = firebaseStorage.getReference("user_images")


        binding.nextBtn.setOnClickListener {
            if (checkUser()) {
                signUp()
            } else {
                Toast.makeText(context, "Bunday hodim mavjud.!", Toast.LENGTH_SHORT).show()
            }
        }

        //Get image
        binding.photo.setOnClickListener {
            getImageContent.launch("image/*")
        }

        return binding.root
    }

    private fun signUp() {
        val id = UUID.randomUUID().toString()
        val login = binding.edtLogin.text.toString()
        val parol = binding.edtPassword.text.toString()
        val fullName = binding.edtName.text.toString()
        val job = binding.edtJob.text.toString()
        val jobAddress = binding.edtAddress.text.toString()
        val medicalHistory = binding.edtHistory.text.toString()
        val position = binding.edtPosition.text.toString()
        val phoneNumber = binding.edtPhoneNumber.text.toString()

        val worker = Worker(
            id = id,
            login = login,
            parol = parol,
            fullName = fullName,
            job = job,
            job_address = jobAddress,
            medical_history = medicalHistory,
            position = position,
            phoneNumber = phoneNumber,
            lat = null,
            long = null,
            car_number = "A375AA",
            imageLink = photoUri
        )


        MyData.worker = worker
        findNavController().navigate(R.id.smsCodeFragment)
    }


    private fun checkUser(): Boolean {
        val list = ArrayList<Worker>()
        launch {
            registerViewModel.getAllWorkers().collectLatest {
                list.addAll(it)
            }
        }

        val login = binding.edtLogin.text.toString()
        val password = binding.edtPassword.text.toString()
        val phoneNumber = binding.edtPhoneNumber.text.toString()
        for (worker in list) {
            return !(worker.login == login && worker.parol == password && worker.id == phoneNumber)
        }
        return true
    }

    //GetImage Content
    private var getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri ?: return@registerForActivityResult

            binding.photo.text = uri.path
            val uuid = UUID.randomUUID()
            val uploadTask = reference.child(uuid.toString()).putFile(uri)

            binding.progressBar.visibility = View.VISIBLE
            uploadTask.addOnSuccessListener {
                if (it.task.isSuccessful) {
                    val downloadUrl = it.metadata?.reference?.downloadUrl
                    downloadUrl?.addOnSuccessListener { imageUri ->
                        photoUri = imageUri.toString()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }.addOnFailureListener {
                binding.progressBar.visibility = View.GONE
            }
        }

    override val coroutineContext: CoroutineContext
        get() = Job()
}