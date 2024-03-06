package com.sneakersphere.sneakersphereapp
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductViewHolder(
    itemView: View,
    private val clickListener: ProductAdapter.OnProductClickListener
) : RecyclerView.ViewHolder(itemView) {
    private val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
    private val productPriceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)
    private val productImageView: ImageView = itemView.findViewById(R.id.productImageView)

    fun bind(product: Product) {
        productNameTextView.text = product.name
        productPriceTextView.text = product.price
        Glide.with(itemView.context).load(product.image).into(productImageView)

        itemView.setOnClickListener {
            clickListener.onProductClick(product)
        }
    }
}
