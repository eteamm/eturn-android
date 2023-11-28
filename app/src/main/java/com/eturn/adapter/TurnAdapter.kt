package com.eturn.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.eturn.R
import com.eturn.TurnActivity
import com.eturn.data.Turn


public class TurnAdapter(private val context: Context, private val id_user : Int) : RecyclerView.Adapter<TurnAdapter.turnHolder>() {
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
        if (turn.idUser==id_user){
            holder.nameTextView.setTextColor(holder.getThisColor(context))
        }
        holder.Description.text ="Подробнее: " + turn.description
        holder.Author.text = turn.nameCreator
        val People: Array<String> = arrayOf("человек","человек","человека","человека","человека","человек","человек","человек","человек","человек")
        holder.Number.text = turn.numberOfPeople.toString()+" "+People[turn.numberOfPeople % 10]
        if (Type){
            holder.ClickonCW.setOnClickListener(){
                val intent = Intent(context, TurnActivity::class.java)
                intent.addCategory("CurrentTurn")
                intent.putExtra("Name",turn.name)
                intent.putExtra("Author",turn.nameCreator)
                intent.putExtra("Description",turn.description)
                intent.putExtra("NumberOfPeople",turn.numberOfPeople)
                intent.putExtra("IdCreator",turn.idUser)
                intent.putExtra("CurrentUser", id_user)
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
                val intent = Intent(context, TurnActivity::class.java)
                intent.addCategory("CurrentTurn")
                intent.putExtra("Name",turn.name)
                intent.putExtra("Author",turn.nameCreator)
                intent.putExtra("Description",turn.description)
                intent.putExtra("NumberOfPeople",turn.numberOfPeople)
                intent.putExtra("IdCreator",turn.idUser)
                intent.putExtra("CurrentUser", id_user)
                context.startActivity(intent)

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