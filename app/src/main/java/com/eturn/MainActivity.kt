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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eturn.adapter.TurnAdapter
import com.eturn.data.Turn
import com.eturn.requests.ExampleTry
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.URL


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ex = ExampleTry()
        ex.run()

//        val url = "https://mdn.github.io/learning-area/javascript/oojs/json/superheroes.json"
//        val request: Request = Request.Builder()
//            .url(url)
//            .build()

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

        val loggedUserId = 5
        val gson = Gson()
        val MyTurns = gson?.fromJson(myJson, Array<Turn>::class.java)?.toList()
        val InDostupTurns = gson?.fromJson(dostupJson, Array<Turn>::class.java)?.toList()
        val MyTurnsforOrganisations =
            gson?.fromJson(myJsonforOrganisations, Array<Turn>::class.java)?.toList()
        val InDostupTurnsforOrganisations =
            gson?.fromJson(dostupJsonforOrganisations, Array<Turn>::class.java)?.toList()
        var AccessBtns = true
        var TypeBtns = true
        val bcreateturn = findViewById<Button>(R.id.CreateTurnBtn)
        val MyTurnsBtn = findViewById<Button>(R.id.bMy)
        val InDostupBtn = findViewById<Button>(R.id.MainScreenInDostupBtn)
        val StudiedBtn = findViewById<Button>(R.id.bStud)
        val OrganizationBtn = findViewById<Button>(R.id.bOrg)
        val SearchTurns = findViewById<EditText>(R.id.NameForSearch)
        val recyclerView: RecyclerView = findViewById(R.id.turnsRec)
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager;
        val turnAdapter = TurnAdapter(this, loggedUserId)
        recyclerView.adapter = turnAdapter
        val turnList = mutableListOf<Turn>()
        MyTurns?.forEach {
            var turn =
                Turn(it.id, it.name, it.description, it.nameCreator, it.idUser, it.numberOfPeople)
            turnList.add(0, turn)
        }
        turnAdapter.setItems(turnList, true)
        recyclerView.isNestedScrollingEnabled = false;


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
            AccessBtns = false
            checkFilter(AccessBtns,TypeBtns, turnAdapter)

        }
        StudiedBtn.setOnClickListener {
            AccessBtns = true
            checkFilter(AccessBtns,TypeBtns, turnAdapter)

        }
        MyTurnsBtn.setOnClickListener {
            TypeBtns = true
            checkFilter(AccessBtns,TypeBtns, turnAdapter)

        }

        InDostupBtn.setOnClickListener {
            TypeBtns = false
            checkFilter(AccessBtns,TypeBtns, turnAdapter)
        }

        SearchTurns.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    val s = SearchTurns.text.toString()

                    if ((event.action == KeyEvent.ACTION_DOWN)){
                        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
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
            list = InDostupTurns
            type=true
        } else if (!AccessBtns && TypeBtns) {
            list = MyTurnsforOrganisations
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