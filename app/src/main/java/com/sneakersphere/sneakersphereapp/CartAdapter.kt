package com.sneakersphere.sneakersphereapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartItems: List<CartItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = cartItems.size

    fun submitList(items: List<CartItem>) {
        cartItems = items
        notifyDataSetChanged()
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        private val productPriceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)
        private val selectedSizeTextView: TextView = itemView.findViewById(R.id.selectedSizeTextView)
        private val productImageView: ImageView = itemView.findViewById(R.id.productImageView)
        private val removeItemButton: ImageView = itemView.findViewById(R.id.removeItemButton)
        private val selectCheckBox: CheckBox = itemView.findViewById(R.id.selectCheckBox)

        fun bind(item: CartItem) {
            productNameTextView.text = item.name
            productPriceTextView.text = String.format("%.2f", item.price)
            selectedSizeTextView.text = "Size: ${item.size}"
            productImageView.setImageResource(item.imageResource)
            selectCheckBox.isChecked = item.isSelected

            selectCheckBox.setOnCheckedChangeListener { _, isChecked ->
                item.isSelected = isChecked
                updateTotalAmount()
            }

            removeItemButton.setOnClickListener {
                showRemoveItemConfirmationDialog(item)
            }
        }

        private fun showRemoveItemConfirmationDialog(item: CartItem) {
            val dialogView = LayoutInflater.from(itemView.context).inflate(R.layout.remove_item, null)

            val confirmRemoveButton = dialogView.findViewById<Button>(R.id.confirmRemoveButton)
            val cancelRemoveButton = dialogView.findViewById<Button>(R.id.cancelRemoveButton)

            val alertDialog = AlertDialog.Builder(itemView.context)
                .setView(dialogView)
                .create()

            confirmRemoveButton.setOnClickListener {
                ShoppingCart.removeItem(item)
                updateTotalAmount()
                cartItems = ShoppingCart.getItems()
                notifyDataSetChanged()
                alertDialog.dismiss()
            }

            cancelRemoveButton.setOnClickListener {
                alertDialog.dismiss()
            }

            alertDialog.show()
        }

        private fun updateTotalAmount() {
            (itemView.context as? CartActivity)?.updateTotalAmount()
        }
    }
}