package com.rm.averagepriceapp.presentation.properties

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rm.averagepriceapp.R
import com.rm.averagepriceapp.common.hide
import com.rm.averagepriceapp.common.show
import com.rm.averagepriceapp.common.showViews
import com.rm.averagepriceapp.databinding.FragmentPropertiesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertiesFragment : Fragment(R.layout.fragment_properties) {
    private var _binding: FragmentPropertiesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PropertiesViewModel by viewModels()

    @SuppressLint("StringFormatMatches")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPropertiesBinding.bind(view)
        with(binding) {
            viewModel.viewState.observe(viewLifecycleOwner) {
                when (it) {
                    is PropertiesListState.Loading -> {
                        progressBar.show()
                    }
                    is PropertiesListState.Success -> {
                        propertyAveragePrice.text = getString(R.string.average_property_price_value, it.data)
                        progressBar.hide()
                        showViews(priceLabel, propertyAveragePrice)
                    }
                    is PropertiesListState.Error -> {
                        propertyAveragePrice.text = it.errorMessage
                        progressBar.hide()
                        showViews(priceLabel, propertyAveragePrice)
                    }
                }
            }
        }
    }
}