package com.eturn
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eturn.R
import com.eturn.adapter.AllowGroupAdapter
import com.eturn.data.AllowGroup
class CreateTurnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turn_create)
        val allowGroupsRec = findViewById<RecyclerView>(R.id.allowRec)
        allowGroupsRec.setHasFixedSize(true)
        allowGroupsRec.layoutManager = LinearLayoutManager(this)
        val allowGroupList = mutableListOf<AllowGroup>()
        val allowEdit = findViewById<EditText>(R.id.createAllowGroup)
        val allowGroupAdapter = AllowGroupAdapter(this)
        allowGroupsRec.adapter = allowGroupAdapter
        allowGroupsRec.isNestedScrollingEnabled = false;
        allowGroupAdapter.setItems(allowGroupList)


        val nameTurn : EditText = findViewById(R.id.NameTurn)
        val descTurn : EditText = findViewById(R.id.SettingsTurn)
        val warningText : TextView = findViewById(R.id.textView5)

        val saveButton = findViewById<Button>(R.id.createBtnCreate)
        val cancelButton = findViewById<Button>(R.id.backBtnCreate)
        val warningText1 : TextView = findViewById(R.id.deleteOneGroup)

        val idUser = intent.getIntExtra("idUser",-1);

        saveButton.setOnClickListener {
            val msg: String = nameTurn.text.toString()
            if (msg.trim().isEmpty()) {
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator.vibrate(200)
                }
            } else {
                val intent1 = Intent(this, TurnActivity::class.java)
                intent1.addCategory("CreateTurn")
                intent1.putExtra("NameTurn", nameTurn.text.toString())
                intent1.putExtra("About", descTurn.text.toString())
                intent1.putExtra("Author", idUser)
                startActivity(intent1)
                finish()
            }
        }

        cancelButton.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
            finish()
        }


        nameTurn.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                saveButton.isClickable = nameTurn.length() != 0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(nameTurn.length() != 0){
                    warningText.visibility = View.GONE
                }
                else {
                    warningText.visibility = View.VISIBLE
                }
            }
        })

        fun isNumeric(s: String): Boolean {
            return try {
                s.toDouble()
                true
            } catch (e: NumberFormatException) {
                false
            }
        }


        allowEdit.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    val s = allowEdit.text.toString()
                    if (s.length == 4 && isNumeric(s)){
                        warningText1.visibility = View.GONE

                        val g = AllowGroup(0, s.toInt())
                        allowGroupAdapter.addAllowGroup(g)


                        allowEdit.requestFocus()
                        allowEdit.isCursorVisible = true
                    }
                    else if ((event.action == KeyEvent.ACTION_DOWN)){
                        warningText1.visibility = View.VISIBLE
                        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                        if (Build.VERSION.SDK_INT >= 26) {
                            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            vibrator.vibrate(200)
                        }


                    }
                    allowEdit.setText("")
                    allowEdit.requestFocus()
                    allowEdit.isCursorVisible = true
                    return true
                }



                return false
            }
        })


    }


}