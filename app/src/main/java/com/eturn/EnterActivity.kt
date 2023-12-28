package com.eturn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.eturn.data.User
import com.google.gson.Gson

class EnterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)
        val EnterButton : Button = findViewById(R.id.EnterBtn)
        val loginText : EditText = findViewById(R.id.login)
        val passwordText : EditText = findViewById(R.id.password)
        val errorLogin : TextView = findViewById(R.id.ErrorLoginEnter)
        val errorPassword: TextView = findViewById(R.id.errorPasswordEnter)




        EnterButton.setOnClickListener(){
            errorLogin.visibility = View.GONE
            errorPassword.visibility = View.GONE
            val string1 : String = loginText.text.toString()
            val string2 : String = passwordText.text.toString()

            var url = "http://90.156.229.190:8089/user/login?login=$string1&password=$string2"
            val queue = Volley.newRequestQueue(applicationContext)
// GET POST PUT DELETE
            val request = object : StringRequest(
                Request.Method.GET,
                url,
                {
                        result ->
                    run {
                        val id = result.toLong()
                        if (id==-1L){
                            errorPassword.visibility = View.VISIBLE
                        }
                        else if (id==0L){
                            errorLogin.visibility = View.VISIBLE
                        }
                        else if (id>0){
                            val intent = Intent(this, MainActivity::class.java)

                            val sPref = getSharedPreferences("UserAndTurnInfo", MODE_PRIVATE)
                            val editor = sPref.edit()
                            editor.putLong("USER_ID", id)
                            editor.apply()
                            startActivity(intent)
                        }
                    }
                },
                {
                        error -> Log.d("MYYY", "$error")
                }
            ){
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
            }
            queue.add(request)
        }

    }


}