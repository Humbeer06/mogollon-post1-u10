package com.universidad.enviosservice.estrategia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EnvioExpressStrategyTest {

    private final EnvioExpressStrategy estrategia = new EnvioExpressStrategy();

    @ParameterizedTest(name = "peso {0} kg, {1} km -> {2}")
    @CsvSource({
        "3.0, 100.0, 156.0",    // tramo 1, incluye el recargo x1.3 propio de EXPRESS
        "5.0, 100.0, 156.0",    // limite exacto del tramo 1
        "8.0, 100.0, 161.85",   // tramo 2
        "20.0, 100.0, 185.25",  // limite exacto del tramo 2
        "25.0, 100.0, 198.25"   // tramo 3
    })
    void calculaCostoPorTramoDePeso(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @ParameterizedTest(name = "{0} km -> {1}")
    @CsvSource({
        "500.0, 780.0",   // sin recargo por distancia en el limite
        "600.0, 968.5"    // con recargo de 25 antes del x1.3
    })
    void aplicaRecargoPorDistanciaAntesDelMultiplicador(double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(3.0, distanciaKm), 0.01);
    }
}
