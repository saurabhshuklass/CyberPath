package com.example.cyberpath.utils

import android.view.View

object ClickAnimation {

    fun animate(view: View, onClick: () -> Unit) {

        view.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(80)
            .withEndAction {

                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(80)
                    .withEndAction {

                        onClick()

                    }

            }

    }

}