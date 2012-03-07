package javax.security.auth.message.config;

import javax.security.auth.message.ClientAuth;

//$Id$

/**
 * <p>This ClientAuthContext class encapsulates ClientAuthModules that are used to 
 * secure service requests made by a client, and to validate any responses received 
 * to those requests. A caller typically uses this class in the following manner:<p>
 * <ol>
 * <li>Retrieve an instance of this class via ClientAuthConfig.getAuthContext.</li>
 * <li>Invoke secureRequest.</li>
 * <p> ClientAuthContext implementation invokes secureRequest of encapsulated 
 * ClientAuthModule(s). Module(s) attach credentials to initial request object 
 * (for example, a username and password), and/or secure the request (for example, 
 * sign and encrypt the request).</p>
 * <li>Send request and receive response.</li>
 * <li>Invoke validateResponse.</li>
 * <p>ClientAuthContext implementation invokes validateResponse of encapsulated 
 * ClientAuthModule(s). Module(s) verify or decrypt response as necessary.</p>
 * <li>Invoke cleanSubject method (as necessary) to clean up any authentication 
 * state in Subject.</li>
 * </ol>
 * <p>A ClientAuthContext instance may be used concurrently by multiple callers.</p>
 * <p>Implementations of this interface are responsible for constructing and 
 * initializing the encapsulated modules. The initialization step includes passing 
 * the relevant request and response MessagePolicy objects to the encapsulated modules. 
 * The MessagePolicy objects are obtained by the ClientAuthConfig instance used to obtain 
 * the ClientAuthContext object. See ClientAuthConfig.getAuthContext for more information.</p>
 * <p>Implementations of this interface are instantiated by their associated configuration 
 * object such that they know which modules to invoke, in what order, and how results 
 * returned by preceding modules are to influence subsequent module invocations.</p>
 * <p>Calls to the inherited methods of this interface pass a Map that maybe used by the 
 * underlying modules to save and communicate state across a sequence of calls from 
 * secureRequest to validateResponse to cleanSubject. The same Map instance must be passed 
 * to all methods in a call sequence, and a different Map should be passed to each 
 * call sequence.</p>
 * 
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)</a> 
 *  @since  May 12, 2006 
 *  @version $Revision$
 */
public interface ClientAuthContext extends ClientAuth
{ 
}
