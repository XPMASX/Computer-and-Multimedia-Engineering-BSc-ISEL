from pdm.pdm import PDM
from plan.plan_pdm.modelo.modelo_pdm_plan import ModeloPDMPlan
from plan.plan_pdm.plano_pdm import PlanoPDM
from plan.planeador import Planeador


class PlaneadorPDM(Planeador):

    def __init__(self, gama=0.85, delta_max=1):
        self.__gama = gama
        self.__delta_max = delta_max

    def planear(self, modelo_plan, objetivos):
        """
        1- Criamos instancia do ModeloPDMPlan
        2- Criamos instancia de PDM
        3- Obtemos utilidade e politica atraves do metodo resolver do pdm criado
        4- Se existirem devolvemos instancia de PlanoPDM

        Args:
            modelo_plan, objetivos: Usados para criar instacia de ModeloPDMPlan

        Returns: Devolvemos instancia de PlanoPDM
        """

        if objetivos:
            modelo_pdm_plan = ModeloPDMPlan(modelo_plan, objetivos)
            pdm = PDM(modelo_pdm_plan, self.__gama, self.__delta_max)
            # para o cenario 4 codigo abaixo
            # pdm = PDM(modelo_pdm_plan, 0.95, self.__delta_max)
            utilidade, politica = pdm.resolver()
            if utilidade and politica:
                return PlanoPDM(utilidade, politica)
