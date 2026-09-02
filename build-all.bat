@echo off
echo ========================================================
echo   Compilando e instalando Retos en el repositorio local
echo ========================================================

echo [1/4] Instalando Reto #1...
cd "Reto #1\Reto1"
call mvnw.cmd install -DskipTests
cd ..\..\

echo [2/4] Instalando Reto #2...
cd "Reto2"
call mvnw.cmd install -DskipTests
cd ..\

echo [3/4] Instalando Reto #3...
cd "Reto #3\Reto3"
call mvnw.cmd install -DskipTests
cd ..\

echo [4/4] Compilando MenuIntegrador...
cd "MenuIntegrador"
call mvnw.cmd clean compile
cd ..\

echo.
echo ========================================================
echo   Proyecto Integrador listo!
echo   Para ejecutar: cd MenuIntegrador ^& .\mvnw.cmd javafx:run
echo ========================================================
