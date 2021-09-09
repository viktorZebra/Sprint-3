package ru.sber.qa

import io.mockk.every
import io.mockk.mockkClass
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.time.*


internal class HrDepartmentTest {
    private val certificateRequest = mockkClass(CertificateRequest::class)

    @BeforeEach
    fun setUp() {
        mockkStatic(LocalDateTime::class)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @ParameterizedTest
    @MethodSource("receiveRequestWeekendDayExceptionTestParams")
    fun receiveRequestWeekendDayExceptionTest(currentDay: DayOfWeek) {
        every { LocalDateTime.now(Clock.systemUTC()).dayOfWeek } returns currentDay

        assertThrows<WeekendDayException> { HrDepartment.receiveRequest(certificateRequest) }
    }

    @ParameterizedTest
    @MethodSource("receiveRequestNotAllowReceiveRequestExceptionTestParams")
    fun receiveRequestNotAllowReceiveRequestExceptionTest(currentDay: DayOfWeek, certificateType: CertificateType) {
        every { LocalDateTime.now(Clock.systemUTC()).dayOfWeek } returns currentDay
        every { certificateRequest.certificateType } returns certificateType

        assertThrows<NotAllowReceiveRequestException> { HrDepartment.receiveRequest(certificateRequest) }
    }

    @ParameterizedTest
    @MethodSource("receiveRequestTestParams")
    fun receiveRequestTest(currentDay: DayOfWeek, certificateType: CertificateType) {
        every { LocalDateTime.now(Clock.systemUTC()).dayOfWeek } returns currentDay
        every { certificateRequest.certificateType } returns certificateType

        assertDoesNotThrow { HrDepartment.receiveRequest(certificateRequest) }
    }

    @ParameterizedTest
    @MethodSource("processNextRequestParams")
    fun processNextRequestTest(currentDay: DayOfWeek, certificateType: CertificateType, hrEmployeeNumber: Long) {
        val certificate = mockkClass(Certificate::class)

        every { LocalDateTime.now(Clock.systemUTC()).dayOfWeek } returns currentDay
        every { certificateRequest.certificateType } returns certificateType
        every { certificateRequest.process(hrEmployeeNumber) } returns certificate

        HrDepartment.receiveRequest(certificateRequest)

        assertDoesNotThrow { HrDepartment.processNextRequest(hrEmployeeNumber) }
    }

    companion object {
        @JvmStatic
        fun receiveRequestWeekendDayExceptionTestParams() = listOf(
                Arguments.of(DayOfWeek.SATURDAY),
                Arguments.of(DayOfWeek.SUNDAY)
        )

        @JvmStatic
        fun receiveRequestNotAllowReceiveRequestExceptionTestParams() = listOf(
                Arguments.of(DayOfWeek.TUESDAY, CertificateType.NDFL),
                Arguments.of(DayOfWeek.THURSDAY, CertificateType.NDFL),
                Arguments.of(DayOfWeek.MONDAY, CertificateType.LABOUR_BOOK),
                Arguments.of(DayOfWeek.WEDNESDAY, CertificateType.LABOUR_BOOK),
                Arguments.of(DayOfWeek.FRIDAY, CertificateType.LABOUR_BOOK)
        )

        @JvmStatic
        fun receiveRequestTestParams() = listOf(
                Arguments.of(DayOfWeek.TUESDAY, CertificateType.LABOUR_BOOK),
                Arguments.of(DayOfWeek.THURSDAY, CertificateType.LABOUR_BOOK),
                Arguments.of(DayOfWeek.MONDAY, CertificateType.NDFL),
                Arguments.of(DayOfWeek.WEDNESDAY, CertificateType.NDFL),
                Arguments.of(DayOfWeek.FRIDAY, CertificateType.NDFL)
        )

        @JvmStatic
        fun processNextRequestParams() = listOf(
                Arguments.of(DayOfWeek.TUESDAY, CertificateType.LABOUR_BOOK, 0L),
                Arguments.of(DayOfWeek.THURSDAY, CertificateType.LABOUR_BOOK, 1L),
                Arguments.of(DayOfWeek.MONDAY, CertificateType.NDFL, 2L),
                Arguments.of(DayOfWeek.WEDNESDAY, CertificateType.NDFL, 3L),
                Arguments.of(DayOfWeek.FRIDAY, CertificateType.NDFL, 4L)
        )
    }
}