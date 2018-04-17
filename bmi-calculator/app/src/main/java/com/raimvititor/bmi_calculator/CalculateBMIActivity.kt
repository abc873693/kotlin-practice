package com.raimvititor.bmi_calculator

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import com.raimvititor.bmi_calculator.libs.Constants
import kotlinx.android.synthetic.main.activity_calculate_bmi.*
import java.util.*

class CalculateBMIActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var preference: SharedPreferences
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_bmi)
        preference = PreferenceManager.getDefaultSharedPreferences(this)
        setView()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            (R.id.buttonCalculate) -> {
                val name: String = editName.text.toString()
                val height: String = editHeight.text.toString()
                val weight: String = editWeight.text.toString()
                val waistline: String = editWaistline.text.toString()
                val sex: String
                val waistlineHint: String
                if (name.isEmpty() || height.isEmpty() || weight.isEmpty() || waistline.isEmpty()) {
                    Toast.makeText(this, "請勿留空", Toast.LENGTH_SHORT).show()
                    return
                }
                if (height.toFloat() == 0f || weight.toFloat() == 0f || waistline.toFloat() == 0f) {
                    Toast.makeText(this, "請勿填零", Toast.LENGTH_SHORT).show()
                    return
                }
                when (radioGroupSex.checkedRadioButtonId) {
                    R.id.radioMale -> {
                        sex = "先生"
                        waistlineHint = if (waistline.toFloat() >= 90f)
                            "超過標準"
                        else
                            "標準"
                    }
                    R.id.radioFemale -> {
                        sex = "小姐"
                        waistlineHint = if (waistline.toFloat() >= 80f)
                            "超過標準"
                        else
                            "標準"
                    }
                    else -> {
                        Toast.makeText(this, "請選擇性別", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                val bmi = weight.toFloat() / (height.toFloat() / 100) / (height.toFloat() / 100)
                val bmiHint = when {
                    (bmi < 18.5) -> "體重過輕"
                    (bmi < 24) -> "健康體位"
                    (bmi < 27) -> "體重過重"
                    (bmi < 30) -> "輕度肥胖"
                    (bmi < 35) -> "中度肥胖"
                    else -> "重度肥胖"
                }
                textResult.text = String.format("*%s%s您好\n您的 BMI = %.2f(%s)\n腰圍 %.1f 公分(%s)"
                        , name, sex, bmi, bmiHint, waistline.toFloat(), waistlineHint)
                preference.edit {
                    putString(Constants.PREF_NAME, name)
                    putString(Constants.PREF_SEX, sex)
                    putString(Constants.PREF_HEIGHT, height)
                    putString(Constants.PREF_WEIGHT, weight)
                    putString(Constants.PREF_WAISTLINE, waistline)
                }
                textToSpeech.speak(textResult!!.text.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
            }
            R.id.buttonBackHome -> {
                finish()
            }
        }
    }

    private fun setView() {
        supportActionBar?.title = "計算BMI-身體質量指數"
        editName.setText(preference.getString(Constants.PREF_NAME, ""))
        editHeight.setText(preference.getString(Constants.PREF_HEIGHT, ""))
        editWeight.setText(preference.getString(Constants.PREF_WEIGHT, ""))
        editWaistline.setText(preference.getString(Constants.PREF_WAISTLINE, ""))
        when (preference.getString(Constants.PREF_SEX, "")) {
            "先生" -> {
                radioGroupSex.check(R.id.radioMale)
            }
            "小姐" -> {
                radioGroupSex.check(R.id.radioFemale)
            }
        }
        buttonCalculate.setOnClickListener(this)
        buttonBackHome.setOnClickListener(this)
        textToSpeech = TextToSpeech(this,
                TextToSpeech.OnInitListener {
                    if (it == TextToSpeech.SUCCESS) {
                        textToSpeech.language = Locale.TAIWAN
                    }
                })
    }
}
