package com.example.cart.checkout

data class CheckoutRVModal(
    var checkout_id: String,
    var account: String,
    var product_name: String,
    var product_amount: String,
    var total: String,
    var date: String
)
