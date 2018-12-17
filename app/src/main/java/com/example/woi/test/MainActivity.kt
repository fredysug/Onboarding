package com.example.woi.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var saldo: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Kirim DANA"

        val recName = intent.getStringExtra("receiverName") ?: ""
        saldo = intent.getLongExtra("Saldo", 0L)

        receiverName.text = recName

        setMessage(Allowed(saldo))

        inputAmount.addTextChangedListener(textWatcher)
        clearIcon.setOnClickListener {
            inputAmount.text.clear()
        }
    }

    private fun setMessage(inputStatus: InputStatus) {
        message.text = inputStatus.message
    }

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (!s.isNullOrEmpty()) {
                clearIcon.visibility = View.VISIBLE
            } else {
                clearIcon.visibility = View.GONE
            }
            s?.let {
                val inputStatus = if (it.toString().toLongOrNull() ?: 0L > saldo) {
                    OverLimit
                } else {
                    Allowed(saldo)
                }
                setMessage(inputStatus)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }
}


sealed class InputStatus(val message: String)

object OverLimit : InputStatus("Saldo DANA Anda tidak mencukupi!")
class Allowed(balance: Long) : InputStatus("Saldo DANA Anda: $balance")

