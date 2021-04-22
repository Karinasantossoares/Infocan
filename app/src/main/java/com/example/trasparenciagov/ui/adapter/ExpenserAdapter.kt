package com.example.trasparenciagov.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trasparenciagov.R
import com.example.trasparenciagov.model.network.DespesasResponse
import com.example.trasparenciagov.databinding.ItemExpensesBinding
import com.example.trasparenciagov.extensions.toDate
import com.example.trasparenciagov.extensions.toText
import com.example.trasparenciagov.extensions.toTextDate

class ExpenserAdapter(
    private val listDespesasResponse: List<DespesasResponse>,
) :
    RecyclerView.Adapter<ExpenserAdapter.InfocanViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpenserAdapter.InfocanViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_expenses, parent, false)
        return InfocanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InfocanViewHolder, position: Int) {
        holder.bindView(listDespesasResponse[position])
    }


    override fun getItemCount() = listDespesasResponse.count()


    inner class InfocanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemExpensesBinding.bind(itemView)

        fun bindView(despesasResponse: DespesasResponse) {
            binding.tvDateList.text = despesasResponse.dataDocumento.toTextDate()
            binding.tvTitleDetailsMembers.text =
                despesasResponse.tipoDespesa
            binding.tvPriceList.text = despesasResponse.valorDocumento.toString()
        }
    }
}

