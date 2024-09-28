::[\] Mostre todo o conteúdo de Earth em termos de subdiretorias e ficheiros e para todos os níveis de
::profundidade. Repita mas mostrando apenas as subdiretorias. Mostre todo o conteúdo de Earth em
::termos de subdiretorias e para todos os níveis de profundidade, mas com visualização em árvore
::(tree). Repita mas mostrando também os ficheiros existentes.

@echo off

dir Earth /S
dir /S /AD
tree Earth
tree Earth /f
