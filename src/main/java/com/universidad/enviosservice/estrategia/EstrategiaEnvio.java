package com.universidad.enviosservice.estrategia;

/**
 * Contrato comun de las formulas de costo por metodo de envio (patron Strategy).
 * Cada implementacion encapsula unicamente la tarifa y los recargos propios de
 * un metodo; las reglas transversales (fragilidad, tipo de cliente) viven en
 * el orquestador ServicioCalculoEnvio.
 */
public interface EstrategiaEnvio {

    /**
     * @param pesoKg      peso del paquete en kilogramos
     * @param distanciaKm distancia del envio en kilometros
     * @return costo base del envio, sin ajustes por fragilidad ni tipo de cliente
     */
    double calcular(double pesoKg, double distanciaKm);
}
