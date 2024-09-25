from controlo_delib.controlo_delib import ControloDelib
from plan.plan_pdm.planeador_pdm import PlaneadorPDM
from sae import Simulador

"""
Criamos uma instancia de controlo deliberativo passando um planeador PDM
"""

controlo = ControloDelib(PlaneadorPDM())
#Simulador(3, controlo).executar()

"Não funciona com a gama dada pelo docente devido a esta ser demasiado baixa, ou seja, " \
"o agente não tem visão suficiente"

Simulador(4, controlo).executar()
