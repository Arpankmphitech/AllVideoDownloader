package com.example.allvideodownloaderrevolt.act

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.allvideodownloaderrevolt.commonClass.Constant
import com.example.allvideodownloaderrevolt.commonClass.Utils
import com.example.allvideodownloaderrevolt.databinding.ActLoginScreenBinding

class LoginScreenAct : AppCompatActivity() {

    lateinit var actLoginBinding: ActLoginScreenBinding
    lateinit var txtEmail: String
    lateinit var txtPassword: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actLoginBinding = ActLoginScreenBinding.inflate(layoutInflater)
        setContentView(actLoginBinding.root)

        loginInitView()

    }

    override fun onResume() {
        super.onResume()
        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)

        val txtEmailData = sh.getString("txtEmail", "")
        val txtPassword = sh.getString("txtPassword", "")

        Toast.makeText(this, "===== $txtEmailData ===== $txtPassword", Toast.LENGTH_SHORT).show()

    }

    private fun loginInitView() {

        actLoginBinding.apply {

            btnLogin.setOnClickListener {

                txtEmail = edtEmail.text.toString()
                txtPassword = edtPassword.text.toString()

                if (txtEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {

                    Toast.makeText(
                        this@LoginScreenAct, "Please Enter valid txtEmail ", Toast.LENGTH_SHORT
                    ).show()

                } else if (txtPassword.isEmpty() && txtPassword.length < 8) {

                    Toast.makeText(
                        this@LoginScreenAct,
                        "Please Enter 8 digit txtPassword ",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Utils.setDataFromSharedPrefrences(
                        Constant.TABLE_NAME_LOGIN,
                        MODE_PRIVATE,
                        txtEmail,
                        txtPassword,
                        activity = this@LoginScreenAct
                    )


                }
            }

        }

    }
}