package au.com.emerg.taxitowncars.activites

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.TextView
import au.com.emerg.taxitowncars.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var et_email: TextInputEditText
    private lateinit var et_password: TextInputEditText
    private lateinit var til_email: TextInputLayout
    private lateinit var til_password: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)

        til_email = findViewById(R.id.til_email)
        til_password = findViewById(R.id.til_password)

        findViewById<TextView>(R.id.tv_register).setOnClickListener(this)
        findViewById<MaterialButton>(R.id.btn_login).setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_register -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }

            R.id.btn_login -> {
                val email = et_email.text.toString().trim()
                val password = et_password.text.toString().trim()

                til_email.error = null
                til_password.error = null

                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    til_email.error = getString(R.string.enter_valid_email)
                } else if (password.isEmpty()) {
                    til_password.error = getString(R.string.enter_valid_password)
                } else {

                }
            }
        }
    }
}
