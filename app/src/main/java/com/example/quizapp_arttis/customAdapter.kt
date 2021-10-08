package com.example.quizapp_arttis

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class customAdapter(val contexto: Context, val list: ArrayList<String>) : ArrayAdapter<String>(contexto, R.layout.customadapterlayout, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val text : String = getItem(position).toString()

        val view : View = convertView?: LayoutInflater.from(contexto).inflate(R.layout.customadapterlayout, parent, false)

        val textView: TextView = view.findViewById(R.id.txtAdapter)
        textView.text = text
        return view
    }
}