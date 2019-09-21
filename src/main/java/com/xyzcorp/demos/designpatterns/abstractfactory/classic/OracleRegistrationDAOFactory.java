package com.xyzcorp.demos.designpatterns.abstractfactory.classic;

/**
 * User: Daniel Hinojosa (dhinojosa@evolutionnext.com)
 * Date: 5/29/12
 * Time: 9:53 PM
 */
public class OracleRegistrationDAOFactory implements RegistrationDAOFactory {

    public RegistrationDAO create() {
        return new OracleRegistrationDAO();
    }
}
