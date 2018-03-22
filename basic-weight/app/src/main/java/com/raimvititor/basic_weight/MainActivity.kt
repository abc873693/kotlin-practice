package com.raimvititor.basic_weight

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(p0: View?) {
        textResult.text =
                "${findViewById<RadioButton>(radioGroupTicketType.checkedRadioButtonId).text}票" +
                " ${findViewById<RadioButton>(radioGroupTicketQuantity.checkedRadioButtonId).text}"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonConfirm.setOnClickListener(this)
    }
}
