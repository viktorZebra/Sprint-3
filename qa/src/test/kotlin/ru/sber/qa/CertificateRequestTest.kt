package ru.sber.qa

import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.random.Random

internal class CertificateRequestTest {
    @BeforeEach
    fun setUp() {
        mockkObject(Scanner)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @ParameterizedTest
    @MethodSource("processParams")
    fun process(hrEmployeeNumber: Long, certificateType: CertificateType, data: ByteArray) {

        val certificateRequest = CertificateRequest(hrEmployeeNumber, certificateType)
        every { Scanner.getScanData() } returns data

        val expected = Certificate(certificateRequest, hrEmployeeNumber, Scanner.getScanData())
        val actual = certificateRequest.process(hrEmployeeNumber)

        assertEqualsCertificate(expected, actual)
    }

    companion object {
        @JvmStatic
        fun processParams() = listOf(
                Arguments.of(0L, CertificateType.NDFL, byteArrayOf(0,1,2,3)),
                Arguments.of(100000L, CertificateType.LABOUR_BOOK, byteArrayOf(0,-1,2,3))
        )
    }

    private fun assertEqualsCertificate(excepted: Certificate, actual: Certificate) {
        Assertions.assertEquals(excepted.certificateRequest, actual.certificateRequest)
        Assertions.assertEquals(excepted.data, actual.data)
        Assertions.assertEquals(excepted.processedBy, actual.processedBy)
    }
}