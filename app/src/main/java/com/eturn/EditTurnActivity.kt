package com.eturn

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eturn.R
import com.eturn.adapter.AllowGroupAdapter
import com.eturn.data.AllowGroup


class EditTurnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turn_edit)

        val noName = findViewById<TextView>(R.id.noName)
        val noGroups = findViewById<TextView>(R.id.noGroups)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val nameMassage = findViewById<EditText>(R.id.queueNameBlock)
        val goToMembers = findViewById<ImageView>(R.id.goToMembersBtn)
        val aboutQueue = findViewById<EditText>(R.id.queueDescriptionBlock)
        val warningText1 = findViewById<TextView>(R.id.toLessNum)
        val warningText2 = findViewById<TextView>(R.id.deleteOneGroup)

        //перенос инфы с TurnActivity
        val inAllInf = intent //интент для получения
        val top = inAllInf.getStringExtra("Top") //появление названия очереди
        val about = inAllInf.getStringExtra("About")
        //var descPiece = about.substring(11)
        val author = inAllInf.getStringExtra("Author")
        nameMassage.setText(top)
        aboutQueue.setText(about?.substring(11))



        noName.visibility = TextView.GONE
        noGroups.visibility = TextView.GONE

        saveButton.setOnClickListener {
            val msg: String = nameMassage.text.toString()
            if (msg.trim().isEmpty()) {
                noName.visibility = EditText.VISIBLE
            } else {
                //отправка инфы в TurnActivity
                val outAllInf = Intent(this, TurnActivity::class.java) //интент для отправки
                outAllInf.addCategory("EditTurn")
                outAllInf.putExtra("Top", nameMassage.getText().toString())
                outAllInf.putExtra("About", aboutQueue.getText().toString())
                outAllInf.putExtra("Author", author.toString())
                startActivity(outAllInf)
                finish()
            }
        }

        goToMembers.setOnClickListener {
            val intent3 = Intent(this, MembersActivity::class.java)
            startActivity(intent3)
            finish()
        }

        cancelButton.setOnClickListener {
            val outAllInf = Intent(this, TurnActivity::class.java) //интент для отправки
            outAllInf.addCategory("EditTurn")
            outAllInf.putExtra("Top", top.toString())
            outAllInf.putExtra("About", about.toString())
            outAllInf.putExtra("Author", author.toString())
            startActivity(outAllInf)
            finish()
        }

        val allowEdit = findViewById<EditText>(R.id.queueGroupsBlock)
        val allowGroupsRec = findViewById<RecyclerView>(R.id.listOfGroups)
        allowGroupsRec.layoutManager = LinearLayoutManager(this)
        val allowGroupAdapter = AllowGroupAdapter(this)
        allowGroupsRec.adapter = allowGroupAdapter
        allowGroupsRec.isNestedScrollingEnabled = false;

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

                        //allowGroupAdapter.itemCount

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
//                        allowEdit.requestFocus()
//                        allowEdit.isCursorVisible = true

                    }
                    allowEdit.setText("")
                    //warningText2.visibility = View.VISIBLE
                    allowEdit.requestFocus()
                    allowEdit.isCursorVisible = true
                    return true
                }
                if (allowGroupAdapter.itemCount == 0) {
                    noGroups.visibility = View.VISIBLE
                }
                return false

            }
        })
    }


}