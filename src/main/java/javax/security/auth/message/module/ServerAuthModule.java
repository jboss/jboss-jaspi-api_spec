package javax.security.auth.message.module;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.ServerAuth;
import java.util.Map;

//$Id$

/**
 *  <p>A ServerAuthModule validates client requests and secures responses 
 *  back to the client.</p>
 *
 *  <p>A module implementation should assume it may be used to secure different 
 *  requests as different clients. A module should also assume it may be used 
 *  concurrently by multiple callers. It is the module implementation's responsibility 
 *  to properly save and restore any state as necessary. A module that does not need 
 *  to do so may remain completely stateless.</p>
 *
 *  <p>Every implementation of the interface must provide a public zero argument constructor.</p>
 *  
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)</a> 
 *  @since  May 12, 2006 
 *  @version $Revision$
 *  @see javax.security.auth.message.config.ServerAuthContext
 */
public interface ServerAuthModule extends ServerAuth 
{
   /**
    * <p>Get the one or more Class objects representing the message
    * types supported by the module.</p>
    * @return An array of Class objects, with at least one element defining a message type supported by the
    *         module.
    */
   public java.lang.Class[] getSupportedMessageTypes();
   
   /**
    * <p>Initialize this module with request and response message policies to 
    *    enforce, a CallbackHandler, and any module-specific configuration 
    *    properties.</p>
    *
    * <p>The request policy and the response policy must not both be null.</p>
    * 
    * @param requestPolicy The request policy this module must enforce, or null.
    * @param responsePolicy The response policy this module must enforce, or null.
    * @param handler CallbackHandler used to request information.
    * @param options A Map of module-specific configuration properties.
    * @throws AuthException - If module initialization fails, including for the case
    *                         where the options argument contains elements that are 
    *                         not supported by the module.
    */
   public void initialize(MessagePolicy requestPolicy, MessagePolicy responsePolicy,
         CallbackHandler handler, Map options) throws AuthException;
}
