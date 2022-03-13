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
import com.google.android.material.shape.Shapeable
import com.hatta.zwallet.R
import com.hatta.zwallet.data.Transaction
import com.hatta.zwallet.model.Invoice
import com.hatta.zwallet.utils.BASE_URL
import com.hatta.zwallet.utils.Helper.formatPrice
import okhttp3.internal.http.RequestLine

class TransactionAdapter (private var data: List<Invoice>): RecyclerView.Adapter <TransactionAdapter.TransactionAdapterHolder> (){
    lateinit var contextAdapter : Context

    class TransactionAdapterHolder (view: View):RecyclerView.ViewHolder(view){
        private  val image: ShapeableImageView = view.findViewById(R.id.imageTransaction)
        private  val name: TextView = view.findViewById(R.id.nameTransaction)
        private  val type: TextView = view.findViewById(R.id.typeTransaction)
        private  val amount: TextView = view.findViewById(R.id.amountTransaction)

        fun bindData(data: Invoice,context: Context, position: Int) {
            name.text = data.name
            type.text = data.type?.uppercase()
            amount.formatPrice(data.amount.toString())
            Glide.with(image)
                .load(BASE_URL + data.image)
                .apply(
                    RequestOptions.circleCropTransform()
                        .placeholder(R.drawable.ic_baseline_person_24)
                )
                .into(image)
           }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        this.contextAdapter = parent.context

        val inflaterView: View = inflater.inflate(R.layout.item_transaction_home,parent,false)
        return TransactionAdapterHolder(inflaterView)
    }

    override fun onBindViewHolder(holder: TransactionAdapter.TransactionAdapterHolder, position: Int) {
       holder.bindData(this.data[position], contextAdapter,position)
    }

    override fun getItemCount(): Int {
        return this.data.size
    }

    fun addData(data: List<Invoice>){
        this.data = data
    }
}