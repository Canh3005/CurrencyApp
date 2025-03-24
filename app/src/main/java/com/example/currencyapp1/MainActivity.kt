package com.example.currencyapp1

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtAmount = findViewById<EditText>(R.id.edtAmount)
        val spinnerFrom = findViewById<Spinner>(R.id.spinnerFrom)
        val spinnerTo = findViewById<Spinner>(R.id.spinnerTo)
        val tvConvertedAmount = findViewById<TextView>(R.id.tvConvertedAmount)
        val tvExchangeRate = findViewById<TextView>(R.id.tvExchangeRate)

        // Danh sách các đồng tiền hỗ trợ
        val currencies = arrayOf("USD", "VND", "EUR", "JPY", "GBP")

        // Adapter cho Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        // Tỷ giá cố định
        val exchangeRates = mapOf(
            "USD" to 1.0,
            "VND" to 25640.0,
            "EUR" to 0.93,
            "JPY" to 150.3,
            "GBP" to 0.77
        )

        fun updateConversion() {
            val amountText = edtAmount.text.toString()
            val fromCurrency = spinnerFrom.selectedItem.toString()
            val toCurrency = spinnerTo.selectedItem.toString()

            if (amountText.isNotEmpty()) {
                val amount = amountText.toDoubleOrNull()
                if (amount != null && amount > 0) {
                    val fromRate = exchangeRates[fromCurrency] ?: 1.0
                    val toRate = exchangeRates[toCurrency] ?: 1.0
                    val convertedAmount = (amount / fromRate) * toRate

                    tvConvertedAmount.text = "%.2f $toCurrency".format(convertedAmount)

                    // ✅ Cập nhật tỷ giá theo cách đúng
                    val exchangeRate = toRate / fromRate
                    tvExchangeRate.text = "1 $fromCurrency = %.2f $toCurrency".format(exchangeRate)
                } else {
                    tvConvertedAmount.text = "Số tiền không hợp lệ"
                }
            } else {
                tvConvertedAmount.text = "Nhập số tiền để chuyển đổi"
            }
        }


        // Cập nhật kết quả khi thay đổi dữ liệu
        edtAmount.setOnKeyListener { _, _, _ ->
            updateConversion()
            false
        }

        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                updateConversion()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                updateConversion()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
