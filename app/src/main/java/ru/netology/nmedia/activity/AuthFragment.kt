package ru.netology.nmedia.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.databinding.FragmentAuthBinding
import ru.netology.nmedia.dto.User
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.AuthViewModel

class AuthFragment:Fragment() {
    private val viewModel: AuthViewModel by viewModels()
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val binding =FragmentAuthBinding.inflate(inflater,
            container,
            false)


        binding.password.requestFocus()
        binding.login.requestFocus()





        binding.entry.setOnClickListener{

         val log = binding.login
         val pass = binding.password
            when {
                log.isEmpty() -> {
                    binding.login.hint = "LOGIN:*"
                    Snackbar.make(binding.root, "Поля не заполнены", Snackbar.LENGTH_LONG).show()
                }
                pass.isEmpty() -> {
                    binding.password.hint = "PASSWORD:*"
                    Snackbar.make(binding.root, "Поля не заполнены", Snackbar.LENGTH_LONG).show()
                }
                else -> {
                    viewModel.logUser(log.toString(), pass.toString())
                    AndroidUtils.hideKeyboard(requireView())
                    findNavController().popBackStack()
                }
            }
        }
        viewModel.dataState.observe(viewLifecycleOwner) { state ->
            binding.progress.isVisible = state.loading
            if (state.error){
                Snackbar.make(binding.root, "404 User not found", Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.logger.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, "404 User not found", Snackbar.LENGTH_LONG).show()
        }

return binding.root
    }

}