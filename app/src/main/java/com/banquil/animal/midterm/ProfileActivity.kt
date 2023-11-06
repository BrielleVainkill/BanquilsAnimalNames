package com.banquil.animal.midterm

import android.content.SharedPreferences
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.banquil.animal.midterm.constants.Constants
import com.banquil.animal.midterm.databinding.ActivityProfileBinding
import com.banquil.animal.midterm.models.Animal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProfileActivity : AppCompatActivity() {


    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(Constants.PARAM_NAME)
        val desc = intent.getStringExtra(Constants.PARAM_DESC)

        binding.name.text = name
        binding.desc.text = desc

        binding.blockButton.setOnClickListener {
            val animalName = intent.getStringExtra(Constants.PARAM_NAME) // Get the animal's name from the intent

            val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
            val gson = Gson()

            // Retrieve the list of animals from SharedPreferences
            val json = sharedPreferences.getString("animalList", null)
            val type = object : TypeToken<List<Animal>>() {}.type
            val animals = gson.fromJson<List<Animal>>(json, type).toMutableList()

            // Update the isBlocked status for the animal with the matching name
            val animalToUpdate = animals.firstOrNull { it.name == animalName }
            animalToUpdate?.isBlocked = true

            // Save the updated list of animals back to SharedPreferences
            val updatedJson = gson.toJson(animals)
            sharedPreferences.edit().putString("animalList", updatedJson).apply()

            // back to AnimalNames Activity
            val intent = Intent(this, AnimalNamesActivity::class.java)
            startActivity(intent)
        }
    }
}