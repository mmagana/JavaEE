/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wscalculadora.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;

/**
 *
 * @author palto
 */
@WebService()
@Stateless()
public class WSCalculadora {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "sumar")
    public int sumar(@WebParam(name = "num1")
    int num1, @WebParam(name = "num2")
    int num2) {
        int resultado = num1 + num2;
        return resultado;
    }

}
