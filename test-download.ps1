# Test script for downloading generated EFO documents
# Usage: .\test-download.ps1

$workerId = 4451
$employmentDate = "2025-09-03"
$fileName = "mikulas_marcell_20250903.pdf"

$url = "http://localhost:8080/api/efo-foglalkoztatasok/generated-documents/$workerId/$employmentDate/$fileName"

Write-Host "Testing document download..." -ForegroundColor Cyan
Write-Host "URL: $url" -ForegroundColor Yellow

try {
    # Test without authentication (should fail)
    Write-Host "`nTest 1: Without authentication (should get 401/403)" -ForegroundColor Magenta
    $response = Invoke-WebRequest -Uri $url -Method Get -UseBasicParsing -ErrorAction Stop
    Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Content-Type: $($response.Headers['Content-Type'])" -ForegroundColor Green
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "StatusCode: $($_.Exception.Response.StatusCode.Value__)" -ForegroundColor Red
}

Write-Host "`n" 
Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "1. Make sure the backend is running (mvnw spring-boot:run)" -ForegroundColor White
Write-Host "2. Get a valid JWT token from Keycloak login" -ForegroundColor White
Write-Host "3. Use this command with token:" -ForegroundColor White
Write-Host "   `$headers = @{'Authorization' = 'Bearer YOUR_TOKEN_HERE'}" -ForegroundColor Gray
Write-Host "   Invoke-WebRequest -Uri '$url' -Headers `$headers -OutFile 'downloaded.pdf'" -ForegroundColor Gray
