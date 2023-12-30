package com.eturn
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.eturn.adapter.PositionsAdapter
import com.eturn.data.Turn
import com.eturn.data.Position
import com.eturn.data.PositionFirst
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TurnActivity : AppCompatActivity() {

    private lateinit var inAnimation: Animation
    private lateinit var outAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turn)



        val Pencil: ImageView = findViewById(R.id.editTurnImg)


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
        val name = ""
        val desc = ""
        val author : String?

        val sPref = getSharedPreferences("UserAndTurnInfo", MODE_PRIVATE)
        val idMy = sPref.getLong("USER_ID",0)
        val idTurnThis = sPref.getLong("TURN_ID",0)
        val userName = sPref.getString("USERNAME", "Ошибка вывода информации")
        val userGroup = sPref.getString("USERGROUP", "Ошибка");

        var loggedUserId = idMy
        var creatorUserId = 0L
        var admin = 0

        val firstPositionBlock : CardView = findViewById(R.id.curBox);
        val firstPositionBlockText : LinearLayout = findViewById(R.id.firstPositionText)
        val infoAboutFirstPosition : LinearLayout = findViewById(R.id.infoAboutFirstPosition);
        val startButton : Button = findViewById(R.id.curJoinBtn)
        val infoAboutCountPositionsAtFirst : TextView = findViewById(R.id.timeDuration2)
        val firstPositionName : TextView = findViewById(R.id.numberTxt)
        val firstPositionGroup : TextView = findViewById(R.id.positionNumberTxt)
        val firstPositionIndex : TextView = findViewById(R.id.posId)

        // TODO сделать изменение текста в первой позиции

        var started = false;
        var firstPositionId = 0L;

        val recyclerView: RecyclerView = findViewById(R.id.PositionsRec)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = false
        var positionsAdapter = PositionsAdapter(this, 0)
        var url = "http://90.156.229.190:8089/turn/$idTurnThis";
        val queue = Volley.newRequestQueue(applicationContext)
        val request = object : StringRequest(
            Request.Method.GET,
            url,
            {
                    result ->
                run {
                    val gson = Gson()
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

                    if (loggedUserId==creatorUserId){
                        admin = 2
                    }
                    if (loggedUserId == creatorUserId) {
                        Pencil.visibility = View.VISIBLE
                    }
                    else{
                        Pencil.visibility = View.GONE
                    }
                    positionsAdapter = PositionsAdapter(this, admin)
                    recyclerView.adapter = positionsAdapter
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

        val errorTurn : TextView = findViewById(R.id.errorTurnMessage)
        val progressTurn : ProgressBar = findViewById(R.id.progressTurn)
//        errorTurn.visibility = View.VISIBLE
//        progressTurn.visibility = View.GONE

        var url2 = "http://90.156.229.190:8089/position/all/$idTurnThis";
        val request2 = object : StringRequest(
            Request.Method.GET,
            url2,
            {
                    result ->
                run {
                    progressTurn.visibility = View.GONE
                    var exist = true
                    if (result=="[]" || result == null){
                        exist=false
                        errorTurn.visibility = View.VISIBLE
                        progressTurn.visibility = View.GONE
                    }
                    if (exist){
                        val gson = Gson()
                        val type = object : TypeToken<MutableList<Position>>(){}.type
                        val positions : MutableList<Position> = gson?.fromJson(result, type)!!;
                        if (positions == null){
                            errorTurn.visibility = View.VISIBLE
                            progressTurn.visibility = View.GONE
                        }
                        else{
                            progressTurn.visibility = View.GONE
                            val b : Boolean = positions[0].userId==loggedUserId
                            if (b){
                                positions.removeAt(0)
                            }
                            if (positions!=null){
                                positionsAdapter.setItems(positions, loggedUserId, b)
                                recyclerView.visibility = View.VISIBLE
                            }
                            else{
                                errorTurn.visibility = View.VISIBLE
                                progressTurn.visibility = View.GONE
                            }

                        }
                    }
                    else{
                        errorTurn.visibility = View.VISIBLE
                        progressTurn.visibility = View.GONE
                    }

                }
            },
            {
                    error -> run{
                errorTurn.visibility = View.VISIBLE
                progressTurn.visibility = View.GONE
            }
            }
        ){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }
        queue.add(request2)


        //
        inAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_in)
        outAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_out)
        //
        val ButtonToPeople: Button = findViewById(R.id.turnPeopleBtn)
        val WarningTxt: CardView = findViewById(R.id.WarningJoinTxt)
        val ShareBtn1: Button = findViewById(R.id.ShareBtn)


        var b = true
         // добавлояем bool переменную
        val pos: Array<String> = arrayOf("позиций","позиция","позиции","позиции","позиции","позиций","позиций","позиций","позиций","позиций")

        //
            ShareBtn1.visibility = View.GONE



        //
        var firstExist = false;
        val JoinBtn: Button = findViewById(R.id.createPositionBtn)
        JoinBtn.setOnClickListener() {
            val textWarn = findViewById<TextView>(R.id.textWarnTurn)
            JoinBtn.isClickable = false
            val url5 = "http://90.156.229.190:8089/position?idTurn=$idTurnThis&idUser=$loggedUserId";
            val request5 = object : StringRequest(
                Request.Method.POST,
                url5,
                {
                        result ->
                    run {
                        val idCreatedPosition = result.toLong()
                        var countToStay = 0L;
                        if (idCreatedPosition<=0){
                            countToStay = idCreatedPosition*(-1);
                            textWarn.text = resources.getString(R.string.warningTxtTurn, countToStay)
                            WarningTxt.visibility = View.VISIBLE
                            Handler().postDelayed({
                                WarningTxt.startAnimation(outAnimation)
                                Handler().postDelayed({
                                    WarningTxt.visibility = View.GONE
                                    JoinBtn.isClickable = true
                                }, 2000)
                            }, 3000)
                        }
                        else{
                            if (userName!=null && userGroup!=null){
                                if (positionsAdapter.itemCount==0){
                                    firstExist = true
                                    val positionFirst = PositionFirst(idCreatedPosition,userName,userGroup,1, false, loggedUserId, 0)

                                    firstPositionName.text = positionFirst.name
                                    firstPositionGroup.text = positionFirst.group
                                    firstPositionIndex.text="#${positionFirst.number}"
                                    firstExist = true
                                    firstPositionId = result.toLong()
                                    infoAboutFirstPosition.visibility=View.GONE
                                    startButton.text = resources.getString(R.string.Enter2)
                                    startButton.visibility=View.VISIBLE
                                    positionsAdapter.numberChange(true)
                                    errorTurn.visibility = View.VISIBLE

                                }
                                else{
                                    if (positionsAdapter.itemCount<20){
                                        val position = Position(idCreatedPosition,userName,userGroup,positionsAdapter.getLastNumber()+1, false, loggedUserId)
                                        positionsAdapter.addPosition(position)
                                    }
                                    var url6 = "http://90.156.229.190:8089/position/first?turnId=$idTurnThis&userId=$loggedUserId";
                                    val request6 = object : StringRequest(
                                        Request.Method.GET,
                                        url6,
                                        {
                                                result ->
                                            run {
                                                val gson = Gson()
                                                val positionFirst = gson?.fromJson(result, PositionFirst::class.java);
                                                if (positionFirst==null){
                                                    firstPositionBlock.visibility = View.GONE
                                                    firstPositionBlockText.visibility = View.GONE
                                                }
                                                else {
                                                    firstPositionBlock.visibility = View.VISIBLE
                                                    firstPositionBlockText.visibility = View.VISIBLE
                                                    firstPositionName.text = positionFirst.name
                                                    firstPositionGroup.text = positionFirst.group
                                                    firstPositionIndex.text="#${positionFirst.number}"
                                                    if (positionFirst.difference==0){
                                                        firstExist = true
                                                        firstPositionId = positionFirst.id
                                                        infoAboutFirstPosition.visibility=View.GONE
                                                        started = positionFirst.start
                                                        if (positionFirst.start){
                                                            startButton.text = resources.getString(R.string.exitBtnTextTurn)
                                                        }
                                                        else{
                                                            startButton.text = resources.getString(R.string.Enter2)
                                                        }
                                                        startButton.visibility=View.VISIBLE
                                                        positionsAdapter.numberChange(true)
                                                    }
                                                    else{
                                                        positionsAdapter.numberChange(false)
                                                        startButton.visibility=View.GONE
                                                        infoAboutCountPositionsAtFirst.text = resources.getString(R.string.numberofpeople1, positionFirst.difference)
                                                    }
                                                }
                                            }
                                        },
                                        {
                                                error ->
                                            run{
                                                firstPositionBlock.visibility = View.GONE
                                                firstPositionBlockText.visibility = View.GONE
                                            }
                                        }
                                    ){
                                        override fun getBodyContentType(): String {
                                            return "application/json; charset=utf-8"
                                        }
                                    }
                                    queue.add(request6)
                                }
                            }
                            firstPositionBlock.visibility = View.VISIBLE
                            firstPositionBlockText.visibility = View.VISIBLE

                        }
                    }
                },
                {
                        error -> run{

                    }
                }
            ){
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
            }
            queue.add(request5)


//            JoinBtn.isClickable = false
//            val positionNew = Position(
//                9,
//                "Иванов Юрий Владимирович",
//                "2391",
//                loggedUserId
//            ) // idUser для каждого пользователя свой
//            var temp = positionsAdapter.addPosition(positionNew)
//            if (temp != 0) {
//                temp++
//                val str = String.format(getString(R.string.warningTxtTurn), temp)
//                val textWarn = findViewById<TextView>(R.id.textWarnTurn)
//                textWarn.text=str
//                WarningTxt.visibility = View.VISIBLE
//
//                Handler().postDelayed({
//                    WarningTxt.startAnimation(outAnimation)
//                    Handler().postDelayed({
//                        WarningTxt.visibility = View.GONE
//                        JoinBtn.isClickable = true
//                    }, 2000)
//                }, 3000)
//            }

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


        var url3 = "http://90.156.229.190:8089/position/first?turnId=$idTurnThis&userId=$loggedUserId";
        val request3 = object : StringRequest(
            Request.Method.GET,
            url3,
            {
                    result ->
                run {
                    val gson = Gson()
                    val positionFirst = gson?.fromJson(result, PositionFirst::class.java);
                    if (positionFirst==null){
                        firstPositionBlock.visibility = View.GONE
                        firstPositionBlockText.visibility = View.GONE
                    }
                    else {
                        firstPositionBlock.visibility = View.VISIBLE
                        firstPositionBlockText.visibility = View.VISIBLE
                        firstPositionName.text = positionFirst.name
                        firstPositionGroup.text = positionFirst.group
                        firstPositionIndex.text="#${positionFirst.number}"
                        if (positionFirst.difference==0){
                            firstExist = true
                            firstPositionId = positionFirst.id
                            infoAboutFirstPosition.visibility=View.GONE
                            started = positionFirst.start
                            if (positionFirst.start){
                                startButton.text = resources.getString(R.string.exitBtnTextTurn)
                            }
                            else{
                                startButton.text = resources.getString(R.string.Enter2)
                            }
                            startButton.visibility=View.VISIBLE
                            positionsAdapter.numberChange(true)
                        }
                        else{
                            positionsAdapter.numberChange(false)
                            startButton.visibility=View.GONE
                            infoAboutCountPositionsAtFirst.text = resources.getString(R.string.numberofpeople1, positionFirst.difference)
                        }
                    }
                }
            },
            {
                    error ->
                run{
                    firstPositionBlock.visibility = View.GONE
                    firstPositionBlockText.visibility = View.GONE
                }
            }
        ){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }
        queue.add(request3)

        startButton.setOnClickListener(){
            if (firstPositionId==0L){
                return@setOnClickListener
            }
            var url3 = "http://90.156.229.190:8089/position/rules?id=$firstPositionId&isStarted=true";
            val request3 = object : StringRequest(
                Request.Method.PUT,
                url3,
                {
                        result ->
                    run {
                        if (started){
                            var url4 = "http://90.156.229.190:8089/position/first?turnId=$idTurnThis&userId=$loggedUserId";
                            val request4 = object : StringRequest(
                                Request.Method.GET,
                                url4,
                                {
                                        result ->
                                    run {
                                        val gson = Gson()
                                        val positionFirst = gson?.fromJson(result, PositionFirst::class.java);
                                        if (positionFirst==null){
                                            firstPositionBlock.visibility = View.GONE
                                            firstPositionBlockText.visibility = View.GONE
                                        }
                                        else{
                                            firstPositionBlock.visibility = View.VISIBLE
                                            firstPositionBlockText.visibility = View.VISIBLE
                                            firstPositionName.text = positionFirst.name
                                            firstPositionGroup.text = positionFirst.group
                                            firstPositionIndex.text="#${positionFirst.number}"
                                            if (positionFirst.difference==0){
                                                firstExist = true
                                                firstPositionId = positionFirst.id
                                                infoAboutFirstPosition.visibility=View.GONE
                                                started = positionFirst.start
                                                if (positionFirst.start){
                                                    startButton.text = resources.getString(R.string.exitBtnTextTurn)
                                                }
                                                else{
                                                    startButton.text = resources.getString(R.string.Enter2)
                                                }
                                                startButton.visibility=View.VISIBLE
                                            }
                                            else{
                                                startButton.visibility=View.GONE
                                                infoAboutCountPositionsAtFirst.text = resources.getString(R.string.numberofpeople1, positionFirst.difference)
                                            }
                                        }
                                    }
                                },
                                {
                                        error ->
                                    run{
                                        firstPositionBlock.visibility = View.GONE
                                        firstPositionBlockText.visibility = View.GONE
                                    }
                                }
                            ){
                                override fun getBodyContentType(): String {
                                    return "application/json; charset=utf-8"
                                }
                            }
                            queue.add(request4)
                        }
                        else{
                            started = true
                            startButton.text = resources.getString(R.string.exitBtnTextTurn)
                        }
                    }
                },
                {
                        error ->
                    run{
                        firstPositionBlock.visibility = View.GONE
                        firstPositionBlockText.visibility = View.GONE
                    }
                }
            ){
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
            }
            queue.add(request3)
        }



//        nameTurn.text = yourPos?.name
//        myPosNumber2.text = yourPos?.groupNumber
//        var numberpeopleafter : TextView = findViewById(R.id.timeDuration2)
//        val timeDuration : TextView = findViewById(R.id.timeDuration3)
//
//        if(yourPos?.isFirst == true){
//            positionsList.removeAt(0)
//            positionsAdapter.setItems(positionsList, loggedUserId, yourPos?.isFirst == true)
//            if(yourPos?.isGo == false){
//                curjoinBtn.visibility = View.VISIBLE
//                numberpeopleafter.visibility = View.GONE
//                timeDuration.visibility = View.GONE
//                curjoinBtn.setOnClickListener(){
//                    if(!ispressed){
//                        curjoinBtn.text = "выйти"
//                        curjoinBtn.setTextColor(Color.parseColor("#F4694D"))
//                        ispressed = true
//                    }
//                    else {
//                        curPeoplebox.visibility = View.GONE
//                        yourTurn.visibility = View.GONE
//                        positionsAdapter.numberChange(false)
//                    }
//                }
//            }
//        }
//        else{
//            val numbr = String.format(getString(R.string.numberofpeople1), yourPos?.number)
//            numberpeopleafter.text = numbr
//        }

        val scrollview = findViewById<ScrollView>(R.id.scrollview)
        val relLayout = findViewById<RelativeLayout>(R.id.relLayout)
        val linTurnParent = findViewById<LinearLayout>(R.id.LinearTurnParent)


        scrollview.viewTreeObserver.addOnScrollChangedListener(object : ViewTreeObserver.OnScrollChangedListener{
            override fun onScrollChanged() {
                var screenHeight : Int = relLayout.height
                var partOfScroll : Int = (screenHeight.toDouble() * 0.3).toInt()
                var scrollY: Int = scrollview.scrollY
                var scrollHeight : Int = linTurnParent.height
//                if(scrollHeight-(scrollY+screenHeight) < partOfScroll){
//                    var positionP = Positions(2,"Yuri", "2391", 3)
//                    val positionsList = mutableListOf<Positions>()
//                    positionsList.add(positionP)
//                    positionsAdapter.addNewPositions(positionsList)
//                }
            }

        })





    }


}
