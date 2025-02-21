package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val viewModel: UserViewModel by viewModels()
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.errorTextView.visibility = View.INVISIBLE
        binding.loginButton.setOnClickListener {
            val (first, second) = validateInputs(binding.login.text.toString(), binding.password.text.toString())
            if (first) {
                binding.errorTextView.visibility = View.GONE
                val login = binding.login.text.toString()
                val password = binding.password.text.toString()
                // val remember = binding.rememberSwitch.isChecked
                uiScope.launch {
                    val answer = viewModel.loginUser(login, password)
                    if (answer != null) {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, SuccessFragment(answer), "SUCCESS_FRAGMENT_TAG")
                            .commit()
                    } else {
                        binding.errorTextView.text = "Ошибка: Неверный логин или пароль"
                        binding.errorTextView.visibility = View.VISIBLE
                    }
                }
            } else {
                binding.errorTextView.text = second
                binding.errorTextView.visibility = View.VISIBLE
            }
        }
        binding.signupText.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RegisterFragment(viewModel), "REGISTER_FRAGMENT_TAG")
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    fun validateInputs(
        login: String,
        password: String,
    ): Pair<Boolean, String> {
        return when {
            login.isEmpty() -> {
                Pair(false, "Ошибка: Пустая строка логина")
            }
            password.isEmpty() -> {
                Pair(false, "Ошибка: Пустая строка пароля")
            }
            else -> Pair(true, "")
        }
    }
}
