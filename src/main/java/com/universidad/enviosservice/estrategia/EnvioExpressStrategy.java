package com.universidad.enviosservice.estrategia;

public class EnvioExpressStrategy implements EstrategiaEnvio {

    private static final TarifaPorTramos TARIFA = new TarifaPorTramos(1.2, 0, 1.5, 2.0);

    @Override
    public double calcular(double pesoKg, double distanciaKm) {
        double costo = TARIFA.costo(pesoKg, distanciaKm);
        if (distanciaKm > 500) {
            costo += 25;
        }
        // Recargo propio de EXPRESS (prioridad), aplicado antes de los ajustes transversales
        return costo * 1.3;
    }
}
