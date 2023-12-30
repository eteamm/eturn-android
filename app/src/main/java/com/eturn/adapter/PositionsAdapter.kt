package com.eturn.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.eturn.R
import com.eturn.data.Position
import com.eturn.data.PositionFirst
import com.google.gson.Gson


public class PositionsAdapter(private val context: Context, val admin : Int) : RecyclerView.Adapter<PositionsAdapter.HolderPositions>() {

    private var ListPositions = ArrayList<Position>()
    private var idCurrent = 0L
    private var isFirst = false
    class HolderPositions(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTextView: TextView = itemView.findViewById(R.id.numberTxt)
        val userGroupTextView: TextView = itemView.findViewById(R.id.positionNumberTxt)
        val numberTextView: TextView = itemView.findViewById<TextView>(R.id.posId)
        val layout: LinearLayout = itemView.findViewById<LinearLayout>(R.id.contentLay)
        val src = R.drawable.cancel
        fun getDeleteButton(status : Int) : ImageButton? {
            return if (status>0){
                itemView.findViewById<ImageButton>(R.id.delBtn)
            } else null
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderPositions {
        val view = if (admin>0){
            LayoutInflater.from(parent.context).inflate(R.layout.position_admin, parent, false)
        } else{
            LayoutInflater.from(parent.context).inflate(R.layout.position, parent, false)
        }
        return HolderPositions(view)
    }

    override fun getItemCount(): Int {
        return ListPositions.size
    }

    fun addPosition(p : Position){
        ListPositions.add(p);
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: HolderPositions, position: Int) {

        val positions : Position = ListPositions[position] //заполнение данных в эл списка
        holder.userNameTextView.text = positions.name
        var i : Int
        if(isFirst){
            i = position+2
        }
        else{
            i = position+1
        }
        var count = 0

        if(admin == 0) {
            if (idCurrent == positions.userId) {
                val exitPosition = ImageButton(context)
                exitPosition.setImageResource(holder.src)
                exitPosition.background = null

                exitPosition.setOnClickListener{
                    val queue = Volley.newRequestQueue(context)
                    var url6 = "http://90.156.229.190:8089/position/${positions.id}";
                    val request6 = object : StringRequest(
                        Request.Method.DELETE,
                        url6,
                        {
                                result ->
                            run {
                                val deleted = ListPositions.removeAt(position)
                                notifyDataSetChanged()
                            }
                        },
                        {
                                error ->
                            run{

                            }
                        }
                    ){
                        override fun getBodyContentType(): String {
                            return "application/json; charset=utf-8"
                        }
                    }
                    queue.add(request6)

                }
                holder.layout.addView(exitPosition)
            }
        }
        else {
            holder.getDeleteButton(admin)?.setOnClickListener {
                val deleted = ListPositions.removeAt(position)
                notifyDataSetChanged()
            }
        }
        holder.numberTextView.text= "#${positions.number}"  // если тру то +i если фалс то +i+1
        holder.userGroupTextView.text = positions.group
    }

    fun numberChange(isF: Boolean){
        isFirst = isF
        notifyDataSetChanged()
    }

    fun getLastNumber() : Int {
        return ListPositions.get(ListPositions.lastIndex).number
    }

    fun addNewPositions(items: MutableList<Position>){
        items.forEach {
            ListPositions.add(it)
        }
        notifyDataSetChanged()
    }


    fun setItems(item: MutableList<Position>, idUser : Long, isF : Boolean) {
        isFirst = isF
        ListPositions.clear()
        ListPositions.addAll(item)
        idCurrent = idUser
        notifyDataSetChanged()
    }
}