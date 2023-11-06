package com.banquil.animal.midterm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.banquil.animal.midterm.constants.Constants
import com.banquil.animal.midterm.databinding.ActivityProfileBinding
import com.banquil.animal.midterm.models.Animal

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
            // Locate the animal by its identifier
            /*
            val animalToUpdate = it.name == name }
            animalToUpdate.isBlocked = true
            */
            // Reload activity
            val intent = Intent(this, ManageBlockActivity::class.java)
            startActivity(intent)
        }
    }
}