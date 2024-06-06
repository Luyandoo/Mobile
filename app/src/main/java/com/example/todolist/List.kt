package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun TodoApp() {
    var taskText by remember { mutableStateOf(TextFieldValue("")) }
    var todoList by remember { mutableStateOf(listOf<TodoItem>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = taskText,
                onValueChange = { taskText = it },
                modifier = Modifier.weight(1f),
                label = { Text("New task") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (taskText.text.isNotEmpty()) {
                    todoList = todoList + TodoItem(taskText.text)
                    taskText = TextFieldValue("")
                }
            }) {
                Text("Add")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(todoList) { todoItem ->
                TodoItemRow(
                    todoItem = todoItem,
                    onDelete = {
                        todoList = todoList - todoItem
                    },
                    onComplete = {
                        val updatedList = todoList.map {
                            if (it == todoItem) it.copy(isCompleted = !it.isCompleted) else it
                        }
                        todoList = updatedList
                    }
                )
            }
        }
    }
}

@Composable
fun TodoItemRow(todoItem: TodoItem, onDelete: () -> Unit, onComplete: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Checkbox(
            checked = todoItem.isCompleted,
            onCheckedChange = { onComplete() }
        )
        Text(
            text = todoItem.task,
            modifier = Modifier.weight(1f),
            style = if (todoItem.isCompleted) {
                LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
            } else {
                LocalTextStyle.current
            }
        )
        IconButton(onClick = { onDelete() }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

