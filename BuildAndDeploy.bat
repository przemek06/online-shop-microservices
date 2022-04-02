for /d %%x in (%CD%\*) do (
    CD %%x
    call mvn clean package -Dmaven.test.skip
    CD ..
)
call docker-compose up -d
pause