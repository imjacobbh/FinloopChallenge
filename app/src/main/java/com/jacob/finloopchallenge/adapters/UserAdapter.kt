package com.jacob.finloopchallenge.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.jacob.finloopchallenge.AppConstants.USER_ID_SELECTED
import com.jacob.finloopchallenge.AppConstants.USER_NAME
import com.jacob.finloopchallenge.FinloopChallengeApplication.Companion.prefs
import com.jacob.finloopchallenge.activities.SecondActivity
import com.jacob.finloopchallenge.data.User
import com.jacob.finloopchallenge.databinding.ItemUserBinding

class UserAdapter(private val usersList: List<User>, val activityLauncher : ActivityResultLauncher<Intent>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun getItemViewType(position: Int): Int = position
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class UserViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun checkFavoriteItem(idUser: Int): Boolean {
            var check = false
            val favorites: List<String>? = prefs.getFavorites()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val views = ItemUserBinding.inflate(LayoutInflater.from(parent.context))
        return UserViewHolder(views)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = usersList[position]
        holder.binding.cbFavoriteUser.setOnCheckedChangeListener(null)
        holder.binding.cbFavoriteUser.isChecked = false
        holder.binding.cbFavoriteUser.isChecked = holder.checkFavoriteItem(item.id)
        holder.binding.tvUsername.text = item.name
        holder.binding.cbFavoriteUser.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                prefs.deleteIDfromFavoriteList(item.id)
            } else {
                prefs.saveIDtoFavoritesList(item.id)
            }
        }
        holder.binding.cvItem.setOnClickListener {
            val intent = Intent(holder.itemView.context, SecondActivity::class.java)
            intent.putExtra(USER_ID_SELECTED, item.id)
            intent.putExtra(USER_NAME, item.name)
            activityLauncher.launch(intent)
        }

    }

    override fun getItemCount(): Int = usersList.size
}