::[Earth\Ásia\China] Crie o ficheiro com a cidade de Shanghai com uma população de 24000000 por
::redirecção de output, com: echo xxxx > file. Mostre o conteúdo de Shanghai.txt (que deve ter 24
::milhões em valor numérico) (use type). Crie o país Perú na América do Sul, com as cidades de Lima
::(com 9800000 de pop.) e Arequipa (com 900000 …). (Nota: não altere a diretoria corrente sem ser
::solicitado). Altere o nome da diretoria de Oceania para Oceânia. Altere a diretoria corrente para a
::Oceânia. Nota: caso obtenha Access Denied, sem ter nenhum ficheiro aberto dentro de Oceania, feche
::algum Explorer que tenha aberto com essa diretoria visível

@echo off

echo 24000000>Shangai.txt
type Shangai.txt
md "..\..\América do Sul\Perú"
echo 9800000>"..\..\América do sul\Perú\Lima.txt" & echo 900000>"..\..\América do sul\Perú\Arequipa.txt"
ren "..\..\Oceania" Oceânia
cd "..\..\Oceânia"
