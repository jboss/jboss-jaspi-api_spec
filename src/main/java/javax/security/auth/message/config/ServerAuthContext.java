package javax.security.auth.message.config;

import javax.security.auth.message.ServerAuth;

/**
 *  <p>This ServerAuthContext class encapsulates ServerAuthModules that are used to validate service requests
 *  received from clients, and to secure any response returned for those requests. A caller typically uses this class
 *  in the following manner:</p>
 *
 *  <ol>
 *     <li>Retrieve an instance of this class via AuthContextFactory.getAuthContext.</li>
 *     <li>Invoke <i>validateRequest</i>.
 *         <p>ServerAuthContext implementation invokes validateRequest of one or more encapsulated ServerAuthModules.
 *         Modules validate credentials present in request (for example, decrypt and verify a signature).</p>
 *     </li>
 *     <li><p>If credentials valid and sufficient, authentication complete</p>
 *         <p>Perform authorization check on authenticated identity and, if successful,
 *          dispatch to requested service application.</p>
 *      </li>
 *      <li>Service Application Finished. </li>
 *      <li>Invoke <i>secureResponse</i>.
 *          <p>ServerAuthContext implementation invokes secureResponse of one or more encapsulated
 *          ServerAuthModules. Modules secure response (sign and encrypt response, for example), and prepare
 *          response message.</p>
 *      </li>
 *      <li>Send secured response to client.</li>
 *      <li>Invoke cleanSubject (as necessary) to clean up any authentication state in Subject(s).</li>
 *   </ol>
 *
 *   <p>A ServerAuthContext instance may be used concurrently by multiple callers.</p>
 *
 *   <p>Implementations of this interface are responsible for constructing and initializing the encapsulated modules.
 *   The initialization step includes passing the relevant request and response MessagePolicy objects to the
 *   encapsulated modules. The MessagePolicy objects are obtained by the ServerAuthConfig instance used to
 *   obtain the ServerAuthContext object. See ServerAuthConfig.getAuthContext for more information.</p>
 *
 *   <p>Implementations of this interface are instantiated by their associated configuration object such that they know
 *   which modules to invoke, in what order, and how results returned by preceding modules are to influence
 *   subsequent module invocations.</p>
 *
 *   <p>Calls to the inherited methods of this interface delegate to the corresponding methods of the encapsulated
 *   authentication modules.</p>
 *   
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)</a> 
 *  @since  May 12, 2006 
 *  @version $Revision$
 *  @see ServerAuthConfig
 *  @see javax.security.auth.message.module.ServerAuthModule
 */
public interface ServerAuthContext extends ServerAuth
{
}
