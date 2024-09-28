::[cmds] Para todas as cidades existentes dentro de Earth, mostre cada uma numa linha com: o
::continente, o país, a cidade e a sua população, tudo separado por um caractere ‘-‘ (sinal de menos).
::Deverá produzir linhas do género: África-Angola-Huambo-12000000. Utilize um FOR para os
::continentes, outro para os países, e outro para as cidades. Para as cidades utilize um FOR /F para obter
::a sua população. Dado que há cidades com espaços no seu nome é necessário indicar para usar nomes
::com aspas (colocar nas opções “tokens=1 usebackq”) e colocar o nome entre aspas: … in (“%%x”), com
::x o nome da cidade.

@echo off

setlocal
setlocal EnableDelayedExpansion

cd ../Earth


for /d %%i in (*) do (
	for /d %%j in ("%%i"\*) do (
		for %%k in ("%%j"\*) do (
			for /f "tokens=1 usebackq" %%y in ("%%k") do echo %%k - %%y
			
		)
	)
)

endlocal

