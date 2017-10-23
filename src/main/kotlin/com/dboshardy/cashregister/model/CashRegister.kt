package com.dboshardy.cashregister.model

import kotlin.coroutines.experimental.EmptyCoroutineContext.plus

private val PROPER_INPUT_FORMAT = "TWENTIES TENS FIVES TWOS ONES"
private val TWENTIES_INDEX = 0
private val TENS_INDEX = 1
private val FIVES_INDEX = 2
private val TWOS_INDEX = 3
private val ONES_INDEX = 4

data class CashRegister(private var bills: List<Int> = listOf(0,0,0,0,0)) {
    constructor(twenties: Int, tens: Int, fives: Int, twos: Int, ones: Int) : this(listOf(twenties, tens, fives, twos, ones))
    private val NUM_BILL_TYPES = bills.size


    /*
     * Takes an array of bills assumed to be in proper order and puts them in the register
     */
    fun put(bills: List<Int>): String {
        if (bills.size != NUM_BILL_TYPES) {
            return "Could not add bills, please specify bills in the proper format: 'put ${PROPER_INPUT_FORMAT}'"
        }

        this.bills = this.bills.zip(bills).map{ it.first + it.second }

        return show()
    }

    /*
     * Takes an array of bills assumed to be in proper order and takes them out of the register
     */
    fun take(bills: List<Int>): String {
        if (bills.size != NUM_BILL_TYPES) {
            return "Could not add bills, please specify bills in the proper format: 'put ${PROPER_INPUT_FORMAT}'"
        }

        this.bills = this.bills.zip(bills).map{ takeBills(it.first, it.second) }

        return show()
    }

    /*
     * Removes bills from register, only removes as many are present
     */
    private fun takeBills(billsInRegister: Int, newBills: Int): Int {
        return if(newBills > billsInRegister) {
            0
        } else {
            billsInRegister - newBills
        }

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


    fun twenties(): Int {
        return bills[TWENTIES_INDEX]
    }

    fun tens(): Int {
        return bills[TENS_INDEX]
    }

    fun fives(): Int {
        return bills[FIVES_INDEX]
    }

    fun twos(): Int {
        return bills[TWOS_INDEX]
    }

    fun ones(): Int {
        return bills[ONES_INDEX]
    }
}