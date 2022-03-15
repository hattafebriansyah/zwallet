package com.hatta.zwallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.hatta.zwallet.R
import com.hatta.zwallet.model.ContactUser
import com.hatta.zwallet.model.Invoice
import com.hatta.zwallet.utils.BASE_URL

class ContactAdapter (private var data: List<ContactUser>) : RecyclerView.Adapter <ContactAdapter.ContactAdapterHolder>() {
    private lateinit var contextAdapter: Context

    class ContactAdapterHolder (view: View):RecyclerView.ViewHolder(view){
        private  val image: ShapeableImageView = view.findViewById(R.id.imageContact)
        private  val name: TextView = view.findViewById(R.id.nameContact)
        private  val phone: TextView = view.findViewById(R.id.phoneContact)

        fun bindData(data: ContactUser, context: Context, position: Int) {
            name.text = data.name
            phone.text = data.phone
            Glide.with(image)
                .load(BASE_URL + data.image)
                .apply(
                    RequestOptions.circleCropTransform()
                        .placeholder(R.drawable.ic_baseline_person_24)
                )
                .into(image)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ContactAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.contextAdapter = parent.context

        val inflatedView: View = inflater.inflate(R.layout.contact_transaction,parent,false)
        return ContactAdapter.ContactAdapterHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ContactAdapter.ContactAdapterHolder, position: Int) {
        holder.bindData(this.data[position], contextAdapter,position)
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    fun addData(data: List<ContactUser>){
        this.data = data
    }


}