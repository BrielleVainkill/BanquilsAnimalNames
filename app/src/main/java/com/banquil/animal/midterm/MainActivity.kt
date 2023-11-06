package com.banquil.animal.midterm

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.banquil.animal.midterm.adapters.AnimalAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.banquil.animal.midterm.models.Animal
import com.banquil.animal.midterm.databinding.ActivityAnimalnamesBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AnimalNamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimalnamesBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()
    private val animals: MutableList<Animal> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalnamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val placeholderDesc = getString(R.string.placeholder_desc) //for lorem ipsum

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)

        // Load the list of animals from SharedPreferences
        val json = sharedPreferences.getString("animalList", null)

        if (json != null) {
            val type = object : TypeToken<List<Animal>>() {}.type
            val savedAnimals: List<Animal> = gson.fromJson(json, type)
            animals.addAll(savedAnimals)
        } else {
            // If there is no saved data, add default animals
            animals.add(Animal("Wombat", placeholderDesc))
            animals.add(Animal("Orca", placeholderDesc))
            animals.add(Animal("Hampter", placeholderDesc))
            animals.add(Animal("Prairie Dog", placeholderDesc))
            animals.add(Animal("Guinea Pig", placeholderDesc))
            animals.add(Animal("Marmot", placeholderDesc))
            animals.add(Animal("Capybara", placeholderDesc))
            animals.add(Animal("Platypus", placeholderDesc))
        }

        val nonBlockedAnimals = animals.filter { !it.isBlocked }

        nonBlockedAnimals.sortedBy { it.name } // sorts non-blocked animals from A to Z

        binding.animalList.layoutManager = LinearLayoutManager(this)
        binding.animalList.adapter = AnimalAdapter(
            this,
            nonBlockedAnimals.toMutableList()
        )
        binding.manageBlockButton.setOnClickListener {
          val intent = Intent(this, ManageBlockActivity::class.java)
          startActivity(intent)
        }
    }
    override fun onPause() {
        super.onPause()

        // Serialize and save the list of animals to SharedPreferences
        val json = gson.toJson(animals)
        sharedPreferences.edit().putString("animalList", json).apply()
    }
}
