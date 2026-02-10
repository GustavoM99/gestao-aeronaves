@echo off
REM Start Gestão de Aeronaves Backend
REM This script starts the Spring Boot application (PostgreSQL)

echo.
echo ========================================
echo   Starting Gestao de Aeronaves API
echo ========================================
echo.

echo API will be available at: http://localhost:8080
echo.
echo Starting application (ensure PostgreSQL is running)...
echo.

REM Run the application
mvnw.cmd spring-boot:run

pause
