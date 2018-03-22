title mycmd
for /f "tokens=2 delims=," %%a in (
  'tasklist /v /fo csv ^| findstr /i "mycmd"'
) do (
  set "mypid=%%~a"
)
echo %mypid%

pause