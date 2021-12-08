package com.example.myapplication.util

import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.CheckBox
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans


import android.content.res.Resources
import android.text.SpannableStringBuilder
import android.text.Spanned
import androidx.annotation.StringRes
import com.example.myapplication.R

/**
 * Fills template string with (possibly) spanned args.
 *
 * @return Spanned string
 */
fun Resources.getSpannedString(@StringRes resId: Int, vararg formatArgs: CharSequence): Spanned {
    var lastArgIndex = 0
    val spannableStringBuilder = SpannableStringBuilder(getString(resId, *formatArgs))
    for (arg in formatArgs) {
        val argString = arg.toString()
        lastArgIndex = spannableStringBuilder.indexOf(argString, lastArgIndex)
        if (lastArgIndex != -1) {
            spannableStringBuilder.replace(lastArgIndex, lastArgIndex + argString.length, arg)
            lastArgIndex += argString.length
        }
    }
    return spannableStringBuilder
}

fun CheckBox.setClubRulesText(clubRulesClickListener: () -> Unit) {

    // Turn on ClickableSpan.
    movementMethod = LinkMovementMethod.getInstance()

    val clubRulesClickSpan = object : ClickableSpan() {
        override fun onClick(widget: View) = clubRulesClickListener()
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = resources.getColor(R.color.brand_blue, null)
        }
    }

    text = resources.getSpannedString(
        R.string.sign_up_terms_and_conditions_template,
        buildSpannedString {
            inSpans(clubRulesClickSpan) {
                append(resources.getSpannedString(R.string.sign_up_club_rules))
            }
        }
    )
}