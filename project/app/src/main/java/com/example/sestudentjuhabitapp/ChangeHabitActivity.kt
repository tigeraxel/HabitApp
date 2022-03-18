package com.example.sestudentjuhabitapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import android.content.Intent


class ChangeHabitActivity : AppCompatActivity() {
    val habit = HabitClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_habit)

        val intent = intent
        val habitName = intent.getStringExtra("habitName")

        val fm: FragmentManager = supportFragmentManager
        val fragment: EditHabitFragment =
            fm.findFragmentById(R.id.edit_fragment_container_view) as EditHabitFragment
        fragment.saveHabitName(habitName!!)
        val changeHabitBtn = findViewById<Button>(R.id.change_button)
        val deleteHabitBtn = findViewById<Button>(R.id.delete_button)

        deleteHabitBtn.setOnClickListener {
            val confirmDeletePopup =
                AlertDialog.Builder(this) // A popup, asking if the user really want to delete.

            confirmDeletePopup.setCancelable(false)
                .setMessage(R.string.change_delete_habit_popup_detail_text)
                .setPositiveButton(R.string.yes, DialogInterface.OnClickListener { _, _ ->
                    habit.deleteHabit(habitName!!)
                    finish()
                })
                .setNegativeButton(R.string.no, DialogInterface.OnClickListener { dialog, _ ->
                    dialog.cancel()
                })
            val alert = confirmDeletePopup.create()
            alert.setTitle(R.string.change_delete_habit_popup_title_text)
            alert.show()
        }
        changeHabitBtn.setOnClickListener() {
            fragment.updateHabit()
            finish()
        }
    }

}