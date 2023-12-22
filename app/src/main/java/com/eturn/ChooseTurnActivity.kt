package com.eturn


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView
import com.eturn.R

class ChooseTurnActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_turn)

        var teachButton : CardView = findViewById(R.id.TurnButtonChoose1)
        var orgButton : CardView = findViewById(R.id.TurnButtonChoose2)
        var id = intent.getIntExtra("idUser",-1)
        teachButton.setOnClickListener(){
            val intent = Intent(this, CreateTurnActivity::class.java)
            intent.putExtra("idUser", id)
            intent.putExtra("type", true);
            startActivity(intent)
            finish()
        }

//        orgButton.setOnClickListener(){
//            val intent = Intent(this, CreateTurnActivity::class.java)
//            intent.putExtra("type", false);
//            startActivity(intent)
//            finish()
//        }

    }
}