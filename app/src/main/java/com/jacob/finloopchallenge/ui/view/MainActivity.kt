package com.jacob.finloopchallenge.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.jacob.finloopchallenge.AppConstants
import com.jacob.finloopchallenge.AppConstants.RESULT_CODE
import com.jacob.finloopchallenge.AppConstants.USER_ID_SELECTED
import com.jacob.finloopchallenge.ui.adapters.UserAdapter
import com.jacob.finloopchallenge.databinding.ActivityMainBinding
import com.jacob.finloopchallenge.domain.model.UserModel
import com.jacob.finloopchallenge.core.showToast
import com.jacob.finloopchallenge.ui.viewmodel.FavoritesViewModel
import com.jacob.finloopchallenge.ui.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UserAdapter.OnItemInteraction {
    private val usersViewModel: UsersViewModel by viewModels()
    private val favoriteListViewModel: FavoritesViewModel by viewModels()
    private val secondLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_CODE) {
                val data: Intent? = it.data
                val id: Int = data!!.getIntExtra(USER_ID_SELECTED, -1)
                if (id != -1) {
                    adapter.notifyItemChanged(id - 1)

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
        adapter = UserAdapter(this)
        binding.recyclerVList.adapter = adapter
        usersViewModel.userListModel.observe(this) {
            if (it.isNullOrEmpty()) {
                showToast(" No Internet Connection")
            } else {
                if (adapter.currentList.toString() != it.toString()) {
                    adapter.submitList(it)
                    showToast("Data refreshed")
                }
            }
        }
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            Log.d("token user", token)
        }
        usersViewModel.isLoading.observe(this) {
            binding.swipe.isRefreshing = it
        }
        supportActionBar!!.title = "Users"
        binding.swipe.setOnRefreshListener {
            usersViewModel.onRefresh()
        }
        favoriteListViewModel.favoriteList.observe(this) {
            showToast("Favorite list updated")
        }
        intent.extras?.let {
            if (it.containsKey("userId") && it.containsKey("username")){
                val intent = Intent(this, SecondActivity::class.java)
                val username = it.getString("username")
                val userId = it.getString("userId")
                intent.putExtra(USER_ID_SELECTED, userId?.toInt())
                intent.putExtra(AppConstants.USER_NAME, username)
                secondLauncher.launch(intent)
            }

        }
    }

    override fun onClick(item: UserModel) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(USER_ID_SELECTED, item.id)
        intent.putExtra(AppConstants.USER_NAME, item.name)
        secondLauncher.launch(intent)
    }

    override fun onFavoriteStateChange(id: Int) {
        favoriteListViewModel.onUpdateFavoriteList(id)
    }

    override fun checkFavoriteItem(idUser: Int): Boolean {
        var check = false
        val favorites: List<String>? = favoriteListViewModel.favorites.getFavorites()
        if (favorites != null) {
            for (it in favorites) {
                if (idUser == it.toInt()) {
                    check = true
                    break
                }
            }
        }
        return check
    }
}
