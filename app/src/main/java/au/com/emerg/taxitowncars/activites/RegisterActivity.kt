package au.com.emerg.taxitowncars.activites

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import au.com.emerg.taxitowncars.R

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_back -> {
                finish()
            }
        }
    }
}
