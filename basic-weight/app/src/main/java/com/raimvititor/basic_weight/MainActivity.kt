package com.raimvititor.basic_weight

import android.annotation.SuppressLint
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var textToSpeech: TextToSpeech
    var speechState = false

    @SuppressLint("SetTextI18n")
    override fun onClick(p0: View?) {
        textResult.text =
                "${findViewById<RadioButton>(radioGroupTicketType.checkedRadioButtonId).text}票" +
                " ${findViewById<RadioButton>(radioGroupTicketQuantity.checkedRadioButtonId).text}"
        if (speechState) return
        val text = textResult!!.text.toString()
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonConfirm.setOnClickListener(this)
        textToSpeech = TextToSpeech(this,
                TextToSpeech.OnInitListener {
                    if (it == TextToSpeech.SUCCESS) {
                        // set US English as language for tts
                        textToSpeech.language = Locale.TAIWAN
                    }
                })
    }
}
