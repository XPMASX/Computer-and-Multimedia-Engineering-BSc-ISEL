::[\] Crie o continente Ásia, nele crie o país China. Em China crie o ficheiro com a cidade de Beijing com
::uma população de 22000000, por redirecção de output com: echo xxxx > file (xxxx é o valor da
::população). Altere a diretoria corrente para a China.

@echo off

md Earth\Ásia\China
echo 22000000>Earth\Ásia\China\Beijing.txt
cd Earth\Ásia\China