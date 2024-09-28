::[Earth\Oceânia] Altere o nome de Australia para Austrália. Copie o ficheiro de Sidney a diretoria
::Austrália. Apague o ficheiro de Sidney na Oceania. Altere a diretoria corrente para a Nova Zelândia.
::[Earth\Oceânia\Nova Zelândia] Mova (sem fazer cópia) Wellington para a Nova Zelândia – indique com
::destino apenas “.” (sem aspas).

@echo off

ren Australia Austrália
copy Sidney.txt Austrália\Sidney.txt
del Sidney.txt
cd Nova Zelândia
move ..\Wellington.txt .
