package com.banquil.animal.midterm.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.banquil.animal.midterm.ManageBlockActivity
import com.banquil.animal.midterm.databinding.ItemBlockedanimalBinding
import com.banquil.animal.midterm.models.Animal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

public class BlockedAnimalAdapter(
    private val activity: Activity,
    private val animals: MutableList<Animal>,
): RecyclerView.Adapter<BlockedAnimalAdapter.BlockedAnimalViewHolder>() {

    class BlockedAnimalViewHolder(
        private val activity: Activity,
        private val binding: ItemBlockedanimalBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(animal: Animal) {
            binding.name.text = animal.name
            binding.unblockButton.setOnClickListener {

                animal.isBlocked = false

                // Update SharedPreferences and refresh the ManageBlockActivity
                updateAnimalStatusInSharedPreferences(animal)

                // Refresh ManageBlock Activity
                val intent = Intent(activity, ManageBlockActivity::class.java)
                activity.startActivity(intent)
            }
        }
        private fun updateAnimalStatusInSharedPreferences(animal: Animal) {
            val sharedPreferences = activity.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
            val gson = Gson()

            val json = sharedPreferences.getString("animalList", null)
            val type = object : TypeToken<List<Animal>>() {}.type

            val animals = gson.fromJson<List<Animal>>(json, type).toMutableList()

            val index = animals.indexOfFirst { it.name == animal.name }
            if (index != -1) {
                // Update the isBlocked status for the specific animal
                animals[index].isBlocked = false

                // Convert the updated list back to JSON and save it to SharedPreferences
                val updatedJson = gson.toJson(animals)
                sharedPreferences.edit().putString("animalList", updatedJson).apply()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BlockedAnimalViewHolder {
        val binding = ItemBlockedanimalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BlockedAnimalViewHolder(activity, binding)
    }

    override fun getItemCount() = animals.size
    override fun onBindViewHolder(
        holder: BlockedAnimalViewHolder,
        position: Int,
    ) {
        holder.bind(animals[position])
    }



}
