package ru.degus.cnjoke.fragments

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import ru.degus.cnjoke.App
import ru.degus.cnjoke.MainActivity
import ru.degus.cnjoke.R
import ru.degus.cnjoke.components.WEB_URL
import ru.degus.cnjoke.databinding.WebFragmentBinding


class WebFragment : BaseFragment<WebFragmentBinding>(R.layout.web_fragment) {
    override fun injectDagger() {
        App.instance.getAppComponent()
                .activitySubComponentBuilder()
                .with(navigator as AppCompatActivity)
                .build()
                .inject(this)
    }

    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        webView = binding.webView                                                 //привязка webView
        progressBar = binding.webProgressBar                                      //привязка прогресс бара

        loadWeb()

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState.getBundle("webViewState"))      //подгрузка состояния
        }
        else {
            if ((navigator as MainActivity).isConnected()) webView.loadUrl(WEB_URL)      //проверка на доступ к сети и загрузка страницы
        }
    }

    private fun loadWeb() {

        webView.webViewClient = object : WebViewClient() {                          //реализация WebViewClient

            @TargetApi(Build.VERSION_CODES.N)
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(request.url.toString())
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
                try {
                    progressBar.visibility = View.GONE                              //отключение прогресс бара при полной загрузке страницы
                } catch (exception: java.lang.Exception) {
                    exception.printStackTrace()
                }
            }
        }

        webView.settings.loadsImagesAutomatically = true                            //стандартные настройки webView
        webView.settings.javaScriptEnabled
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
    }

    override fun onSaveInstanceState(outState: Bundle) {            //сохранение состояния webView
        super.onSaveInstanceState(outState)
        val bundle = Bundle()
        webView.saveState(bundle)
        outState.putBundle("webViewState", bundle)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {  //загрузка состояния webView
        super.onViewStateRestored(savedInstanceState)
        val bundle = Bundle()
        webView.saveState(bundle)
        savedInstanceState?.putBundle("webViewState", bundle)
    }

    override fun setTitle() {
        (navigator as MainActivity).setActionBarTitle("Api info")     //установка заголовка фрагмента
    }

}