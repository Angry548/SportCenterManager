package com.esfe.sistemagimnasio.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String manejarErrorIntegridad(
            DataIntegrityViolationException exception,
            Model model) {

        String causa = "";

        if (exception.getMostSpecificCause() != null) {
            causa = exception
                    .getMostSpecificCause()
                    .getMessage();
        }

        /*
         * Caso típico:
         * se intenta eliminar un registro utilizado
         * por otra tabla mediante una FOREIGN KEY.
         */
        if (causa.contains("REFERENCE constraint") ||
                causa.contains("FOREIGN KEY")) {

            model.addAttribute(
                    "titulo",
                    "No se puede eliminar el registro"
            );

            model.addAttribute(
                    "mensaje",
                    "Este registro está relacionado con otros datos del sistema."
            );

            model.addAttribute(
                    "detalle",
                    "Para eliminarlo, primero debes eliminar o reasignar los registros que dependen de él."
            );

        } else {

            model.addAttribute(
                    "titulo",
                    "No se pudo completar la operación"
            );

            model.addAttribute(
                    "mensaje",
                    "La operación viola una restricción de integridad de la base de datos."
            );

            model.addAttribute(
                    "detalle",
                    "Verifica los datos relacionados antes de intentarlo nuevamente."
            );
        }

        return "error/registro-relacionado";
    }
}