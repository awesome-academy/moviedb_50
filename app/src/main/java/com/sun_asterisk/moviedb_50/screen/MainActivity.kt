package com.sun_asterisk.moviedb_50.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.screen.navigation.ContainerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(
                R.id.mainFrameLayout,
                ContainerFragment()
            )
            .commit()
    }
}
