package com.example.trasparenciagov.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trasparenciagov.R
import com.example.trasparenciagov.model.network.PerfilPersonResponse
import com.example.trasparenciagov.databinding.ItemMembersBinding
import com.example.trasparenciagov.extensions.setOnClickListenerAnim
import com.squareup.picasso.Picasso

class MembersAdapter(
    private val onClick: (PerfilPersonResponse) -> Unit,
    private var listPersonCongress: List<PerfilPersonResponse> = mutableListOf()
) : RecyclerView.Adapter<MembersAdapter.InfocanViewHolder>() {


    fun addAll(listPersonCongress: List<PerfilPersonResponse>) {
        this.listPersonCongress = listPersonCongress
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfocanViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_members, parent, false)
        return InfocanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InfocanViewHolder, position: Int) {
        holder.bindView(listPersonCongress[position])
    }


    override fun getItemCount() = listPersonCongress.count()


    inner class InfocanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMembersBinding.bind(itemView)

        fun bindView(personCongress: PerfilPersonResponse) {
            binding.tvNamePerson.text = personCongress.nome
            binding.tvBroken.text = personCongress.siglaPartido
                Picasso.get()
                    .load(personCongress.urlFoto)
                    .fit()
                    .centerCrop()
                    .into(binding.ivPerson)
                binding.ivPerson.setOnClickListenerAnim {
                    onClick.invoke(personCongress)
                }


        }
    }
}

