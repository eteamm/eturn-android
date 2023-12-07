package com.eturn


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.eturn.R

class ChooseTurnActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_turn)

        var teachButton : Button = findViewById(R.id.teachBtn)
        var orgButton : Button = findViewById(R.id.orgBtn)

        teachButton.setOnClickListener(){
            val intent = Intent(this, CreateTurnActivity::class.java)
            startActivity(intent)
            finish()
        }

        orgButton.setOnClickListener(){
            val intent = Intent(this, CreateTurnActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}