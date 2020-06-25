package com.rosen.ezy.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rosen.ezy.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clicksHandler()
    }

    private fun clicksHandler() {
        my_incomes.setOnClickListener { showMyIncomes() }
        my_expenditures.setOnClickListener {  }
    }

    private fun showMyIncomes() {
        startActivity(Intent(applicationContext, ListsActivity::class.java))
    }

}
