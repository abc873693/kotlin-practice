package com.raimvititor.bmi_calculator

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import com.raimvititor.bmi_calculator.libs.Constants
import com.raimvititor.bmi_calculator.libs.Utils
import kotlinx.android.synthetic.main.activity_calculate_bmr.*
import java.util.*

class CalculateBMRActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var preference: SharedPreferences
    private var calendar = Calendar.getInstance()
    private lateinit var textToSpeech: TextToSpeech
    private var speechState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_bmr)
        preference = PreferenceManager.getDefaultSharedPreferences(this)
        setView()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            (R.id.buttonCalculate) -> {
                val name: String = editName.text.toString()
                val height: String = editHeight.text.toString()
                val weight: String = editWeight.text.toString()
                if (name.isEmpty() || height.isEmpty() || weight.isEmpty() || editBirthday.text.isEmpty()) {
                    Toast.makeText(this, "請勿留空", Toast.LENGTH_SHORT).show()
                    return
                }
                if (height.toFloat() == 0f || weight.toFloat() == 0f) {
                    Toast.makeText(this, "請勿填零", Toast.LENGTH_SHORT).show()
                    return
                }
                val sex: String = when (radioGroupSex.checkedRadioButtonId) {
                    R.id.radioMale -> {
                        "先生"
                    }
                    R.id.radioFemale -> {
                        "小姐"
                    }
                    else -> {
                        Toast.makeText(this, "請選擇性別", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                val bmr: Float = when (radioGroupSex.checkedRadioButtonId) {
                    R.id.radioMale ->
                        (13.7f * weight.toFloat() + 5f * height.toFloat() - 6.8f * Utils.getOld(calendar.time) + 66f)
                    R.id.radioFemale ->
                        (13.7f * weight.toFloat() + 5f * height.toFloat() - 6.8f * Utils.getOld(calendar.time) + 66f)
                    else -> 0f
                }
                textResult.text = String.format("*%s%s您好\n您的 BMR = %.1f大卡", name, sex, bmr)
                preference.edit {
                    putString(Constants.PREF_NAME, name)
                    putString(Constants.PREF_SEX, sex)
                    putString(Constants.PREF_HEIGHT, height)
                    putString(Constants.PREF_WEIGHT, weight)
                    putLong(Constants.PREF_BIRTHDAY, calendar.timeInMillis)
                }
                if (speechState) return
                textToSpeech.speak(textResult!!.text.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
            }
            R.id.editBirthday -> {
                DatePickerDialog(this, calendar.get(Calendar.YEAR),
                        DatePickerDialog.OnDateSetListener { _, p1, p2, p3 ->
                            calendar.set(p1, p2, p3)
                            setEditBirthday(calendar)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH))
                        .show()
            }
            R.id.buttonBackHome -> {
                finish()
            }
        }
    }

    private fun setView() {
        supportActionBar?.title = "計算BMR-基礎代謝率"
        Utils.setEditTextNotEdit(editBirthday)
        editName.setText(preference.getString(Constants.PREF_NAME, ""))
        editHeight.setText(preference.getString(Constants.PREF_HEIGHT, ""))
        editWeight.setText(preference.getString(Constants.PREF_WEIGHT, ""))
        when (preference.getLong(Constants.PREF_BIRTHDAY, 0)) {
            0L -> {
                calendar = Calendar.getInstance()
            }
            else -> {
                calendar.time = Date(preference.getLong(Constants.PREF_BIRTHDAY, 0))
                setEditBirthday(calendar)
            }
        }
        when (preference.getString(Constants.PREF_SEX, "")) {
            "先生" -> {
                radioGroupSex.check(R.id.radioMale)
            }
            "小姐" -> {
                radioGroupSex.check(R.id.radioFemale)
            }
        }
        editBirthday.setOnClickListener(this)
        buttonCalculate.setOnClickListener(this)
        buttonBackHome.setOnClickListener(this)
        textToSpeech = TextToSpeech(this,
                TextToSpeech.OnInitListener {
                    if (it == TextToSpeech.SUCCESS) {
                        textToSpeech.language = Locale.TAIWAN
                    }
                })
    }

    private fun setEditBirthday(calendar: Calendar) {
        editBirthday.setText(
                String.format("%d/%02d/%02d (%d歲)",
                        calendar[Calendar.YEAR],
                        calendar[Calendar.MONTH] + 1,
                        calendar[Calendar.DAY_OF_MONTH],
                        Utils.getOld(calendar.time)
                ))
    }
}
