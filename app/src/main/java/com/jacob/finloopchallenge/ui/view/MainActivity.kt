package com.jacob.finloopchallenge.ui.view

import android.annotation.SuppressLint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jacob.finloopchallenge.AppConstants.RESULT_CODE
import com.jacob.finloopchallenge.AppConstants.USER_ID_SELECTED
import com.jacob.finloopchallenge.ui.adapters.UserAdapter
import com.jacob.finloopchallenge.databinding.ActivityMainBinding
import com.jacob.finloopchallenge.showToast
import com.jacob.finloopchallenge.ui.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val usersViewModel: UsersViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    private val secondLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_CODE) {
                val data: Intent? = it.data
                val id: Int = data!!.getIntExtra(USER_ID_SELECTED, -1)
                if (id != -1) {
                    adapter.notifyItemChanged(id - 1, null)

                }
            }
        }
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.recyclerVList.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(emptyList(), secondLauncher)
        binding.recyclerVList.adapter = adapter
        usersViewModel.onCreate()
        usersViewModel.userListModel.observe(this) {
            if (it.isNullOrEmpty()) {
                showToast(" No Internet Connection")
            } else {
                if (adapter.usersList.toString() != it.toString()) {
                    adapter.usersList = it
                    adapter.notifyDataSetChanged()
                    showToast("Data refreshed")
                }

            }

        }
        usersViewModel.isLoading.observe(this) {
            binding.swipe.isRefreshing = it
        }
        supportActionBar!!.title = "Users"
        binding.swipe.setOnRefreshListener {
            usersViewModel.onCreate()
        }
    }

}