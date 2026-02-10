@echo off
echo ========================================
echo   Iniciando Frontend - Gestao Aeronaves
echo ========================================
echo.

echo Verificando se as dependencias estao instaladas...
if not exist "node_modules\" (
    echo.
    echo [AVISO] Dependencias nao encontradas!
    echo Instalando dependencias do npm...
    echo.
    call npm install
    if errorlevel 1 (
        echo.
        echo [ERRO] Falha ao instalar dependencias
        echo.
        pause
        exit /b 1
    )
)

echo.
echo Iniciando servidor de desenvolvimento...
echo.
echo Frontend: http://localhost:5173
echo Backend API: http://localhost:8080
echo.
echo Pressione Ctrl+C para encerrar o servidor
echo ========================================
echo.

call npm run dev

pause
