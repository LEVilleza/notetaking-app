package com.example.noteapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

class DateFragment : Fragment() {
    lateinit var viewModel: MyViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(requireActivity())[MyViewModel::class.java]

        val view = inflater.inflate(R.layout.date_fragment, container, false)

        val dateText = view.findViewById<TextView>(R.id.selectedDate_text)
        val dateButton = view.findViewById<Button>(R.id.selectDate_button)

        viewModel.selectedDate.observe(viewLifecycleOwner){selectedDate ->
            dateText.text = if (selectedDate.isEmpty()) "No date selected" else selectedDate
        }

        dateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val dateDialog = DatePickerDialog(requireContext(),
                {_, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = String.format("%04d-%02d-%02d",selectedYear, selectedMonth+1, selectedDay)
                    viewModel.setSelectedDate(formattedDate)
                },
                year,
                month,
                day
            )

            dateDialog.show()
        }




        return view
    }
}