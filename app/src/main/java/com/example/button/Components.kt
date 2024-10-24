package com.example.buttoncheckbox

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.button.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiComponents() {
    var showMessage by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var showRadioButtons by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableIntStateOf(0) }
    var showProgress by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val images = listOf(
        painterResource(id = R.drawable.image1),
        painterResource(id = R.drawable.image2),
        painterResource(id = R.drawable.image3)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gatitos's App", color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF6200EE)),
                actions = {
                    Icon(painter = painterResource(id = R.drawable.ic_icon), contentDescription = "Icono de Gatitos")
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(Color(0xFFF5F5F5)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    message = "Botón presionado"
                    showProgress = true
                    scope.launch {
                        delay(5000)
                        showProgress = false
                    }
                    selectedImage = (selectedImage + 1) % images.size
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Presionar", color = Color.White)
            }

            if (showProgress) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = showMessage,
                    onCheckedChange = { showMessage = it },
                    modifier = Modifier.padding(8.dp)
                )
                Text("Activar")
            }

            if (showMessage) {
                Text(text = message, color = Color.Black, modifier = Modifier.padding(8.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = showRadioButtons,
                    onCheckedChange = { showRadioButtons = it },
                    modifier = Modifier.padding(8.dp)
                )
                Text("Mostrar opciones")
            }

            if (showRadioButtons) {
                RadioButtonGroup { selectedOption ->
                    message = "Opción seleccionada: $selectedOption"
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = images[selectedImage],
                contentDescription = "Imagen actualizada",
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun RadioButtonGroup(onSelected: (String) -> Unit) {
    val options = listOf("Opción 1", "Opción 2", "Opción 3")
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(modifier = Modifier.padding(16.dp)) {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp)
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = {
                        selectedOption = option
                        onSelected(option)
                    }
                )
                Text(option)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUiComponents() {
    UiComponents()
}
