package com.example.demo1.kotlinApp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demo1.ui.theme.Demo1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Demo1Theme {
                // 显示框架  A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    //相当于垂直显示
    Column() {
        Text(text = "Hello $name!")
        Text(text = "hello world", modifier = Modifier
            .padding(start = 0.dp, top = 20.dp, end = 0.dp, bottom = 0.dp)
            .background(Color.Gray)
            .clickable {
                Log.e("-------", "点击方法")
            }
            )
        //相当于水平显示
        Row(modifier = Modifier
            .background(Color.Blue)
            .padding(20.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
            ) {

            Text(text = "Compose 课程已经全部完成", modifier = Modifier.weight(1f), fontSize = 16.sp, color = Color.White, onTextLayout = {
                Log.e("当Text内容发生变化：",it.layoutInput.text.toString())
            })
            Button(onClick = {}) {
                Text(text = "改变点什么吧")
            }
        }


    }
//    Box() {
//        Text(text = "相当于碎片")
//    }




}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Demo1Theme {
        Greeting("Android")
    }
}