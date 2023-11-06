package com.banquil.animal.midterm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.banquil.animal.midterm.adapters.BlockedAnimalAdapter
import com.banquil.animal.midterm.databinding.ActivityManageblockBinding
import com.banquil.animal.midterm.models.Animal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ManageBlockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageblockBinding
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageblockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val json = sharedPreferences.getString("animalList", null)

        val type = object : TypeToken<List<Animal>>() {}.type
        val animals: List<Animal> = gson.fromJson(json, type)


        val blockedAnimals = animals.filter { it.isBlocked }

        blockedAnimals.sortedBy { it.name } // sorts non-blocked animals from A to Z

        binding.animalList.layoutManager = LinearLayoutManager(this)
        binding.animalList.adapter = BlockedAnimalAdapter(
            this,
            blockedAnimals.toMutableList()
        )
    }
}