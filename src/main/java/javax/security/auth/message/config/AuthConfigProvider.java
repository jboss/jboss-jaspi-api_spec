package javax.security.auth.message.config;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;

//$Id$

/**
 *  <p>This interface is implemented by objects that can be used to obtain authentication 
 *  context configuration objects,that is, ClientAuthConfig or ServerAuthConfig objects.</p>
 *
 *  <p>Authentication context configuration objects serve as sources of the authentication
 *  context objects, that is, ClientAuthContext or ServerAuthContext objects, for a specific message layer and messaging context.</p>
 *
 *  <p>Authentication context objects encapsulate the initialization, configuration, and invocation of authentication
 *  modules, that is, ClientAuthModule or ServerAuthModule objects, for a specific message exchange
 *  within a specific message layer and messaging context.</p>
 *
 *  <p>Callers do not directly operate on authentication modules. Instead, they rely on a ClientAuthContext or
 *  ServerAuthContext to manage the invocation of modules. A caller obtains an instance of ClientAuthContext or
 *  ServerAuthContext by calling the respective getAuthContext method on a ClientAuthConfig or
 *  ServerAuthConfig object obtained from an AuthConfigProvider.</p>
 *
 *  <p> The following represents a typical sequence of calls for obtaining a client 
 *  authentication context object, and then using it to secure a request.</p>
 *  <ol>
 *      <li>AuthConfigProvider provider;</li>
 *      <li>ClientAuthConfig config = provider.getClientAuthConfig(layer,appID,cbh);</li>
 *      <li>String authContextID = config.getAuthContextID(messageInfo);</li>
 *      <li>ClientAuthContext context = config.getAuthContext(authContextID,subject,properties);</li>
 *      <li>context.secureRequest(messageInfo,subject);</li>
 *  </ol>
 *
 *  <p>Every implementation of this interface must offer a public, two argument constructor with the following signature:</p>
 *
 *  <pre>public AuthConfigProviderImpl(Map properties, AuthConfigFactory factory);</pre>
 *
 *  <p>where the properties argument may be null, and where all values and keys occurring in a non-null properties
 *  argument must be of type String. When the factory argument is not null, it indicates that the provider is to self-register
 *  at the factory by calling the following method on the factory:</p>
 *
 *  <pre>public String
 *  registerConfigProvider(AuthConfigProvider provider, String layer, String appContext, String description);</pre>
 *
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)</a> 
 *  @since  May 12, 2006 
 *  @version $Revision$
 *  @see ClientAuthContext, ServerAuthContext, AuthConfigFactory
 */
public interface AuthConfigProvider
{
   /**
    * <p>Get an instance of ClientAuthConfig from this provider.</p>
    *
    * <p>The implementation of this method returns a ClientAuthConfig instance that
    * describes the configuration of ClientAuthModules at a given message layer, and 
    * for use in an identified application context.</p>
    * 
    * @param layer      A String identifying the message layer for the returned ClientAuthConfig object. This
    *                   argument must not be null.
    * @param appContext A String that identifies the messaging context for the returned ClientAuthConfig
    *                   object. This argument must not be null.
    * @param handler    A CallbackHandler to be passed to the ClientAuthModules encapsulated by
    *                   ClientAuthContext objects derived from the returned ClientAuthConfig. This argument may be null, in
    *                   which case the implementation may assign a default handler to the configuration. The CallbackHandler
    *                   assigned to the configuration must support the Callback objects required to be supported by the profile
    *                   of this specification being followed by the messaging runtime. The CallbackHandler instance must be
    *                   initialized with any application context needed to process the required callbacks on behalf of the
    *                   corresponding application.
    * @return           A ClientAuthConfig Object that describes the configuration of ClientAuthModules at the
    *                   message layer and messaging context identified by the layer and appContext arguments. This method
    *                   does not return null.
    *
    * @throws AuthException If this factory does not support the assignment of a default CallbackHandler to the returned ClientAuthConfig.
    * @throws SecurityException If the caller does not have permission to retrieve the configuration.
    */
   ClientAuthConfig getClientAuthConfig( String layer, String appContext, 
         CallbackHandler handler) throws AuthException, SecurityException;
   
   /**
    * <p>Get an instance of ServerAuthConfig from this provider.</p>
    *
    * <p>The implementation of this method returns a ServerAuthConfig instance that
    * describes the configuration of ServerAuthModules at a given message layer, and 
    * for use in an identified application context.</p>
    * 
    * @param layer A String identifying the message layer for the returned ServerAuthConfig object. This
    *              argument must not be null.
    * @param appContext A String that identifies the messaging context for the returned ServerAuthConfig
    *                   object. This argument must not be null.
    * @param handler A CallbackHandler to be passed to the ServerAuthModules encapsulated by
    *                ServerAuthContext objects derived from the returned ServerAuthConfig. This argument may be null,
    *                in which case the implementation may assign a default handler to the configuration. The
    *                CallbackHandler assigned to the configuration must support the Callback objects required to be
    *                supported by the profile of this specification being followed by the messaging runtime. The
    *                CallbackHandler instance must be initialized with any application context needed to process the
    *                required callbacks on behalf of the corresponding application.
    *
    * @return A ServerAuthConfig Object that describes the configuration of ServerAuthModules at a given
    *         message layer, and for a particular application context. This method does not return null.
    *
    * @throws AuthException If this provider does not support the assignment of a default CallbackHandler
    *                       to the returned ServerAuthConfig.
    * @throws SecurityException If the caller does not have permission to retrieve the
    *                           configuration.
    */
   ServerAuthConfig getServerAuthConfig( String layer, String appContext,
         CallbackHandler handler) throws AuthException, SecurityException;
   
   /**
    * <p>Causes a dynamic configuration provider to update its internal state such that any resulting change to its
    * state is reflected in the corresponding authentication context configuration objects previously created by the
    * provider within the current process context.</p>
    *
    * @throws SecurityException If the caller does not have permission to refresh the provider,
    *                           or if an error occurred during the refresh.
    */
   void refresh();
}
