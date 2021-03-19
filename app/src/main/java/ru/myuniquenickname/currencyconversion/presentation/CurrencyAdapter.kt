package ru.myuniquenickname.currencyconversion.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.myuniquenickname.currencyconversion.databinding.CurrencyItemFragmentBinding
import ru.myuniquenickname.currencyconversion.domain.Currency
import java.math.RoundingMode

class CurrencyAdapter(private val clickListener: OnRecyclerItemClicked) :
    ListAdapter<Currency, CurrencyAdapter.CurrencyViewHolder>(CurrencyDiffCallback()) {

    class CurrencyViewHolder(val binding: CurrencyItemFragmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CurrencyItemFragmentBinding.inflate(layoutInflater, parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val itemCurrency = getItem(position)
        holder.binding.apply {
            rubTextView.text = "${itemCurrency.value.toBigDecimal().setScale(2, RoundingMode.UP)} RUB"
            changeCurrencyTextView.text = "1 ${itemCurrency.charCode}"
            holder.itemView.setOnClickListener { clickListener.onClick(itemCurrency) }
        }
    }

    interface OnRecyclerItemClicked {
        fun onClick(currency: Currency)
    }
}

private class CurrencyDiffCallback : DiffUtil.ItemCallback<Currency>() {

    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        (oldItem.numCode == newItem.numCode)

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean =
        (oldItem.value == newItem.value && oldItem.previous == newItem.previous)
}
