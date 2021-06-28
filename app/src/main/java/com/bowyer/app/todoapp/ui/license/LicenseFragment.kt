package com.bowyer.app.todoapp.ui.license

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.databinding.FragmentTodoLicenseBinding
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LicenseFragment : Fragment() {

  @Inject
  lateinit var viewModel: LicenseViewModel

  @Inject
  lateinit var licenseAdapter: LicenseAdapter

  private lateinit var binding: FragmentTodoLicenseBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentTodoLicenseBinding.inflate(inflater, container, false)
    viewModel.state.observe(viewLifecycleOwner) { state ->
      updateLicense(state.licenseList)
    }
    lifecycleScope.launch {
      viewModel.event.collect { event ->
        when (event) {
          is LicenseEvent.PresentLicensePage -> {
            presentCustomTabs(event.url)
          }
        }
      }
    }
    initRecyclerView()
    viewModel.onCreateView()
    return binding.root
  }

  private fun initRecyclerView() {
    val dividerItemDecoration = DividerItemDecoration(
      requireContext(),
      LinearLayoutManager(requireContext()).orientation
    ).apply {
      ContextCompat.getDrawable(
        requireContext(),
        R.drawable.divider
      )?.let {
        setDrawable(
          it
        )
      }
    }
    binding.recycler.apply {
      layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      adapter = licenseAdapter
      itemAnimator = SlideInDownAnimator()
      addItemDecoration(dividerItemDecoration)
    }
  }

  private fun updateLicense(list: List<LicenseItemModel>) {
    licenseAdapter.update(
      list = list,
      onClickItem = viewModel::onClickLicenseItem
    )
  }

  private fun presentCustomTabs(url: String) {
    val schemeParams = CustomTabColorSchemeParams.Builder()
      .setToolbarColor(ContextCompat.getColor(requireContext(), R.color.primary))
      .build()

    val builder = CustomTabsIntent.Builder()
      .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, schemeParams)
      .setShowTitle(true)
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
  }
}
