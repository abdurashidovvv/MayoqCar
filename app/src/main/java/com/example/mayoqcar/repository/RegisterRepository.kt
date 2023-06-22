package com.example.mayoqcar.repository

import android.util.Log
import com.example.mayoqcar.models.Worker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

class RegisterRepository {

    private val databaseReference = FirebaseDatabase.getInstance().reference.child("workers")

    private val mutableStateFlow = MutableStateFlow<List<Worker>>(emptyList())

    suspend fun getAllWorkers(): MutableStateFlow<List<Worker>> {

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = ArrayList<Worker>()
                for (child in snapshot.children) {
                    val user = child.getValue(Worker::class.java)
                    user?.let { users.add(it) }
                }
                mutableStateFlow.value = users
                Log.d("onDataChange", "onDataChange: ${mutableStateFlow.value}")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("SignInRepository", "onCancelled: ${error.message}")
            }
        })
        return mutableStateFlow
    }

    suspend fun addWorker(worker: Worker){
        databaseReference.child(worker.id.toString()).setValue(worker)
    }
}