package com.bowyer.app.todoapp.ui.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bowyer.app.todoapp.R
import com.bowyer.app.todoapp.databinding.FragmentTodoListBinding
import com.bowyer.app.todoapp.domain.RefreshState
import com.bowyer.app.todoapp.ext.safeNavigate
import com.bowyer.app.todoapp.ui.todolist.weather.ToDoListWeatherItemModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ToDoListFragment : Fragment() {

  @Inject
  lateinit var viewModel: ToDoListViewModel

  @Inject
  lateinit var todoListAdapter: ToDoListAdapter

  private lateinit var binding: FragmentTodoListBinding

  private val navController: NavController by lazy { findNavController() }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentTodoListBinding.inflate(inflater, container, false)
    viewModel.state.observe(viewLifecycleOwner) { state ->
      state?.todoList?.let { list ->
        updateTodo(list)
      }
      state?.weatherState?.weatherItemModel?.let { weatherItemModel ->
        updateWeather(weatherItemModel)
      }
      binding.swipeRefresh.isRefreshing = state.refreshState == RefreshState.LOADING
    }
    lifecycleScope.launch {
      viewModel.event.collect { event ->
        when (event) {
          is ToDoListEvent.OpenEditToDo -> {

            val action = ToDoListFragmentDirections.actionEditTodo(
              todo = event.toDoContent
            )
            navController.safeNavigate(action)
          }
          ToDoListEvent.OpenInsertToDo -> {
            val action = ToDoListFragmentDirections.actionInsertTodo()
            navController.safeNavigate(action)
          }
          is ToDoListEvent.PresentWeeklyWeatherBottomSheet -> {
            val action = ToDoListFragmentDirections.actionPresentWeeklyWeather(
              weeklyWeatherItemModel = event.weeklyWeather
            )
            navController.safeNavigate(action)
          }
        }
      }
    }
    initSwipeRefresh()
    initRecyclerView()
    viewModel.onCreateView(requireContext())
    binding.add.setOnClickListener {
      viewModel.onClickAddToDo()
    }
    return binding.root
  }

  private fun initSwipeRefresh() {
    binding.swipeRefresh.setOnRefreshListener {
      viewModel.onRefresh(requireContext())
    }
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
      adapter = todoListAdapter
      itemAnimator = SlideInDownAnimator()
      addItemDecoration(dividerItemDecoration)
    }
  }

  private fun updateWeather(
    model: ToDoListWeatherItemModel
  ) {
    todoListAdapter.updateWeather(
      model = model,
      onClickItem = {
        viewModel.onClickWeather()
      }
    )
  }

  private fun updateTodo(list: List<ToDoListItemModel>) {
    todoListAdapter.updateTodo(
      list = list,
      onClickItemListener = object : ToDoListItem.OnClickItemListener {
        override fun onItemClick(model: ToDoListItemModel) {
          viewModel.onClickToDoListItem(model = model)
        }

        override fun onCheckChanged(model: ToDoListItemModel) {
          viewModel.onClickToDoListDone(model = model)
        }
      }
    )
  }
}
