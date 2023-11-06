package com.banquil.animal.midterm.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.banquil.animal.midterm.ProfileActivity
import com.banquil.animal.midterm.ManageBlockActivity
import com.banquil.animal.midterm.constants.Constants
import com.banquil.animal.midterm.databinding.ItemAnimalBinding
import com.banquil.animal.midterm.databinding.ItemBlockedanimalBinding
import com.banquil.animal.midterm.models.Animal

public class AnimalAdapter(
    private val activity: Activity,
    private val animals: MutableList<Animal>,
    ): RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    class AnimalViewHolder(
        private val activity: Activity,
        private val binding: ItemAnimalBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(animal: Animal) {
            binding.name.text = animal.name
            binding.row.setOnClickListener {

                // Declare Intent
                val intent = Intent(
                    activity,
                    ProfileActivity::class.java
                )
                intent.putExtra(Constants.PARAM_NAME, animal.name)
                intent.putExtra(Constants.PARAM_DESC, animal.desc)

                activity.startActivity(intent)
            }
        }
    }


    class BlockedAnimalViewHolder(
        private val activity: Activity,
        private val binding: ItemBlockedanimalBinding,
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(animal: Animal) {
            binding.name.text = animal.name
            binding.unblockButton.setOnClickListener {

                // Update the isBlocked status to false for the current animal
                animal.isBlocked = false

                // Reload activity
                val intent = Intent(activity, ManageBlockActivity::class.java)
                activity.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimalViewHolder {
        val binding = ItemAnimalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnimalViewHolder(activity, binding)
    }
    override fun getItemCount() = animals.size


    override fun onBindViewHolder(
        holder: AnimalViewHolder,
        position: Int,
    ) {
        holder.bind(animals[position])
    }
}
