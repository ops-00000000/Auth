package ru.netology.nmedia.activity

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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



        binding.login.requestFocus()
        binding.password.requestFocus()




        binding.entry.setOnClickListener{

         val log = binding.login.toString()
         val pass = binding.password.toString()
            if (log.isEmpty() || log.isBlank()){
                binding.login.hint = "Это поле не заполнено"
                binding.loginName.setTextColor(Color.RED)
                //Toast.makeText(this@AuthFragment,getString(R.string.empty_error), Toast.LENGTH_LONG).show()
            }
            else if (pass.isEmpty() || pass.isBlank()){
                binding.password.hint = "Это поле не заполнено"
                binding.passwordName.setTextColor(Color.RED)
                //Toast.makeText(this@AuthFragment,getString(R.string.empty_error), Toast.LENGTH_LONG).show()
            }
            else {
                viewModel.logUser(log, pass)
                findNavController().popBackStack()
                AndroidUtils.hideKeyboard(requireView())
            }
        }



return binding.root
    }

}