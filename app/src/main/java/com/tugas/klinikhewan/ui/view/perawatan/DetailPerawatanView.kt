package com.tugas.klinikhewan.ui.view.perawatan

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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
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
import com.tugas.klinikhewan.model.Jenis
import com.tugas.klinikhewan.model.Perawatan
import com.tugas.klinikhewan.ui.CustomWidget.CustomedTopAppBar
import com.tugas.klinikhewan.ui.navigation.DestinasiDetailJenis
import com.tugas.klinikhewan.ui.navigation.DestinasiDetailPerawatan
import com.tugas.klinikhewan.ui.view.pasien.OnError
import com.tugas.klinikhewan.ui.view.pasien.OnLoading
import com.tugas.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.tugas.klinikhewan.ui.viewmodel.jenis.DetailJenisViewModel
import com.tugas.klinikhewan.ui.viewmodel.jenis.DetailJnsUiState
import com.tugas.klinikhewan.ui.viewmodel.perawatan.DetailPerawatanViewModel
import com.tugas.klinikhewan.ui.viewmodel.perawatan.DetailPrwtnUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPerawatanScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdatePrwtn: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPerawatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CustomedTopAppBar(
                title = DestinasiDetailPerawatan.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPerawatanbyId()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdatePrwtn,
                shape = RoundedCornerShape(50),
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Jenis"
                )
            }
        }
    ) { innerPadding ->
        DetailPerawatanStatus(
            modifier = Modifier.padding(innerPadding),
            detailPrwtnUiState = viewModel.perawatanDetailState,
            retryAction = { viewModel.getPerawatanbyId() }
        )
    }
}

@Composable
fun DetailPerawatanStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailPrwtnUiState: DetailPrwtnUiState
) {
    when (detailPrwtnUiState) {
        is DetailPrwtnUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailPrwtnUiState.Success -> {
            if (detailPrwtnUiState.perawatan.idperawatan.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Data tidak ditemukan.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                ItemDetailPerawatan(
                    perawatan = detailPrwtnUiState.perawatan, modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailPrwtnUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPerawatan(
    modifier: Modifier = Modifier,
    perawatan: Perawatan
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
            ComponentDetailPerawatan(judul = "Id Perawatan", isinya = perawatan.idperawatan, icon = Icons.Default.Info)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPerawatan(judul = "Id Hewan", isinya = perawatan.idhewan, icon = Icons.Default.Info)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPerawatan(judul = "Id Dokter", isinya = perawatan.iddokter, icon = Icons.Default.Info)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPerawatan(judul = "Tanggal Perawatan", isinya = perawatan.tanggalperawatan, icon = Icons.Default.DateRange)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPerawatan(judul = "Detail Perawatan", isinya = perawatan.detailperawatan, icon = Icons.Default.Star)
        }
    }
}

@Composable
fun ComponentDetailPerawatan(
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