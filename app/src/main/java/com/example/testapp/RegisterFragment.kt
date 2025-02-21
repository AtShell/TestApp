package com.example.testapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.testapp.databinding.FragmentRegisterBinding
import com.example.testapp.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment(
    private val viewModel: UserViewModel,
) : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    private lateinit var drawableStart: Drawable
    private lateinit var drawableEnd: Drawable
    private val alf = ('a'..'z') + ('A'..'Z') + ('0'..'9') + ('!') + ('$')
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        drawableStart = ContextCompat.getDrawable(requireContext(), R.drawable.ic_lock)!!
        drawableEnd = ContextCompat.getDrawable(requireContext(), R.drawable.ic_check_two)!!
        binding.errorTextView.visibility = View.INVISIBLE
        binding.registerButton.setOnClickListener {
            val (first, second) =
                validateInputs(
                    binding.login.text.toString(),
                    binding.username.text.toString(),
                    binding.password.text.toString(),
                    binding.passwordRepeat.text.toString(),
                )
            if (first) {
                binding.errorTextView.visibility = View.GONE
                val login = binding.login.text.toString()
                val username = binding.username.text.toString()
                val password = binding.password.text.toString()
                val user = User(0, login, password, username)
                uiScope.launch {
                    val flag = viewModel.registerUser(user)
                    if (flag) {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, LoginFragment(), "LOGIN_FRAGMENT_TAG2")
                            .addToBackStack(null)
                            .commit()
                    } else {
                        binding.errorTextView.text = "Ошибка: Имя пользователя уже занято"
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
                .replace(R.id.fragmentContainer, LoginFragment(), "LOGIN_FRAGMENT_TAG3")
                .addToBackStack(null)
                .commit()
        }

        binding.login.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                    checkCharSequence(s, binding.login)
                }

                override fun afterTextChanged(s: Editable?) {}
            },
        )

        binding.username.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                    checkCharSequence(s, binding.username)
                }

                override fun afterTextChanged(s: Editable?) {}
            },
        )

        binding.password.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                    checkCharSequence(s, binding.password)
                }

                override fun afterTextChanged(s: Editable?) {}
            },
        )

        binding.passwordRepeat.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {}

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                    checkCharSequence(s, binding.passwordRepeat)
                }

                override fun afterTextChanged(s: Editable?) {}
            },
        )

        return binding.root
    }

    fun validateInputs(
        login: String,
        username: String,
        password: String,
        passwordRepeat: String,
    ): Pair<Boolean, String> {
        return when {
            login.isEmpty() -> Pair(false, "Ошибка: Пустая строка логина")

            username.isEmpty() -> Pair(false, "Ошибка: Пустая строка имени пользователя")

            password.isEmpty() -> Pair(false, "Ошибка: Пустая строка пароля")

            passwordRepeat.isEmpty() -> Pair(false, "Ошибка: Пустая строка повтора пароля")

            password != passwordRepeat -> Pair(false, "Ошибка: Пароли не совпадают")

            isInvalid(login) -> Pair(false, "Ошибка: Недопустимые символы в первой строке")

            isInvalid(username) -> Pair(false, "Ошибка: Недопустимые символы во второй строке")

            isInvalid(password) -> Pair(false, "Ошибка: Недопустимые символы в третьей строке")

            isInvalid(passwordRepeat) -> Pair(false, "Ошибка: Недопустимые символы в четвертой строке")

            else -> Pair(true, "")
        }
    }

    fun isInvalid(text: String): Boolean {
        text.forEach {
            if (it !in alf) return true
        }
        return false
    }

    private fun checkCharSequence(
        s: CharSequence?,
        editText: EditText,
    ) {
        if (s.isNullOrEmpty()) {
            hideDrawableEnd(editText)
        } else {
            var bool = true
            for (it in s) {
                if (it !in alf) {
                    hideDrawableEnd(editText)
                    bool = false
                    break
                }
            }
            if (bool) showDrawableEnd(editText)
        }
    }

    private fun hideDrawableEnd(editText: EditText) {
        editText.setCompoundDrawablesWithIntrinsicBounds(
            drawableStart,
            null,
            null,
            null,
        )
    }

    private fun showDrawableEnd(editText: EditText) {
        editText.setCompoundDrawablesWithIntrinsicBounds(
            drawableStart,
            null,
            drawableEnd,
            null,
        )
    }
}
