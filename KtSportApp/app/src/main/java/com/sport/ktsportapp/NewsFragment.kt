package com.sport.ktsportapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sport.ktsportapp.databinding.FragmentNewsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitClient.apiService.getNews()
                if (response.isSuccessful) {
                    val newsList = response.body()
                    // Отобразите полученные данные новостей на RecyclerView
                    if (!newsList.isNullOrEmpty()) {
                        val adapter = NewsAdapter(newsList)
                        binding.recyclerViewNews.adapter = adapter
                        binding.recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())
                    }
                } else {
                    // Обработка ошибки получения данных
                }
            } catch (e: Exception) {
                // Обработка ошибки
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



