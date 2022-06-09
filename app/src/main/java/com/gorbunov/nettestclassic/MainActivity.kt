package com.gorbunov.nettestclassic

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.gorbunov.nettestclassic.viewmodel.NetTestViewModel

/**
 * Основная активити приложения
 */
class MainActivity : AppCompatActivity() {

    /**
     * объявление полей, к которым нужен доступ по всему классу активити
     */
    private lateinit var viewModel: NetTestViewModel
    lateinit var animation: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * Получение вью модели с помощью встроенного механизма
         */
        viewModel = ViewModelProvider(this).get(NetTestViewModel::class.java)
        /**
         * Присвоение ссылки на анимацию в xml
         */
        animation = findViewById(R.id.animation_view)

        /**
         * Присвоение ссылки на список и установка менеджера.
         * В данном случае ставится классический LinearLayout
         */
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        /**
         * Получение ссылки на textView с заголовком
         */
        val header = findViewById<TextView>(R.id.header)


        /**
         * Подписываемся на изменения в поле LiveData, получаем неизменяемую копию
         *  Проходимся по возможным состояниям и меняем отображение в зависимости от текущего состояния
         */
        viewModel.state.observe(this) {
            when (it) {
                is NetTestViewModel.NetTestViewState.Loading -> {
                    animation.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    header.visibility = View.GONE
                }
                is NetTestViewModel.NetTestViewState.Error -> {
                    animation.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    header.visibility = View.VISIBLE
                    header.text = it.e.message
                }
                is NetTestViewModel.NetTestViewState.NetTestOk -> {
                    animation.visibility = View.GONE
                    header.visibility = View.VISIBLE
                    /**
                     * Установка текста с разными цветами
                     */
                    header.text = Html.fromHtml("<font color=#000000>Изучайте</font> " +
                            "<font color=#0000FF>актуальные темы</font>", Html.FROM_HTML_MODE_LEGACY)
                    recyclerView.adapter = NetTestRecyclerAdapter(it.data)
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }
    }
}