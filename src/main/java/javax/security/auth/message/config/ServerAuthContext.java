package javax.security.auth.message.config;

import javax.security.auth.message.ServerAuth;

/**
 *  <p>This ServerAuthContext class encapsulates ServerAuthModules that are used to 
 *     secure requests made as a client. A caller typically uses this class in the 
 *     following manner:</p>
 *   <ol>
 *   <li>Retrieve an instance of this class via AuthContextFactory.getAuthContext.</li> 
 *   <li>Invoke <i>validateRequest</i>. 
 *    <p>ServerAuthContext implementation invokes encapsulated ServerAuthModule(s). 
 *    Module(s) verify or decrypt response as necessary.</p>
 *    </li>
 *    <li><p>Authentication complete.</p>
 *    <p>Perform authorization check on authenticated identity and, if successful, 
 *    dispatch to requested service application.</p>
 *    </li>
 *    <li>Service Application Finished. </li>
 *    <li>Invoke <i>secureResponse</i>.<p> ServerAuthContext implementation invokes 
 *    encapsulated ServerAuthModule(s). Module(s) secure response (sign and encrypt 
 *    response, for example).</p> 
 *    </li>
 *    <li>Send final response to client.</li>
 *    <li>Invoke <i>disposeSubject</i> method (as necessary) to clean up any authentication 
 *        state in Subject.<p>A ServerAuthContext instance may be used concurrently 
 *        by multiple callers.</p>
 *    </li> 
 *   </ol>
 *   </p>
 *   <p>A ServerAuthContext instance may be used concurrently by multiple callers.</p>
 *   
 *   <p>Implementations of this interface are responsible for constructing and initializing 
 *   the encapsulated modules. The initialization step includes passing the relevant request 
 *   and response MessagePolicy objects to the encapsulated modules. The MessagePolicy objects 
 *   are obtained from the ServerAuthConfig instance that was provided when this ServerAuthContext 
 *   instance was created. 
 *   @See AuthContextFactory#getAuthContext for more information.
 *   </p>
 *   <p>Implementations also have custom logic to determine what modules to invoke, and in 
 *   what order. In addition, this custom logic may control whether subsequent modules are 
 *   invoked based on the success or failure of previously invoked modules.</p>
 *   <p>The caller is responsible for passing in a state Map that can be used by underlying 
 *   modules to save and communicate state across a sequence of calls from secureRequest to 
 *   validateResponse to disposeSubject. The same Map instance must be passed to all methods 
 *   in the call sequence. Furthermore, each call sequence should be passed its own unique 
 *   shared state Map instance.</p>
 *   
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)</a> 
 *  @since  May 12, 2006 
 *  @version $Revision$
 */
public interface ServerAuthContext extends ServerAuth
{
}
