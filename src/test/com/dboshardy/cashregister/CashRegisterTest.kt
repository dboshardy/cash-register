package com.dboshardy.cashregister

import com.dboshardy.cashregister.model.CashRegister
import org.junit.Before
import kotlin.test.assertEquals

class CashRegisterTest {
    private var register: CashRegister = CashRegister() // empty register
    @Before
    fun setUp() {
        val bills = listOf(5, 5, 5, 5, 5)
        register = CashRegister(bills)
    }

    @org.junit.Test
    fun testPutBills() {
        val bills = listOf(1, 2, 3, 4, 5)
        register.put(bills)
        assertEquals(expected = 6, actual = register.twenties(), message = "After adding 1 bill to the twenties, register should have 6 twenties")
        assertEquals(expected = 7, actual = register.tens(), message = "After adding 2 bill to the tens, register should have 7 twenties")
        assertEquals(expected = 8, actual = register.fives(), message = "After adding 3 bill to the fives, register should have 8 fives")
        assertEquals(expected = 9, actual = register.twos(), message = "After adding 4 bill to the twos, register should have 9 twos")
        assertEquals(expected = 10, actual = register.ones(), message = "After adding 5 bill to the ones, register should have 10 ones")
    }

    @org.junit.Test
    fun testTakeBills() {
        val bills = listOf(1, 2, 3, 4, 5)
        register.take(bills)
        assertEquals(expected = 4, actual = register.twenties(), message = "After taking away 1 bill to the twenties, register should have 4 twenties")
        assertEquals(expected = 3, actual = register.tens(), message = "After taking away 2 bill to the tens, register should have 3 twenties")
        assertEquals(expected = 2, actual = register.fives(), message = "After taking away 3 bill to the fives, register should have 2 fives")
        assertEquals(expected = 1, actual = register.twos(), message = "After taking away 4 bill to the twos, register should have 1 twos")
        assertEquals(expected = 0, actual = register.ones(), message = "After taking away 5 bill to the ones, register should have 0 ones")
    }

    @org.junit.Test
    fun testShowBillsInRegister() {
        assertEquals(expected = "$${5 * 20 + 10 * 5 + 5 * 5 + 2 * 5 + 5} 5 5 5 5 5", actual = register.show(), message = "Register should output total value and all bills contained within register when show() is called.")
    }
    @org.junit.Test
    fun testTakeTooManyBills() {
        val bills = listOf(6, 7, 8, 9, 10)
        register.take(bills)
        assertEquals(expected = 0, actual = register.twenties(), message = "After taking away more bills than present, register now has 0 bills")
        assertEquals(expected = 0, actual = register.tens(), message = "After taking away more bills than present, register now has 0 bills")
        assertEquals(expected = 0, actual = register.fives(), message = "After taking away more bills than present, register now has 0 bills")
        assertEquals(expected = 0, actual = register.twos(), message = "After taking away more bills than present, register now has 0 bills")
        assertEquals(expected = 0, actual = register.ones(), message = "After taking away more bills than present, register now has 0 bills")
    }


    @org.junit.Test
    fun testMakeChange() {
        val amount = 43
        val change = register.makeChange(amount).split(" ").map { it.toInt()}
        assertEquals(expected = 3, actual = register.twenties(), message = "After making change, register now has 0 bills")
        assertEquals(expected = 5, actual = register.tens(), message = "After making change, register now has 0 bills")
        assertEquals(expected = 5, actual = register.fives(), message = "After making change, register now has 0 bills")
        assertEquals(expected = 4, actual = register.twos(), message = "After making change, register now has 0 bills")
        assertEquals(expected = 4, actual = register.ones(), message = "After making change, register now has 0 bills")

        assertEquals(expected = 2, actual = change[0], message = "After making change for $43, change should show proper amount of twenties (2)")
        assertEquals(expected = 0, actual = change[1], message = "After making change for $43, change should show proper amount of tens (0)")
        assertEquals(expected = 0, actual = change[2], message = "After making change for $43, change should show proper amount of fives (0)")
        assertEquals(expected = 1, actual = change[3], message = "After making change for $43, change should show proper amount of twos (1)")
        assertEquals(expected = 1, actual = change[4], message = "After making change for $43, change should show proper amount of ones (1)")
    }

    @org.junit.Test
    fun testMakeChangeWithLargeAmounts() {
        register.put(listOf(10000000, 10000000, 10000000, 10000000, 10000000))
        val amount = 123456789
        val change = register.makeChange(amount).split(" ").map { it.toInt()}

        // 123456789 / 20 == 6172839.45
        // 123456789 % 20 == 9
        // 6172839 twenties
        // 1 five
        // 2 twos

        assertEquals(expected = 3827166, actual = register.twenties(), message = "After making change, register now has 0 bills")
        assertEquals(expected = 10000005, actual = register.tens(), message = "After making change, register now has 0 bills")
        assertEquals(expected = 10000004, actual = register.fives(), message = "After making change, register now has 0 bills")
        assertEquals(expected = 10000003, actual = register.twos(), message = "After making change, register now has 0 bills")
        assertEquals(expected = 10000005, actual = register.ones(), message = "After making change, register now has 0 bills")

        assertEquals(expected = 6172839, actual = change[0], message = "After making change for $, change should show proper amount of twenties (2)")
        assertEquals(expected = 0, actual = change[1], message = "After making change for $43, change should show proper amount of tens (0)")
        assertEquals(expected = 1, actual = change[2], message = "After making change for $43, change should show proper amount of fives (0)")
        assertEquals(expected = 2, actual = change[3], message = "After making change for $43, change should show proper amount of twos (1)")
        assertEquals(expected = 0, actual = change[4], message = "After making change for $43, change should show proper amount of ones (1)")
    }
    @org.junit.Test
    fun testCannotMakeChange() {
        register.take(listOf(5, 2, 3, 5, 5))
        assertEquals(expected = "I apologize, but I cannot make change for $512 with the bills I have.\n$40 0 3 2 0 0", actual = register.makeChange(512), message = "When total in register is less than requested amount to make change for, error should be returned")
        assertEquals(expected = "I apologize, but I cannot make change for $33 with the bills I have.\n$40 0 3 2 0 0", actual = register.makeChange(33), message = "When register cannot make change with bills present, error message should be returned.")
    }
}
