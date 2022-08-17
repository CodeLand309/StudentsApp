package com.example.studentsapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.studentsapp.R
import com.example.studentsapp.databinding.ActivityHomeBinding
import com.example.studentsapp.dependency.AppModule
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(R.layout.activity_home) {

    lateinit var userType: AppModule.UserType

//    inner class FragmentAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity){
//        override fun getItemCount(): Int =
//            if(userType == AppModule.UserType.Student) 2
//            else 3
//
//        override fun createFragment(position: Int): Fragment {
//            return if(userType == AppModule.UserType.Student) {
//                when (position) {
//                    0 -> StudentDetailsFragment()
//                    else -> Fragment()
//                }
//            }else {
//                when (position) {
//                    0 -> TeacherHomeFragment()
//                    1 -> TeachersListFragment()
//                    else -> Fragment()
//                }
//            }
//        }
//
//    }

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setupActionBarWithNavController(navController)

//        val tabLayout = binding.tabsHost
//        val viewPager = binding.viewPager
//
//        viewPager.adapter = FragmentAdapter(this)
//
//        TabLayoutMediator(tabLayout, viewPager){tab, position ->
//            tab.text = "Tab ${position}"
//        }.attach()
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}