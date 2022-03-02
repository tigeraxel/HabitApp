package com.example.sestudentjuhabitapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/*
To use the fragment, call the class method: newInstance()

val a = EditHabitFragment() This would work, but not compatible with runtime config changes.

The helper initializer, newInstance(), allows for the use of Bundles, which will survive runtime config changes.

To send data to the fragment's Bundle:

fun newInstance(data : data type) = EditHabitFragment().apply{
    arguments = Bundle().apply{
        putDatatype("key", data)
    }
}
 */

class EditHabitFragment : Fragment() {

    // Create vars here to store any desired data on creation. startValue example:
    //var startValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //startValue = requireArguments().getInt("key") // Example
    }

    override fun onCreateView( // Returns a root view object.
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_edit_habit, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Needs to find and fill any required views.
        super.onViewCreated(view, savedInstanceState)

        //val editText = view.findViewById<EditText>(R.id.exampleId) // Example
        // editText.text // Example
    }

    companion object {
        fun newInstance() = EditHabitFragment()
    }
}