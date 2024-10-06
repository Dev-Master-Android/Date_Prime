package com.example.datacardapp

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dateprime.R
import com.example.dateprime.databinding.ActivityDataBinding
import java.text.SimpleDateFormat
import java.util.*

class DataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        val firstName = intent.getStringExtra("FIRST_NAME")
        val lastName = intent.getStringExtra("LAST_NAME")
        val birthDate = intent.getStringExtra("BIRTH_DATE")
        val phoneNumber = intent.getStringExtra("PHONE_NUMBER")
        val imageUri = intent.getStringExtra("IMAGE_URI")

        binding.textViewFirstName.text = firstName
        binding.textViewLastName.text = lastName
        binding.textViewPhoneNumber.text = phoneNumber
        binding.imageView.setImageURI(Uri.parse(imageUri))

        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val birthDateDate = sdf.parse(birthDate!!)
        val now = Calendar.getInstance()

        val age = now.get(Calendar.YEAR) - birthDateDate.year - 1900
        val nextBirthday = Calendar.getInstance().apply {
            set(Calendar.MONTH, birthDateDate.month)
            set(Calendar.DAY_OF_MONTH, birthDateDate.date)
            set(Calendar.YEAR, if (before(now)) now.get(Calendar.YEAR) + 1 else now.get(Calendar.YEAR))
        }
        val daysUntilBirthday = (nextBirthday.timeInMillis - now.timeInMillis) / (24 * 60 * 60 * 1000)

        binding.textViewAge.text = "$age лет"
        binding.textViewDaysUntilBirthday.text = "$daysUntilBirthday дней до дня рождения"

        val monthsUntilBirthday = if (birthDateDate.month < now.get(Calendar.MONTH)) {
            12 - now.get(Calendar.MONTH) + birthDateDate.month
        } else {
            birthDateDate.month - now.get(Calendar.MONTH)
        }
        binding.textViewMonthsUntilBirthday.text = "$monthsUntilBirthday месяцев до дня рождения"
        binding.buttonExit.setOnClickListener {
            finishAffinity()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                finishAffinity()
                Toast.makeText(this, "Программа завершена", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
