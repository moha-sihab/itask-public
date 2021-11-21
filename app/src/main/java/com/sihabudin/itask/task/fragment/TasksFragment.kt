package com.sihabudin.itask.task.fragment

import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sihabudin.itask.R
import com.sihabudin.itask.databinding.FragmentTasksBinding
import com.sihabudin.itask.task.adapter.SectionPagerAdapter

class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.today,
            R.string.tomorrow,
            R.string.nextWeek
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sectionPagerAdapter = SectionPagerAdapter(this)

        with(binding) {
            viewPagerTask.adapter = sectionPagerAdapter
            TabLayoutMediator(tabTask, viewPagerTask) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

        }


    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.findItem(R.id.search).isVisible = false
        menu.findItem(R.id.action_credit).isVisible = false
        menu.findItem(R.id.action_category).isVisible = false
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}