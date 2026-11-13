package com.universidad.enviosservice.estrategia;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EnvioEstandarStrategyTest {

    private final EnvioEstandarStrategy estrategia = new EnvioEstandarStrategy();

    @ParameterizedTest(name = "peso {0} kg, {1} km -> {2}")
    @CsvSource({
        "3.0, 100.0, 50.0",    // tramo 1: pesoKg <= 5
        "5.0, 100.0, 50.0",    // limite exacto del tramo 1 (<= 5 incluye el 5)
        "8.0, 100.0, 52.4",    // tramo 2: 5 < pesoKg <= 20
        "20.0, 100.0, 62.0",   // limite exacto del tramo 2 (<= 20 incluye el 20)
        "25.0, 100.0, 68.0"    // tramo 3: pesoKg > 20
    })
    void calculaCostoPorTramoDePeso(double pesoKg, double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(pesoKg, distanciaKm), 0.01);
    }

    @ParameterizedTest(name = "{0} km -> {1}")
    @CsvSource({
        "500.0, 250.0",   // limite: distanciaKm > 500 es falso en 500, sin recargo
        "501.0, 260.5",   // primer valor con recargo de 10
        "600.0, 310.0"
    })
    void aplicaRecargoSoloPorEncimaDe500Km(double distanciaKm, double esperado) {
        assertEquals(esperado, estrategia.calcular(3.0, distanciaKm), 0.01);
    }
}
