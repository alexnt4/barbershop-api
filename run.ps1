# Script para ejecutar el proyecto con Java 21
# Ajusta la ruta segun donde instalaste Java 21

# Parametro para seleccionar la accion
param(
    [string]$action = "help"
)

$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.9.10-hotspot"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  Barbershop API - Java 21 Environment" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "Usando Java desde: $env:JAVA_HOME" -ForegroundColor Green
java -version
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Cargar variables de entorno desde .env
Write-Host "Cargando variables de entorno desde .env..." -ForegroundColor Cyan
if (Test-Path ".env") {
    Get-Content ".env" | ForEach-Object {
        if ($_ -match '^\s*([^#][^=]+)=(.*)$') {
            $name = $matches[1].Trim()
            $value = $matches[2].Trim()
            [Environment]::SetEnvironmentVariable($name, $value, "Process")
            Write-Host "  [OK] Variable cargada: $name" -ForegroundColor Green
        }
    }
    Write-Host ""
} else {
    Write-Host "  [ADVERTENCIA] Archivo .env no encontrado" -ForegroundColor Yellow
    Write-Host ""
}

switch ($action) {
    "install" {
        Write-Host "Instalando dependencias..." -ForegroundColor Yellow
        .\mvnw.cmd clean install
    }
    "run" {
        Write-Host "Ejecutando la aplicación..." -ForegroundColor Yellow
        .\mvnw.cmd spring-boot:run
    }
    "build" {
        Write-Host "Compilando sin tests..." -ForegroundColor Yellow
        .\mvnw.cmd clean package -DskipTests
    }
    "test" {
        Write-Host "Ejecutando tests..." -ForegroundColor Yellow
        .\mvnw.cmd test
    }
    default {
        Write-Host 'Uso: .\run.ps1 -action <comando>' -ForegroundColor Yellow
        Write-Host ''
        Write-Host 'Comandos disponibles:' -ForegroundColor Cyan
        Write-Host '  install  - Instalar dependencias (mvnw clean install)' -ForegroundColor White
        Write-Host '  run      - Ejecutar la aplicación (mvnw spring-boot:run)' -ForegroundColor White
        Write-Host '  build    - Compilar sin tests (mvnw clean package -DskipTests)' -ForegroundColor White
        Write-Host '  test     - Ejecutar tests (mvnw test)' -ForegroundColor White
        Write-Host ''
        Write-Host 'Ejemplo: .\run.ps1 -action run' -ForegroundColor Green
    }
}
