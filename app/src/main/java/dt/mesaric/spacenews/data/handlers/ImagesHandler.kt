package dt.mesaric.spacenews.data.handlers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import androidx.navigation.navOptions
import dt.mesaric.spacenews.data.factory.createGetHttpUrlConnection
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

private const val ARTICLE_PICTURE_WIDTH = 1000
private const val ARTICLE_PICTURE_HEIGHT = 750
private const val QUALITY = 100
private const val TAG = "ImagesHandler"

fun downloadImageAndStore (context: Context, url: String, fileName: String): String?{
    val ext = url.substring(url.lastIndexOf("."))
    val file = getFile(context, fileName, ext)
    try {
        val con = createGetHttpUrlConnection(url)
        con.inputStream.use { `is` ->
            FileOutputStream(file).use { fos ->
                val bitmap = BitmapFactory.decodeStream(`is`)
                val resizedBitmap : Bitmap = getResizedBitmap(bitmap,
                    ARTICLE_PICTURE_WIDTH, ARTICLE_PICTURE_HEIGHT)
                val buffer: ByteArray = getBytesFromBitmap(resizedBitmap)
                fos.write(buffer)
                return file.absolutePath
            }
        }
    } catch (e: Exception){
        Log.e(TAG, e.message, e)
    }
    return null
}

fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
    val bos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, bos)
    return bos.toByteArray()
}

fun getResizedBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
    val width = bitmap.width
    val height = bitmap.height
    val scaleWidth = newWidth.toFloat() / width
    val scaleHeight = newHeight.toFloat() / height
    val matrix = Matrix()
    matrix.postScale(scaleWidth, scaleHeight)
    return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
}

fun getFile(context: Context, fileName: String, ext: String): File {
    val dir: File? = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, File.separator.toString() + fileName + ext)
    if (file.exists()){
        file.delete()
    }
    return file
}
