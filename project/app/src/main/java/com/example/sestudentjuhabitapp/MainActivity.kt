package com.example.sestudentjuhabitapp

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

const val habitNamePlaceholder = "habitName"
const val alarmChannelId = "HabitAlarm"
const val notificationNamePlaceholder = "HabitApp"
const val notificationDescriptionPlaceholder = "Description" // The description text for the notification, should be name of relevant habit.
const val notificationIdPlaceholder = 13 // Should be a unique id for each habit that sets an alarm.

lateinit var notificationsManager : NotificationManager

class MainActivity : AppCompatActivity() {

    val habit = HabitClass()
    private var mAuth = FirebaseAuth.getInstance();
    private val database = Firebase.database
    private var email = mAuth.currentUser?.email.toString().replace(".", "")
    lateinit var habitsRef: DatabaseReference
    lateinit var habitList: MutableList<HabitData>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.listView)

        // Create the mandatory (for API > 25) notification channel:
        createNotificationChannels()

        val btnCreateHabitActivity = findViewById<Button>(R.id.btnCreateHabitActivity)
        val btnChallengeActivity = findViewById<Button>(R.id.btnChallengeActivity)
        val btnSettingsActivity = findViewById<ImageButton>(R.id.btnSettingsActivity)

        //Starts NewHabitActivity when clicked
        btnCreateHabitActivity.setOnClickListener {
            val intent = Intent(this, NewHabitActivity::class.java)
            startActivity(intent)
        }

        //Starts ChallengeActivity when clicked
        btnChallengeActivity.setOnClickListener {
            val intent = Intent(this, ChallengeActivity::class.java)
            startActivity(intent)
        }

        //Starts SettingsActivity when clicked
        btnSettingsActivity.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        habitList = mutableListOf()
        habitsRef = database.getReference(databaseUsersPath).child(email).child(databaseHabitsPathString)

        database.getReference(databaseUsersPath).child(email).child(databaseChallengesPathString).get()
            .addOnSuccessListener {
                if (it.hasChildren()) {
                    for (snapshot in it.children) {
                        var reqHabit = snapshot.getValue<HabitData>()
                        val dialogBuilder = AlertDialog.Builder(this)
                        var arrayOfDays = arrayListOf<String>()
                        if (reqHabit!!.days!![monday] == true)
                            arrayOfDays.add(getString(R.string.monday))
                        if (reqHabit!!.days!![tuesday] == true)
                            arrayOfDays.add(getString(R.string.tuesday))
                        if (reqHabit!!.days!![wednesday] == true)
                            arrayOfDays.add(getString(R.string.wednesday))
                        if (reqHabit!!.days!![thursday] == true)
                            arrayOfDays.add(getString(R.string.thursday))
                        if (reqHabit!!.days!![friday] == true)
                            arrayOfDays.add(getString(R.string.friday))
                        if (reqHabit!!.days!![saturday] == true)
                            arrayOfDays.add(getString(R.string.saturday))
                        if (reqHabit!!.days!![sunday] == true)
                            arrayOfDays.add(getString(R.string.sunday))
                        var days = arrayOfDays.toString().replace("[", "")
                        days = days.replace("]", "")
                        var notification = ""
                        if (reqHabit.pushNotification == true)
                            notification = getString(R.string.notifications_is_on)
                        else notification = getString(R.string.notifications_is_off)

                        val haveChallengeYou = resources.getString(R.string.have_challenged_you);
                        val theNameOfTheChallengeIs =
                            resources.getString(R.string.The_name_of_the_challenge_is);
                        val theChallengeIsDoneOn =
                            resources.getString(R.string.The_challenge_is_done_on);
                        val theTimeEverydayIs = resources.getString(R.string.The_time_everyday_is);

                        dialogBuilder.setMessage(
                            "${reqHabit!!.challenger} " + haveChallengeYou + "\n" +
                                    theNameOfTheChallengeIs + " ${reqHabit!!.name}" + "\n" +
                                    theChallengeIsDoneOn + " $days" + "\n" +
                                    theTimeEverydayIs + " ${reqHabit!!.time}" + "\n" +
                                    "$notification"
                        )
                            // if the dialog is cancelable
                            .setCancelable(false)
                            // positive button text and action
                            .setPositiveButton(
                                getString(R.string.accept),
                                DialogInterface.OnClickListener { dialog, id ->
                                    habit.acceptChallenge(reqHabit.name!!)
                                    dialog.cancel()
                                })
                            // negative button text and action
                            .setNegativeButton(
                                getString(R.string.deny),
                                DialogInterface.OnClickListener { dialog, id ->
                                    habit.deleteChallenge(reqHabit.name!!)
                                    dialog.cancel()
                                })
                        // create dialog box
                        val alert = dialogBuilder.create()
                        // set title for alert dialog box
                        alert.setTitle(getString(R.string.challenge_request))
                        // show alert dialog
                        alert.show()
                    }
                }
            }

        habitsRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) { }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (h in snapshot.children) {
                        val habit = h.getValue(HabitData::class.java)
                        habitList.add(habit!!)
                    }
                    val adapter = habitAdapter(applicationContext, R.layout.habits, habitList)
                    listView.adapter = adapter
                    listView.setOnItemClickListener { _, _, position, _ ->

                        var selectedHabit = habitList[position]

                        // Alert dialog that allows the user to select if they want to edit or view the habit in a calendar view
                        val selectActionDialog = AlertDialog.Builder(this@MainActivity)
                        selectActionDialog.setMessage(R.string.selected_habit_popup_text)
                        selectActionDialog.setCancelable(true)
                        selectActionDialog.setPositiveButton(R.string.select_edit_habit,
                            DialogInterface.OnClickListener { _, _ ->
                                val changeHabitIntent =
                                    Intent(this@MainActivity, ChangeHabitActivity::class.java)
                                changeHabitIntent.putExtra(habitNamePlaceholder, selectedHabit.name!!)
                                startActivity(changeHabitIntent)
                            })

                        selectActionDialog.setNegativeButton(R.string.select_show_in_calendar,
                            DialogInterface.OnClickListener { _, _ ->
                                val habitCalendarIntent =
                                    Intent(this@MainActivity, CalenderActivity::class.java)
                                habitCalendarIntent.putExtra(habitNamePlaceholder, selectedHabit.name!!)
                                startActivity(habitCalendarIntent)
                            })

                        val alert = selectActionDialog.create()
                        alert.show()
                    }
                }
            }
        })
    }
    private fun createNotificationChannels(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val notificationName = notificationNamePlaceholder
            val notificationDescriptionText = notificationDescriptionPlaceholder
            val notificationImportance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(alarmChannelId, notificationName, notificationImportance).apply {
                description = notificationDescriptionText
            }
            notificationsManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationsManager.createNotificationChannel(notificationChannel)
        }
    }
}
fun displayHabitNotification(contentTitle : String, contentText : String, context : Context){

    val notificationBuilder = NotificationCompat.Builder(context, alarmChannelId)

    notificationBuilder
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(contentTitle) // The title that is displayed to the user.
        .setContentText(contentText)
        .priority = NotificationCompat.PRIORITY_DEFAULT

    notificationsManager.notify(notificationIdPlaceholder, notificationBuilder.build())
}