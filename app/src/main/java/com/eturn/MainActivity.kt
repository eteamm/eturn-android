package com.eturn

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.eturn.adapter.TurnAdapter
import com.eturn.data.Turn
import com.eturn.data.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//import okhttp3.Call
//import okhttp3.Callback
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.Response


class MainActivity : AppCompatActivity() {
    var loggedUserId = 1L
    var logged = 1L
    val turnAdapter = TurnAdapter(this, loggedUserId)
    override fun onResume() {
        super.onResume()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sPref = getSharedPreferences("UserAndTurnInfo", MODE_PRIVATE)
        val idMy = sPref.getLong("USER_ID",0)
        logged = idMy

             val progress = findViewById<ProgressBar>(R.id.progressBarTurn)
            var url = "http://90.156.229.190:8089/user/$logged";
            val queue = Volley.newRequestQueue(applicationContext)
// GET POST PUT DELETE
            val request = object : StringRequest(
                Request.Method.GET,
                url,
                {
                    result ->
                    run {
                        val gson = Gson()
//                        val c = result.toByteArray(Charsets.ISO_8859_1)
//                        val user = c.toString(Charsets.UTF_8)
                        val userInfo = gson?.fromJson(result, User::class.java);
                        if (userInfo != null) {
                            loggedUserId = userInfo.id
                            val nameText = findViewById<TextView>(R.id.userNameMainTxt)
//                            nameText.text = result;
                            nameText.text = userInfo.name;

                            val roleText = findViewById<TextView>(R.id.statusMainTxt)
                            roleText.text = userInfo.role

                            val groupText = findViewById<TextView>(R.id.groupMainTxt)
                            groupText.text = userInfo.group
                        };
                    }
                },
                {
                    error -> Log.d("MYYY", "$error")
                }
            ){
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
//                override fun getParams(): MutableMap<String, String>? {
//                    val params = HashMap<String, String>()
//                    params.put("Content-type","application/json; charset=utf-8")
//                    return params
//                }
            }
            queue.add(request)

        var AccessBtns = true
        var TypeBtns = true
        val bcreateturn = findViewById<Button>(R.id.CreateTurnBtn)
        val MyTurnsBtn = findViewById<Button>(R.id.bMy)
        val InDostupBtn = findViewById<Button>(R.id.MainScreenInDostupBtn)
        val StudiedBtn = findViewById<Button>(R.id.bStud)
        val OrganizationBtn = findViewById<Button>(R.id.bOrg)
        val SearchTurns = findViewById<EditText>(R.id.NameForSearch)

        val errorNotFound = findViewById<TextView>(R.id.turnNotFoundError)

        val view : View = findViewById(R.id.view)
        val view2 : View = findViewById(R.id.view2)
        val view3 : View = findViewById(R.id.view3)
        val view4 : View = findViewById(R.id.view4)



        val bExit = findViewById<ImageView>(R.id.exitImageView)
        bExit.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish()
        }


        bcreateturn.setOnClickListener {
            val intent = Intent(this, ChooseTurnActivity::class.java)
            intent.putExtra("idUser", logged)
            startActivity(intent)
            finish()
        }

        OrganizationBtn.setOnClickListener {
            progress.visibility = View.VISIBLE
            TypeBtns = false
//            AccessBtns = false
            checkFilter(AccessBtns,TypeBtns, turnAdapter, queue)
            view2.setBackgroundResource(R.drawable.button_right_clicked)
            view.setBackgroundResource(R.drawable.button_left_noclicked)
//            view3.setBackgroundResource(R.drawable.button_left_noclicked)
//            view4.setBackgroundResource(R.drawable.button_right_noclicked)
            OrganizationBtn.setTextColor(Color.parseColor("#20232A"))
            StudiedBtn.setTextColor(Color.parseColor("#9ADBFF"))
//            MyTurnsBtn.setTextColor(Color.parseColor("#9ADBFF"))
//            InDostupBtn.setTextColor(Color.parseColor("#9ADBFF"))


        }
        StudiedBtn.setOnClickListener {
            progress.visibility = View.VISIBLE
//            AccessBtns = true
            TypeBtns = true
            checkFilter(AccessBtns,TypeBtns, turnAdapter, queue)
            view2.setBackgroundResource(R.drawable.button_right_noclicked)
            view.setBackgroundResource(R.drawable.button_left_clicked)
//            view3.setBackgroundResource(R.drawable.button_left_noclicked)
//            view4.setBackgroundResource(R.drawable.button_right_noclicked)
            StudiedBtn.setTextColor(Color.parseColor("#20232A"))
            OrganizationBtn.setTextColor(Color.parseColor("#9ADBFF"))
//            MyTurnsBtn.setTextColor(Color.parseColor("#9ADBFF"))
//            InDostupBtn.setTextColor(Color.parseColor("#9ADBFF"))

        }
        MyTurnsBtn.setOnClickListener {
            progress.visibility = View.VISIBLE
            AccessBtns = true
            checkFilter(AccessBtns,TypeBtns, turnAdapter, queue)
            view3.setBackgroundResource(R.drawable.button_left_clicked)
//            view.setBackgroundResource(R.drawable.button_left_noclicked)
//            view2.setBackgroundResource(R.drawable.button_right_noclicked)
            view4.setBackgroundResource(R.drawable.button_right_noclicked)
            MyTurnsBtn.setTextColor(Color.parseColor("#20232A"))
//            StudiedBtn.setTextColor(Color.parseColor("#9ADBFF"))
//            OrganizationBtn.setTextColor(Color.parseColor("#9ADBFF"))
            InDostupBtn.setTextColor(Color.parseColor("#9ADBFF"))
        }

