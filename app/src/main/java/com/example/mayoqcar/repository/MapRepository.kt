package com.example.mayoqcar.repository

import android.util.Log
import com.example.mayoqcar.models.Call
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow

class MapRepository {

    private val TAG = "MapRepository"
    private val databaseReference =
        FirebaseDatabase.getInstance().reference.child("emergency_calls")
    private val mutableStateFlow = MutableStateFlow<List<Call>>(emptyList())

    suspend fun getAllCalls(): MutableStateFlow<List<Call>> {

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val calls = ArrayList<Call>()
                for (child in snapshot.children) {
                    val call = child.getValue(Call::class.java)
                    call?.let { calls.add(call) }
                }
                mutableStateFlow.value = calls
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: ${error.message}")
            }
        })

        return mutableStateFlow
    }

    suspend fun addCall(call: Call) {
        val callId = databaseReference.push().key ?: return
        databaseReference.child(callId).setValue(call)
    }
}