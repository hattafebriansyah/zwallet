package com.hatta.zwallet.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hatta.zwallet.R
import com.hatta.zwallet.databinding.ItemContactTransferBinding
import com.hatta.zwallet.model.ContactUser
import com.hatta.zwallet.utils.BASE_URL

class ContactAdapter (
    private var data: List<ContactUser>,
    private val clickListener: (ContactUser, View) -> Unit,
) : RecyclerView.Adapter <ContactAdapter.ContactAdapterHolder>() {
    private lateinit var contextAdapter: Context

    class ContactAdapterHolder(private val binding: ItemContactTransferBinding): RecyclerView
    .ViewHolder(binding.root) {
        fun bindData(data: ContactUser, onClick: (ContactUser, View) -> Unit){
            binding.nameContact.text = data.name
            binding.phoneContact.text = data.phone
            Glide.with(binding.imageContact)
                .load(BASE_URL + data.image)
                .apply(
                    RequestOptions.circleCropTransform()
                        .placeholder(R.drawable.ic_baseline_person_24)
                )
                .into(binding.imageContact)

            binding.root.setOnClickListener { onClick(data, binding.root) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ContactAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.contextAdapter = parent.context

        val binding = ItemContactTransferBinding.inflate(inflater, parent, false)
        return ContactAdapterHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactAdapterHolder, x: Int) {
        holder.bindData(this.data[x], clickListener)
    }


    override fun getItemCount(): Int {
        return this.data.size
    }

    fun addData(data: List<ContactUser>){
        this.data = data
    }


}