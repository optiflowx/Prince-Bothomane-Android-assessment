package com.glucode.about_you.about.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ItemQuickStatBinding

class QuickStatItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var binder: ItemQuickStatBinding =
        ItemQuickStatBinding.inflate(LayoutInflater.from(context), this, true)

    @ColorInt
    private val textColor: Int = ContextCompat.getColor(context, R.color.black)

    val tvStatTitle: TextView
        get() = binder.statTitle

    val tvStatValue: TextView
        get() = binder.statValue

    init {
        orientation = VERTICAL
        gravity = CENTER

        tvStatTitle.setTextColor(textColor)
        tvStatValue.setTextColor(textColor)
    }
}