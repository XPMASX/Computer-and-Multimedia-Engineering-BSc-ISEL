::[Earth] Mostre o conteúdo da diretoria África. (Nota: só saia da diretoria corrente quando for assim
::mencionado). Altere a diretoria corrente para a subdiretoria Europa. [Earth\Europa] Mostre o
::conteúdo da diretoria Canadá. Altere a diretoria corrente para t:\ (use sempre um path relativo).

@echo off

dir África
cd Europa
dir "..\América do norte\Canadá"
cd ..\..\