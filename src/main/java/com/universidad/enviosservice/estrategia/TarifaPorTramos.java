package com.universidad.enviosservice.estrategia;

/** Tarifa lineal por tramos de peso, compartida por las estrategias de envio. */
record TarifaPorTramos(double porKm, double cargoFijo, double tarifaTramo2, double tarifaTramo3) {

    double costo(double pesoKg, double distanciaKm) {
        double base = distanciaKm * porKm + cargoFijo;
        if (pesoKg <= 5) {
            return base;
        }
        if (pesoKg <= 20) {
            return base + (pesoKg - 5) * tarifaTramo2;
        }
        return base + 15 * tarifaTramo2 + (pesoKg - 20) * tarifaTramo3;
    }
}
