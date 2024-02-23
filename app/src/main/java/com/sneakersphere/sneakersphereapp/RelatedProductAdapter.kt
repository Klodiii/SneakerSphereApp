package com.sneakersphere.sneakersphereapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RelatedProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<RelatedProductAdapter.RelatedProductViewHolder>() {

    inner class RelatedProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        val productPriceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)
        val productImageView: ImageView = itemView.findViewById(R.id.productImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.related_product_item, parent, false)
        return RelatedProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: RelatedProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productNameTextView.text = product.name
        holder.productPriceTextView.text = product.price
        holder.productImageView.setImageResource(product.imageResource)


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductDetailsActivity::class.java).apply {
                // Pass data to ProductDetailsActivity
                putExtra("productName", product.name)
                putExtra("productPrice", product.price)
                putExtra("productImage", product.imageResource)
                putExtra("productDescription", product.description)
                // Add more data if needed
            }
            holder.itemView.context.startActivity(intent)
        }

    }


    override fun getItemCount(): Int {
        return productList.size
    }
    fun setOnItemClickListener(listener: (Int) -> Unit) {
        this.onItemClickListener = listener
    }
    private var onItemClickListener: ((Int) -> Unit)? = null


}
