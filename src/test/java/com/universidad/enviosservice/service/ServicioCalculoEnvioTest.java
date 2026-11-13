package com.universidad.enviosservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ServicioCalculoEnvioTest {

    private final ServicioCalculoEnvio servicio = new ServicioCalculoEnvio();

    // --- Suite inicial de la Parte 1 (se conserva sin cambios) ---

    @Test
    void calculaEnvioEstandarPesoBajo() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "REGULAR", false);
        assertEquals(50.0, costo, 0.01);
    }

    @Test
    void aplicaDescuentoClientePremium() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "PREMIUM", false);
        assertEquals(45.0, costo, 0.01);
    }

    // --- Ramas que la Parte 1 dejo sin cubrir: reglas transversales ---

    @Test
    void aplicaRecargoDeFragilidadDel15PorCiento() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "REGULAR", true);
        assertEquals(57.5, costo, 0.01);
    }

    @Test
    void aplicaDescuentoClienteCorporativo() {
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "CORPORATIVO", false);
        assertEquals(42.5, costo, 0.01);
    }

    @Test
    void combinaFragilidadYDescuentoPremiumEnEseOrden() {
        // 50 * 1.15 = 57.5 ; 57.5 * 0.9 = 51.75
        double costo = servicio.calcularCosto("ESTANDAR", 3.0, 100.0, "PREMIUM", true);
        assertEquals(51.75, costo, 0.01);
    }

    @Test
    void clienteRegularNoRecibeDescuento() {
        double costo = servicio.calcularCosto("EXPRESS", 3.0, 100.0, "REGULAR", false);
        assertEquals(156.0, costo, 0.01);
    }

    // --- Ramas que la Parte 1 dejo sin cubrir: despacho a cada estrategia ---

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
        "ESTANDAR, 52.4",
        "EXPRESS, 161.85",
        "OVERNIGHT, 227.5",
        "INTERNACIONAL, 239.0"
    })
    void despachaCadaMetodoDeEnvioASuEstrategia(String metodo, double esperado) {
        double costo = servicio.calcularCosto(metodo, 8.0, 100.0, "REGULAR", false);
        assertEquals(esperado, costo, 0.01);
    }

    @Test
    void combinaEstrategiaFragilidadYDescuentoCorporativo() {
        // OVERNIGHT 8 kg / 100 km = 227.5 ; *1.15 = 261.625 ; *0.85 = 222.38
        double costo = servicio.calcularCosto("OVERNIGHT", 8.0, 100.0, "CORPORATIVO", true);
        assertEquals(222.38, costo, 0.01);
    }

    // --- Errores ---

    @Test
    void rechazaMetodoDeEnvioNoSoportado() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
            () -> servicio.calcularCosto("DRON", 3.0, 100.0, "REGULAR", false));
        assertEquals("Metodo de envio no soportado: DRON", ex.getMessage());
    }

    @Test
    void propagaLaRestriccionDeDistanciaDeOvernight() {
        assertThrows(IllegalArgumentException.class,
            () -> servicio.calcularCosto("OVERNIGHT", 3.0, 301.0, "REGULAR", false));
    }
}
