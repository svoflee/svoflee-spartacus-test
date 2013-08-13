@echo off

cd %~dp0
cd ..
call mvn install -Dmaven.test.skip=true
pause