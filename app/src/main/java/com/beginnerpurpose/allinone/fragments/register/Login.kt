package com.beginnerpurpose.allinone.fragments.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.beginnerpurpose.allinone.MainActivity
import com.beginnerpurpose.allinone.R
import com.beginnerpurpose.allinone.databinding.FragmentLoginBinding
import com.beginnerpurpose.allinone.viewmodels.register.LoginVMFactory
import com.beginnerpurpose.allinone.viewmodels.register.LoginViewModel
import com.beginnerpurpose.allinone.data.NewApplication
import com.beginnerpurpose.allinone.events.EventObserver
import com.beginnerpurpose.allinone.events.VoidEventObserver
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class Login : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val mViewModel: LoginViewModel by viewModels {
        LoginVMFactory((this.activity?.application as NewApplication).repository)
    }

    private lateinit var alertDialog: AlertDialog
    private lateinit var builder: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.loginViewModel = mViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        builder = MaterialAlertDialogBuilder(requireActivity())
            .setView(inflater.inflate(R.layout.alert_dialog_loading, null))
            .setCancelable(false)
        alertDialog = builder.create()

        mViewModel.getUsernameList().observe(viewLifecycleOwner) {
                list -> mViewModel.usernameList = list
        }

        (requireActivity() as MainActivity).updateNavigationMenu(R.menu.login_menu)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.startDialog.observe(viewLifecycleOwner, VoidEventObserver {
            alertDialog.show()
        })

        mViewModel.emptyField.observe(viewLifecycleOwner, EventObserver {
            if (binding.usernameLayout.editText!!.text.isEmpty()){
                binding.usernameLayout.error = it
            }

            if (binding.passwordLayout.editText!!.text.isEmpty()){
                binding.passwordLayout.error = it
            }
        })

        mViewModel.goToSignUp.observe(viewLifecycleOwner, VoidEventObserver {
            findNavController().navigate(LoginDirections.actionLoginToSignUp())
        })

        mViewModel.correctInfo.observe(viewLifecycleOwner, EventObserver {
            if (binding.rememberBox.isChecked){
                Log.d("za","43")
            }

            alertDialog.dismiss()
            Snackbar.make(binding.loginLayout, it, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(requireContext().getColor(R.color.success))
                .show()
        })

        mViewModel.incorrectInfo.observe(viewLifecycleOwner, EventObserver {
            alertDialog.dismiss()
            Snackbar.make(binding.loginLayout, it, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(requireContext().getColor(R.color.error))
                .show()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}