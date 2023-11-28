package com.example.mainlist.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mainlist.R
import com.example.mainlist.data.Positions


public class PositionsAdapter(private val context: Context, val admin : Int) : RecyclerView.Adapter<PositionsAdapter.HolderPositions>() {

    private var ListPositions = ArrayList<Positions>()
    private var idCurrent = 0

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


    override fun onBindViewHolder(holder: HolderPositions, position: Int) {

        val positions : Positions = ListPositions[position] //заполнение данных в эл списка
        holder.userNameTextView.text = positions.name
        var i = position+1
        var count = 0

        if(admin == 0) {
            if (idCurrent == positions.idUser) {
                val exitPosition = ImageButton(context)
                exitPosition.setImageResource(holder.src)
                exitPosition.background = null
                exitPosition.setOnClickListener{
                    val deleted = ListPositions.removeAt(position)
                    notifyDataSetChanged()
                }
                holder.layout.addView(exitPosition)
            }
        }
        else{
            val deletedBtn = holder.getDeleteButton(admin)
            if (deletedBtn!=null){
                deletedBtn.setOnClickListener{
                    val deleted = ListPositions.removeAt(position)
                    notifyDataSetChanged()
                }
            }
        }
        holder.numberTextView.text="#"+i  // если тру то +i если фалс то +i+1
        holder.userGroupTextView.text = positions.groupNumber
    }



    fun addPosition(position: Positions) : Int{
        var count = ListPositions.size
        var last = 0
        var Type = -1
        for(i in count-1 downTo 0 step 1){
            last++
            if (ListPositions[i].idUser==position.idUser) {
                Type = 0
                break
            }
        }
//        last = count - last
        if (last > 20 || Type != 0){
            ListPositions.add(position)
            notifyDataSetChanged()
            return 0
        }
        return 20-last
    }

    fun getLast(id : Int):Int{
        var count=0
        var b = false
        ListPositions.forEach {
            if(it.idUser==id){
                b = true
                return@forEach
            }
            count++
        }
        if (!b) count=-1
        return count
    }

    fun setItems(item: MutableList<Positions>, idUser : Int) {
        ListPositions.clear()
        ListPositions.addAll(item)
        idCurrent = idUser
        notifyDataSetChanged()
    }
}