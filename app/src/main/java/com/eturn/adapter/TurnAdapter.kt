package com.eturn.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.eturn.R
import com.eturn.TurnActivity
import com.eturn.data.Turn
import android.text.TextWatcher
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


public class TurnAdapter(private val context: Context, private val id_user : Long) : RecyclerView.Adapter<TurnAdapter.turnHolder>() {
    private var turnList = ArrayList<Turn>();
    private var Type : Boolean = false;

    class turnHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.turnNameElement)
        val Author:TextView = itemView.findViewById(R.id.TurnAuthorElement)
        val ButtonTextView: Button = itemView.findViewById(R.id.JoinBtn)
        val Description : TextView = itemView.findViewById(R.id.turnDescElement)
        val Number : TextView = itemView.findViewById(R.id.turnSizeElement)
        val ClickonCW:CardView = itemView.findViewById(R.id.TurnCV)
        val Join = itemView.findViewById<LinearLayout>(R.id.joinLayout)
        fun getThisColor(context : Context) : Int{
            return context.resources.getColor(R.color.colorMain)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): turnHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.turn, parent, false)
        return turnHolder(view) //возвращает элементы для списка
    }

    override fun getItemCount(): Int {
        return turnList.size //возвращает кол-во элементов
    }

    override fun onBindViewHolder(holder: turnHolder, position: Int) {
        val turn : Turn = turnList[position] //заполнение данных в эл списка
        holder.nameTextView.text = turn.name
        if (turn.userId.toLong()==id_user){
            holder.nameTextView.setTextColor(holder.getThisColor(context))
        }
        holder.Description.text ="Подробнее: " + turn.description
        holder.Author.text = turn.creator
        val People: Array<String> = arrayOf("человек","человек","человека","человека","человека","человек","человек","человек","человек","человек")
        holder.Number.text = turn.countUsers.toString()+" "+People[turn.countUsers % 10]
        if (Type){
            holder.ClickonCW.setOnClickListener(){
                val sPref = context.getSharedPreferences("UserAndTurnInfo", AppCompatActivity.MODE_PRIVATE)
                val editor = sPref.edit()
                editor.putLong("TURN_ID", turn.id)
                editor.apply()
                val intent = Intent(context, TurnActivity::class.java)
                context.startActivity(intent)
            }
            holder.Join.visibility = View.GONE
            holder.ButtonTextView.visibility = View.GONE
            holder.Description.visibility = View.GONE
        }
        else{
            holder.ClickonCW.isClickable = false
            holder.Join.visibility = View.VISIBLE
            holder.ButtonTextView.visibility = View.VISIBLE
            holder.Description.visibility = View.VISIBLE
            holder.ButtonTextView.setOnClickListener(){
                val queue = Volley.newRequestQueue(context)
                val idTurn = turn.id;
                val url2 =
                    "http://90.156.229.190:8089/member?userId=$id_user&turnId=$idTurn&accessMemberEnum=MEMBER";

                val request2 = object : StringRequest(
                    Request.Method.POST,
                    url2,
                    { result1 ->
                        run {
                            val url3 = "http://90.156.229.190:8089/turn/new_member?userId=$id_user&turnId=$idTurn";
                            val request3 = object : StringRequest(
                                Request.Method.PUT,
                                url3,
                                {
                                        result2 ->
                                    run {
                                        val intent = Intent(context, TurnActivity::class.java)
                                        context.startActivity(intent)
                                        val sPref = context.getSharedPreferences("UserAndTurnInfo", AppCompatActivity.MODE_PRIVATE)
                                        val editor = sPref.edit()
                                        editor.putLong("TURN_ID", turn.id)
                                        editor.apply()
                                    }
                                },
                                {
                                        error -> Log.d("MYYY", error.toString())
                                }
                            ){
                                override fun getBodyContentType(): String {
                                    return "application/json; charset=utf-8"
                                }
                            }
                            queue.add(request3)
                        }
                    },
                    { error -> Log.d("MYYY", error.toString())
                    }
                ) {
                    override fun getBodyContentType(): String {
                        return "application/json; charset=utf-8"
                    }
                }
                queue.add(request2)
            }

        }
    }

    fun addTurn(turn: Turn){
        turnList.add(turn)
        notifyDataSetChanged()
    }

    fun setItems(item: MutableList<Turn>, type: Boolean) {
        Type = type
        turnList.clear()
        turnList.addAll(item)
        notifyDataSetChanged()
    }
}