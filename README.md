# Cash Register (Change Machine)

## To run
```bash
java com.dboshardy.cashregister.CashRegisterRunner.java
```

### Commands

```bash
show           # shows the money present in the register in the format $TOTAL TWENTIES TENS FIVES TWOS ONES
put a b c d e  # adds money to the register in the format above
take a b c d e # removes money from the register in the format above
change x       # makes change for x given the bills present in the register (returns 'sorry' if cannot make change)
```