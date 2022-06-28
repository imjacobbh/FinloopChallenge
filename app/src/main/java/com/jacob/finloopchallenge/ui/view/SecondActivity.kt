package com.jacob.finloopchallenge.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.jacob.finloopchallenge.AppConstants.RESULT_CODE
import com.jacob.finloopchallenge.AppConstants.USER_ID_SELECTED
import com.jacob.finloopchallenge.AppConstants.USER_NAME
import com.jacob.finloopchallenge.databinding.ActivitySecondBinding

import com.jacob.finloopchallenge.domain.model.UserDetailsModel
import com.jacob.finloopchallenge.core.showToast
import com.jacob.finloopchallenge.ui.viewmodel.FavoritesViewModel
import com.jacob.finloopchallenge.ui.viewmodel.UserDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {
    private var userID = 0
    private lateinit var user: List<UserDetailsModel>
    private lateinit var username: String
    private lateinit var binding: ActivitySecondBinding
    private val userDetailsModel: UserDetailViewModel by viewModels()
    private val favoritesViewModel : FavoritesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val extras: Bundle? = intent.extras
        binding = ActivitySecondBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.cbFavoriteUser.isVisible = false
        extras?.let {
            userID = it.getInt(USER_ID_SELECTED)
            username = it.getString(USER_NAME).toString()
            userDetailsModel.isLoading.observe(this) { loading ->
                binding.loading.isVisible = loading
            }
            userDetailsModel.onCreate(userID)
            userDetailsModel.userDetailModel.observe(this) {userD->
                if (userD.isNullOrEmpty())
                    showToast(" No Internet Connection", Toast.LENGTH_LONG)
                else {
                    user = userD
                    initContentView()
                }
            }
        }
        supportActionBar!!.title = "User Detail"
        favoritesViewModel.favoriteList.observe(this){

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initContentView() {
        binding.cbFavoriteUser.isVisible = true
        val result = Intent()
        binding = ActivitySecondBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.cbFavoriteUser.isChecked =
            favoritesViewModel.favoriteList.value?.contains(user[0].userId.toString()) ?: false
        binding.tvUsernameD.text = username
        binding.tvJobTitle.text = user[0].jobtitle
        binding.tvSalary.text = user[0].salary.toString()
        binding.cbFavoriteUser.setOnCheckedChangeListener { _, _ ->
           favoritesViewModel.onUpdateFavoriteList(user[0].userId)
            result.putExtra(USER_ID_SELECTED, user[0].userId)
        }
        setResult(RESULT_CODE, result)
    }
}