package com.example.sestudentjuhabitapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class habitAdapter(val mCtx: Context, val layoutResId: Int, val habitList: List<HabitDataClass>) :
    ArrayAdapter<HabitDataClass>(mCtx, layoutResId, habitList) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val textViewName = view.findViewById<TextView>(R.id.TextViewName)

        val habit = habitList[position]

        textViewName.text = habit.name

        return view
    }
}