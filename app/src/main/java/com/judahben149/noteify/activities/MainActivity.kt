package com.judahben149.noteify.activities

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
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

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: NavigationView

    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables here
        navView = binding.navigationView
        drawerLayout = binding.drawerLayout
        setSupportActionBar(binding.toolBar)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navView.menu, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)


        navView.setupWithNavController(navController)

        mViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.actionbar_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        when(item.itemId) {
//            R.id.menu_deleteAllNotes -> {
//                deleteAllNotes()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }


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