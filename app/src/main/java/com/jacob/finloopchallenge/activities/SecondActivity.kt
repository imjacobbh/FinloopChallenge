package com.jacob.finloopchallenge.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.jacob.finloopchallenge.AppConstants
import com.jacob.finloopchallenge.AppConstants.USER_ID_SELECTED
import com.jacob.finloopchallenge.AppConstants.USER_NAME
import com.jacob.finloopchallenge.data.APIService
import com.jacob.finloopchallenge.data.User
import com.jacob.finloopchallenge.data.UserDetails
import com.jacob.finloopchallenge.databinding.ActivitySecondBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {
    private var userID = 0
    private lateinit var user: List<UserDetails>
    private lateinit var username: String
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras : Bundle? = intent.extras
        extras?.let{
             userID = it.getInt(USER_ID_SELECTED)
             username = it.getString(USER_NAME).toString()
             getUserDetailsFromServer(userID)
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://61959c9274c1bd00176c6df0.mockapi.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun getUserDetailsFromServer(userID: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getUserDetails("users/$userID/userdetail")
             user = call.body()!!
            runOnUiThread {
                if(call.isSuccessful){
                    initContentView()
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
    private fun  initContentView(){
        binding = ActivitySecondBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.tvUsernameD.text = username
        binding.tvJobTitle.text = user!![0].jobtitle
        binding.tvSalary.text = user!![0].salary.toString()

    }
}