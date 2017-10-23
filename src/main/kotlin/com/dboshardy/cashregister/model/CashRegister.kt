package com.dboshardy.cashregister.model

data class CashRegister(var twenties: Int = 0, var tens: Int = 0, var fives: Int = 0, var twos: Int = 0, var ones: Int = 0) {
    private val PROPER_INPUT_FORMAT = "TWENTIES TENS FIVES TWOS ONES"
    private val NUM_BILL_TYPES = 5
    private val TWENTIES_INDEX = 0
    private val TENS_INDEX = 1
    private val FIVES_INDEX = 2
    private val TWOS_INDEX = 3
    private val ONES_INDEX = 4


    fun put(bills: Array<Int>): String {
        if(bills.size != NUM_BILL_TYPES) {
            return "Could not add bills, please specify bills in the proper format: 'put ${PROPER_INPUT_FORMAT}'"
        }

        this.twenties += bills[TWENTIES_INDEX]
        this.tens += bills[TENS_INDEX]
        this.fives += bills[FIVES_INDEX]
        this.twos += bills[TWOS_INDEX]
        this.ones += bills[ONES_INDEX]

        return show()
    }

    fun show(): String {
        return "\$${total()} $twenties $tens $fives $twos $ones"
    }

    fun total(): Int {
        return twenties * 20 + tens * 10 + fives * 5 + twos * 2 + ones
    }

}