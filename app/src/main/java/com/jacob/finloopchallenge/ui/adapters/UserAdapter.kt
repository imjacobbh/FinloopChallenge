package com.jacob.finloopchallenge.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jacob.finloopchallenge.databinding.ItemUserBinding
import com.jacob.finloopchallenge.domain.model.UserModel

class UserAdapter(private val onItemClickListener: OnItemInteraction) :
    ListAdapter<UserModel, UserAdapter.UserViewHolder>(DiffUtilCallback()) {


    inner class UserViewHolder(var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemInteraction {
        fun onClick(item: UserModel)
        fun onFavoriteStateChange(id: Int)
        fun checkFavoriteItem(idUser: Int): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val views = ItemUserBinding.inflate(LayoutInflater.from(parent.context))
        return UserViewHolder(views)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.cbFavoriteUser.isChecked = onItemClickListener.checkFavoriteItem(item.id)
        holder.binding.tvUsername.text = item.name
        holder.binding.cbFavoriteUser.setOnClickListener {
            onItemClickListener.onFavoriteStateChange(item.id)
        }
        holder.binding.cvItem.setOnClickListener {
            onItemClickListener.onClick(item)
        }
    }
}
private class DiffUtilCallback : DiffUtil.ItemCallback<UserModel>() {
    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
        oldItem == newItem

}