# Cash Register (Change Machine)

## To run
*NOTE: This requires Java 8 and Kotlin 1.1.51.*

Either import the project into Intellij using `File > New > Project From Existing Sources > (select dir) > Import Project From External Model > Choose Maven > click Search Recursively For Maven Projects and automatically import`
Once the project has been loaded into IntelliJ, right click on `CashRegisterRunner` and click "Run".

Or run with Maven:

```bash
mvn compile exec:java
```

### Commands

```bash
show           # shows the money present in the register in the format $TOTAL TWENTIES TENS FIVES TWOS ONES
put a b c d e  # adds money to the register in the format above
take a b c d e # removes money from the register in the format above
change x       # makes change for x given the bills present in the register (returns 'sorry' if cannot make change)
help           # shows commands
```