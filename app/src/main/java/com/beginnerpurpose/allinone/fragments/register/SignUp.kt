package com.beginnerpurpose.allinone.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.beginnerpurpose.allinone.MainActivity
import com.beginnerpurpose.allinone.R
import com.beginnerpurpose.allinone.data.NewApplication
import com.beginnerpurpose.allinone.databinding.FragmentSignUpBinding
import com.beginnerpurpose.allinone.events.EventObserver
import com.beginnerpurpose.allinone.viewmodels.register.SignUpVMFactory
import com.beginnerpurpose.allinone.viewmodels.register.SignUpViewModel
import com.google.android.material.snackbar.Snackbar

class SignUp : Fragment() {

    private var _binding : FragmentSignUpBinding? = null
    val binding get() = _binding!!

    private val mViewModel: SignUpViewModel by viewModels {
        SignUpVMFactory((this.activity?.application as NewApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)

        binding.signupViewModel = mViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        (requireActivity() as MainActivity).updateNavigationMenu(R.menu.main_menu)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.userCreated.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(binding.signupLayout, it, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(requireContext().getColor(R.color.purple_700))
                .show()

            findNavController().navigate(SignUpDirections.actionSignUpToLogin())
        })

        mViewModel.emptyField.observe(viewLifecycleOwner, EventObserver {
            if (binding.usernameeLayout.editText!!.text.isEmpty()){
                binding.usernameeLayout.error = it
            }

            if (binding.passworddLayout.editText!!.text.isEmpty()){
                binding.passworddLayout.error = it
            }

            if (binding.firstnameLayout.editText!!.text.isEmpty()){
                binding.firstnameLayout.error = it
            }

            if (binding.lastnameLayout.editText!!.text.isEmpty()){
                binding.lastnameLayout.error = it
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}