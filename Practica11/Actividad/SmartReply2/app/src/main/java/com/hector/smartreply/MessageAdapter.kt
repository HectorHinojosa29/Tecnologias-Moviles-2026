package com.hector.smartreply

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hector.smartreply.databinding.ItemMessageBinding

class MessageAdapter(
    private val messages: MutableList<Message>
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(
        private val binding: ItemMessageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {
            binding.tvSender.text = message.sender
            binding.tvMessage.text = message.text
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {

        val binding = ItemMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int
    ) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size
}