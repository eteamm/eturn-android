package com.eturn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eturn.R
import com.eturn.data.AllowGroup

class AllowGroupAdapter(private val context: Context) : RecyclerView.Adapter<AllowGroupAdapter.allowGroupHolder>(){
    private var allowGroupList = ArrayList<AllowGroup>()
    class allowGroupHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val TextGroup : TextView = itemView.findViewById(R.id.NumberAllowGroupTxt)
        val DeleteImage : ImageView = itemView.findViewById(R.id.deleteAllowGroupImgBtn)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): allowGroupHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.allow_group, parent, false)
        return allowGroupHolder(view) //возвращает элементы для списка
    }

    override fun onBindViewHolder(holder: allowGroupHolder, position: Int) {
        val allowGroup : AllowGroup = allowGroupList[position]
        holder.TextGroup.text = allowGroup.number.toString()
        holder.DeleteImage.setOnClickListener(){
            val deleted = allowGroupList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return allowGroupList.size
    }

    fun addAllowGroup(item: AllowGroup) : Int{
        allowGroupList.forEach{
            if (it.number==item.number){
                return 0;
            }
        }
        allowGroupList.add(item)
        notifyDataSetChanged()
        return 1;
    }

    fun setItems(items: MutableList<AllowGroup>){
        allowGroupList.clear()
        allowGroupList.addAll(items)
        notifyDataSetChanged()
    }


}