package javax.security.auth.message.config;

import javax.security.auth.message.AuthException;
import javax.security.auth.message.MessageInfo;

//$Id$

/**
 *  <p>This interface defines the common functionality implemented by 
 *  Authentication context configuration objects.</p>
 *  
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)</a> 
 *  @since  May 12, 2006 
 *  @version $Revision$
 */
public interface AuthConfig
{
   /**
    * Get the application context identifier of this authentication context 
    * configuration object.
    * @return the String identifying the application context of this configuration 
    *         object or null if the configuration object pertains to an unspecified 
    *         application context.
    */
   String getAppContext();
   
   String getAuthContextID(MessageInfo messageInfo);
   
   /**
    * Get the message layer name of this authentication context configuration object.
    * @return the message layer name of this configuration object, or null if the 
    *         configuration object pertains to an unspecified message layer.
    */
   String getMessageLayer();
   
   boolean isProtected();
   
   /**
    * Causes a dynamic anthentication context configuration object to update its internal 
    * state and such that any change to its state is recognized by any authentication 
    * context objects that were previously obtained from the config object.
    * @throws AuthException if an error occured during the update.
    * @throws SecurityException if the caller does not have permission to refresh 
    *                           the configuration object.
    */
   void refresh();
}
