package ru.sber.io

import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
/**
 * Реализовать методы архивации и разархивации файла.
 * Для реализиации использовать ZipInputStream и ZipOutputStream.
 */
class Archivator {

    /**
     * Метод, который архивирует файл logfile.log в архив logfile.zip.
     * Архив должен располагаться в том же каталоге, что и исходной файл.
     */
    fun zipLogfile(filePath: String = "lodfile.log", zipFilePath: String = "lodfile.zip") {
        try {
            val zipOutputStream = ZipOutputStream(FileOutputStream(zipFilePath))
            val fileInputStream = FileInputStream(filePath)
            val buffer: ByteArray
            val zipEntry = ZipEntry(filePath)

            fileInputStream.use {
                buffer = fileInputStream.readBytes()
            }

            zipOutputStream.use {
                zipOutputStream.putNextEntry(zipEntry)
                zipOutputStream.write(buffer)
                zipOutputStream.closeEntry()
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    /**
     * Метод, который извлекает файл из архива.
     * Извлечь из архива logfile.zip файл и сохарнить его в том же каталоге с именем unzippedLogfile.log
     */
    fun unzipLogfile(filePath: String = "unzippedLogfile.log", zipFilePath: String = "lodfile.zip") {
        try {
            val zipInputStream = ZipInputStream(FileInputStream(zipFilePath))
            val fileOutputStream = FileOutputStream(filePath)
            val buffer: ByteArray

            zipInputStream.use{
                zipInputStream.nextEntry
                buffer = zipInputStream.readBytes()
                zipInputStream.closeEntry()
            }

            fileOutputStream.use {
                fileOutputStream.write(buffer)
            }
        }catch (e: Exception){
            println(e.message)
        }
    }
}