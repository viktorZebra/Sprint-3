package ru.sber.qa

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.random.Random
import kotlin.test.assertEquals

internal class ScannerTest {
    @BeforeEach
    fun setUp() {
        mockkObject(Random)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @ParameterizedTest
    @MethodSource("getScanDataScanTimeoutExceptionTestParams")
    fun getScanDataScanTimeoutExceptionTest(timeOut: Long) {
        every { Random.nextLong(5000L, 15000L) } returns timeOut

        assertThrows<ScanTimeoutException> { Scanner.getScanData() }
    }

    @ParameterizedTest
    @MethodSource("getScanDataTestParams")
    fun getScanDataTest(timeOut: Long, byteArray: ByteArray) {
        every { Random.nextLong(5000L, 15000L) } returns timeOut
        every { Random.nextBytes(100) } returns byteArray

        assertEquals(byteArray, Scanner.getScanData())
    }

    companion object {
        @JvmStatic
        fun getScanDataScanTimeoutExceptionTestParams() = listOf(
                Arguments.of(10_001L)
        )

        @JvmStatic
        fun getScanDataTestParams() = listOf(
                Arguments.of(0L, Random.nextBytes(100)),
                Arguments.of(9999L, Random.nextBytes(100))
        )
    }
}