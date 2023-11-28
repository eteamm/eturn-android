package com.example.mainlist.adapter


import android.app.Fragment
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.mainlist.R


public class MyFragment : Fragment() {
    val IDM_A = 101;
    val IDM_B = 102;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.member, container, false)
        val button = root.findViewById<ImageButton>(R.id.pointsMember)
        registerForContextMenu(button)
        return root
    }
    override fun onCreateContextMenu(
        menu: ContextMenu, v: View?,
        menuInfo: ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(Menu.NONE, IDM_A, Menu.NONE, "Menu A")
        menu.add(Menu.NONE, IDM_B, Menu.NONE, "Menu B")
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            IDM_A -> {
                Toast.makeText(activity, "Выбран пункт А", Toast.LENGTH_LONG)
                    .show()
                return true
            }

            IDM_B -> {
                Toast.makeText(activity, "Выбран пункт B", Toast.LENGTH_LONG)
                    .show()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

}