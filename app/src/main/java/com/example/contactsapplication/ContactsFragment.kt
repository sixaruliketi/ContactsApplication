package com.example.contactsapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsapplication.databinding.FragmentContactsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ContactsFragment : Fragment() {

    private lateinit var binding: FragmentContactsBinding

    private lateinit var adapter: ContactsRecyclerViewAdapter

    private val contactList = mutableListOf<Contact>()

    private val db = FirebaseDatabase.getInstance().getReference("Contact")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ContactsRecyclerViewAdapter(contactList)
        contactsRecyclerView.adapter = adapter
        contactsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        fetchData()
        addData()
    }

    private fun addData() {
        binding.floatingActionButton.setOnClickListener {
            AddContactDialogFragment().show(parentFragmentManager,"AddContactDialog")
        }
    }

    private fun fetchData() {

        db.child("test").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val contactTest = snapshot.getValue(Contact::class.java) ?: return
                binding.test.text = contactTest.id
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

//        val id = FirebaseDatabase.getInstance().reference.push().key

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                contactList.clear()
                for (child in snapshot.children){
                    val contact = child.getValue(Contact::class.java)
                    if (contact != null){
                        contactList.add(contact)
                        Log.d("TAG", "$contact")
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "error: ${error.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ContactsFragment()
    }
}