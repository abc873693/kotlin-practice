package com.raimvititor.recycleview

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.raimvititor.recycleview.adapter.CheckAdapter
import com.raimvititor.recycleview.models.CheckModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val items: MutableList<CheckModel> = ArrayList()
    var textToSpeech: TextToSpeech? = null

    override fun onClick(p0: View?) {
        var text = ""
        var index = 1
        items.filter { it -> it.check }
                .forEach {
                    text += "${index++} :  ${it.text} \n"
                }
        textOrderContent.text = text
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
    }

    private fun setView() {
        for (s in resources.getStringArray(R.array.items)) {
            items.add(CheckModel(s, false))
        }
        recyclerViewCheck.setHasFixedSize(false)
        recyclerViewCheck.layoutManager = GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false)
        recyclerViewCheck.adapter = CheckAdapter(this, items)
        buttonConfirm.setOnClickListener(this)
        textToSpeech = TextToSpeech(this, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech!!.language = Locale.TAIWAN
            }
        })
    }
}
