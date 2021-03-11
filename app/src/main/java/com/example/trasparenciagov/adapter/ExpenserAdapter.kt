package com.example.trasparenciagov.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trasparenciagov.R
import com.example.trasparenciagov.model.network.DespesasResponse
import com.example.trasparenciagov.databinding.ItemExpensesBinding
import com.example.trasparenciagov.extensions.addMask
import com.example.trasparenciagov.extensions.toDate
import com.example.trasparenciagov.extensions.toText
import java.text.SimpleDateFormat
import java.util.*

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
            binding.tvDateList.setText(despesasResponse.dataDocumento)
            binding.tvTitleDetailsMembers.text =
                despesasResponse.tipoDespesa
            binding.tvPriceList.text = despesasResponse.valorDocumento.toString()
        }
    }
}

