package com.example.dogapplication.ui.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapplication.databinding.GridViewItemBinding
import com.example.dogapplication.entity.Dog

class PhotoGridAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Dog, PhotoGridAdapter.DogViewHolder>(DiffCallback) {

    class DogViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: Dog) {
            binding.dog = dog
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the list of dogs
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DogViewHolder {
        return DogViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(dog)
        }
        holder.bind(dog)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the Dog
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Dog]
     */
    class OnClickListener(val clickListener: (dog: Dog) -> Unit) {
        fun onClick(dog: Dog) = clickListener(dog)
    }
}
