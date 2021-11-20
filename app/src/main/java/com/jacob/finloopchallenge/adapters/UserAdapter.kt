package com.jacob.finloopchallenge.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jacob.finloopchallenge.AppConstants.USER_ID_SELECTED
import com.jacob.finloopchallenge.AppConstants.USER_NAME
import com.jacob.finloopchallenge.activities.SecondActivity
import com.jacob.finloopchallenge.data.User
import com.jacob.finloopchallenge.databinding.ItemUserBinding

class UserAdapter(private val usersList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    private lateinit var binding: ItemUserBinding
    inner class UserViewHolder(view : View) : RecyclerView.ViewHolder(view){

        internal fun setData(user: User){
            binding.tvUsername.text = user.name
            binding.cvItem.setOnClickListener {
                val intent = Intent(itemView.context,SecondActivity::class.java)
                intent.putExtra(USER_ID_SELECTED,user.id)
                intent.putExtra(USER_NAME,user.name)
                itemView.context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context))
        return UserViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setData(usersList[position])
    }

    override fun getItemCount(): Int = usersList.size
}