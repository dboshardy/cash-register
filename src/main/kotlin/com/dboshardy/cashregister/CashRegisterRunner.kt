package com.dboshardy.cashregister

import com.dboshardy.cashregister.model.CashRegister
import java.util.*

public class CashRegisterRunner {

    companion object {
        @JvmStatic
        public fun main(args: Array<String>) {
            val scanner = Scanner(System.`in`)
            val register = CashRegister()
            var input = "show"
            println("Welcome to the Cash Register. Enter \"help\" for a list of available commands.")
            println("The register has the following money")
            while (input != "quit") {
                println(parseCommand(input, register))
                input = scanner.nextLine()
            }
            println("Bye")
        }

        private fun parseCommand(input: String, register: CashRegister): String {
            val cleanedInput = input.trim()
            val inputArr = cleanedInput.split(" ")
            val command = inputArr.first()
            return try {
                val bills = inputArr.drop(1).map { it.toInt() }
                when (command) {
                    "help" -> showHelp()
                    "show" -> register.show()
                    "put" -> register.put(bills)
                    "take" -> register.take(bills)
                    "change" -> register.makeChange(bills.first()) // first element is the amount, no bills in this format ('change x'
                    else -> cannotUnderstand()
                }
            } catch (e: NumberFormatException) {
                return cannotUnderstand()
            }

        }

        private fun cannotUnderstand(): String {
            return "Sorry, I didn't understand your command, for a list of the available commands.\n${showHelp()}"
        }

        private fun showHelp(): String {
            return "The available commands are:\n'show': shows the current state of the register\n'put TWENTIES TENS FIVES ONES': add bills to the register\n'take TWENTIES TENS FIVES ONES': take bills out of register\n'change AMOUNT': makes change for given amount if possible\nNOTE: For all commands, do not include a dollar sign."
        }
    }
}