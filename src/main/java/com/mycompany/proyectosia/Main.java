
package com.mycompany.proyectosia;

import com.mycompany.proyectosia.controlador.SistemaAsistenciaEscolar;
import java.io.IOException;

/**
 *
 * @author geoff
 */
public class Main {
    public static void main(String arg [])throws IOException{
        SistemaAsistenciaEscolar sistema = new SistemaAsistenciaEscolar();
        sistema.iniciar();
    }
}
