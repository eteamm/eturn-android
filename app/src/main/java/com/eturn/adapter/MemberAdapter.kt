package com.eturn.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eturn.R
import com.eturn.data.Member


public class MemberAdapter(private val context: Context, private val type: Int) : RecyclerView.Adapter<MemberAdapter.MemberHolder>()  {

    private var memberList = ArrayList<Member>();

    class MemberHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val memberNameTextView: TextView = itemView.findViewById(R.id.memberNameTxt)
        val imageContextMenuButton: ImageButton = itemView.findViewById(R.id.pointsMember)
        val popupMenu = PopupMenu(itemView.context, imageContextMenuButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.member, parent, false)

        return MemberHolder(view) //возвращает элементы для списка
    }

    override fun getItemCount(): Int {
        return memberList.size //возвращает кол-во элементов
    }

    override fun onBindViewHolder(holder: MemberHolder, position: Int) {
        val member : Member = memberList[position] //заполнение данных в эл списка
        if (type == 1){
            holder.popupMenu.inflate(R.menu.member_item)
        }
        else if (type == 2){
            holder.popupMenu.inflate(R.menu.member_item_2)
        } else {
            holder.popupMenu.inflate(R.menu.member_item_3)
        }
        holder.imageContextMenuButton.setOnClickListener {
            holder.popupMenu.show()
            holder.popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.rename -> {
                        memberList.removeAt(position)
                        notifyDataSetChanged()
                    }
                    R.id.delete ->{
                        memberList.removeAt(position)
                        notifyDataSetChanged()
                    }
                    R.id.createUserMenuItem -> {
                        memberList.removeAt(position)
                        notifyDataSetChanged()
                    }
                }
                true
            })
        }
//        holder.getMenu(context, position)
//        val popupMenu = androidx.appcompat.widget.PopupMenu(context, holder.imageView)
        holder.memberNameTextView.text = member.Name
//        val button = holder.imageContextMenuButton
//        popupMenu.inflate(holder.getMenu(context))
//        popupMenu.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.menu1 -> {
//                    textView.text = "Вы выбрали PopupMenu 1"
//                    true
//                }
//                R.id.menu2 -> {
//                    textView.text = "Вы выбрали PopupMenu 2"
//                    true
//                }
//                R.id.menu3 -> {
//                    textView.text = "Вы выбрали PopupMenu 3"
//                    true
//                }
//                else -> false
//            }
//        }
//
//       holder.imageView.setOnClickListener {
//           popupMenu.show()
//        }


    }


    fun addMember(member: Member){
        memberList.add(member)
        notifyDataSetChanged()
    }

    fun setItems(item: MutableList<Member>) {
        memberList.clear()
        memberList.addAll(item)
        notifyDataSetChanged()
    }

}