package ru.degus.cnjoke.fragments

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.degus.cnjoke.App
import ru.degus.cnjoke.MainActivity
import ru.degus.cnjoke.R
import ru.degus.cnjoke.adapters.MainAdapter
import ru.degus.cnjoke.databinding.MainFragmentBinding
import ru.degus.cnjoke.viewmodels.MainViewModel
import javax.inject.Inject


class  MainFragment : BaseFragment<MainFragmentBinding>(R.layout.main_fragment) {
    override fun injectDagger() {
        App.instance.getAppComponent()
            .activitySubComponentBuilder()
            .with(navigator as AppCompatActivity)
            .build()
            .inject(this)

        App.instance.getViewModelSubComponent().inject(viewModel)
    }

    @Inject
    lateinit var viewModel: MainViewModel                                           //инъекция MainViewModel

    lateinit var adapter: MainAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel                                           //привязка MainViewModel

        createRecyclerView()                                                    //создания recyclerView с переферией

        observeViewModel()                                                              //подписка на изменения состаяния viewModel

        binding.btnReload.setOnClickListener {
            if ((navigator as MainActivity).isConnected()) {                       //проверка на наличие доступа к сети
                if (viewModel.requestText.isNotEmpty()) viewModel.downloadJokes()   //проверка на пустую строку в поле ввода
                else navigator.toast("Please, enter a number")
            } else navigator.toast("No internet connection")
        }
    }

    private fun observeViewModel() {
        viewModel.errorData.observe(viewLifecycleOwner,
                Observer {
                    showInformDialog("Error", it.message) {
                        navigator.closeApp()                                        //закрытия приложения при ошибке в получении запроса
                    }
                }
        )

        viewModel.jokesData.observe(viewLifecycleOwner,
                Observer {
                    if (it != null)
                        adapter.setItems(it)                             //установка нового списка в recyclerView при удачной загрузке списка альбомов
                }
        )
    }

    private fun createRecyclerView() {                                          //создание recyclerView
        val llm = LinearLayoutManager(navigator as Context)
        binding.rvDownloadJokes.layoutManager = llm                             //установка LinearLayoutManager

        adapter = MainAdapter()
        binding.rvDownloadJokes.adapter = adapter                               //установка адаптера
        binding.rvDownloadJokes.addItemDecoration(DividerItemDecoration(navigator as Context, LinearLayoutManager.VERTICAL)) //добавление разделитей между элементами списка
    }

    override fun setTitle() {                                                   //установка заголовка фрагмента
        (navigator as MainActivity).setActionBarTitle("Jokes")
    }
}