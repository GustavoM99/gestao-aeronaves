# API Test Script for Gestão de Aeronaves
# Run this after starting the backend to test all endpoints

Write-Host "`n==========================================" -ForegroundColor Cyan
Write-Host "  Testing Gestão de Aeronaves API" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

$baseUrl = "http://localhost:8080"

# Test 1: Get all aircraft
Write-Host "`n[TEST 1] GET /aeronaves - Getting all aircraft..." -ForegroundColor Yellow
try {
    $result = Invoke-RestMethod -Uri "$baseUrl/aeronaves" -Method Get
    Write-Host "✓ SUCCESS: Found $($result.Count) aircraft" -ForegroundColor Green
} catch {
    Write-Host "✗ FAILED: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 2: Get statistics - Unsold count
Write-Host "`n[TEST 2] GET /aeronaves/statistics/nao-vendidos - Count unsold..." -ForegroundColor Yellow
try {
    $result = Invoke-RestMethod -Uri "$baseUrl/aeronaves/statistics/nao-vendidos" -Method Get
    Write-Host "✓ SUCCESS: $result aircraft not sold" -ForegroundColor Green
} catch {
    Write-Host "✗ FAILED: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 3: Get decade statistics
Write-Host "`n[TEST 3] GET /aeronaves/statistics/decadas - Decade distribution..." -ForegroundColor Yellow
try {
    $result = Invoke-RestMethod -Uri "$baseUrl/aeronaves/statistics/decadas" -Method Get
    Write-Host "✓ SUCCESS: Found $($result.Count) decades" -ForegroundColor Green
    $result | ForEach-Object { Write-Host "  - $($_.decada): $($_.quantidade) aeronaves" }
} catch {
    Write-Host "✗ FAILED: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 4: Get manufacturer statistics
Write-Host "`n[TEST 4] GET /aeronaves/statistics/marcas - Manufacturer statistics..." -ForegroundColor Yellow
try {
    $result = Invoke-RestMethod -Uri "$baseUrl/aeronaves/statistics/marcas" -Method Get
    Write-Host "✓ SUCCESS: Found $($result.Count) manufacturers" -ForegroundColor Green
    $result | ForEach-Object { Write-Host "  - $($_.marca): $($_.quantidade) aeronaves" }
} catch {
    Write-Host "✗ FAILED: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 5: Create new aircraft
Write-Host "`n[TEST 5] POST /aeronaves - Creating new aircraft..." -ForegroundColor Yellow
try {
    $newAircraft = @{
        nome = "Test Aircraft $(Get-Random)"
        marca = "Boeing"
        ano = 2023
        descricao = "Test aircraft created by script"
        vendido = $false
    } | ConvertTo-Json

    $result = Invoke-RestMethod -Uri "$baseUrl/aeronaves" -Method Post -Body $newAircraft -ContentType "application/json"
    $createdId = $result.id
    Write-Host "✓ SUCCESS: Created aircraft with ID $createdId" -ForegroundColor Green
    
    # Test 6: Get the created aircraft
    Write-Host "`n[TEST 6] GET /aeronaves/$createdId - Getting created aircraft..." -ForegroundColor Yellow
    $result = Invoke-RestMethod -Uri "$baseUrl/aeronaves/$createdId" -Method Get
    Write-Host "✓ SUCCESS: Retrieved aircraft '$($result.nome)'" -ForegroundColor Green
    
    # Test 7: Update the aircraft
    Write-Host "`n[TEST 7] PUT /aeronaves/$createdId - Updating aircraft..." -ForegroundColor Yellow
    $updateAircraft = @{
        nome = "Updated Test Aircraft"
        marca = "Boeing"
        ano = 2023
        descricao = "Updated description"
        vendido = $true
    } | ConvertTo-Json
    
    $result = Invoke-RestMethod -Uri "$baseUrl/aeronaves/$createdId" -Method Put -Body $updateAircraft -ContentType "application/json"
    Write-Host "✓ SUCCESS: Updated aircraft, vendido = $($result.vendido)" -ForegroundColor Green
    
    # Test 8: Search aircraft
    Write-Host "`n[TEST 8] GET /aeronaves/find?termo=Boeing - Searching..." -ForegroundColor Yellow
    $result = Invoke-RestMethod -Uri "$baseUrl/aeronaves/find?termo=Boeing" -Method Get
    Write-Host "✓ SUCCESS: Found $($result.Count) Boeing aircraft" -ForegroundColor Green
    
    # Test 9: Delete the aircraft
    Write-Host "`n[TEST 9] DELETE /aeronaves/$createdId - Deleting aircraft..." -ForegroundColor Yellow
    Invoke-RestMethod -Uri "$baseUrl/aeronaves/$createdId" -Method Delete
    Write-Host "✓ SUCCESS: Aircraft deleted" -ForegroundColor Green
    
} catch {
    Write-Host "✗ FAILED: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 10: Get valid manufacturers
Write-Host "`n[TEST 10] GET /aeronaves/manufacturers - Getting valid manufacturers..." -ForegroundColor Yellow
try {
    $result = Invoke-RestMethod -Uri "$baseUrl/aeronaves/manufacturers" -Method Get
    Write-Host "✓ SUCCESS: Found $($result.Count) valid manufacturers" -ForegroundColor Green
    Write-Host "Valid manufacturers: $($result -join ', ')" -ForegroundColor Cyan
} catch {
    Write-Host "✗ FAILED: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 11: Test validation (should fail)
Write-Host "`n[TEST 11] POST /aeronaves - Testing validation (should fail)..." -ForegroundColor Yellow
try {
    $invalidAircraft = @{
        nome = "X"  # Too short
        marca = "InvalidBrand"  # Invalid manufacturer
        ano = 1800  # Too old
    } | ConvertTo-Json

    Invoke-RestMethod -Uri "$baseUrl/aeronaves" -Method Post -Body $invalidAircraft -ContentType "application/json"
    Write-Host "✗ UNEXPECTED: Validation should have failed!" -ForegroundColor Red
} catch {
    Write-Host "✓ SUCCESS: Validation correctly rejected invalid data" -ForegroundColor Green
}

Write-Host "`n==========================================" -ForegroundColor Cyan
Write-Host "  All tests completed!" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host ""
