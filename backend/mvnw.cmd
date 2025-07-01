@echo off
@REM Maven Wrapper for Windows

if "%MAVEN_HOME%"=="" (
    echo MAVEN_HOME not set, trying to find Maven in PATH
    set MAVEN_EXEC=mvn
) else (
    set MAVEN_EXEC=%MAVEN_HOME%\bin\mvn.cmd
)

%MAVEN_EXEC% %*
