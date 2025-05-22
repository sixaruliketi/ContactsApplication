package com.example.contactsapplication

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapplication.databinding.ContactItemBinding

class ContactsRecyclerViewAdapter(private val list : List<Contact>) : RecyclerView.Adapter<ContactsRecyclerViewAdapter.Holder>() {

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding = ContactItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item,parent,false)
        return Holder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            val contact = list[position]
            contactName.text = contact.name
            contactNumber.text = contact.number
        }
    }

}