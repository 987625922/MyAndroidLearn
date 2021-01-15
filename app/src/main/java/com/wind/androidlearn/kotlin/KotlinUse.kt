package com.wind.androidlearn.kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wyt.zdf.myapplication.R

const val stat = ""

class MainActivity : AppCompatActivity() {
    val array1 = intArrayOf(1, 2, 3)

    companion object {
        fun newIntent(context: Context): Intent {
            var intent = Intent(context, MainActivity::class.java)
            return intent
        }

        fun intentTo(context: Context) {
            context.startActivity(newIntent(context))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)
    }


    fun test1() {
        var count = 1

        when (count) {
            0 -> println(count)
            in 1..2 -> println(count)
            else -> println(count)
        }
    }

}