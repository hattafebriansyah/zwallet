package com.hatta.zwallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.Shapeable
import com.hatta.zwallet.R
import com.hatta.zwallet.data.Transaction

class TransactionAdapter (private var data: List<Transaction>): RecyclerView.Adapter <TransactionAdapter.TransactionAdapterHolder> (){
    lateinit var contextAdapter : Context

    class TransactionAdapterHolder (view: View):RecyclerView.ViewHolder(view){
        private  val image: ShapeableImageView = view.findViewById(R.id.imageTransaction)
        private  val name: TextView = view.findViewById(R.id.nameTransaction)
        private  val type: TextView = view.findViewById(R.id.typeTransaction)
        private  val amount: TextView = view.findViewById(R.id.amountTransaction)

        fun bindData(data: Transaction,context: Context, position: Int) {
            name.text = data.transactionName
            type.text = data.transactionType
            amount.text = data.transactionNominal.toString()
            image.setImageDrawable(data.transactionImage)
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
}