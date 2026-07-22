package com.example.cyberpath.utils

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.cyberpath.R

object RecyclerAnimation {

    fun animate(recyclerView: RecyclerView) {

        val controller =
            AnimationUtils.loadLayoutAnimation(
                recyclerView.context,
                R.anim.layout_animation_fall_down
            )

        recyclerView.layoutAnimation = controller
        recyclerView.scheduleLayoutAnimation()

    }

}