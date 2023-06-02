package com.tamertokbaev.momari.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.tamertokbaev.momari.R
import com.tamertokbaev.momari.globals.Constants
import com.tamertokbaev.momari.models.AuthResponse
import com.tamertokbaev.momari.models.User
import com.tamertokbaev.momari.services.AuthService
import com.tamertokbaev.momari.services.ServiceBuilder

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    fun onCLickSignInButton(view: View) {
        val emailEditText = findViewById<EditText>(R.id.signin_email)
        val passwordEditText = findViewById<EditText>(R.id.signin_password)

        val emailInputLayout = findViewById<TextInputLayout>(R.id.signin_email_error)
        val passwordInputLayout = findViewById<TextInputLayout>(R.id.signin_password_error)

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        val destinationService = ServiceBuilder.buildService(AuthService::class.java)
        val requestCall =
            destinationService.signIn(User(name = null, email = email, password = password, profile_photo = null))
        requestCall.enqueue(object : retrofit2.Callback<AuthResponse> {
            override fun onResponse(
                call: retrofit2.Call<AuthResponse>,
                response: retrofit2.Response<AuthResponse>
            ) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful) {

                    val authResponse: AuthResponse = response.body()!!
                    val userData = authResponse.user
                    val token = authResponse.token

                    if (authResponse.message == "success") {
                        // Shared preferences is used to store some state globally inside application
                        // In this case, I'm going to store user data globally, so I can then get in in any activity I need
                        val sharedPreferences = getSharedPreferences(
                            Constants.APP_SHARED_PREF_NAME,
                            Context.MODE_PRIVATE
                        )
                        val editor = sharedPreferences.edit()
                        editor.apply {
                            putString(Constants.APP_SHARED_USER_EMAIL_KEY, userData?.email)
                            putString(Constants.APP_SHARED_USER_NAME_KEY, userData?.name)
                            putString(Constants.APP_SHARED_USER_PROFILE_PHOTO_KEY, userData?.profile_photo)
                            putString(Constants.APP_SHARED_USER_TOKEN_KEY, token?.access_token)
                        }.apply()

                        // Change activity on successful sign in
                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        emailInputLayout.setError("Email or password is incorrect")
                        passwordInputLayout.setError("Email or password is incorrect")
                    }
                } else {
                    Log.d("Request error", response.message())
                    emailInputLayout.setError("Email or password is incorrect")
                    passwordInputLayout.setError("Email or password is incorrect")
                }
            }

            override fun onFailure(call: retrofit2.Call<AuthResponse>, t: Throwable) {
                Log.d("Exception", "Occurred exception: ${t}")
                Toast.makeText(this@SignInActivity, "Something went wrong $t", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    // THIS USED WHEN I WAS DOING AUTHORIZATION WITH SQLITE. BUT NOW I USE MY OWN BACKEND FOR AUTHORIZATION
//    fun onCLickSignInButton(view: View){
//        val emailEditText                = findViewById<EditText>(R.id.signin_email)
//        val passwordEditText             = findViewById<EditText>(R.id.signin_password)
//
//        val emailInputLayout             = findViewById<TextInputLayout>(R.id.signin_email_error)
//        val passwordInputLayout             = findViewById<TextInputLayout>(R.id.signin_password_error)
//
//        val email                        = emailEditText.text.toString()
//        val password                     = passwordEditText.text.toString()
//
//        val dbManager: DBManager = DBManager(this)
//        val statusOfExecutedQuery = dbManager.checkCredentialsUser(email, password)
//
//        if(statusOfExecutedQuery){
//
//            // Shared preferences is used to store some state globally inside application
//            // In this case, I'm going to store user data globally, so I can then get in in any activity I need
//            val sharedPreferences = getSharedPreferences(Constants.APP_SHARED_PREF_NAME, Context.MODE_PRIVATE)
//            val editor = sharedPreferences.edit()
//            editor.apply{
//                putString(Constants.APP_SHARED_USER_EMAIL_KEY, email)
//            }.apply()
//
//            // Change activity on successful registration
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//        else{
//            emailInputLayout.setError("Email or password is incorrect")
//            passwordInputLayout.setError("Email or password is incorrect")
//            // Clear text and provide some error message to user
//            emailEditText.text.clear()
//            passwordEditText.text.clear()
//        }
//    }

    fun onClickSingUpLink(view: View) {
        val link = findViewById<TextView>(R.id.signUpLink)
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }
}