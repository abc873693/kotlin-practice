package com.raimvititor.bmi_calculator

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var handler: Handler

    private val runnable = Runnable(function = {
        updateTime()
    })

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.buttonCalculateBMI -> {
                startActivity(Intent(this, CalculateBMIActivity::class.java))
            }
            R.id.buttonCalculateBMR -> {
                startActivity(Intent(this, CalculateBMRActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
    }

    private fun setView() {
        buttonCalculateBMI.setOnClickListener(this)
        buttonCalculateBMR.setOnClickListener(this)
        handler = Handler()
        handler.post(runnable)
    }

    private fun updateTime() {
        val dateFormat = SimpleDateFormat("hh:mm:ss aa", Locale.TAIWAN)
        val time = Date()
        textTime.text = dateFormat.format(time)
        handler.postDelayed(runnable, 500)
    }
}
