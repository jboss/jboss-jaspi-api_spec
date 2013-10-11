package javax.security.auth.message.config;

import javax.security.auth.message.MessageInfo;

//$Id$

/**
 *  <p>This interface defines the common functionality implemented by Authentication context configuration objects.</p>
 *  
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)</a> 
 *  @since  May 12, 2006 
 *  @version $Revision$
 *  @see ClientAuthContext, ServerAuthContext
 */
public interface AuthConfig
{
   /**
    * <p>Get the application context identifier of this authentication context configuration object.</p>
    *
    * @return The String identifying the application context of this configuration
    *         object or null if the configuration object pertains to an unspecified 
    *         application context.
    */
   String getAppContext();

   /**
    * <p>Get the authentication context identifier corresponding to the request and response objects encapsulated in
    * messageInfo.</p>
    *
    * @param messageInfo A contextual Object that encapsulates the client request and server response objects.
    *
    * @return The authentication context identifier corresponding to the encapsulated request and response
    *         objects, or null.
    *
    * @throws IllegalArgumentException If the type of the message objects incorporated in
    *                                  messageInfo are not compatible with the message types supported by this
    *                                  authentication context configuration object.
    */
   String getAuthContextID(MessageInfo messageInfo);
   
   /**
    * <p>Get the message layer name of this authentication context configuration object.</p>
    *
    * @return The message layer name of this configuration object, or null if the
    *         configuration object pertains to an unspecified message layer.
    */
   String getMessageLayer();

   /**
    * <p>Used to determine whether the authentication context configuration object encapsulates any protected
    * authentication contexts.</p>
    *
    * @return True if the configuration object encapsulates at least one protected authentication context.
    *         Otherwise, this method returns false.
    */
   boolean isProtected();
   
   /**
    * <p>Causes a dynamic authentication context configuration object to update the internal state that it uses to
    * process calls to its getAuthContext method.</p>
    *
    * @throws SecurityException If the caller does not have permission to refresh the configuration object,
    *                           or if an error occurred during the update.
    */
   void refresh();
}
