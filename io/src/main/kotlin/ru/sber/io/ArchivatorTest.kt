package src.main.kotlin.ru.sber.io

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.sber.io.Archivator

internal class ArchivatorTest {
    private val archivator = Archivator()

    @Test
    fun zipLogfile() {
        assertDoesNotThrow {archivator.zipLogfile()}
    }

    @Test
    fun unzipLogfile() {
        assertDoesNotThrow {archivator.unzipLogfile()}
    }
}