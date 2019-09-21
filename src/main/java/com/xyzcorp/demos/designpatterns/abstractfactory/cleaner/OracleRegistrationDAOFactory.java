package com.xyzcorp.demos.designpatterns.abstractfactory.cleaner;


/**
 * User: Daniel Hinojosa (dhinojosa@evolutionnext.com)
 * Date: 5/29/12
 * Time: 9:53 PM
 */
public class OracleRegistrationDAOFactory extends RegistrationDAOFactory {

   public RegistrationDAO create() {
      return new OracleRegistrationDAO();
   }
}
