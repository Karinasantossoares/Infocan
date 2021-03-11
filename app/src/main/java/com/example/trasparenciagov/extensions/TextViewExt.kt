package com.example.trasparenciagov.extensions

import android.widget.EditText
import android.widget.TextView
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher

fun TextView.addMask(mask:String){
    val smf = SimpleMaskFormatter(mask)
    val mtw = MaskTextWatcher(this, smf)
    addTextChangedListener(mtw)
}