package com.example.convertingjsontokotlindataclass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.convertingjsontokotlindataclass.ui.theme.ConvertingJSONToKotlinDataClassTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConvertingJSONToKotlinDataClassTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Json_Converter()
                }
            }
        }
    }
}
data class TodoItem(
    var userId: Int,
    var id: Int,
    var title: String,
    var completed: Boolean
)


interface ApiService {

    @GET("todos")
    suspend fun fetchTodos(): List<TodoItem>


}


object RetrofitInstance {

    private const val TodoBase_URL = "https://jsonplaceholder.typicode.com/"

    private val retrofit by lazy {

        Retrofit.Builder().baseUrl(TodoBase_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val apiService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}



@Preview(showBackground = true)
@Composable

fun Json_Converter(){

    var todoItemList by remember { mutableStateOf<List<TodoItem>>(listOf())}
    var isLoading by remember { mutableStateOf(true)}

    LaunchedEffect(Unit){

        todoItemList = RetrofitInstance.apiService.fetchTodos()
        isLoading = false
    }

    if (isLoading){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(modifier = Modifier.size(70.dp))
        }
    }else {
        LazyColumn {
            items(items = todoItemList) { todoItem ->
                Text("Todo Number ${todoItem.id}:")
                Text("Task: ${todoItem.title}")
                Text("Competed: ${todoItem.completed}")
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
