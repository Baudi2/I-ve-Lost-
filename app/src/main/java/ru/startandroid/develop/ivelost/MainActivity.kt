package ru.startandroid.develop.ivelost

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import ru.startandroid.develop.ivelost.databinding.ActivityMainBinding
import ru.startandroid.develop.testprojectnavigation.utils.APP_ACTIVITY
import ru.startandroid.develop.testprojectnavigation.utils.explainAppBar
import ru.startandroid.develop.testprojectnavigation.utils.explainBinding
import ru.startandroid.develop.testprojectnavigation.utils.explainSetSupportActionBar

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var drawer: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        explainBinding() //!.
        val view = binding.root
        setContentView(view)

        APP_ACTIVITY = this
        //? к этим переменным можно получить в любом месте через APP_ACTIVITY,
        //? это нужно чтобы прописать функции в funsUtils для использования по всему приложению
        drawer = binding.drawerLayout
        toolbar = binding.toolbar
        navView = binding.navView

        //? объявляем host для фрагментов и находим navController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.findNavController()

        explainAppBar() //!.
        appBarConfiguration = AppBarConfiguration(
                setOf(R.id.fragmentFound2, R.id.fragmentLost2,
                        R.id.fragmentMessages2, R.id.fragmentProfile2,
                        R.id.fragmentAbout2, R.id.fragmentReview2),
                drawer
        )

        //? определяем что произойдет по нажатию меню элемента home
        binding.navView.menu.findItem(R.id.home_drawer).setOnMenuItemClickListener {
            //? по нажатию вызываем метод onBackPressed() который работает как обычная кнопка назад
            //? и скрываем drawer чтобы он не был открыт после перехода обратно
            onBackPressed()
            drawer.close()
            true
        }

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        explainSetSupportActionBar() //!.

        //? тут пока недоделано, это должен был быть drawerLayout т.е. бургер, но пока не понятно.
        binding.navView.setupWithNavController(navController)
    }

    //? функция отвечает за работу backButton. По сути без нее нажатие на backButton на верху не должно давать никакого эффекта.
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}







