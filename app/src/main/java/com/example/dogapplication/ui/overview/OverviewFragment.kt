package com.example.dogapplication.ui.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dogapplication.databinding.OverviewFragmentBinding

class OverviewFragment : Fragment() {


    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, OverviewViewModelFactory(activity.application))
            .get(OverviewViewModel::class.java)
    }


    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = OverviewFragmentBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
            viewModel.displayDogDetails(it)
        })
        // Observe the navigateToSelectedDog LiveData and Navigate when it isn't null
        // After navigating, call displayDogDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedDog.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController()
                    .navigate(OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayDogDetailsComplete()
            }
        })
        return binding.root
    }

}
