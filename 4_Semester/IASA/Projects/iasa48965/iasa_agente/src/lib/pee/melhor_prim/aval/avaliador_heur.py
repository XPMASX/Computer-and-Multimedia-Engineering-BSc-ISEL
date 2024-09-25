from abc import ABC

from pee.mec_proc.fronteira.avaliador import Avaliador


class AvaliadorHeur(Avaliador, ABC):

    def definir_heuristica(self, heuristica):
        """
        Visa guardar a heuristica recebida

        Args:
            heuristica: heuristica a utilizar na avaliação

        """

        self._heuristica = heuristica
