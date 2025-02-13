package com.glucode.about_you.about.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.glucode.about_you.R
import com.glucode.about_you.databinding.ViewAboutInfoCardBinding

class AboutInfoCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : CardView(context, attrs, defStyleAttr) {
    private val binder = ViewAboutInfoCardBinding.inflate(LayoutInflater.from(context), this)

    @ColorInt
    private val textColor: Int = ContextCompat.getColor(context, R.color.white)

    val tvName: TextView
        get() = binder.name

    val tvRole: TextView
        get() = binder.role

    val ivProfile: ImageView
        get() = binder.profileImage

    val qsYears: QuickStatItem
        get() = binder.qsYears

    val qsCoffees: QuickStatItem
        get() = binder.qsCoffees

    val qsBugs: QuickStatItem
        get() = binder.qsBugs

    private val cvQuickStatsContainer: CardView
        get() = binder.quickStatsContainer

    init {
        tvName.setTextColor(textColor)
        tvRole.setTextColor(textColor)

        cvQuickStatsContainer.radius = resources.getDimension(R.dimen.corner_radius_large)
        cvQuickStatsContainer.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))

        radius = resources.getDimension(R.dimen.corner_radius_extra_large)
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.black))
    }
}