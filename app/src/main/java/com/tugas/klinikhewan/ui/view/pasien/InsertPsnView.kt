package com.tugas.klinikhewan.ui.view.pasien

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tugas.klinikhewan.model.Jenis
import com.tugas.klinikhewan.ui.CustomWidget.CustomedTopAppBar
import com.tugas.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.tugas.klinikhewan.ui.viewmodel.pasien.InsertPsnUiEvent
import com.tugas.klinikhewan.ui.viewmodel.pasien.InsertPsnUiState
import com.tugas.klinikhewan.ui.viewmodel.pasien.InsertPsnViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF6200EE),
    contentColor: Color = androidx.compose.ui.graphics.Color.White
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = contentColor
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = contentColor
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor
        ),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPasienScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPsnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Tambah Pasien",
                canNavigateBack = true,
                navigateUp = navigateBack,
                backgroundColor = Color(0xFF7C444F), // Contoh warna kustom untuk latar belakang TopBar
                contentColor = androidx.compose.ui.graphics.Color.Black // Contoh warna kustom untuk teks dan ikon
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
                EntryBody(
                    insertPsnUiState = viewModel.uiStatePsn,
                    onPasienValueChange = viewModel::updateInsertPsnState,
                    listJenis = viewModel.listjenis,
                    onSaveClick = {
                        coroutineScope.launch {
                            viewModel.insertPsn()
                            navigateBack()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EntryBody(
    insertPsnUiState: InsertPsnUiState,
    onPasienValueChange: (InsertPsnUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    listJenis: List<Jenis>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        FormInput(
            insertPsnUiEvent = insertPsnUiState.insertPsnUiEvent,
            onValueChange = onPasienValueChange,
            modifier = Modifier.fillMaxWidth(),
            listJenis = listJenis
        )
        Button(
            onClick = onSaveClick,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7C444F),
                contentColor = androidx.compose.ui.graphics.Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertPsnUiEvent: InsertPsnUiEvent,
    onValueChange: (InsertPsnUiEvent) -> Unit,
    listJenis: List<Jenis>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = insertPsnUiEvent.idhewan,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(idhewan = it)) },
            label = { Text("Id Hewan") },
            placeholder = { Text("Masukkan ID hewan") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Info, contentDescription = "ID Hewan")
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF7C444F),
                cursorColor = Color(0xFF7C444F)
            )
        )
        OutlinedTextField(
            value = insertPsnUiEvent.namahewan,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(namahewan = it)) },
            label = { Text("Nama Hewan") },
            placeholder = { Text("Masukkan nama hewan") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Nama Hewan")
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF7C444F),
                cursorColor = Color(0xFF7C444F)
            )
        )
        var expanded by remember { mutableStateOf(false) }
        var selectedJenis by remember { mutableStateOf(insertPsnUiEvent.jenishewanid) }
        val jenisNama = listJenis.find { it.idjenishewan == selectedJenis }?.namajenishewan ?: "Pilih Jenis Hewan"

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = jenisNama,
                onValueChange = {},
                readOnly = true,
                label = { Text("Jenis Hewan") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF7C444F),
                    cursorColor = Color(0xFF7C444F)
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listJenis.forEach { jenis ->
                    DropdownMenuItem(
                        text = { Text(jenis.namajenishewan) },
                        onClick = {
                            selectedJenis = jenis.idjenishewan
                            onValueChange(insertPsnUiEvent.copy(jenishewanid = jenis.idjenishewan))
                            expanded = false
                        }
                    )
                }
            }
        }
        OutlinedTextField(
            value = insertPsnUiEvent.pemilik,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(pemilik = it)) },
            label = { Text("Pemilik") },
            placeholder = { Text("Masukkan nama pemilik") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Pemilik")
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF7C444F),
                cursorColor = Color(0xFF7C444F)
            )
        )
        OutlinedTextField(
            value = insertPsnUiEvent.kontakpemilik,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(kontakpemilik = it)) },
            label = { Text("Kontak Terhubung") },
            placeholder = { Text("Masukkan nomor kontak pemilik") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Phone, contentDescription = "Kontak")
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF7C444F),
                cursorColor = Color(0xFF7C444F)
            )
        )
        OutlinedTextField(
            value = insertPsnUiEvent.tanggallahir,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(tanggallahir = it)) },
            label = { Text("Tanggal Lahir") },
            placeholder = { Text("Masukkan tanggal lahir (yyyy-mm-dd)") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Tanggal Lahir")
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF7C444F),
                cursorColor = Color(0xFF7C444F)
            )
        )
        OutlinedTextField(
            value = insertPsnUiEvent.catatankesehatan,
            onValueChange = { onValueChange(insertPsnUiEvent.copy(catatankesehatan = it)) },
            label = { Text("Catatan Kesehatan") },
            placeholder = { Text("Masukkan catatan kesehatan") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Catatan Kesehatan")
            },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF7C444F),
                cursorColor = Color(0xFF7C444F)
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputFieldWithIcon(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.primary)
                )
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = { Text(text = placeholder) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
