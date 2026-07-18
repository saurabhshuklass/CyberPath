package com.example.cyberpath.utils

import com.example.cyberpath.R

object AvatarManager {

    fun getAvatar(avatar: Int): Int {

        return when (avatar) {

            1 -> R.drawable.avatar1

            2 -> R.drawable.avatar2

            3 -> R.drawable.avatar3

            4 -> R.drawable.avatar4

            5 -> R.drawable.avatar5

            6 -> R.drawable.avatar6

            else -> R.drawable.avatar1

        }

    }

}