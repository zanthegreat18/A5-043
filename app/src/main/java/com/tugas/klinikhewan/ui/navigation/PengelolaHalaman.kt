package com.tugas.klinikhewan.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tugas.klinikhewan.ui.view.dokter.DetailDokterScreen
import com.tugas.klinikhewan.ui.view.dokter.EntryDokterScreen
import com.tugas.klinikhewan.ui.view.dokter.HomeDokterScreen
import com.tugas.klinikhewan.ui.view.dokter.UpdateDokterScreen
import com.tugas.klinikhewan.ui.view.jenis.DetailJenisScreen
import com.tugas.klinikhewan.ui.view.jenis.EntryJnsScreen
import com.tugas.klinikhewan.ui.view.jenis.HomeJenisScreen
import com.tugas.klinikhewan.ui.view.jenis.UpdateJenisScreen
import com.tugas.klinikhewan.ui.view.pasien.DetailScreenPasien
import com.tugas.klinikhewan.ui.view.pasien.EntryPasienScreen
import com.tugas.klinikhewan.ui.view.pasien.HomeScreen
import com.tugas.klinikhewan.ui.view.pasien.UpdatePasienScreen
import com.tugas.klinikhewan.ui.view.perawatan.DetailPerawatanScreen
import com.tugas.klinikhewan.ui.view.perawatan.HomePerawatnScreen
import com.tugas.klinikhewan.ui.view.perawatan.InsertPerawatanScreen
import com.tugas.klinikhewan.ui.view.perawatan.UpdatePerawatanScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = Modifier,
    ){
        composable(Home.route){
            HomeScreen(
                navigateToEntry = { navController.navigate(AddPasien.route) },
                navigateToSeeDokter = { navController.navigate(HomeDokter.route) },
                navigateToSeeJenis = { navController.navigate(HomeJenis.route) },
                navigateToSeePerawatan = { navController.navigate(HomePerawatan.route) },
                onDetailClick = { idhewan ->
                    navController.navigate("${DesttinasiDetailPasien.route}/$idhewan")
                }
            )
        }
        composable(AddPasien.route){
            EntryPasienScreen(
                navigateBack = { navController.navigate(Home.route) {
                    popUpTo(Home.route) {
                        inclusive = true
                    }
                }
            }
            )
        }
        composable(DesttinasiDetailPasien.routeWithArg, arguments = listOf(navArgument(DesttinasiDetailPasien.idPASIEN) {
            type = NavType.StringType }
        )
        ){
            val idhewan = it.arguments?.getString(DesttinasiDetailPasien.idPASIEN)
            idhewan?.let { hewan ->
                DetailScreenPasien(
                    navigateToAddPerawatan = { navController.navigate(AddPerawatan.route) {
                        popUpTo(HomePerawatan.route) {
                            inclusive = true
                        }
                    }
                    },
                    navigateToItemUpdatePsn = { navController.navigate("${UpdatePasien.route}/$idhewan") },
                    navigateBack = { navController.navigate(Home.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(UpdatePasien.routeWithArg, arguments = listOf(navArgument(DesttinasiDetailPasien.idPASIEN){
            type = NavType.StringType
        }
        )
        ){
            val idhewan = it.arguments?.getString(UpdatePasien.idPASIEN)
            idhewan?.let { hewan ->
                UpdatePasienScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
        composable(HomeJenis.route){
            HomeJenisScreen(
                navigateToAdd = { navController.navigate(AddJenis.route) {
                    popUpTo(HomeJenis.route) {
                        inclusive = true
                    }
                }
                },
                onDetailClickJenis = { idjenishewan ->
                    navController.navigate("${DestinasiDetailJenis.route}/$idjenishewan")
                },
                navigateToBack = {
                    navController.navigate(Home.route) {
                        popUpTo(Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(DestinasiDetailJenis.routeWithArg, arguments = listOf(navArgument(DestinasiDetailJenis.idJENIS) {
            type = NavType.StringType }
        )
        ){
            val idjenishewan = it.arguments?.getString(DestinasiDetailJenis.idJENIS)
            idjenishewan?.let { jenis ->
                DetailJenisScreen(
                    navigateToItemUpdateJns = { navController.navigate("${UpdateJenis.route}/$idjenishewan") },
                    navigateBack = { navController.navigate(HomeJenis.route) {
                        popUpTo(HomeJenis.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(UpdateJenis.routeWithArg, arguments = listOf(navArgument(DestinasiDetailJenis.idJENIS){
            type = NavType.StringType
        }
        )
        ){
            val idjenishewan = it.arguments?.getString(UpdateJenis.idJENIS)
            idjenishewan?.let { jenis ->
                UpdateJenisScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
        composable(AddJenis.route){
            EntryJnsScreen(navigateBack = {
                navController.navigate(HomeJenis.route) {
                    popUpTo(HomeJenis.route) {
                        inclusive = true
                    }
                }
            }
            )
        }
        composable(HomeDokter.route){
            HomeDokterScreen(
                navigateToAdd = { navController.navigate(AddDokter.route) {
                    popUpTo(HomeDokter.route) {
                        inclusive = true
                    }
                }
                },
                onDetailClickDokter = { iddokter ->
                    navController.navigate("${DestinasiDetailDokter.route}/$iddokter")
                },
                navigateToBack = {
                    navController.navigate(Home.route) {
                        popUpTo(Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(AddDokter.route){
            EntryDokterScreen(navigateBack = {
                navController.navigate(HomeDokter.route) {
                    popUpTo(HomeDokter.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetailDokter.routeWithArg, arguments = listOf(navArgument(DestinasiDetailDokter.idDOKTER) {
            type = NavType.StringType }
        )
        ){
            val iddokter = it.arguments?.getString(DestinasiDetailDokter.idDOKTER)
            iddokter?.let { dokter ->
                DetailDokterScreen(
                    navigateToItemUpdateDktr = { navController.navigate("${UpdateDokter.route}/$iddokter") },
                    navigateBack = { navController.navigate(HomeDokter.route) {
                        popUpTo(HomeDokter.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(UpdateDokter.routeWithArg, arguments = listOf(navArgument(DestinasiDetailDokter.idDOKTER){
            type = NavType.StringType
        }
        )
        ){
            val iddokter = it.arguments?.getString(UpdateDokter.idDOKTER)
            iddokter?.let { dokter ->
                UpdateDokterScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
        composable(HomePerawatan.route){
            HomePerawatnScreen(
                navigateToAdd = { navController.navigate(AddPerawatan.route) {
                    popUpTo(HomePerawatan.route) {
                        inclusive = true
                    }
                }
                },
                onDetailClickPerawatan = { idperawatan ->
                    navController.navigate("${DestinasiDetailPerawatan.route}/$idperawatan")
                },
                navigateToBack = {
                    navController.navigate(Home.route) {
                        popUpTo(Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(AddPerawatan.route){
            InsertPerawatanScreen(
                navigateBack = { navController.navigate(HomePerawatan.route) {
                    popUpTo(HomePerawatan.route) {
                        inclusive = true
                    }
                }
                }
            )
        }
        composable(DestinasiDetailPerawatan.routeWithArg, arguments = listOf(navArgument(DestinasiDetailPerawatan.idPERAWATAN) {
            type = NavType.StringType }
        )
        ){
            val idperawatan = it.arguments?.getString(DestinasiDetailPerawatan.idPERAWATAN)
            idperawatan?.let { perawatan ->
                DetailPerawatanScreen(
                    navigateToItemUpdatePrwtn = { navController.navigate("${UpdatePerawatan.route}/$idperawatan") },
                    navigateBack = { navController.navigate(HomePerawatan.route) {
                        popUpTo(HomePerawatan.route) { inclusive = true }
                    }
                    }
                )
            }
        }
        composable(UpdatePerawatan.routeWithArg, arguments = listOf(navArgument(DestinasiDetailPerawatan.idPERAWATAN){
            type = NavType.StringType
        }
        )
        ){
            val idperawatan = it.arguments?.getString(UpdatePerawatan.idPERAWATAN)
            idperawatan?.let { perawatan ->
                UpdatePerawatanScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}