package com.tugas.klinikhewan.ui.view.dokter

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tugas.klinikhewan.ui.CustomWidget.CustomedTopAppBar
import com.tugas.klinikhewan.ui.navigation.UpdateDokter
import com.tugas.klinikhewan.ui.navigation.UpdateJenis
import com.tugas.klinikhewan.ui.viewmodel.PenyediaViewModel
import com.tugas.klinikhewan.ui.viewmodel.dokter.UpdateDokterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateDokterScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdateDokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomedTopAppBar(
                title = UpdateDokter.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryDokterBody(
            modifier = Modifier.padding(padding),
            insertDktrUiState = viewModel.updateDktrUiState,
            onDokterValueChange = viewModel::updateInsertDktrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateDokter()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}