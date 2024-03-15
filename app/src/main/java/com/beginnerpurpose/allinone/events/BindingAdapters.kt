package com.beginnerpurpose.allinone.events

import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import com.beginnerpurpose.allinone.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("checkEmptyField")
fun TextInputEditText.field(view: TextInputLayout) {
    view.editText!!.doOnTextChanged { text, start, before, count ->
        if (text!!.isEmpty()){
            view.error = context.getString(R.string.emptyField)
        } else {
            view.error = null
        }
    }
}

//@BindingAdapter("checkFirstField", "checkSecondField", "checkThirdField", "checkFourthField", "function", requireAll = false)
//fun Button.checkFieldsAndOnClick(firstView: TextInputLayout?,
//                  secondView: TextInputLayout?,
//                  thirdView: TextInputLayout?,
//                  fourthView: TextInputLayout?) {
//    this.setOnClickListener {
//
//        if (firstView != null){
//            if (firstView.editText!!.text.isEmpty())
//                firstView.error = context.getString(R.string.emptyField)
//        }
//
//        if (secondView != null){
//            if (secondView.editText!!.text.isEmpty())
//                secondView.error = context.getString(R.string.emptyField)
//        }
//
//        if (thirdView != null){
//            if (thirdView.editText!!.text.isEmpty())
//                thirdView.error = context.getString(R.string.emptyField)
//        }
//
//        if (fourthView != null){
//            if (fourthView.editText!!.text.isEmpty())
//                fourthView.error = context.getString(R.string.emptyField)
//        }
//    }
//}