package com.flum.warningcard

import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.flum.warningcard.ui.theme.RedWarning
import com.flum.warningcard.ui.theme.WarningCardTheme
import com.flum.warningcard.ui.theme.YellowWarning


class MainActivity : ComponentActivity() {

    companion object {
        const val WARNING_INTERVAL_MINUTE = 1
    }

    lateinit var sp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        sp = getSharedPreferences("time", MODE_PRIVATE)

        setContent {
            WarningCardTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val cardColor = if(checkRedCard()) RedWarning else YellowWarning
                    Box(modifier = Modifier
                        .padding(5.dp)
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(10))
                        .background(cardColor)) {
                    }
                    sp.edit().putLong("time", System.currentTimeMillis()).apply()
                }
            }
        }
    }

    private fun checkRedCard() : Boolean {
        val showTime = sp.getLong("time", 0)
        if(showTime == 0L) {
            return false
        }
        return System.currentTimeMillis() - showTime < (WARNING_INTERVAL_MINUTE * 60 * 1000)
    }
}