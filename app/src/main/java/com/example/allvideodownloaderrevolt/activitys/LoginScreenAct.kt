package com.example.allvideodownloaderrevolt.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.allvideodownloaderrevolt.common.Constant
import com.example.allvideodownloaderrevolt.common.Utils
import com.example.allvideodownloaderrevolt.databinding.ActLoginScreenBinding

class LoginScreenAct : AppCompatActivity() {

    lateinit var binding: ActLoginScreenBinding
    lateinit var email: String
    lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

    }

    override fun onResume() {
        super.onResume()
        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)

        val EmailData = sh.getString("email", "")
        val PassWord = sh.getString("password", "")

        Toast.makeText(this, "===== $EmailData ===== $PassWord", Toast.LENGTH_SHORT).show()

    }

    private fun initView() {

        binding.apply {

            btnLogin.setOnClickListener {

                email = edtEmail.text.toString()
                password = edtPassword.text.toString()

                if (email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    Toast.makeText(
                        this@LoginScreenAct, "Please Enter valid Email ", Toast.LENGTH_SHORT
                    ).show()

                } else if (password.isEmpty() && password.length < 8) {

                    Toast.makeText(
                        this@LoginScreenAct,
                        "Please Enter 8 digit password ",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Utils.setDataFromSharedPrefrences(
                        Constant.LOGIN_TABLE_NAME,
                        MODE_PRIVATE,
                        email,
                        password,
                        activity = this@LoginScreenAct
                    )


                }
            }

        }

    }
}