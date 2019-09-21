package com.xyzcorp.demos.designpatterns.abstractfactory.classic;

/**
 * User: Daniel Hinojosa (dhinojosa@evolutionnext.com)
 * Date: 5/29/12
 * Time: 5:56 PM
 */
public abstract class RegistrationDAOAbstractFactory {


    public static RegistrationDAOFactory createRegistrationDAO(DAOType daoType) {
         if (daoType == DAOType.MYSQL) {
             return new MySQLRegistrationDAOFactory();
         } else {
             return new OracleRegistrationDAOFactory();
         }
    }
}
