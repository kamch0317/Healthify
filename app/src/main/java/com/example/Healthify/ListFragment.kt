package com.example.Healthify

import Healthify.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Healthify.viewmodel.WorkoutViewModel
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.fragment_list2.view.*

class ListFragment : Fragment() {

    lateinit var model: WorkoutViewModel




    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {




        val view = inflater.inflate(R.layout.fragment_list2, container, false)

        val adapter = ListAdapter()
        val recyclerView = view.recyclerViewforlist
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        model = ViewModelProvider(this)[WorkoutViewModel::class.java]
        model.getAllData.observe(viewLifecycleOwner, Observer { workoutList2 ->
            adapter.setData(workoutList2)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)
        view.backbtn.setOnClickListener{workout()}

        return view
    }





    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllWorkout2()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllWorkout2() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            model.deleteAllWorkout2()
            Toast.makeText(requireContext(), "Deleted!", Toast.LENGTH_SHORT)
                .show()

        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to delete?")
        builder.create().show()
    }

    private fun workout() {
        val intent = Intent(context, WorkoutMain::class.java)
        startActivity(intent)
    }




}

