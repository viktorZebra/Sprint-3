package ru.sber.qa

import io.mockk.mockkClass
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.random.Random

internal class CertificateTest {
    private val certificateRequest = mockkClass(CertificateRequest::class)

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @ParameterizedTest
    @MethodSource("getCertificateTestParams")
    fun getCertificateTest(processedBy: Long, data: ByteArray) {
        val certificate = Certificate(certificateRequest, processedBy, data)

        assertEquals(certificateRequest, certificate.certificateRequest)
    }

    @ParameterizedTest
    @MethodSource("getProcessedByTestParams")
    fun getProcessedByTest(processedBy: Long, data: ByteArray) {
        val certificate = Certificate(certificateRequest, processedBy, data)

        assertEquals(processedBy, certificate.processedBy)
    }

    @ParameterizedTest
    @MethodSource("getDataTestParams")
    fun getDataTest(processedBy: Long, data: ByteArray) {
        val certificate = Certificate(certificateRequest, processedBy, data)

        assertEquals(data, certificate.data)
    }

    companion object {
        @JvmStatic
        fun getCertificateTestParams() = listOf(
                Arguments.of(0, byteArrayOf(0))
        )

        @JvmStatic
        fun getProcessedByTestParams() = listOf(
                Arguments.of(1000000000000L, byteArrayOf(0)),
                Arguments.of(0L, byteArrayOf(0)),
                Arguments.of(-10000000000L, byteArrayOf(0))
        )

        @JvmStatic
        fun getDataTestParams() = listOf(
                Arguments.of(0L, Random.nextBytes(100)),
                Arguments.of(0, Random.nextBytes(100)),
                Arguments.of(0L, Random.nextBytes(100))
        )
    }
}