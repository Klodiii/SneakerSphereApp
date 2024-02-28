package com.sneakersphere.sneakersphereapp
object ShoppingCart {
    private val items: MutableList<CartItem> = mutableListOf()

    fun addItem(item: CartItem) {
        items.add(item)
    }

    fun removeItem(item: CartItem) {
        items.remove(item)
    }

    fun clearCart() {
        items.clear()
    }

    fun getItems(): List<CartItem> {
        return items
    }

    fun removeUncheckedItems() {
        items.removeAll { !it.isSelected }
    }

    fun getTotalPrice(): Double {
        var totalPrice = 0.0
        for (item in items) {
            totalPrice += item.price
        }
        return totalPrice
    }
}
