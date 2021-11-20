package com.jacob.finloopchallenge.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacob.finloopchallenge.adapters.UserAdapter
import com.jacob.finloopchallenge.data.APIService
import com.jacob.finloopchallenge.data.User
import com.jacob.finloopchallenge.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val usersList= mutableListOf<User>()
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initRecyclerView()
        getUsersFromServer()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://61959c9274c1bd00176c6df0.mockapi.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun getUsersFromServer(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getUsersList("users")
            val users = call.body()
            runOnUiThread {
                if(call.isSuccessful){
                    usersList.clear()
                    users?.toCollection(usersList)
                    adapter.notifyDataSetChanged()
                }
                else{
                   showError()
                }
            }
        }
    }
    private fun showError(){
        Toast.makeText(this,"Ha ocurrido un error", Toast.LENGTH_LONG).show()
    }
    private fun initRecyclerView(){
        adapter = UserAdapter(usersList)
        binding.recyclerVList.layoutManager = LinearLayoutManager(this)
        binding.recyclerVList.adapter = adapter
    }
}