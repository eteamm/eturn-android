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
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.eturn.adapter.TurnAdapter
import com.eturn.data.Turn
import com.google.gson.Gson
//import okhttp3.Call
//import okhttp3.Callback
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.Response


class MainActivity : AppCompatActivity() {
    val loggedUserId = 5
    val turnAdapter = TurnAdapter(this, loggedUserId)
    val turnList = mutableListOf<Turn>()
    override fun onResume() {
        super.onResume()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val ex = ExampleTry()
//        ex.run()

//        Thread {

//            val client = OkHttpClient()
//
//            val request = Request.Builder()
//                .url("http://90.156.229.190:8089/user/1")
//                .build()
//
//            client.newCall(request).enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    e.printStackTrace()
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    response.use {
//                        if (!response.isSuccessful) {
//                            throw IOException(
//                                "Запрос к серверу не был успешен:" +
//                                        " ${response.code} ${response.message}"
//                            )
//                        }
//                        // пример получения всех заголовков ответа
//                        for ((name, value) in response.headers) {
//                            Log.i("MYYY", "$name: $value")
//                        }
//                        // вывод тела ответа
//                        Log.e("MYYY", response.body!!.string())
//                    }
//                }
//            })
//        }.start()

            val url = "http://90.156.229.190:8089/user/1";
            val queue = Volley.newRequestQueue(applicationContext)
            val request = StringRequest(
                url,
                {
                    result -> Log.d("MYYY", "$result")
                },
                {
                    error -> Log.d("MYYY", "$error")
                }
            )
            queue.add(request)


//        OkHttpClient().newCall(request)
//            .enqueue(object : Callback {
//
//                override fun onFailure(call: Call, e: IOException) {
//                    val text = "Нихуя"
//                    val duration = Toast.LENGTH_SHORT
//
//                    val toast = Toast.makeText(applicationContext, text, duration)
//                    toast.show()
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    val text = "РАБОТАЕТ СУКА, РАБОТАЕТ!"
//                    val duration = Toast.LENGTH_SHORT
//
//                    val toast = Toast.makeText(applicationContext, text, duration)
//                    toast.show()
//                }
//            })






        var AccessBtns = true
        var TypeBtns = true
        val bcreateturn = findViewById<Button>(R.id.CreateTurnBtn)
        val MyTurnsBtn = findViewById<Button>(R.id.bMy)
        val InDostupBtn = findViewById<Button>(R.id.MainScreenInDostupBtn)
        val StudiedBtn = findViewById<Button>(R.id.bStud)
        val OrganizationBtn = findViewById<Button>(R.id.bOrg)
        val SearchTurns = findViewById<EditText>(R.id.NameForSearch)

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
            intent.putExtra("idUser", loggedUserId)
            startActivity(intent)
            finish()
        }

        OrganizationBtn.setOnClickListener {
            TypeBtns = false
//            AccessBtns = false
            checkFilter(AccessBtns,TypeBtns, turnAdapter)
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
//            AccessBtns = true
            TypeBtns = true
            checkFilter(AccessBtns,TypeBtns, turnAdapter)
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
            AccessBtns = true
            checkFilter(AccessBtns,TypeBtns, turnAdapter)
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
//            TypeBtns = false
            AccessBtns = false
            checkFilter(AccessBtns,TypeBtns, turnAdapter)
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


        val myJson = """
        [ 
            {
                id: 1, 
                name: "Зачетная неделя",
                description: "Берите с собой ручки!",
                nameCreator: "Железняк Александр Владимирович",
                idUser: 1,
                numberOfPeople: 46
            }, 
            {
                id: 2, 
                name: "Деканат Отчисления",
                description: "Стучитесь и будьте культурными!", 
                nameCreator: "Холод Иван Иванович", 
                idUser: 4,
                numberOfPeople: 36
            },
            {
            id: 1, 
            name: "Физика Экзамен",
            description: "Зачетки не забудьте.",
            nameCreator: "Леднев Михаил Георгиевич",
            idUser: 2,
            numberOfPeople: 49
            },
            {
            id: 2, 
            name: "Здравпункт",
            description: "Приносите форму М-54, справку о прививках и остальные документы.",
            nameCreator: "Сергеева Анна Анатольевна",
            idUser: 3,
            numberOfPeople: 32
            },
            {
            id: 3, 
            name: "Помощь по ТОЭ",
            description: "Подходите в коворкинг, помогаю с задачками по ТОЭ",
            nameCreator: "Кадун Никита Андреевич",
            idUser: 5,
            numberOfPeople: 77
            },
            {
            id: 4, 
            name: "Экзамен ТОЭ",
            description: "Те, кто не выполнил ИДЗ, на экзамен не допускаются",
            nameCreator: "Самоваров Иван Кириллович",
            idUser: 1,
            numberOfPeople: 61
            },
            {
            id: 1, 
            name: "Физика Экзамен",
            description: "Зачетки не забудьте.",
            nameCreator: "Леднев Михаил Георгиевич",
            idUser: 2,
            numberOfPeople: 49
            },
            {
            id: 2, 
            name: "Здравпункт",
            description: "Приносите форму М-54, справку о прививках и остальные документы.",
            nameCreator: "Сергеева Анна Анатольевна",
            idUser: 3,
            numberOfPeople: 32
            },
            {
            id: 3, 
            name: "Помощь по ТОЭ",
            description: "Подходите в коворкинг, помогаю с задачками по ТОЭ",
            nameCreator: "Кадун Никита Андреевич",
            idUser: 5,
            numberOfPeople: 77
            },
            {
            id: 4, 
            name: "Экзамен ТОЭ",
            description: "Те, кто не выполнил ИДЗ, на экзамен не допускаются",
            nameCreator: "Самоваров Иван Кириллович",
            idUser: 1,
            numberOfPeople: 61
            },
            {
            id: 1, 
            name: "Физика Экзамен",
            description: "Зачетки не забудьте.",
            nameCreator: "Леднев Михаил Георгиевич",
            idUser: 2,
            numberOfPeople: 49
            },
            {
            id: 2, 
            name: "Здравпункт",
            description: "Приносите форму М-54, справку о прививках и остальные документы.",
            nameCreator: "Сергеева Анна Анатольевна",
            idUser: 3,
            numberOfPeople: 32
            },
            {
            id: 3, 
            name: "Помощь по ТОЭ",
            description: "Подходите в коворкинг, помогаю с задачками по ТОЭ",
            nameCreator: "Кадун Никита Андреевич",
            idUser: 5,
            numberOfPeople: 77
            },
            {
            id: 4, 
            name: "Экзамен ТОЭ",
            description: "Те, кто не выполнил ИДЗ, на экзамен не допускаются",
            nameCreator: "Самоваров Иван Кириллович",
            idUser: 1,
            numberOfPeople: 61
            },
            {
            id: 1, 
            name: "Физика Экзамен",
            description: "Зачетки не забудьте.",
            nameCreator: "Леднев Михаил Георгиевич",
            idUser: 2,
            numberOfPeople: 49
            },
            {
            id: 2, 
            name: "Здравпункт",
            description: "Приносите форму М-54, справку о прививках и остальные документы.",
            nameCreator: "Сергеева Анна Анатольевна",
            idUser: 3,
            numberOfPeople: 32
            },
            {
            id: 3, 
            name: "Помощь по ТОЭ",
            description: "Подходите в коворкинг, помогаю с задачками по ТОЭ",
            nameCreator: "Кадун Никита Андреевич",
            idUser: 5,
            numberOfPeople: 77
            },
            {
            id: 4, 
            name: "Экзамен ТОЭ",
            description: "Те, кто не выполнил ИДЗ, на экзамен не допускаются",
            nameCreator: "Самоваров Иван Кириллович",
            idUser: 1,
            numberOfPeople: 61
            },
            {
            id: 1, 
            name: "Физика Экзамен",
            description: "Зачетки не забудьте.",
            nameCreator: "Леднев Михаил Георгиевич",
            idUser: 2,
            numberOfPeople: 49
            },
            {
            id: 2, 
            name: "Здравпункт",
            description: "Приносите форму М-54, справку о прививках и остальные документы.",
            nameCreator: "Сергеева Анна Анатольевна",
            idUser: 3,
            numberOfPeople: 32
            },
            {
            id: 3, 
            name: "Помощь по ТОЭ",
            description: "Подходите в коворкинг, помогаю с задачками по ТОЭ",
            nameCreator: "Кадун Никита Андреевич",
            idUser: 5,
            numberOfPeople: 77
            },
            {
            id: 4, 
            name: "Экзамен ТОЭ",
            description: "Те, кто не выполнил ИДЗ, на экзамен не допускаются",
            nameCreator: "Самоваров Иван Кириллович",
            idUser: 1,
            numberOfPeople: 61
            },
            {
            id: 1, 
            name: "Физика Экзамен",
            description: "Зачетки не забудьте.",
            nameCreator: "Леднев Михаил Георгиевич",
            idUser: 2,
            numberOfPeople: 49
            },
            {
            id: 2, 
            name: "Здравпункт",
            description: "Приносите форму М-54, справку о прививках и остальные документы.",
            nameCreator: "Сергеева Анна Анатольевна",
            idUser: 3,
            numberOfPeople: 32
            },
            {
            id: 3, 
            name: "Помощь по ТОЭ",
            description: "Подходите в коворкинг, помогаю с задачками по ТОЭ",
            nameCreator: "Кадун Никита Андреевич",
            idUser: 5,
            numberOfPeople: 77
            },
            {
            id: 4, 
            name: "Экзамен ТОЭ",
            description: "Те, кто не выполнил ИДЗ, на экзамен не допускаются",
            nameCreator: "Самоваров Иван Кириллович",
            idUser: 1,
            numberOfPeople: 61
            },
            {
            id: 1, 
            name: "Физика Экзамен",
            description: "Зачетки не забудьте.",
            nameCreator: "Леднев Михаил Георгиевич",
            idUser: 2,
            numberOfPeople: 49
            },
            {
            id: 2, 
            name: "Здравпункт",
            description: "Приносите форму М-54, справку о прививках и остальные документы.",
            nameCreator: "Сергеева Анна Анатольевна",
            idUser: 3,
            numberOfPeople: 32
            },
            {
            id: 3, 
            name: "Помощь по ТОЭ",
            description: "Подходите в коворкинг, помогаю с задачками по ТОЭ",
            nameCreator: "Кадун Никита Андреевич",
            idUser: 5,
            numberOfPeople: 77
            },
            {
            id: 4, 
            name: "Экзамен ТОЭ",
            description: "Те, кто не выполнил ИДЗ, на экзамен не допускаются",
            nameCreator: "Самоваров Иван Кириллович",
            idUser: 1,
            numberOfPeople: 61
            },
            {
            id: 5, 
            name: "Зачет по физре",
            description: "Жду всех с зачетками",
            nameCreator: "Комилов Виктор Матвеевич",
            idUser: 1,
            numberOfPeople: 101
            },
            {
            id: 6, 
            name: "Мои учебные",
            description: "Если уже сходили в здравпункт и подтвердили справку, приходите в деканат",
            nameCreator: "Горин Николай Олегович",
            idUser: 5,
            numberOfPeople: 55
            }
        ]
        """.trimIndent()

        val dostupJson = """
       [
           {
            id: 1, 
            name: "Физика Экзамен",
            description: "Зачетки не забудьте.",
            nameCreator: "Леднев Михаил Георгиевич",
            idUser: 2,
            numberOfPeople: 49
            },
            {
            id: 2, 
            name: "Здравпункт",
            description: "Приносите форму М-54, справку о прививках и остальные документы.",
            nameCreator: "Сергеева Анна Анатольевна",
            idUser: 3,
            numberOfPeople: 32
            },
            {
            id: 3, 
            name: "Помощь по ТОЭ",
            description: "Подходите в коворкинг, помогаю с задачками по ТОЭ",
            nameCreator: "Кадун Никита Андреевич",
            idUser: 5,
            numberOfPeople: 77
            },
            {
            id: 4, 
            name: "Экзамен ТОЭ",
            description: "Те, кто не выполнил ИДЗ, на экзамен не допускаются",
            nameCreator: "Самоваров Иван Кириллович",
            idUser: 1,
            numberOfPeople: 61
            },
            {
            id: 5, 
            name: "Зачет по физре",
            description: "Жду всех с зачетками",
            nameCreator: "Комилов Виктор Матвеевич",
            idUser: 1,
            numberOfPeople: 101
            },
            {
            id: 6, 
            name: "Учебные доступные",
            description: "Если уже сходили в здравпункт и подтвердили справку, приходите в деканат",
            nameCreator: "Горин Николай Олегович",
            idUser: 5,
            numberOfPeople: 55
            }
        ]
        """.trimIndent()

        val myJsonforOrganisations = """
        [ 
            {
                id: 1, 
                name: "Военкомат",
                description: "Берите с собой ручки и ножки!",
                nameCreator: "Военный комиссар",
                idUser: 1,
                numberOfPeople: 5
            }, 
            {
                id: 2, 
                name: "Деканат Отчисления",
                description: "Стучитесь и будьте культурными!", 
                nameCreator: "Холод Иван Иванович", 
                idUser: 4,
                numberOfPeople: 36
            },
            
            {
            id: 3, 
            name: "Мои огранизацонные",
            description: "Бахилы с собой иметь всем!!!!!!!!!!!",
            nameCreator: "Глав врач",
            idUser: 5,
            numberOfPeople: 33
            }
        ]
        """.trimIndent()

        val dostupJsonforOrganisations = """
       [
           {
            id: 1, 
            name: "Дополнительная сессия",
            description: "Зачетки не забудьте.",
            nameCreator: "Леднев Михаил Георгиевич",
            idUser: 2,
            numberOfPeople: 49
            },
            {
            id: 2,
            name: "Доступные организацонные",
            description: "Приносите форму М-54, справку о прививках и остальные документы.",
            nameCreator: "Сергеева Анна Анатольевна",
            idUser: 3,
            numberOfPeople: 32
            
            }
        ]
        """.trimIndent()

        val gson = Gson()
        val MyTurns = gson?.fromJson(myJson, Array<Turn>::class.java)?.toList()
        val InDostupTurns = gson?.fromJson(dostupJson, Array<Turn>::class.java)?.toList()
        val MyTurnsforOrganisations =
            gson?.fromJson(myJsonforOrganisations, Array<Turn>::class.java)?.toList()
        val InDostupTurnsforOrganisations =
            gson?.fromJson(dostupJsonforOrganisations, Array<Turn>::class.java)?.toList()
        val recyclerView: RecyclerView = findViewById(R.id.turnsRec)
        recyclerView.setHasFixedSize(true)
        val RecView = findViewById<RecyclerView>(R.id.turnsRec)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager;

        recyclerView.adapter = turnAdapter

        MyTurns?.forEach {
            var turn =
                Turn(it.id, it.name, it.description, it.nameCreator, it.idUser, it.numberOfPeople)
            turnList.add(0, turn)
        }
        turnAdapter.setItems(turnList, true)
        RecView.visibility = View.VISIBLE
        recyclerView.isNestedScrollingEnabled = false;

    }

    private fun checkFilter(AccessBtns : Boolean, TypeBtns : Boolean, turnAdapter: TurnAdapter){
        val myJson = """
        [ 
            {
                id: 1, 
                name: "Зачетная неделя",
                description: "Берите с собой ручки!",
                nameCreator: "Железняк Александр Владимирович",
                idUser: 1,
                numberOfPeople: 46
            }, 
            {
                id: 2, 
                name: "Деканат Отчисления",
                description: "Стучитесь и будьте культурными!", 
                nameCreator: "Холод Иван Иванович", 
                idUser: 4,
                numberOfPeople: 36
            },
            {
            id: 1, 
            name: "Физика Экзамен",
            description: "Зачетки не забудьте.",
            nameCreator: "Леднев Михаил Георгиевич",
            idUser: 2,
            numberOfPeople: 49
            },
            {
            id: 2, 
            name: "Здравпункт",
            description: "Приносите форму М-54, справку о прививках и остальные документы.",
            nameCreator: "Сергеева Анна Анатольевна",
            idUser: 3,
            numberOfPeople: 32
            },
            {
            id: 3, 
            name: "Помощь по ТОЭ",
            description: "Подходите в коворкинг, помогаю с задачками по ТОЭ",
            nameCreator: "Кадун Никита Андреевич",
            idUser: 5,
            numberOfPeople: 77
            },
            {
            id: 4, 
            name: "Экзамен ТОЭ",
            description: "Те, кто не выполнил ИДЗ, на экзамен не допускаются",
            nameCreator: "Самоваров Иван Кириллович",
            idUser: 1,
            numberOfPeople: 61
            },
            {
            id: 5, 
            name: "Зачет по физре",
            description: "Жду всех с зачетками",
            nameCreator: "Комилов Виктор Матвеевич",
            idUser: 1,
            numberOfPeople: 101
            },
            {
            id: 6, 
            name: "Мои учебные",
            description: "Если уже сходили в здравпункт и подтвердили справку, приходите в деканат",
            nameCreator: "Горин Николай Олегович",
            idUser: 5,
            numberOfPeople: 55
            }
        ]
        """.trimIndent()

        val dostupJson = """
       [
           {
            id: 1, 
            name: "Физика Экзамен",
            description: "Зачетки не забудьте.",
            nameCreator: "Леднев Михаил Георгиевич",
            idUser: 2,
            numberOfPeople: 49
            },
            {
            id: 2, 
            name: "Здравпункт",
            description: "Приносите форму М-54, справку о прививках и остальные документы.",
            nameCreator: "Сергеева Анна Анатольевна",
            idUser: 3,
            numberOfPeople: 32
            },
            {
            id: 3, 
            name: "Помощь по ТОЭ",
            description: "Подходите в коворкинг, помогаю с задачками по ТОЭ",
            nameCreator: "Кадун Никита Андреевич",
            idUser: 5,
            numberOfPeople: 77
            },
            {
            id: 4, 
            name: "Экзамен ТОЭ",
            description: "Те, кто не выполнил ИДЗ, на экзамен не допускаются",
            nameCreator: "Самоваров Иван Кириллович",
            idUser: 1,
            numberOfPeople: 61
            },
            {
            id: 5, 
            name: "Зачет по физре",
            description: "Жду всех с зачетками",
            nameCreator: "Комилов Виктор Матвеевич",
            idUser: 1,
            numberOfPeople: 101
            },
            {
            id: 6, 
            name: "Учебные доступные",
            description: "Если уже сходили в здравпункт и подтвердили справку, приходите в деканат",
            nameCreator: "Горин Николай Олегович",
            idUser: 5,
            numberOfPeople: 55
            }
        ]
        """.trimIndent()

        val myJsonforOrganisations = """
        [ 
            {
                id: 1, 
                name: "Военкомат",
                description: "Берите с собой ручки и ножки!",
                nameCreator: "Военный комиссар",
                idUser: 1,
                numberOfPeople: 5
            }, 
            {
                id: 2, 
                name: "Деканат Отчисления",
                description: "Стучитесь и будьте культурными!", 
                nameCreator: "Холод Иван Иванович", 
                idUser: 4,
                numberOfPeople: 36
            },
            
            {
            id: 3, 
            name: "Мои огранизацонные",
            description: "Бахилы с собой иметь всем!!!!!!!!!!!",
            nameCreator: "Глав врач",
            idUser: 5,
            numberOfPeople: 33
            }
        ]
        """.trimIndent()

        val dostupJsonforOrganisations = """
       [
           {
            id: 1, 
            name: "Дополнительная сессия",
            description: "Зачетки не забудьте.",
            nameCreator: "Леднев Михаил Георгиевич",
            idUser: 2,
            numberOfPeople: 49
            },
            {
            id: 2,
            name: "Доступные организацонные",
            description: "Приносите форму М-54, справку о прививках и остальные документы.",
            nameCreator: "Сергеева Анна Анатольевна",
            idUser: 3,
            numberOfPeople: 32
            
            }
        ]
        """.trimIndent()

        val gson = Gson()
        val MyTurns = gson?.fromJson(myJson, Array<Turn>::class.java)?.toList()
        val InDostupTurns = gson?.fromJson(dostupJson, Array<Turn>::class.java)?.toList()
        val MyTurnsforOrganisations =
            gson?.fromJson(myJsonforOrganisations, Array<Turn>::class.java)?.toList()
        val InDostupTurnsforOrganisations =
            gson?.fromJson(dostupJsonforOrganisations, Array<Turn>::class.java)?.toList()

        var list : List<Turn>? = null
        var type = true
        if (AccessBtns && TypeBtns) {
            list = MyTurns
            type=true
        } else if (AccessBtns && !TypeBtns) {
            list = MyTurnsforOrganisations
            type=true
        } else if (!AccessBtns && TypeBtns) {
            list = InDostupTurns
            type=false
        } else if (!AccessBtns && !TypeBtns) {
            list = InDostupTurnsforOrganisations
            type=false
        }
        if (list != null) {
            pullList(turnAdapter, list,type)
        }
    }



    private fun pullList(turnAdapter : TurnAdapter, list : List<Turn>, type : Boolean){
        val turns = mutableListOf<Turn>()

        list?.forEach {
            var turn = Turn(
                it.id,
                it.name,
                it.description,
                it.nameCreator,
                it.idUser,
                it.numberOfPeople
            )
            turns.add(0, turn)
        }
        turnAdapter.setItems(turns, type)



    }


}