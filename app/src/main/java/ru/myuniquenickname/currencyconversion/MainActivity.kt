package ru.myuniquenickname.currencyconversion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.myuniquenickname.currencyconversion.presentation.CurrencyListFragment

class MainActivity : AppCompatActivity() {
    private val fragmentMoviesList = CurrencyListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container_fragment, fragmentMoviesList)
                .commit()
        }
    }
}
