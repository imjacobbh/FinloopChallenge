package com.jacob.finloopchallenge.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.jacob.finloopchallenge.AppConstants.RESULT_CODE
import com.jacob.finloopchallenge.AppConstants.USER_ID_SELECTED
import com.jacob.finloopchallenge.AppConstants.USER_NAME
import com.jacob.finloopchallenge.FinloopChallengeApplication.Companion.prefs
import com.jacob.finloopchallenge.databinding.ActivitySecondBinding

import com.jacob.finloopchallenge.domain.model.UserDetailsModel
import com.jacob.finloopchallenge.showToast
import com.jacob.finloopchallenge.ui.viewmodel.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {
    private var userID = 0
    private lateinit var user: List<UserDetailsModel>
    private lateinit var username: String
    private lateinit var binding: ActivitySecondBinding
    private val userDetailsModel: UserDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val extras: Bundle? = intent.extras
        binding = ActivitySecondBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.cbFavoriteUser.isVisible= false
        extras?.let {
            userID = it.getInt(USER_ID_SELECTED)
            username = it.getString(USER_NAME).toString()
            userDetailsModel.isLoading.observe(this, Observer {
                binding.loading.isVisible = it
            })
            userDetailsModel.onCreate(userID)
            userDetailsModel.userDetailModel.observe(this, Observer {
                if(it.isNullOrEmpty())
                    showToast(" No Internet Connection", Toast.LENGTH_LONG)
                else{
                    user = it
                    initContentView()
                }
            })
        }
        supportActionBar!!.title = "User Detail"
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    private fun initContentView() {
        binding.cbFavoriteUser.isVisible= true
        val result = Intent()
        binding = ActivitySecondBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.cbFavoriteUser.isChecked =
            prefs.getFavorites()?.contains(user[0].userId.toString()) ?: false
        binding.tvUsernameD.text = username
        binding.tvJobTitle.text = user[0].jobtitle
        binding.tvSalary.text = user[0].salary.toString()
        binding.cbFavoriteUser.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                prefs.deleteIDfromFavoriteList(user[0].userId)
                showToast("Removed from favorites")

            } else {
                prefs.saveIDtoFavoritesList(user[0].userId)
                showToast("Added to favorites")
            }
            result.putExtra(USER_ID_SELECTED, user[0].userId)
        }
        setResult(RESULT_CODE, result)
    }
}