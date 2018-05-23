package com.maks.hp.tochka.extend

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.addAfterTextChangedListener(f: (Editable) -> Unit) {
    addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(p0: Editable) = f(p0)
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    })
}