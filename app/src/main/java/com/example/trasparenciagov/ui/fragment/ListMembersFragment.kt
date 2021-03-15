package com.example.trasparenciagov.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.trasparenciagov.R
import com.example.trasparenciagov.adapter.MembersAdapter
import com.example.trasparenciagov.databinding.FragmentListMembersBinding
import com.example.trasparenciagov.extensions.setOnClickListenerAnim
import com.example.trasparenciagov.viewModel.InfocanViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class ListMembersFragment : Fragment() {
    private lateinit var binding: FragmentListMembersBinding
    private val viewModel: InfocanViewModel by sharedViewModel()
    private lateinit var membersAdapter: MembersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_members, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPoliticalLocal()
        viewModel.verifyItemSave()

        membersAdapter = MembersAdapter(onClick = { perfilPerson ->
            viewModel.selectedPolitical(perfilPerson)
            findNavController().navigate(R.id.action_listSaveMembersFragment_to_detailsCongressPersonFragment)
        })

        viewModel.showUfPreferences()
        binding.btnOkListSaveFragment.setOnClickListenerAnim {
            val siglaUf = binding.etTypeItState.text.toString()
            val list = siglaUf.split(",")
            viewModel.getFirstListPolitical(list)
        }

        binding.textLoadMore.setOnClickListenerAnim {
            val siglaUf = binding.etTypeItState.text.toString()
            val list = listOf(siglaUf)
            viewModel.getListPolitical(list)
        }

        viewModel.ufPreferencesLiveData.observe(viewLifecycleOwner, Observer {
            binding.etTypeItState.setText(it)

        })

        viewModel.setTitleSaveOrResultMembers.observe(viewLifecycleOwner, Observer {
            binding.tvSaves.text = it
        })

        viewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbLoad.isVisible = it
        })


        viewModel.errorListPoliticalLiveData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        viewModel.messageloadmore.observe(viewLifecycleOwner, Observer {
            binding.textLoadMore.isVisible = it
        })

        viewModel.successListPoliticalLiveData.observe(
            viewLifecycleOwner,
            Observer { listPersonResponse ->
                if (listPersonResponse != null) {
                    membersAdapter.addAll(listPersonResponse)
                    binding.rvDeputies.adapter = membersAdapter
                }
            })
    }
}

