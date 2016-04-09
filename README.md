# CSV File Calculator
Does calculations on CSV files

Call it with

java -jar ./csvfilecalculator.jar C:/path/to/your/file.csv 1 4

1. argument: absolute file path
2. argument: column index (0-based) of the filling and grouping column (the column must hold dates in dd.MM.yyyy)
    a. filling: lines will be filled so that the column contains consecutive entries
    b. grouping: if there are several lines with the same value in this column they will be grouped together
3. argument: column index (0-based) of the aggregation column (the column must hold numbers like -3.5 or 2,8)
    a. aggregation: values of this column will be summed up during grouping