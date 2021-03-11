package com.example.trasparenciagov.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.trasparenciagov.R
import com.example.trasparenciagov.adapter.ExpenserAdapter
import com.example.trasparenciagov.databinding.FragmentDetailsCongressPersonBinding
import com.example.trasparenciagov.extensions.addMask
import com.example.trasparenciagov.extensions.toText
import com.example.trasparenciagov.model.network.SendEmail
import com.example.trasparenciagov.viewModel.InfocanViewModel
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.util.*


class DetailsCongressPersonFragment : Fragment() {
    private lateinit var binding: FragmentDetailsCongressPersonBinding
    private val viewModel: InfocanViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details_congress_person,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getDetailsExpense()
        viewModel.getDetailsPolitical()


        binding.tvAtualizationDetails.addMask("NN/NN/NNNN")
        binding.tvAtualizationDetails.setText(Date().toText())
        binding.btnSendEmailDetails.setOnClickListener {
            viewModel.sendEmail()
        }

        viewModel.sendEmailPoliticalLiveData.observe(viewLifecycleOwner, Observer {
            try {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("mailto:${it.email}"))
                intent.putExtra(Intent.EXTRA_SUBJECT, it.subject)
                intent.putExtra(Intent.EXTRA_TEXT, it.bodyEmail)
                startActivity(Intent.createChooser(intent, it.title))
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    R.string.message_email_not_app,
                    Toast.LENGTH_SHORT
                ).show()
                e.printStackTrace()
            }
        })

        viewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbLoad.isVisible = it
        })

        viewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            binding.pbLoad.isVisible = it
        })

        viewModel.succesDetailsPolitical.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.nameMemberDetails.text = it.nome
                binding.emailMembersDetails.text = it.email
                binding.tvNumberDateOfBirth.text = it.dataNascimento
                binding.tvNameParty.text = it.siglaPartido
                binding.tvStatusSituation.text = it.situacao
                binding.tvNumberPhone.text = it.telefone
                it.urlFoto.let { image ->
                    Picasso.get()
                        .load(image)
                        .fit()
                        .centerCrop().into(binding.ivPersonDetails)
                }
            }
        })

        viewModel.successListExpense.observe(viewLifecycleOwner, Observer {
            val adapter = ExpenserAdapter(it)
            binding.itemDetailsExpenses.adapter = adapter
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearSelecetedLiveData()
    }

}