package com.action.cocktail.dashboard.vm

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.action.cocktail.R
import com.action.cocktail.dashboard.Model.DrinkModel
import com.bumptech.glide.Glide

class CockTailAdapter(
    private var cockTailList: List<DrinkModel?>
) : RecyclerView.Adapter<CockTailAdapter.CockTailViewHolder>() {

    inner class CockTailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cockTailName: TextView = itemView.findViewById(R.id.tvCocktailName)
        val cockTailImage: ImageView = itemView.findViewById(R.id.ivCocktail)
        val cockTailCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val cockTailAlcoholic: TextView = itemView.findViewById(R.id.tvAlcoholic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CockTailViewHolder {
        return CockTailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cocktail, parent, false)
        )
    }

    override fun getItemCount(): Int = cockTailList.size

    override fun onBindViewHolder(holder: CockTailViewHolder, position: Int) {
        val drink = cockTailList[position]

        holder.cockTailName.text = drink?.strDrink ?: "Unknown"
        holder.cockTailCategory.text = drink?.strCategory ?: "Unknown"
        holder.cockTailAlcoholic.text = when (drink?.strAlcoholic) {
            "Alcoholic" -> "Alcoholic "
            "Non alcoholic" -> "Non-Alcoholic "
            else -> "Unknown"
        }

        // Load Image using Glide with placeholder and error handling
        Glide.with(holder.itemView)
            .load(drink?.strDrinkThumb)
            .into(holder.cockTailImage)
    }
}
