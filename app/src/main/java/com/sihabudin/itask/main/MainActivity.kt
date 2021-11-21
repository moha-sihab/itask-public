@file:Suppress("ControlFlowWithEmptyBody")

package com.sihabudin.itask.main


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sihabudin.itask.R
import com.sihabudin.itask.core.domain.model.CategoryModel
import com.sihabudin.itask.core.utils.DefaultData
import com.sihabudin.itask.core.utils.UserPref
import com.sihabudin.itask.credit.CreditFragment
import com.sihabudin.itask.databinding.ActivityMainBinding
import com.sihabudin.itask.home.fragment.HomeFragmentDirections
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.ToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        insertDefaultCategory()
        setupBottomNavigation()



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_credit -> {
                val openCredit = CreditFragment()
                openCredit.show(supportFragmentManager,"Open Credit")
                true
            }
            R.id.action_category -> {
                val openCategory =
                    HomeFragmentDirections.actionHomeFragmentToCategoryListFragment2()
                findNavController(R.id.nav_host_fragment).navigate(openCategory)
                true
            }
            R.id.home -> {
                onBackPressed()
                true
            }
            R.id.search ->{
                val openSearch =  HomeFragmentDirections.actionHomeFragmentToSearchFragment()
                openSearch.searchQuery = "Search by title"
                findNavController(R.id.nav_host_fragment).navigate(openSearch)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }


    private fun setupBottomNavigation() {

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment, R.id.tasksFragment, R.id.historyFragment
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }


    private fun insertDefaultCategory() {
        val userPreference = UserPref(this)
        if (!userPreference.getFlagDefaultData()) {
            val catArrayList: ArrayList<CategoryModel> = DefaultData.defaultDataCategory()

            catArrayList.toObservable()
                .subscribeBy(
                    onNext = { mainViewModel.insertCategory(it) },
                    onError = { Log.e("error insert=", it.printStackTrace().toString()) },
                    onComplete = { userPreference.setFlagDefaultData() }
                )


        }
    }


}




