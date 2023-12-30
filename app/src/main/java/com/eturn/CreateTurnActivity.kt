package com.eturn
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.eturn.adapter.AllowGroupAdapter
import com.eturn.data.AllowGroup
import com.eturn.data.User
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject

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
        val scroll = findViewById<ScrollView>(R.id.scrollCreateTurn)
        allowGroupsRec.adapter = allowGroupAdapter
        allowGroupsRec.isNestedScrollingEnabled = false;
        allowGroupAdapter.setItems(allowGroupList)


        val nameTurn : EditText = findViewById(R.id.nameTurn)
        val descTurn : EditText = findViewById(R.id.SettingsTurn)
        val warningText : TextView = findViewById(R.id.nameTurnCreateError)

        val saveButton = findViewById<Button>(R.id.createBtnCreate)
        val cancelButton = findViewById<Button>(R.id.backBtnCreate)
        val warningText1 : TextView = findViewById(R.id.deleteOneGroup)

        val sPref = getSharedPreferences("UserAndTurnInfo", MODE_PRIVATE)
        val idMy = sPref.getLong("USER_ID",0)
        val idUser = idMy
        val type : Boolean = intent.getBooleanExtra("type", true);
        val queue = Volley.newRequestQueue(applicationContext)

        saveButton.setOnClickListener {
            val msg: String = nameTurn.text.toString()
            if (msg.trim().isEmpty() || allowGroupAdapter.getItems().isEmpty()) {
                if (msg.trim().isEmpty()){
                    warningText.visibility = View.VISIBLE
                }
                if (allowGroupAdapter.getItems().isEmpty()){
                    warningText1.text = resources.getString(R.string.warningText3)
                    warningText1.visibility = View.VISIBLE
                }
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    vibrator.vibrate(200)
                }
            } else {
                saveButton.isClickable=false;
                var jsonBody = JsonObject()
                jsonBody.addProperty("name", nameTurn.text.toString())
                jsonBody.addProperty("description", descTurn.text.toString())
                jsonBody.addProperty("creator", idUser)

                var typeString: String = if(type) "EDU"
                else "ORG"
                jsonBody.addProperty("turnType", typeString)
                jsonBody.addProperty("turnAccess", "FOR_ALLOWED_GROUPS")
                jsonBody.add("allowedFaculties", JsonNull.INSTANCE)
                jsonBody.add("allowedDepartments", JsonNull.INSTANCE)
                jsonBody.add("allowedCourses", JsonNull.INSTANCE)

                val list = allowGroupAdapter.getItems()
                if (list.isEmpty()){
                    jsonBody.add("allowedGroups", JsonNull.INSTANCE)
                }
                else {
                    var groups = JsonArray()

                    list.forEach {
                        var group = JsonObject()
                        group.addProperty("id", it.id)
                        group.addProperty("name", it.name)
                        groups.add(group)
                    }
                    jsonBody.add("allowedGroups", groups)
                }

                val requestBody = jsonBody.toString()

                var idTurn = 0L
                val url = "http://90.156.229.190:8089/turn";
                val request = object : StringRequest(
                    Request.Method.POST,
                    url,
                    {
                            result ->
                        run {
                            idTurn = result.toLong()
                            val sPref = getSharedPreferences("UserAndTurnInfo", MODE_PRIVATE)
                            val editor = sPref.edit()
                            editor.putLong("TURN_ID", idTurn)
                            editor.apply()
                            val intent1 = Intent(this, TurnActivity::class.java)
                            intent1.addCategory("CreateTurn")
                            saveButton.isClickable=true;
                            startActivity(intent1)
                            finish()
                        }
                    },
                    {
                            error -> saveButton.isClickable=true;
                    }
                ){
                    override fun getBody(): ByteArray {
                            return requestBody.toByteArray(Charsets.UTF_8)
                    }
                    override fun getBodyContentType(): String {
                        return "application/json; charset=utf-8"
                    }
                }
                queue.add(request)
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


        allowEdit.setOnFocusChangeListener { view, b ->
            run {
                if (b) {
                    scroll.scrollTo(50000, 50000)
                }
            }
        }

        allowEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                scroll.scrollTo(50000, 50000)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                scroll.scrollTo(50000, 50000)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                scroll.scrollTo(50000, 50000)
            }
        })


        allowEdit.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    val s = allowEdit.text.toString()
                    if (s.length == 4 && isNumeric(s)){
                        val url = "http://90.156.229.190:8089/group/$s";
                        val request = object : StringRequest(
                            Request.Method.GET,
                            url,
                            {
                                    result ->
                                run {
                                    warningText1.visibility = View.GONE
                                    val gson = Gson()
                                    val group = gson.fromJson(result, AllowGroup::class.java);
                                    Log.d("MYYY", result)
                                    allowGroupAdapter.addAllowGroup(group)
                                }
                            },
                            {
                                    error ->
                                run{
                                    warningText1.visibility = View.VISIBLE
                                    warningText1.text = resources.getString(R.string.warningCreate)
                                }
                            }
                        ){
                            override fun getBodyContentType(): String {
                                return "application/json; charset=utf-8"
                            }
                        }
                        queue.add(request)
                        scroll.scrollTo(50000, 50000)

//                        warningText1.visibility = View.GONE
//
//                        val g = AllowGroup(0, s)
//                        allowGroupAdapter.addAllowGroup(g)


                        allowEdit.requestFocus()
                        allowEdit.isCursorVisible = true
                    }
                    else if ((event.action == KeyEvent.ACTION_DOWN)){
                        warningText1.text = resources.getString(R.string.warningText1)
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