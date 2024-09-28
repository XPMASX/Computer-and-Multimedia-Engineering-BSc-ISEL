::[Earth\ Oceânia \Nova Zelândia] Corrija o nome de Melbourne (use REN). Corrija o nome de Brazil para
::Brasil. Altere a diretoria corrente para Earth. [Earth] Copie todo o México para a América do Norte
::com um único comando e de modo a não perguntar nada ao utilizador. Nota: coloque México como
::destino e como diretoria. Apague todo o México que ainda está na América do Sul, num único
::comando. Mude a diretoria corrente para cmds em T:\ (sempre com paths relativas).

@echo off

ren ..\Austrália\Melbourni.txt Melbourne.txt
ren "..\..\América do sul\Brazil" Brasil
cd ..\..\
xcopy "América do sul\México" "América do Norte\México" /e /i /s
del "América do sul\México\México.txt" & del "América do sul\México\Guadalajara.txt" & rd "América do sul\México"
cd ..\cmds
