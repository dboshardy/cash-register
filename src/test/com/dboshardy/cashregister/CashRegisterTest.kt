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
    fun testMakeChange() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