        InDostupBtn.setOnClickListener {
            progress.visibility = View.VISIBLE
//            TypeBtns = false
            AccessBtns = false
            checkFilter(AccessBtns,TypeBtns, turnAdapter, queue)
            view4.setBackgroundResource(R.drawable.button_right_clicked)
//            view.setBackgroundResource(R.drawable.button_left_noclicked)
//            view2.setBackgroundResource(R.drawable.button_right_noclicked)
            view3.setBackgroundResource(R.drawable.button_left_noclicked)
            InDostupBtn.setTextColor(Color.parseColor("#20232A"))
//            StudiedBtn.setTextColor(Color.parseColor("#9ADBFF"))
            MyTurnsBtn.setTextColor(Color.parseColor("#9ADBFF"))
//            OrganizationBtn.setTextColor(Color.parseColor("#9ADBFF"))
        }

        SearchTurns.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    val s = SearchTurns.text.toString()

                    if ((event.action == KeyEvent.ACTION_DOWN)){
                        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
                        if (Build.VERSION.SDK_INT >= 26) {
                            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                        } else {
                            vibrator.vibrate(200)
                        }
//                        allowEdit.requestFocus()
//                        allowEdit.isCursorVisible = true

                    }
                    SearchTurns.setText("")
                    SearchTurns.requestFocus()
                    SearchTurns.isCursorVisible = true
                    return true
                }



                return false
            }
        })

        val recyclerView: RecyclerView = findViewById(R.id.turnsRec)
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager;

        recyclerView.adapter = turnAdapter

        var url2 = "http://90.156.229.190:8089/turn?userId=$logged&type=edu&access=participates";
        val requestTurnGet = object : StringRequest(
            Request.Method.GET,
            url2,
            {
                    result ->
                run {
                    val gson = Gson()
                    if (result.equals("[]")){
                        progress.visibility = View.GONE
                        errorNotFound.visibility = View.VISIBLE;
                    }
//                    val c = result.toByteArray(Charsets.ISO_8859_1)
//                    val turns = c.toString(Charsets.UTF_8)
                    val type = object : TypeToken<MutableList<Turn>>(){}.type
                    val turnsList : MutableList<Turn>? = gson?.fromJson(result, type);
                    if (turnsList!=null){
                        turnAdapter.setItems(turnsList, true)
                        recyclerView.visibility = View.VISIBLE
                    }
                    else{
                        errorNotFound.visibility = View.VISIBLE;
                    }
                    progress.visibility = View.GONE

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
        queue.add(requestTurnGet)
        recyclerView.isNestedScrollingEnabled = false;

    }

    private fun checkFilter(AccessBtns : Boolean, TypeBtns : Boolean, turnAdapter: TurnAdapter, queue : RequestQueue){
        val errorNotFound = findViewById<TextView>(R.id.turnNotFoundError)
        val RecView = findViewById<RecyclerView>(R.id.turnsRec)
        RecView.visibility = View.GONE
        val typeAccessList : Boolean;
        val typeAccess : String

        if (AccessBtns){
            typeAccess="participates"
            typeAccessList=true
        }
        else{
            typeAccess="available"
            typeAccessList=false
        }

        val typeTurn : String = if (TypeBtns){
            "edu"
        } else{
            "org"
        }

//        if (AccessBtns && TypeBtns) {
//            type = true;
//            typeAccess="participates"
//            typeTurn="edu"
//        } else if (AccessBtns && !TypeBtns) {
//            type = true;
//            typeAccess="participates"
//            typeTurn="org"
//        } else if (!AccessBtns && TypeBtns) {
//            type = false
//            typeAccess="available"
//            typeTurn="edu"
//        } else if (!AccessBtns && !TypeBtns) {
//            type = false
//            typeAccess="available"
//            typeTurn="org"
//        }
        errorNotFound.visibility = View.GONE;
        val url2 = "http://90.156.229.190:8089/turn?userId=$logged&type=$typeTurn&access=$typeAccess";
        val progress = findViewById<ProgressBar>(R.id.progressBarTurn)
        val requestTurnGet = object : StringRequest(
            Request.Method.GET,
            url2,
            {
                    result ->
                run {
                    val gson = Gson()
                    if (result.equals("[]")){
                        errorNotFound.visibility = View.VISIBLE;
                        progress.visibility = View.GONE
                    }
//                    val c = result.toByteArray(Charsets.ISO_8859_1)
//                    val turns = c.toString(Charsets.UTF_8)
                    val type = object : TypeToken<MutableList<Turn>>(){}.type
                    val turnsList : MutableList<Turn>? = gson?.fromJson(result, type);
                    if (turnsList!=null){
                        turnAdapter.setItems(turnsList, typeAccessList)
                        RecView.visibility = View.VISIBLE
                    }
                    else{
                        RecView.visibility = View.GONE
                        errorNotFound.visibility = View.VISIBLE;
                    }
                    progress.visibility = View.GONE
                }
            },
            {
                    error -> run {
                    Log.d("MYYY", "$error")
                    errorNotFound.visibility = View.VISIBLE;
                }
            }
        ){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }
        queue.add(requestTurnGet)


    }


}