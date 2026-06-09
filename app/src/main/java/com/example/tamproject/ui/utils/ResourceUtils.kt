package com.example.tamproject.ui.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.tamproject.R

/**
 * Mendapatkan Resource ID berdasarkan nama secara aman.
 * Menangani string null/kosong dan memberikan fallback agar tidak crash.
 */
@Composable
fun getResourceId(name: String?): Int {
    val context = LocalContext.current
    return getResourceIdNonComposable(context, name)
}

/**
 * Versi non-composable dari getResourceId untuk fleksibilitas penggunaan.
 */
fun getResourceIdNonComposable(context: Context, name: String?): Int {
    val fallback = R.mipmap.ic_launcher
    if (name.isNullOrBlank()) return fallback
    
    return try {
        // Bersihkan nama: hapus ekstensi jika ada dan ambil bagian terakhir jika berupa path
        val cleanName = name.substringBeforeLast('.')
            .substringAfterLast('/')
            .lowercase()
            .trim()
        
        if (cleanName.isEmpty()) return fallback

        // Cari di drawable dahulu
        var id = context.resources.getIdentifier(cleanName, "drawable", context.packageName)
        
        // Jika tidak ketemu di drawable, cari di mipmap
        if (id == 0) {
            id = context.resources.getIdentifier(cleanName, "mipmap", context.packageName)
        }
        
        // Jika id masih 0, kembalikan fallback
        if (id != 0) id else fallback
    } catch (e: Exception) {
        // Jika terjadi error apa pun (ResourceNotFound, dll), pastikan tidak force close
        fallback
    }
}
