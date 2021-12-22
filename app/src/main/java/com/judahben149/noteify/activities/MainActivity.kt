package com.judahben149.noteify.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import com.judahben149.noteify.R
import com.judahben149.noteify.databinding.ActivityMainBinding
import com.judahben149.noteify.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_note_list.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var mViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
//        setupActionBarWithNavController(navController)

        mViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.deleteAllNotesIcon.setOnClickListener {
            deleteAllNotes()
        }

    }


    private fun deleteAllNotes() {
        val builder = AlertDialog.Builder(this)

        builder.apply {

            setPositiveButton(Html.fromHtml("<font color='#1F5DD1'>Yes</font>")) { _, _ ->
                mViewModel.deleteAllNotes()
                Snackbar.make(binding.root, "Successfully deleted all notes", Snackbar.LENGTH_LONG)
                    .show()

            }
            setNegativeButton(Html.fromHtml("<font color='#1F5DD1'>No</font>")) { _, _ ->

            }

            setTitle("Delete all notes")
            setMessage("Are you sure you want to delete all notes?")
            setIcon(R.drawable.ic_delete)
            create()
            show()
        }
    }
}