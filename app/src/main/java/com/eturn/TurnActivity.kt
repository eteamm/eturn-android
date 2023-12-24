package com.eturn
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.eturn.adapter.PositionsAdapter
import com.eturn.data.Positions
import com.eturn.data.Turn
import com.eturn.data.User
import com.eturn.data.YourPosition
import com.google.gson.Gson
import org.w3c.dom.Text
import java.util.*


class TurnActivity : AppCompatActivity() {

    private lateinit var inAnimation: Animation
    private lateinit var outAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turn)
        val myJson = """
        [{
          id: 1, 
          name: "Васильев Андрей Антонович", 
          groupNumber: 2391,
          idUser: 5
        },
        {
          id: 4, 
          name: "Ненарокова Маргарита Олеговна", 
          groupNumber: 3242,
          idUser: 2
        },
        {
          id: 1, 
          name: "Глоба Валерия Владимировна", 
          groupNumber: 2391,
          idUser: 1
        },
        {
          id: 1, 
          name: "iu", 
          groupNumber: 2391,
          idUser: 5
        },
        {
          id: 4, 
          name: "Ненарокова Маргарита Олеговна", 
          groupNumber: 3242,
          idUser: 2
        },
        {
          id: 1, 
          name: "Глоба Валерия Владимировна", 
          groupNumber: 2391,
          idUser: 1
        },
        {
          id: 4, 
          name: "Ненарокова Маргарита Олеговна", 
          groupNumber: 3242,
          idUser: 2
        },
        {
          id: 1, 
          name: "Глоба Валерия Владимировна", 
          groupNumber: 2391,
          idUser: 1
        },
        {
          id: 4, 
          name: "Ненарокова Маргарита Олеговна", 
          groupNumber: 3242,
          idUser: 2
        },
        {
          id: 1, 
          name: "Глоба Валерия Владимировна", 
          groupNumber: 2391,
          idUser: 1
        },
        {
          id: 4, 
          name: "Ненарокова Маргарита Олеговна", 
          groupNumber: 3242,
          idUser: 2
        },
        {
          id: 1, 
          name: "Глоба Валерия Владимировна", 
          groupNumber: 2391,
          idUser: 1
        }]
        """.trimIndent()

        val jsontemp = """
        {
            idPos: 1,
            isFirst: true,
            name: "Иванов Юрий Владимирович",
            groupNumber: "2391",
            number: 1,
            isGo: false
        }
        """.trimIndent()


        val Pencil: ImageView = findViewById(R.id.editTurnImg)
        var gsonYourPos = Gson()
        var yourPos = gsonYourPos?.fromJson(jsontemp, YourPosition::class.java)



        val People: Array<String> = arrayOf(
            "человек",
            "человек",
            "человека",
            "человека",
            "человека",
            "человек",
            "человек",
            "человек",
            "человек",
            "человек"
        )
        val myTurnName: TextView = findViewById(R.id.nameTurntxt)
        val myTurnAuthor: TextView = findViewById(R.id.nameTeachertxt)
        val myTurnDescription: TextView = findViewById(R.id.descriptionBoxtxt)
        val myTurnNumberOfPeople: TextView = findViewById(R.id.numberPeopletxt)
        val myTurnPeopleTextView: TextView = findViewById(R.id.peopleBoxtxt)
        val category = intent.categories
        val name = "f "
        val desc = "ff "
        val author : String?

        val sPref = getSharedPreferences("UserAndTurnInfo", MODE_PRIVATE)
        val idMy = sPref.getLong("USER_ID",0)
        val idTurnThis = sPref.getLong("TURN_ID",0)
        var loggedUserId = idMy
        var creatorUserId = 0L

        var url = "http://90.156.229.190:8089/turn/$idTurnThis";
        val queue = Volley.newRequestQueue(applicationContext)
        val request = object : StringRequest(
            Request.Method.GET,
            url,
            {
                    result ->
                run {
                    val gson = Gson()
//                        val c = result.toByteArray(Charsets.ISO_8859_1)
//                        val user = c.toString(Charsets.UTF_8)
                    val turnInfo = gson?.fromJson(result, Turn::class.java);
                    if (turnInfo != null) {
                        myTurnName.text = turnInfo.name
                        myTurnAuthor.text = turnInfo.creator
                        if (!turnInfo.description.isEmpty()){
                            val description = resources.getString(R.string.descriptionBoxTurnCurrent,turnInfo.description)
                            myTurnDescription.text = description
                        }
                        myTurnNumberOfPeople.text = turnInfo.countUsers.toString()
                        myTurnPeopleTextView.text = People[turnInfo.countUsers % 10]
                        creatorUserId = turnInfo.userId
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

        var gsonMainqueue = Gson()
        var responseMainqueue = gsonMainqueue?.fromJson(myJson, Array<Positions>::class.java)?.toList()
        var count = 0
        var admin = 0 // модератор!!!
        //
        if (loggedUserId==creatorUserId){
            admin = 2
        }
        //
        inAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_in)
        outAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_out)
        //
        val ButtonToPeople: Button = findViewById(R.id.turnPeopleBtn)
        val recyclerView: RecyclerView = findViewById(R.id.PositionsRec)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = false
        //
        val WarningTxt: CardView = findViewById(R.id.WarningJoinTxt)
        val ShareBtn1: Button = findViewById(R.id.ShareBtn)
        val positionsAdapter = PositionsAdapter(this, admin)
        val positionsList = mutableListOf<Positions>()
        var b = true
        responseMainqueue?.forEach {
            if (loggedUserId != it.idUser && b) {
                count++
            } else {
                b = false
            }
            var position = Positions(it.id, it.name, it.groupNumber, it.idUser)
            positionsList.add(position)  // если пользователь авторизован то не добавляем позицию
        }
        recyclerView.adapter = positionsAdapter
        positionsAdapter.setItems(positionsList, loggedUserId, false) // добавлояем bool переменную
        val pos: Array<String> = arrayOf("позиций","позиция","позиции","позиции","позиции","позиций","позиций","позиций","позиций","позиций")

        //
            ShareBtn1.visibility = View.GONE


        if (loggedUserId == creatorUserId) {
            Pencil.visibility = View.VISIBLE
        }
        else{
            Pencil.visibility = View.GONE
        }
        //
        val JoinBtn: Button = findViewById(R.id.createTurnBtn)
        JoinBtn.setOnClickListener() {
            JoinBtn.isClickable = false
            val positionNew = Positions(
                9,
                "Иванов Юрий Владимирович",
                "2391",
                loggedUserId
            ) // idUser для каждого пользователя свой
            var temp = positionsAdapter.addPosition(positionNew)
            if (temp != 0) {
                temp++
                val str = String.format(getString(R.string.warningTxtTurn), temp)
                val textWarn = findViewById<TextView>(R.id.textWarnTurn)
                textWarn.text=str
                WarningTxt.visibility = View.VISIBLE

                Handler().postDelayed({
                    WarningTxt.startAnimation(outAnimation)
                    Handler().postDelayed({
                        WarningTxt.visibility = View.GONE
                        JoinBtn.isClickable = true
                    }, 2000)
                }, 3000)
            }

        }
        ButtonToPeople.setOnClickListener() {
            val intent = Intent(this, MembersActivity::class.java)
            startActivity(intent)
            finish()
        }

        Pencil.setOnClickListener {
            val intent2 = Intent(this, EditTurnActivity::class.java)
            intent2.putExtra("Top", myTurnName.getText().toString())
            intent2.putExtra("About", myTurnDescription.getText().toString())
            startActivity(intent2)
            finish()
        }

        val goback: ImageView = findViewById(R.id.backTurnImageView)
        goback.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val cancelButton : Button = findViewById(R.id.exitTurnBtn)
        cancelButton.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val exitcurTurn : ImageView = findViewById(R.id.delBtn)
        val curPeoplebox : CardView = findViewById(R.id.curBox)
        val yourTurn : TextView = findViewById(R.id.yourTurnTxt)
        var ispressed = false
        val curjoinBtn : Button = findViewById(R.id.curJoinBtn)
        val nameTurn : TextView = findViewById(R.id.numberTxt)
        val myPosNumber2 : TextView = findViewById(R.id.positionNumberTxt)
        nameTurn.text = yourPos?.name
        myPosNumber2.text = yourPos?.groupNumber
        var numberpeopleafter : TextView = findViewById(R.id.timeDuration2)
        val timeDuration : TextView = findViewById(R.id.timeDuration3)

        if(yourPos?.isFirst == true){
            positionsList.removeAt(0)
            positionsAdapter.setItems(positionsList, loggedUserId, yourPos?.isFirst == true)
            if(yourPos?.isGo == false){
                curjoinBtn.visibility = View.VISIBLE
                numberpeopleafter.visibility = View.GONE
                timeDuration.visibility = View.GONE
                curjoinBtn.setOnClickListener(){
                    if(!ispressed){
                        curjoinBtn.text = "выйти"
                        curjoinBtn.setTextColor(Color.parseColor("#F4694D"))
                        ispressed = true
                    }
                    else {
                        curPeoplebox.visibility = View.GONE
                        yourTurn.visibility = View.GONE
                        positionsAdapter.numberChange(false)
                    }
                }
            }
        }
        else{
            val numbr = String.format(getString(R.string.numberofpeople1), yourPos?.number)
            numberpeopleafter.text = numbr
        }








    }


}
