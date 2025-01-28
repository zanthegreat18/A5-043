package com.tugas.klinikhewan.ui.view.dokter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tugas.klinikhewan.model.Dokter
import com.tugas.klinikhewan.ui.CustomWidget.CustomedTopAppBar
import com.tugas.klinikhewan.ui.navigation.DestinasiDetailDokter
import com.tugas.klinikhewan.ui.view.pasien.OnError
import com.tugas.klinikhewan.ui.view.pasien.OnLoading
import com.tugas.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.tugas.klinikhewan.ui.viewmodel.dokter.DetailDktrUiState
import com.tugas.klinikhewan.ui.viewmodel.dokter.DetailDokterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailDokterScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdateDktr: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailDokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CustomedTopAppBar(
                title = DestinasiDetailDokter.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getDokterbyId()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdateDktr,
                shape = RoundedCornerShape(50),
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Kontak"
                )
            }
        }
    ) { innerPadding ->
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailDktrUiState = viewModel.dokterDetailState,
            retryAction = { viewModel.getDokterbyId() }
        )
    }
}

@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailDktrUiState: DetailDktrUiState
) {
    when (detailDktrUiState) {
        is DetailDktrUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailDktrUiState.Success -> {
            if (detailDktrUiState.dokter.iddokter.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Data tidak ditemukan.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                ItemDetailDktr(
                    dokter = detailDktrUiState.dokter, modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailDktrUiState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailDktr(
    modifier: Modifier = Modifier,
    dokter: Dokter
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF0F0),
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailDktr(judul = "Id Dokter", isinya = dokter.iddokter, icon = Icons.Default.Info)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailDktr(judul = "Nama Dokter", isinya = dokter.namadokter, icon = Icons.Default.Person)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailDktr(judul = "Kontak Terhubung", isinya = dokter.kontak, icon = Icons.Default.Call)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailDktr(judul = "Spesialisasi", isinya = dokter.spesialisasi, icon = Icons.Default.ThumbUp)
        }
    }
}

@Composable
fun ComponentDetailDktr(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
    icon: ImageVector
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = judul,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = isinya,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}