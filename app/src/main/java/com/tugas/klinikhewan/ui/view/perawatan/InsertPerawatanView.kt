package com.tugas.klinikhewan.ui.view.perawatan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tugas.klinikhewan.model.Dokter
import com.tugas.klinikhewan.model.Pasien
import com.tugas.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.tugas.klinikhewan.ui.viewmodel.perawatan.InsertPrwtnUiState
import com.tugas.klinikhewan.ui.viewmodel.perawatan.InsertPrwtnViewModel
import com.tugas.klinikhewan.ui.viewmodel.perawatan.InsertUiPrwtnEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPerawatanScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPrwtnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    val topBarColor = Color(0xFF7C444F)
    val buttonColor = Color(0xFF7C444F)
    val formBorderColor = Color(0xFF7C444F)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Perawatan", color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarColor
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding(),
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            item {
                EntryBodyPerawatan(
                    insertPrwtnUiState = viewModel.uiStatePrwtn,
                    onPerawatanValueChange = viewModel::updateInsertPrwtnState,
                    listHewan = viewModel.listhewan,
                    listDokter = viewModel.listdokter,
                    onSaveClick = {
                        coroutineScope.launch {
                            viewModel.insertPrwtn()
                            navigateBack()
                        }
                    },
                    buttonColor = buttonColor,
                    formBorderColor = formBorderColor
                )
            }
        }
    }
}

@Composable
fun EntryBodyPerawatan(
    insertPrwtnUiState: InsertPrwtnUiState,
    onPerawatanValueChange: (InsertUiPrwtnEvent) -> Unit,
    onSaveClick: () -> Unit,
    listHewan: List<Pasien>,
    listDokter: List<Dokter>,
    buttonColor: Color,
    formBorderColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        FormInputPerawatan(
            insertPrwtnUiEvent = insertPrwtnUiState.insertUiPrwtnEvent,
            onValueChange = onPerawatanValueChange,
            listHewan = listHewan,
            listDokter = listDokter,
            formBorderColor = formBorderColor
        )
        Button(
            onClick = onSaveClick,
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan", color = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPerawatan(
    insertPrwtnUiEvent: InsertUiPrwtnEvent,
    onValueChange: (InsertUiPrwtnEvent) -> Unit,
    listHewan: List<Pasien>,
    listDokter: List<Dokter>,
    formBorderColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = insertPrwtnUiEvent.idperawatan,
            onValueChange = { onValueChange(insertPrwtnUiEvent.copy(idperawatan = it)) },
            label = { Text("Id Perawatan") },
            placeholder = { Text("Masukkan ID perawatan") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Info, contentDescription = "Id Perawatan")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = formBorderColor,
                unfocusedBorderColor = formBorderColor,
                cursorColor = formBorderColor
            )
        )

        DropdownSelector(
            label = "Hewan",
            items = listHewan.map { it.namahewan },
            selectedItem = listHewan.find { it.idhewan == insertPrwtnUiEvent.idhewan }?.namahewan
                ?: "Pilih Hewan",
            onItemSelected = { hewanName ->
                val selectedHewan = listHewan.find { it.namahewan == hewanName }
                selectedHewan?.let { onValueChange(insertPrwtnUiEvent.copy(idhewan = it.idhewan)) }
            },
            formBorderColor = formBorderColor
        )

        DropdownSelector(
            label = "Dokter",
            items = listDokter.map { it.namadokter },
            selectedItem = listDokter.find { it.iddokter == insertPrwtnUiEvent.iddokter }?.namadokter
                ?: "Pilih Dokter",
            onItemSelected = { dokterName ->
                val selectedDokter = listDokter.find { it.namadokter == dokterName }
                selectedDokter?.let { onValueChange(insertPrwtnUiEvent.copy(iddokter = it.iddokter)) }
            },
            formBorderColor = formBorderColor
        )

        OutlinedTextField(
            value = insertPrwtnUiEvent.tanggalperawatan,
            onValueChange = { onValueChange(insertPrwtnUiEvent.copy(tanggalperawatan = it)) },
            label = { Text("Tanggal Perawatan") },
            placeholder = { Text("yyyy-mm-dd") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Tanggal Perawatan")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = formBorderColor,
                unfocusedBorderColor = formBorderColor,
                cursorColor = formBorderColor
            )
        )

        OutlinedTextField(
            value = insertPrwtnUiEvent.detailperawatan,
            onValueChange = { onValueChange(insertPrwtnUiEvent.copy(detailperawatan = it)) },
            label = { Text("Detail Perawatan") },
            placeholder = { Text("Masukkan detail perawatan") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.MailOutline, contentDescription = "Detail Perawatan")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = formBorderColor,
                unfocusedBorderColor = formBorderColor,
                cursorColor = formBorderColor
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    formBorderColor: Color
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = label)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = formBorderColor,
                unfocusedBorderColor = formBorderColor,
                cursorColor = formBorderColor
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

