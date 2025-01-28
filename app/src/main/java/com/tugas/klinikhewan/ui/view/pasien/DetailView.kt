package com.tugas.klinikhewan.ui.view.pasien

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tugas.klinikhewan.R
import com.tugas.klinikhewan.model.Pasien
import com.tugas.klinikhewan.ui.CustomWidget.CustomedTopAppBar
import com.tugas.klinikhewan.ui.navigation.DesttinasiDetailPasien
import com.tugas.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.tugas.klinikhewan.ui.viewmodel.pasien.DetailPsnUiState
import com.tugas.klinikhewan.ui.viewmodel.pasien.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenPasien(
    navigateBack: () -> Unit,
    navigateToItemUpdatePsn: () -> Unit,
    navigateToAddPerawatan: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CustomedTopAppBar(
                title = DesttinasiDetailPasien.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPasienbyId()
                }
            )
        },
        bottomBar = {
            CustomBottomBarDetail(
                navigateToBack = navigateBack ,
                navigateToAdd = navigateToAddPerawatan,
                navigatetoEdit = navigateToItemUpdatePsn
            )
        },
    ) { innerPadding ->
        DetailPasienStatus(
            modifier = Modifier.padding(innerPadding),
            detailPsnUiState = viewModel.pasienDetailState,
            retryAction = { viewModel.getPasienbyId() }
        )
    }
}


@Composable
fun CustomBottomBarDetail(
    navigateToBack: () -> Unit,
    navigateToAdd: () -> Unit,
    navigatetoEdit: () -> Unit,
) {
    BottomAppBar(
        containerColor = Color(0xFF7C444F),
        contentColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButtonWithRoundedBackground(
                iconRes = R.drawable.baseline_add_box_24,
                onClick = navigateToAdd
            )
            IconButtonWithRoundedBackground(
                iconRes = R.drawable.sharp_arrow_back_ios_new_24,
                onClick = navigateToBack
            )
            IconButtonWithRoundedBackground(
                iconRes = R.drawable.baseline_edit_square_24,
                onClick = navigatetoEdit
            )
        }
    }
}


@Composable
fun DetailPasienStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailPsnUiState: DetailPsnUiState
) {
    when (detailPsnUiState) {
        is DetailPsnUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is DetailPsnUiState.Success -> {
            if (detailPsnUiState.pasien.idhewan.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text("Data tidak ditemukan.", style = MaterialTheme.typography.bodyMedium) }
            } else {
                ItemDetailPsn(
                    pasien = detailPsnUiState.pasien, modifier = modifier.fillMaxWidth()
                )
            }
        }
        is DetailPsnUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPsn(
    modifier: Modifier = Modifier,
    pasien: Pasien
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
            ComponentDetailPsn(judul = "Id Hewan", isinya = pasien.idhewan, icon = Icons.Default.Info)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPsn(judul = "Nama Hewan", isinya = pasien.namahewan, icon = Icons.Default.FavoriteBorder)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPsn(judul = "Jenis Hewan ID", isinya = pasien.jenishewanid, icon = Icons.Default.Info)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPsn(judul = "Pemilik", isinya = pasien.pemilik, icon = Icons.Default.Person)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPsn(judul = "Kontak Pemilik", isinya = pasien.kontakpemilik, icon = Icons.Default.Phone)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPsn(judul = "Tgl Lahir", isinya = pasien.tanggallahir, icon = Icons.Default.DateRange)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            ComponentDetailPsn(judul = "Catatan Kesehatan", isinya = pasien.catatankesehatan, icon = Icons.Default.Star)
        }
    }
}

@Composable
fun ComponentDetailPsn(
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



