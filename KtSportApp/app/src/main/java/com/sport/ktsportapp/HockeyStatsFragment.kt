package com.sport.ktsportapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sport.ktsportapp.databinding.FragmentHockeyStatsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import androidx.recyclerview.widget.LinearLayoutManager


class HockeyStatsFragment : Fragment() {

    private var _binding: FragmentHockeyStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHockeyStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = RetrofitClient.apiService.getHockeyStats()
                if (response.isSuccessful) {
                    val statsList = response.body()
                    // Отобразите полученные данные статистики хоккея на RecyclerView
                    if (!statsList.isNullOrEmpty()) {
                        val adapter = HockeyStatsAdapter(statsList)
                        binding.recyclerViewHockeyStats.adapter = adapter
                        binding.recyclerViewHockeyStats.layoutManager = LinearLayoutManager(requireContext())
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



