package ru.myuniquenickname.currencyconversion.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.myuniquenickname.currencyconversion.R
import ru.myuniquenickname.currencyconversion.databinding.FragmentCurrencyListBinding
import ru.myuniquenickname.currencyconversion.domain.Currency
import ru.myuniquenickname.myapplication.data.work_manager.WorkConstraints
import java.math.RoundingMode

class CurrencyListFragment : Fragment() {

    private var _binding: FragmentCurrencyListBinding? = null
    private val binding get() = _binding!!
    private val currencyViewModel: CurrencyViewModel by viewModel()
    private lateinit var adapterCurrency: CurrencyAdapter
    private val workConstraints = WorkConstraints()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            currencyViewModel.loadCurrencyList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        adapterCurrency = CurrencyAdapter(clickListener)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WorkManager.getInstance(requireContext())
            .enqueueUniquePeriodicWork(
                PERIODIC_LOAD_MOVIES,
                ExistingPeriodicWorkPolicy.KEEP,
                workConstraints.constrainedRequest
            )
        initViews()
        initObservers()
        refreshListener()
    }

    private fun refreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            currencyViewModel.refreshCurrencyList()
        }
    }

    private fun initViews() {

        val recyclerView = binding.currencyRecyclerView
        recyclerView.adapter = adapterCurrency
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initObservers() {
        loadingStateObserver()
        currencyListObserver()
        activeCurrencyObserver()
    }

    private fun activeCurrencyObserver() {
        currencyViewModel.activeCurrency.observe(
            viewLifecycleOwner,
            {
                binding.changeCurrencyTextView.text = it.charCode
                binding.editText.addTextChangedListener(
                    object : TextWatcher {

                        override fun afterTextChanged(s: Editable) {
                        }

                        override fun beforeTextChanged(
                            s: CharSequence,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            binding.resultConversionTextView.text = evaluateCurrency(s.toString(), it)
                        }
                    }
                )
            }
        )
    }
    fun evaluateCurrency(value: String, currency: Currency): String {
        var valueEnd = value
        if (value == "") valueEnd = "0"
        return valueEnd.toBigDecimal()
            .divide(currency.value.toBigDecimal(), 2, RoundingMode.UP)
            .multiply(currency.nominal.toBigDecimal())
            .toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun currencyListObserver() {
        currencyViewModel.currencyList.observe(
            viewLifecycleOwner,
            {
                adapterCurrency.submitList(it)
            }
        )
    }

    private fun loadingStateObserver() {
        currencyViewModel.loadingState.observe(
            viewLifecycleOwner,
            {
                when (it.status) {
                    LoadingState.Status.FAILED -> {
                        toastShow(getString(R.string.ERROR_LOADING))
                        binding.progressBar.isVisible = false
                    }
                    LoadingState.Status.RUNNING -> {
                        binding.progressBar.isVisible = true
                        binding.editText.isVisible = false
                    }
                    LoadingState.Status.SUCCESS -> {
                        binding.progressBar.isVisible = false
                        binding.editText.isVisible = true
                        binding.refreshLayout.isRefreshing = false
                    }
                }
            }
        )
    }

    private fun toastShow(text: String?) {
        val toast = Toast.makeText(
            activity,
            text,
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }

    private val clickListener = object : CurrencyAdapter.OnRecyclerItemClicked {
        override fun onClick(currency: Currency) {
            currencyViewModel.setActiveCurrency(currency)
            val valueEditText = binding.editText.text.toString()
            binding.resultConversionTextView.text = evaluateCurrency(valueEditText, currency)
        }
    }
    companion object {
        private const val PERIODIC_LOAD_MOVIES = "PERIODIC_LOAD_MOVIES"
    }
}
