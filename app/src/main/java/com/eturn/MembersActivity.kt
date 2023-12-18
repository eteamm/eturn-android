package com.eturn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eturn.R
import com.eturn.adapter.MemberAdapter
import com.eturn.data.Member
import com.google.gson.Gson

class MembersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)
        val Admins = """
        [ {id: 1, Name: "Маргарита Ненарокова Олеговна", idGroup: 2391, status: 1},  {id: 2, Name: "Сергей Блохин Олегович", idGroup: 2391, status: 1}]
        """.trimIndent()
        val Users = """[ {id: 1, Name: "Иван Самоваров Юрьевич", idGroup: 2391, status: 1},  {id: 2, Name: "Никита Кадун Андреевич", idGroup: 2391, status: 1}]
         """.trimIndent()

        var gson = Gson()
        var ADMINS = gson?.fromJson(Admins,Array<Member>::class.java)?.toList()
        var USERS = gson?.fromJson(Users, Array<Member>::class.java)?.toList()


        val recyclerAdmins: RecyclerView = findViewById(R.id.AdminsRec)
        val recyclerUsers: RecyclerView = findViewById(R.id.UsersRec)
        //val recyclerBlocked: RecyclerView = findViewById(R.id.BlockedRec)
        recyclerAdmins.layoutManager = LinearLayoutManager(this)
        recyclerUsers.layoutManager = LinearLayoutManager(this)
        //recyclerBlocked.layoutManager = LinearLayoutManager(this)
        val AdminsAdapter = MemberAdapter(this,1)
        val UsersAdapter = MemberAdapter(this,2)
        //val BlockedAdapter = MemberAdapter(this,3)

        recyclerAdmins.adapter = AdminsAdapter
        recyclerUsers.adapter = UsersAdapter
        val turnList = mutableListOf<Member>()
        ADMINS?.forEach {
            var memberlist = Member(it.id, it.Name, it.idGroup, it.Status)
            turnList.add(0, memberlist)
        }
        AdminsAdapter.setItems(turnList)

        val turnList2 = mutableListOf<Member>()
        USERS?.forEach {
            var memberlist = Member(it.id, it.Name, it.idGroup, it.Status)
            turnList2.add(0, memberlist)
        }
        UsersAdapter.setItems(turnList2)

        val adminsText = resources.getString(R.string.AdminsQuantity,AdminsAdapter.itemCount)
        val admins : TextView = findViewById(R.id.admins)
        admins.text = adminsText
        val usersText = resources.getString(R.string.StudentsQuantity,UsersAdapter.itemCount)
        val users : TextView = findViewById(R.id.students)
        users.text = usersText

        val SearchButton1 = findViewById<ImageButton>(R.id.searchButton1)
        val SearchButton2 = findViewById<ImageButton>(R.id.searchButton2)
        val SearchButton3 = findViewById<ImageButton>(R.id.searchButton3)
        val AdminsTitle = findViewById<TextView>(R.id.adminss)
        val StudentsTitle = findViewById<TextView>(R.id.members)
        val BlockedTitle = findViewById<TextView>(R.id.blocked)
        val SearchAdmins = findViewById<CardView>(R.id.adminsCardView)
        val SearchStudents = findViewById<CardView>(R.id.membersCardView)
        val SearchBlocked = findViewById<CardView>(R.id.blockedCardView)
        val CancelAdmins = findViewById<ImageButton>(R.id.cancelButton1)
        val CancelStudents = findViewById<ImageButton>(R.id.cancelButton2)
        val CancelBlocked = findViewById<ImageButton>(R.id.cancelButton3)
        val ShowButton1 = findViewById<ImageButton>(R.id.showButton1)
        val HideButton1 = findViewById<ImageButton>(R.id.hideButton1)
        val ShowButton2 = findViewById<ImageButton>(R.id.showButton2)
        val HideButton2 = findViewById<ImageButton>(R.id.hideButton2)
        val ShowButton3 = findViewById<ImageButton>(R.id.showButton3)
        val HideButton3 = findViewById<ImageButton>(R.id.hideButton3)

        SearchButton1.setOnClickListener {
            AdminsTitle.visibility = View.GONE
            SearchAdmins.visibility = View.VISIBLE
            SearchButton1.visibility = View.GONE
            CancelAdmins.visibility = View.VISIBLE
        }

        SearchButton2.setOnClickListener {
            StudentsTitle.visibility = View.GONE
            SearchStudents.visibility = View.VISIBLE
            SearchButton2.visibility = View.GONE
            CancelStudents.visibility = View.VISIBLE
        }

        SearchButton3.setOnClickListener {
            BlockedTitle.visibility = View.GONE
            SearchBlocked.visibility = View.VISIBLE
            SearchButton3.visibility = View.GONE
            CancelBlocked.visibility = View.VISIBLE
        }

        CancelAdmins.setOnClickListener {
            SearchAdmins.visibility = View.GONE
            CancelAdmins.visibility = View.GONE
            AdminsTitle.visibility = View.VISIBLE
            SearchButton1.visibility = View.VISIBLE
        }

        CancelStudents.setOnClickListener {
            SearchStudents.visibility = View.GONE
            CancelStudents.visibility = View.GONE
            StudentsTitle.visibility = View.VISIBLE
            SearchButton2.visibility = View.VISIBLE
        }

        CancelBlocked.setOnClickListener {
            SearchBlocked.visibility = View.GONE
            CancelBlocked.visibility = View.GONE
            BlockedTitle.visibility = View.VISIBLE
            SearchButton3.visibility = View.VISIBLE
        }


        ShowButton1.setOnClickListener {
            ShowButton1.visibility = View.GONE
            HideButton1.visibility = View.VISIBLE
            recyclerAdmins.visibility = View.VISIBLE
            recyclerUsers.visibility = View.GONE
        }

        HideButton1.setOnClickListener {
            HideButton1.visibility = View.GONE
            ShowButton1.visibility = View.VISIBLE
            recyclerAdmins.visibility = View.GONE
        }

        ShowButton2.setOnClickListener {
            ShowButton2.visibility = View.GONE
            HideButton2.visibility = View.VISIBLE
            recyclerUsers.visibility = View.VISIBLE
            recyclerAdmins.visibility = View.GONE
        }

        HideButton2.setOnClickListener {
            HideButton2.visibility = View.GONE
            ShowButton2.visibility = View.VISIBLE
            recyclerUsers.visibility = View.GONE
        }

        ShowButton3.setOnClickListener {
            ShowButton3.visibility = View.GONE
            HideButton3.visibility = View.VISIBLE
            //recyclerUsers.visibility = View.VISIBLE
            //recyclerAdmins.visibility = View.GONE
        }

        HideButton3.setOnClickListener {
            HideButton3.visibility = View.GONE
            ShowButton3.visibility = View.VISIBLE
            //recyclerUsers.visibility = View.GONE
        }

    }
}