package com.jacob.finloopchallenge.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.jacob.finloopchallenge.AppConstants.RESULT_CODE
import com.jacob.finloopchallenge.AppConstants.USER_ID_SELECTED
import com.jacob.finloopchallenge.AppConstants.USER_NAME
import com.jacob.finloopchallenge.FinloopChallengeApplication.Companion.connectionState
import com.jacob.finloopchallenge.FinloopChallengeApplication.Companion.prefs
import com.jacob.finloopchallenge.data.APIService
import com.jacob.finloopchallenge.data.UserDetails
import com.jacob.finloopchallenge.databinding.ActivitySecondBinding
import com.jacob.finloopchallenge.showToast
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
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val extras : Bundle? = intent.extras
        extras?.let{
             userID = it.getInt(USER_ID_SELECTED)
             username = it.getString(USER_NAME).toString()
             getUserDetailsFromServer(userID)
        }
        supportActionBar!!.title= "User Detail"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://61959c9274c1bd00176c6df0.mockapi.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun getUserDetailsFromServer(userID: Int){
        if(!connectionState()){
            showToast("Ha ocurrido un error",Toast.LENGTH_LONG)
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getUserDetails("users/$userID/userdetail")
             user = call.body()!!
            runOnUiThread {
                if(call.isSuccessful){
                    initContentView()
                }
                else{
                    showToast("Ha ocurrido un error",Toast.LENGTH_LONG)
                }
            }
        }
    }
    private fun  initContentView(){
        val result = Intent()
        binding = ActivitySecondBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.cbFavoriteUser.isChecked = prefs.getFavorites()?.contains(user[0].userId.toString())?:false
        binding.tvUsernameD.text = username
        binding.tvJobTitle.text = user[0].jobtitle
        binding.tvSalary.text = user[0].salary.toString()
        binding.cbFavoriteUser.setOnCheckedChangeListener { _, isChecked ->

            if (!isChecked) {
                prefs.deleteIDfromFavoriteList(user[0].userId)
                //binding.cbFavoriteUser.isChecked = false
                showToast("Removed from favorites")

            } else {
                prefs.saveIDtoFavoritesList(user[0].userId)
                //binding.cbFavoriteUser.isChecked = true
                showToast("Added to favorites")
            }
            result.putExtra(USER_ID_SELECTED,user[0].userId)
        }
        setResult(RESULT_CODE,result)
    }
}