package com.dboshardy.cashregister.model

import kotlin.coroutines.experimental.EmptyCoroutineContext.plus

private val PROPER_INPUT_FORMAT = "TWENTIES TENS FIVES TWOS ONES"
private val NUM_BILL_TYPES = 5
private val TWENTIES_INDEX = 0
private val TENS_INDEX = 1
private val FIVES_INDEX = 2
private val TWOS_INDEX = 3
private val ONES_INDEX = 4

data class CashRegister(private var bills: List<Int>) {
    constructor(twenties: Int = 0, tens: Int = 0, fives: Int = 0, twos: Int = 0, ones: Int = 0) : this(listOf(twenties, tens, fives, twos, ones))


    /*
     * Takes an array of bills assumed to be in proper order and puts them in the register
     */
    fun put(bills: List<Int>): String {
        if (bills.size != NUM_BILL_TYPES) {
            return "Could not add bills, please specify bills in the proper format: 'put ${PROPER_INPUT_FORMAT}'"
        }

        this.bills.zip(bills).map{ it.first + it.second }

        return show()
    }

    /*
     * Takes an array of bills assumed to be in proper order and takes them out of the register
     * TODO: Make sure you cannot take more than the present number of each bill out of the register
     */
    fun take(bills: List<Int>): String {
        if (bills.size != NUM_BILL_TYPES) {
            return "Could not add bills, please specify bills in the proper format: 'put ${PROPER_INPUT_FORMAT}'"
        }

        this.bills.zip(bills).map{ it.first - it.second }
        correctNegativeCounts()

        return show()
    }

    private fun correctNegativeCounts() {
        bills = bills.map { makeNegativeZero(amount = it) }
    }

    /*
     * takes any negative bill amount and converts to 0
     * TODO: Change how this works such that you cannot take more bills than are in the register out
     */
    private fun makeNegativeZero(amount: Int): Int {
        return if (amount < 0) 0 else amount
    }

    /*
     * returns the total amount of money in the register along with the number of each kind of bill in the proper format
     */
    fun show(): String {
        return "$${total()} ${bills.joinToString(separator = " ")}"
    }

    /*
     * Returns the computed total amount for the bills currently in the register
     */
    private fun total(): Int {
        return bills.withIndex().map { it.value * valueOf(it.index)}.sum()
    }

    private fun valueOf(index: Int): Int {
        return when(index) {
            TWENTIES_INDEX -> 20
            TENS_INDEX -> 10
            FIVES_INDEX -> 5
            TWOS_INDEX -> 2
            ONES_INDEX -> 1
            else -> 0
        }

    }

}