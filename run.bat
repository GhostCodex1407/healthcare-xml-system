@echo off
title Healthcare XML System Runner
cd /d "C:\Users\Ookeditse Moekhwa\.gemini\antigravity\scratch\healthcare-xml-system"
echo ===============================================================================
echo   Running Healthcare XML System Pipeline
echo ===============================================================================
"C:\Program Files\Java\jdk-27\bin\java.exe" -jar "target\healthcare-xml-system-1.0.0.jar" %*
pause
