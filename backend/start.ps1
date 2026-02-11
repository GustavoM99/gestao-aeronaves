# Start Gestão de Aeronaves Backend
# This script starts the Spring Boot application (PostgreSQL)

Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "  Starting Gestão de Aeronaves API" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

Write-Host "`nAPI will be available at: http://localhost:8080" -ForegroundColor Green
Write-Host "`nStarting application (ensure PostgreSQL is running)...`n" -ForegroundColor Yellow

# Run the application
.\mvnw.cmd spring-boot:run

# If mvnw fails, show helpful message
if ($LASTEXITCODE -ne 0) {
    Write-Host "`nFailed to start the application!" -ForegroundColor Red
    Write-Host "Make sure Java 17+ is installed: java -version" -ForegroundColor Yellow
    Write-Host "Make sure PostgreSQL is running (e.g. docker-compose up -d)" -ForegroundColor Yellow
}
