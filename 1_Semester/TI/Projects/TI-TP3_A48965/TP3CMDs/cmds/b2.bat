::[CMDLine\TP3CMD\cmds] Crie a drive mapeada T: que deverá referenciar CMDLine\TP3CMD. Caso já
::tenha essa drive letter ocupada utilize outra, mas tenha em conta que este enunciado tem em conta
::a drive T:. Mude para a drive T:. Altere a diretoria corrente para cmds com "cd T:\cmds". Aqui deve
::utilizar um path absoluto. Altere o Code Page do CMD para Windows-1252. Mostre o conteúdo da
::diretoria corrente (use dir). Adicione a diretoria corrente à path com “set path=%CD%;%path%”. Desta
::forma, pode-se executar, a partir de qualquer localização, os batch files que estão cmds. Cada vez que
::abrir um cmd (por ex.º com o b1.bat) deve executar este batch file (ignore o erro ao tentar criar a
::drive T:, pois esta já estará criada).

@echo off

cd ..\ 
Subst T: %CD%
T: & cd cmds
chcp 1252
dir
set path=%CD%;%path%