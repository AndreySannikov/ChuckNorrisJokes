package ru.degus.cnjoke

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.degus.cnjoke.fragments.WebFragment


enum class Layout(val id: Int) {                                        //класс точек для перемещений в навигации
    MAIN(R.id.mainFragment),
    WEB(R.id.webFragment)
}
interface MainNavigator {                                               //интерфейс навигации по приложению
    fun navigateTo(layout: Layout)
    fun closeApp()
    fun toast(msg: String)
}

class MainActivity : AppCompatActivity(), MainNavigator {

    private lateinit var nav: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav = Navigation.findNavController(this, R.id.my_nav_host_fragment) //привязка контролера навигации к хосту
        val bnv = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bnv.setupWithNavController(nav)
    }

    override fun navigateTo(layout: Layout) {                                               //метод для перемещения между фрагментами
        Log.d("MainActivity", "onNextFragment = $layout")                             //и передачей информации между ними через Bundle
        nav.navigate(layout.id)
    }

    override fun closeApp() {                                                                   //метод закрытия приложения
        finish()
    }

    override fun toast(msg: String) {                                                           //метод вызова Toast сообщения
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {                                                          //переопределение метода нажатия на системную кнопку Back
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment)
        if (
                navHostFragment != null &&                                                  //если хост не  NULL
                navHostFragment.childFragmentManager.fragments[0] is WebFragment &&         //если текущий фрагмент WebFragment
                (navHostFragment.childFragmentManager.fragments[0] as WebFragment).webView.canGoBack() //если у webView в стеки есть страницы

        ) (navHostFragment.childFragmentManager.fragments[0] as WebFragment).webView.goBack()  //то переход по стеку webView назад
        else super.onBackPressed()                                                          //иначе обычное поведение
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title                                                //метод установки заголовка в actionBar
    }

    fun isConnected(): Boolean {                                                    //стандартная проверка на доступ к сети
        var connected = false
        try {
            val cm = App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message)
        }
        return connected
    }
}