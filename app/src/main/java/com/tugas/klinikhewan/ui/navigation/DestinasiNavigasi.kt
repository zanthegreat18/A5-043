package com.tugas.klinikhewan.ui.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

object Home : DestinasiNavigasi {
    override val route = "Daftar Pasien"
    override val titleRes = "Data Pasien"
}

object HomeJenis : DestinasiNavigasi {
    override val route = "home_jenis"
    override val titleRes = "Data Jenis"
}

object HomeDokter : DestinasiNavigasi {
    override val route = "home_dokter"
    override val titleRes = "Data Dokter"
}

object HomePerawatan : DestinasiNavigasi {
    override val route = "Data Perawatan"
    override val titleRes = "Riwayat Perawatan"
}

object AddPasien : DestinasiNavigasi {
    override val route = "Tambah Pasien"
    override val titleRes = "Entry Data Pasien"
}

object AddJenis : DestinasiNavigasi {
    override val route = "Tambah Jenis"
    override val titleRes = "Entry Data Jenis"
}

object AddDokter : DestinasiNavigasi {
    override val route = "Tambah Dokter"
    override val titleRes = "Entry Data Dokter"
}

object AddPerawatan : DestinasiNavigasi {
    override val route = "Tambah Perawatan"
    override val titleRes = "Entry Data Perawatan"
}

object UpdatePasien: DestinasiNavigasi {
    override val route = "update pasien"
    override val titleRes = "Update Pasien"
    const val idPASIEN = "idhewan"
    val routeWithArg = "$route/{$idPASIEN}"
}

object DesttinasiDetailPasien: DestinasiNavigasi {
    override val route = "detail pasien"
    override val titleRes = "Detail Pasien"
    const val idPASIEN = "idhewan"
    val routeWithArg = "$route/{$idPASIEN}"
}

object DestinasiDetailJenis : DestinasiNavigasi {
    override val route = "detail jenis"
    override val titleRes = "Detail Jenis"
    const val idJENIS = "idjenis"
    val routeWithArg = "$route/{$idJENIS}"
}

object DestinasiDetailDokter : DestinasiNavigasi{
    override val route = "detail dokter"
    override val titleRes = "Detail Dokter"
    const val idDOKTER = "iddokter"
    val routeWithArg = "$route/{$idDOKTER}"
}

object DestinasiDetailPerawatan : DestinasiNavigasi{
    override val route = "detail perawatan"
    override val titleRes = "Detail Perawatan"
    const val idPERAWATAN = "idperawatan"
    val routeWithArg = "$route/{$idPERAWATAN}"
}


object UpdateJenis: DestinasiNavigasi {
    override val route = "update jenis"
    override val titleRes = "Update Jenis"
    const val idJENIS = "idjenis"
    val routeWithArg = "$route/{$idJENIS}"
}

object UpdateDokter: DestinasiNavigasi {
    override val route = "update dokter"
    override val titleRes = "Update Dokter"
    const val idDOKTER = "iddokter"
    val routeWithArg = "$route/{$idDOKTER}"
}

object UpdatePerawatan: DestinasiNavigasi {
    override val route = "update perawatan"
    override val titleRes = "Update Perawatan"
    const val idPERAWATAN = "idperawatan"
    val routeWithArg = "$route/{$idPERAWATAN}"
}
