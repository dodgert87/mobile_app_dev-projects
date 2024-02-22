package com.example.registrationformwithvalidation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistrationForm()
        }
    }
}

@Composable
fun RegistrationForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val isFormValid = !nameError && !emailError && !passwordError && name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = it.isBlank()
            },
            label = { Text("Name") },
            isError = nameError
        )
        if (nameError) {
            Text("Name cannot be empty", color = Color.Red)
        }

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = !(it.contains("@") && it.contains("."))
            },
            label = { Text("Email") },
            isError = emailError
        )
        if (emailError) Text("Enter a valid email", color = Color.Red)

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = it.length < 8
            },
            label = { Text("Password") },
            isError = passwordError
        )
        if (passwordError) Text("Password must be at least 8 characters", color = Color.Red)

        Button(onClick = { /* Handle form submission here */ },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }

        Button(onClick = {
            name = ""
            email = ""
            password = ""
            nameError = false
            emailError = false
            passwordError = false
        },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RegistrationForm()
}