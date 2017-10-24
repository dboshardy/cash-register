package com.dboshardy.cashregister.model


data class CashRegister(private var bills: List<Int> = listOf(0, 0, 0, 0, 0)) {
    private val PROPER_INPUT_FORMAT = "TWENTIES TENS FIVES TWOS ONES"
    private val TWENTIES_INDEX = 0
    private val TENS_INDEX = 1
    private val FIVES_INDEX = 2
    private val TWOS_INDEX = 3
    private val ONES_INDEX = 4

    private val NUM_BILL_TYPES = bills.size


    /*
     * returns string of values
     */
    fun makeChange(amount: Int): String {
        if (cannotMakeChange(amount)) {
            return "I apologize, but I cannot make change for $$amount with the bills I have.\n${show()}"
        }
        return try {
            val billsToEdit: MutableList<Int> = bills.toMutableList()
            val pair: Pair<List<Int>, List<Int>> = makeChange(amount, billsToEdit, mutableListOf(0, 0, 0, 0, 0))
            this.bills = pair.first
            val change = pair.second
            change.joinToString(separator = " ")
        } catch (e: ChangeNotAvailableException) {
            "I apologize, but I cannot make change for $$amount with the bills I have.\n${show()}"
        }
    }


    private tailrec fun makeChange(amount: Int, billsToEdit: MutableList<Int>, change: MutableList<Int>): Pair<List<Int>, List<Int>> {
        return if (billsToEdit.sum() == 0 && amount != 0) {
            // throw error if you run out of bills before amount is 0
            throw ChangeNotAvailableException()
        } else {
            if (billsToEdit[TWENTIES_INDEX] != 0 && amount >= 20) {
                billsToEdit[TWENTIES_INDEX]--
                change[TWENTIES_INDEX]++
                makeChange(amount - 20, billsToEdit, change)
            } else if (billsToEdit[TENS_INDEX] != 0 && amount >= 10) {
                billsToEdit[TENS_INDEX]--
                change[TENS_INDEX]++
                makeChange(amount - 10, billsToEdit, change)
            } else if (billsToEdit[FIVES_INDEX] != 0 && amount >= 5) {
                billsToEdit[FIVES_INDEX]--
                change[FIVES_INDEX]++
                makeChange(amount - 5, billsToEdit, change)
            } else if (billsToEdit[TWOS_INDEX] != 0 && amount >= 2) {
                billsToEdit[TWOS_INDEX]--
                change[TWOS_INDEX]++
                makeChange(amount - 2, billsToEdit, change)
            } else if (billsToEdit[ONES_INDEX] != 0 && amount >= 1) {
                billsToEdit[ONES_INDEX]--
                change[ONES_INDEX]++
                makeChange(amount - 1, billsToEdit, change)
            } else if (amount != 0) {
                throw ChangeNotAvailableException()
            } else {
                // amount is now 0 and the error condition has not been met, i.e. we're done
                Pair(billsToEdit.toList(), change.toList())
            }
        }
    }

    private fun cannotMakeChange(amount: Int): Boolean {
        return total() < amount
    }

    /*
     * Takes an array of bills assumed to be in proper order and puts them in the register
     */
    fun put(bills: List<Int>): String {
        if (bills.size != NUM_BILL_TYPES) {
            return "Could not add bills, please specify bills in the proper format: 'put ${PROPER_INPUT_FORMAT}'"
        }

        this.bills = this.bills.zip(bills).map { it.first + it.second }

        return show()
    }

    /*
     * Takes an array of bills assumed to be in proper order and takes them out of the register
     */
    fun take(bills: List<Int>): String {
        if (bills.size != NUM_BILL_TYPES) {
            return "Could not add bills, please specify bills in the proper format: 'put ${PROPER_INPUT_FORMAT}'"
        }

        try {
            this.bills = this.bills.zip(bills).map { takeBills(it.first, it.second) }
        } catch (e: NotEnoughBillsInRegisterException) {
            return "I apologize, but I cannot give you ${bills.joinToString(separator = " ")} bills, I do not have enough bills.\n${show()}"
        }

        return show()
    }

    /*
     * Removes bills from register, only removes as many are present
     */
    private fun takeBills(billsInRegister: Int, newBills: Int): Int {
        return if (newBills > billsInRegister) {
            throw NotEnoughBillsInRegisterException()
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
        return bills.withIndex().map { it.value * valueOf(it.index) }.sum()
    }

    private fun valueOf(index: Int): Int {
        return when (index) {
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

class NotEnoughBillsInRegisterException : Throwable() {}
class ChangeNotAvailableException : Throwable() {}
class BadArgumentException: Throwable() {}
