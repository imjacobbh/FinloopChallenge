package com.jacob.finloopchallenge.activities

import android.annotation.SuppressLint

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacob.finloopchallenge.AppConstants.RESULT_CODE
import com.jacob.finloopchallenge.AppConstants.USER_ID_SELECTED
import com.jacob.finloopchallenge.adapters.UserAdapter
import com.jacob.finloopchallenge.data.APIService
import com.jacob.finloopchallenge.data.User
import com.jacob.finloopchallenge.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.jacob.finloopchallenge.FinloopChallengeApplication.Companion.connectionState
import com.jacob.finloopchallenge.showToast


class MainActivity : AppCompatActivity() {

    @SuppressLint("NotifyDataSetChanged")
    private val secondLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_CODE) {
                val data: Intent? = it.data
                val id: Int = data!!.getIntExtra(USER_ID_SELECTED, -1)
                if (id != -1) {
                    Log.d("tag personal", "cambiando datos $id")
                    adapter.notifyItemChanged(id - 1, null)

                }
            }
        }
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        binding.recyclerVList.layoutManager = LinearLayoutManager(this)
        binding.recyclerVList.adapter = UserAdapter(emptyList(),secondLauncher)
        setContentView(binding.root)
        getUsersFromServer()
        supportActionBar!!.title = "Users"
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://61959c9274c1bd00176c6df0.mockapi.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun getUsersFromServer() {
        if (!connectionState()) {
            showToast("Ha ocurrido un error",Toast.LENGTH_LONG)
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getUsersList("users")
            val users = call.body()
            runOnUiThread {
                if (call.isSuccessful && users != null) {
                    val usersList = mutableListOf<User>()
                    usersList.clear()
                    users.toCollection(usersList)
                    initRecyclerView(usersList)
                } else {
                    showToast("Ha ocurrido un error",Toast.LENGTH_LONG)
                }
            }
        }
    }


    private fun initRecyclerView(usersList: List<User>) {
        binding.recyclerVList.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(usersList, secondLauncher)
        binding.recyclerVList.adapter = adapter
    }
}