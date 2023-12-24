package com.eturn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.text.TextWatcher
import android.view.View
import android.widget.TextView

class EnterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)
        val EnterButton : Button = findViewById(R.id.EnterBtn)
        val loginText : EditText = findViewById(R.id.login)
        val passwordText : EditText = findViewById(R.id.password)



        var string1 : String = loginText.text.toString()
        var string2 : String = passwordText.text.toString()

        loginText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                string1 = loginText.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        passwordText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                string2 = passwordText.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        EnterButton.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)

            val sPref = getSharedPreferences("UserAndTurnInfo", MODE_PRIVATE)
            val editor = sPref.edit()
            editor.putLong("USER_ID", 1)
            editor.apply()
            if(string1 == "admin" && string2 == "admin"){
                startActivity(intent)
            }
        }

    }


}