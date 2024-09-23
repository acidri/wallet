package com.acidriian.wallet.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.acidriian.wallet.databinding.WalletCardsBinding
import com.acidriian.wallet.model.WalletCard
import javax.inject.Inject


class WalletCardAdapter
@Inject
constructor(): RecyclerView.Adapter<WalletCardAdapter.WalletCardViewHolder>(){

    inner class WalletCardViewHolder(val binding: WalletCardsBinding): RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object: DiffUtil.ItemCallback<WalletCard>(){
        override fun areItemsTheSame(oldItem: WalletCard, newItem: WalletCard): Boolean {
            return oldItem.name == newItem.name
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: WalletCard, newItem: WalletCard): Boolean {
           return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var dataList: MutableList<WalletCard>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletCardViewHolder {
        val binding = WalletCardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WalletCardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: WalletCardViewHolder, position: Int) {
        dataList[position].let { card ->
            holder.binding.tvMyBalance.text = card.name
            holder.binding.tvMyAmount.text = card.amount.toString()
            holder.binding.walletCardPaper.setBackgroundResource(card.image)
            holder.binding.companyIcon.setImageResource(card.companyLogo)
        }
    }
}