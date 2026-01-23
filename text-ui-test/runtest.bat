@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin (
    mkdir ..\bin
)

REM delete output from previous run
if exist ACTUAL.TXT (
    del ACTUAL.TXT
)

REM compile the code into the bin folder
javac -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)

REM run the program, feed commands from input.txt and capture output
java -classpath ..\bin Sky < input.txt > ACTUAL.TXT

REM compare actual output with expected output
FC ACTUAL.TXT EXPECTED.TXT
IF ERRORLEVEL 1 (
    echo Test result: FAILED
    exit /b 1
) ELSE (
    echo Test result: PASSED
    exit /b 0
)
