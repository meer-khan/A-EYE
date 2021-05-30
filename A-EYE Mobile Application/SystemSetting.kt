package com.muddasarajmal.aeye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputLayout
import com.muddasarajmal.aeye.databinding.ActivitySystemSettingBinding
import kotlinx.android.synthetic.main.activity_system_setting.*

class SystemSetting : AppCompatActivity() {
    private lateinit var binding: ActivitySystemSettingBinding
    var startPoint = 0
    var endPoint = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  =  DataBindingUtil.setContentView(this,R.layout.activity_system_setting)

        binding.volumeSeekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                volume.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if(seekBar!=null){
                    startPoint = seekBar.progress
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    endPoint = seekBar.progress
                }
                Toast.makeText(
                    this@SystemSetting,
                    "Volume increased by % ${endPoint - startPoint}",
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        val currencies = resources.getStringArray(R.array.currencies)
        val arrayAdapter = ArrayAdapter(this,R.layout.currency_dropdown_item,currencies)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
    }

}