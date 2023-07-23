package com.lytredrock.emocloudmusic.PageTransformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import java.lang.Math.abs

/**
 * description ： TODO:类的作用
 * author : 苟云东
 * email : 2191288460@qq.com
 * date : 2023/7/23 15:42
 */
private const val MIN_SCALE = 0.75f

class MainPageTransformer: ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width

        when {
            position < -1 -> {
                view.alpha = 0f
            }
            position <= 0 -> {
                view.alpha = 1F
                view.translationX = 0f
                view.scaleX = 1f
                view.scaleY = 1f
            }
            position <= 1 -> {

                view.alpha = 1 - position


                view.translationX = pageWidth * -position


                val scaleFactor: Float = (MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - kotlin.math.abs(position)))
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
            }
            else -> {
                view.alpha = 0f
            }
        }

    }

}
