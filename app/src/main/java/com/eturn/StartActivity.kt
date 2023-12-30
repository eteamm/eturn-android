package com.eturn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.eturn.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val sPref = getSharedPreferences("UserAndTurnInfo", MODE_PRIVATE)
        val idMy = sPref.getLong("USER_ID",0)

        if (idMy!=0L){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val ToMainScreenBtn = findViewById<Button>(R.id.ToMainScreenBtn)
        ToMainScreenBtn.setOnClickListener {
            val intent = Intent(this, EnterActivity::class.java)
            startActivity(intent)
        }
    }
}