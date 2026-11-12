package com.example.noteapp

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

class NotesStorage(context: Context) {

    private val preferences = context.getSharedPreferences(
        "notes_preferences",
        Context.MODE_PRIVATE
    )

    fun getNotes(): List<Note> {
        val notes = mutableListOf<Note>()
        val savedData = preferences.getString("notes", "[]")
        val jsonArray = JSONArray(savedData)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            notes.add(
                Note(
                    id = jsonObject.getInt("id"),
                    date = jsonObject.getString("date"),
                    content = jsonObject.getString("content")
                )
            )
        }

        return notes
    }

    fun addNote(date: String, content: String) {
        val notes = getNotes()

        val highestID = (notes.maxOfOrNull{it.id}?:0)

        val newNote = Note(
            id = highestID+1,
            date = date,
            content = content
        )

        val updatedNotes = notes + newNote
        val jsonArray = JSONArray()

        for (note in updatedNotes) {
            val jsonObject = JSONObject().apply {
                put("id", note.id)
                put("date", note.date)
                put("content", note.content)
            }

            jsonArray.put(jsonObject)
        }

        preferences.edit()
            .putString("notes", jsonArray.toString())
            .apply()
    }
}