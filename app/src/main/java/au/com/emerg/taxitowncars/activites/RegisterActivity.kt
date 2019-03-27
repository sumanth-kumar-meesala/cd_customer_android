package au.com.emerg.taxitowncars.activites

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ImageView
import au.com.emerg.taxitowncars.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : BaseActivity(), View.OnClickListener {

    private lateinit var et_name: TextInputEditText
    private lateinit var et_email: TextInputEditText
    private lateinit var et_phone_number: TextInputEditText
    private lateinit var et_password: TextInputEditText
    private lateinit var et_confirm_password: TextInputEditText
    private lateinit var til_name: TextInputLayout
    private lateinit var til_email: TextInputLayout
    private lateinit var til_phone_number: TextInputLayout
    private lateinit var til_password: TextInputLayout
    private lateinit var til_confirm_password: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        et_name = findViewById(R.id.et_name)
        et_email = findViewById(R.id.et_email)
        et_phone_number = findViewById(R.id.et_phone_number)
        et_password = findViewById(R.id.et_password)
        et_confirm_password = findViewById(R.id.et_confirm_password)

        til_name = findViewById(R.id.til_name)
        til_email = findViewById(R.id.til_email)
        til_phone_number = findViewById(R.id.til_phone_number)
        til_password = findViewById(R.id.til_password)
        til_confirm_password = findViewById(R.id.til_confirm_password)

        findViewById<MaterialButton>(R.id.btn_register).setOnClickListener(this)
        findViewById<ImageView>(R.id.iv_back).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back -> {
                finish()
            }

            R.id.btn_register -> {
                val name = et_name.text.toString().trim()
                val email = et_email.text.toString().trim()
                val phoneNumber = et_phone_number.text.toString().trim()
                val password = et_password.text.toString().trim()
                val confirmPassword = et_confirm_password.text.toString().trim()

                til_name.error = null
                et_email.error = null
                et_phone_number.error = null
                et_password.error = null
                et_confirm_password.error = null

                if (name.isEmpty()) {
                    til_name.error = getString(R.string.enter_valid_name)
                } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    til_email.error = getString(R.string.enter_valid_email)
                } else if (phoneNumber.isEmpty()) {
                    et_phone_number.error = getString(R.string.enter_valid_phone_number)
                } else if (password.isEmpty()) {
                    et_password.error = getString(R.string.enter_valid_password)
                } else if (confirmPassword.isEmpty()) {
                    et_confirm_password.error = getString(R.string.enter_valid_password)
                } else if (!password.equals(confirmPassword)) {
                    et_confirm_password.error = getString(R.string.password_doesnt_match)
                } else {

                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
