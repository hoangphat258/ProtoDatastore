package com.example.protodatastore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.protodatastore.R
import com.example.protodatastore.viewmodel.UserViewModel
import com.example.protodatastore.service.UserPreferenceManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var userPreferenceManager: UserPreferenceManager
    private lateinit var userViewModel: UserViewModel
    private var username = ""
    private var firstName = ""
    private var lastName = ""
    private var phoneNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userPreferenceManager = UserPreferenceManager(applicationContext)
        userViewModel =
            UserViewModel(application)
        initView()
        setupObserver()
    }

    private fun initView() {
        btAdd.setOnClickListener(this)
        btClear.setOnClickListener(this)
    }

    private fun setupObserver() {
        userViewModel.user.observe(this, Observer {
            tvUsername.text = it.username
            tvFirstName.text = it.firstName
            tvLastName.text = it.lastName
            tvPhoneNumber.text = it.phoneNumber.toString()
        })
    }

    private fun addUser() {
        username = etUsername.text.toString()
        firstName = etFirstName.text.toString()
        lastName = etLastName.text.toString()
        phoneNumber = etPhoneNumber.text.toString()

        if (username.isNotEmpty() && firstName.isNotEmpty()
            && lastName.isNotEmpty() && phoneNumber.isNotEmpty()) {
            userViewModel.updateUserPreference(username, firstName, lastName, phoneNumber.toInt())
        } else {
            Toast.makeText(this, "Please fill in all the bank", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearUser() {
        userViewModel.updateUserPreference("", "", "", 0)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btAdd -> addUser()
            R.id.btClear -> clearUser()
        }
    }

}