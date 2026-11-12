package com.example.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class NoteFragment : Fragment() {
    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(
            requireActivity()
        )[MyViewModel::class.java]

        val view = inflater.inflate(
            R.layout.note_fragment,
            container,
            false
        )

        val date_text = view.findViewById<TextView>(R.id.noteDate_text)
        val note_input = view.findViewById<EditText>(R.id.note_input)
        val clear_button = view.findViewById<Button>(R.id.clearNote_button)
        val save_button = view.findViewById<Button>(R.id.saveNote_button)

        viewModel.selectedDate.observe(viewLifecycleOwner) { date ->
            date_text.text = if (date.isEmpty()) {
                "Selected date: None"
            } else {
                "Selected date: $date"
            }
        }

        clear_button.setOnClickListener {
            note_input.text.clear()
        }

        save_button.setOnClickListener {
            val date = viewModel.selectedDate.value
            val content = note_input.text.toString().trim()

            if (date.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please select a date first.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (content.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please enter a note.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.addNote(date, content)

                note_input.text.clear()

                Toast.makeText(
                    requireContext(),
                    "Note saved successfully.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }
}