/*package com.example.coleccionconsolas.clases

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coleccionconsolas.R
import com.example.coleccionconsolas.databinding.ConsolaLayoutBinding

class ConsolaAdapter (private val context: Context, private val items: List<Consola>) : RecyclerView.Adapter<ConsolaAdapter.ConsolaViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsolaAdapter.ConsolaViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ConsolaLayoutBinding.inflate(inflater, parent, false)

        return ConsolaViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ConsolaViewHolder, Position: Int) {
        holder.bind(items[Position])
    }

    override fun getItemCount(): Int {
        return items.size
    }



    class ConsolaViewHolder (private val binding: ConsolaLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Consola){
            binding.txTitulo.text = data.nombre
            binding.txDescripcion.text = data.descripcion

            if(data.fav){
                binding.btnFav.setImageResource(R.drawable.baseline_favorite)
            }else{
                binding.btnFav.setImageResource((R.drawable.baseline_favorite_border))
            }

            binding.btnFav.setOnClickListener {
                if(data.fav){
                    binding.btnFav.setImageResource(R.drawable.baseline_favorite_border)
                    data.fav = false
                }else{
                    binding.btnFav.setImageResource(R.drawable.baseline_favorite)
                    data.fav = true
                }
            }




        }


    }


}

*/