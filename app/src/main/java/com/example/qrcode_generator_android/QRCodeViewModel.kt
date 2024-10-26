package com.example.qrcode_generator_android

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

class QRCodeViewModel : ViewModel() {
    var urlString by mutableStateOf("")
    var qrCodeBitmap by mutableStateOf<Bitmap?>(null)

    fun generateQRCode() {
        val qrCodeBitmap = try {
            val hints = mapOf(
                EncodeHintType.MARGIN to 0,
                EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.M
            )
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(
                urlString,
                BarcodeFormat.QR_CODE,
                300,
                300,
                hints
            )
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
        this.qrCodeBitmap = qrCodeBitmap
    }
}