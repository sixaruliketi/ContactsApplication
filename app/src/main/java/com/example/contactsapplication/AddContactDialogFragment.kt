package com.example.contactsapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.contactsapplication.databinding.AddContactDialogFragmentBinding
import com.google.firebase.database.FirebaseDatabase

class AddContactDialogFragment : DialogFragment() {

    private lateinit var binding : AddContactDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddContactDialogFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)
        saveBtn.setOnClickListener {
            val name = nameET.text.toString()
            val number = numberET.text.toString()

            if (name.isNotEmpty() && number.isNotEmpty()){
                val id = FirebaseDatabase.getInstance().reference.push().key ?: return@setOnClickListener
                val contact = Contact(id,name,number)
                FirebaseDatabase.getInstance().getReference("Contact")
                    .child(id)
                    .setValue(contact)
                    .addOnSuccessListener {
                        dismiss()
                    }
            }
        }
        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

}