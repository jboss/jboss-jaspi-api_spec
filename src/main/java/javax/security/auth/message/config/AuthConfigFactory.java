package javax.security.auth.message.config;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.SecurityPermission;
import java.util.Map;

//$Id$

/**
 *  <p>This class is used to obtain AuthConfigProvider objects that can be used to
 *  obtain authentication context configuration objects, i.e., ClientAuthConfig and
 *  ServerAuthConfig objects.</p>
 *
 *  <p>Authentication context configuration objects are used to obtain authentication context objects.
 *  Authentication context objects, that is, ClientAuthContext and ServerAuthContex objects, encapsulate authentication modules.
 *  Authentication modules are pluggable components that perform security-related processing of request and response messages.</p>
 *
 *  <p>Callers do not operate on modules directly. Instead they rely on an authentication
 *  context to manage the invocation of modules. A caller obtains an authentication
 *  context by calling the getAuthContext method on a ClientAuthConfig or ServerAuthConfig
 *  obtained from an AuthConfigProvider.</p>
 *
 *  <p>The following represents a typical sequence of calls for obtaining a client
 *  authentication context, and then using it to secure a request.</p>
 *
 *  <ol>
 *      <li>AuthConfigFactory factory = AuthConfigFactory.getFactory();</li>
 *      <li>AuthConfigProvider provider = factory.getConfigProvider(layer,appID,listener);</li>
 *      <li>ClientAuthConfig config = provider.getClientAuthConfig(layer,appID,cbh)</li>
 *      <li>String authContextID = config.getAuthContextID(messageInfo);</li>
 *      <li>ClientAuthContext context = config.getAuthContext(authContextID,subject,properties);</li>
 *      <li>context.secureRequest(messageInfo,subject);</li>
 *  </ol>
 *
 *  <p>A system-wide AuthConfigFactory implementation can be set by invoking
 *  setFactory, and retrieved via getFactory.</p>
 *
 *  <p>Every implementation of this abstract class must offer a public, zero argument
 *  constructor. This constructor must support the construction and registration (including
 *  self-registration) of AuthConfigProviders from a persistent declarative representation.</p>
 *
 *  <p>For example, a factory implementation class could interpret the contents of a
 *  file containing a sequence of configuration entries, with one entry per
 *  AuthConfigProvider, with each entry representing:</p>
 *
 *  <ul>
 *      <li>The fully qualified name of the provider implementation class (or null)</li>
 *      <li>The list of the provider initialization properties (which could be empty)</li>
 *  </ul>
 *
 *  <p>Any provider initialization properties must be specified in a form that can be passed to the provider constructor
 *  within a Map of key, value pairs, and where all keys and values within the Map are of type String.</p>
 *
 *  <p>The entry syntax must also provide for the optional inclusion of information sufficient to define a
 *  RegistrationContext. This information would only be present when the factory will register the provider. For
 *  example, each entry could provide for the inclusion of one or more RegistrationContext objects of the following
 *  form:</p>
 *
 *  <ul>
 *      <li>The message layer name (or null)</li>
 *      <li>The application context identifier (or null)</li>
 *      <li>The registration description (or null)</li>
 *  </ul>
 *
 *  <p>When a RegistrationContext is not included, the factory must make it convenient for the provider to self-register
 *  with the factory during the provider construction (see registerConfigProvider(AuthConfigProvider provider, ...)).</p>
 *
 *  <p>An AuthConfigFactory implementation is free to choose is own persistent declarative syntax as long as it
 *  conforms to the requirements defined by this class.</p>
 *
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)</a>
 *  @since  May 12, 2006
 *  @version $Revision$
 *  @see ClientAuthContext
 *  @see ServerAuthContext
 *  @see ClientAuthConfig
 *  @see ServerAuthConfig
 *  @see java.util.Properties
 */
public abstract class AuthConfigFactory
{
   private static AuthConfigFactory _factory = null;

   /* The name of the Security property used to define the default AuthConfigFactory implementation class  */
   public static final String DEFAULT_FACTORY_SECURITY_PROPERTY = "authconfigprovider.factory";

   /* The name of the SecurityPermission required to call getFactory  */
   public static final String GET_FACTORY_PERMISSION_NAME = ("getProperty."+DEFAULT_FACTORY_SECURITY_PROPERTY);

   /* The name of the SecurityPermission required to call setFactory  */
   public static final String SET_FACTORY_PERMISSION_NAME = ("setProperty."+DEFAULT_FACTORY_SECURITY_PROPERTY);

    /* The name of the Security property used to control operations within the factory  */
    private static final String PROVIDER_SECURITY_PROPERTY = "authconfigfactory.provider";

    /* The name of the SecurityPermission to be used to authorize access to the update methods of the factory implementation class  */
   public static final String PROVIDER_REGISTRATION_PERMISSION_NAME = ("setProperty."+PROVIDER_SECURITY_PROPERTY);

   /* The SecurityPermission, with name {@link #GET_FACTORY_PERMISSION_NAME}, that is used to authorize access to the getFactory method  */
   public static final SecurityPermission getFactorySecurityPermission = new SecurityPermission(GET_FACTORY_PERMISSION_NAME);

   /* The SecurityPermission, with name {@link #SET_FACTORY_PERMISSION_NAME}, that is used to authorize access to the setFactory method  */
   public static final SecurityPermission setFactorySecurityPermission = new SecurityPermission(SET_FACTORY_PERMISSION_NAME);

   /* An instance of the SecurityPermission (with name {@link #PROVIDER_REGISTRATION_PERMISSION_NAME}) for use in authorizing access
    * to the update methods of the factory implementation class  */
   public static final SecurityPermission providerRegistrationSecurityPermission = new SecurityPermission(PROVIDER_REGISTRATION_PERMISSION_NAME);

   /* The default AuthConfigFactory implementation  */
   private static final String FACTORY_IMPL = "org.jboss.security.auth.message.config.JBossAuthConfigFactory";

   public AuthConfigFactory()
   {
   }

   /**
    * <p>Disassociate the listener from all the provider registrations whose layer and
    * appContext values are matched by the corresponding arguments to this method.</p>
    *
    * <p>Factories should periodically notify Listeners to effectively detach listeners that are no longer in use.</p>
    *
    * <p>When a SecurityManager is enabled, and before making any changes to the factory, this method must
    * confirm that the calling access control context has been granted the providerRegistrationSecurityPermission.</p>
    *
    * @param listener The RegistrationListener to be detached.
    * @param layer A String identifying the message layer or null.
    * @param appContext A String value identifying the application context or null.
    * @return An array of String values where each value identifies a provider registration
    *         from which the listener was removed. This method never returns null; it returns
    *         an empty array if the listener was not removed from any registrations.
    */
   public abstract String[] detachListener(RegistrationListener  listener, String layer,
         String appContext);

   /**
    * <p>Get a registered AuthConfigProvider from the factory. Get the provider of
    * ServerAuthConfig and ClientAuthConfig objects registered for the identified
    * message layer and application context.</p>
    *
    * <p>All factories shall employ the following precedence rules to select the registered AuthConfigProvider that
    * matches the layer and appContext arguments:</p>
    *
    * <ul>
    *   <li>The provider specifically registered for the values passed as the layer and appContext arguments shall
    *   be selected.</li>
    *   <li>If no provider is selected according to the preceding rule, the provider specifically registered for the
    *   value passed as the appContext argument and for all (that is, null) layers shall be selected.</li>
    *   <li> If no provider is selected according to the preceding rules, the provider specifically registered for the
    *   value passed as the layer argument and for all (that is, null) appContexts shall be selected.</li>
    *   <li>If no provider is selected according to the preceding rules, the provider registered for all (that is, null)
    *   layers and for all (that is, null) appContexts shall be selected.</li>
    *   <li>If no provider is selected according to the preceding rules, the factory shall terminate its search for a
    *   registered provider.</li>
    * </ul>
    *
    * <p>The above precedence rules apply equivalently to registrations created with a null or non-null className
    * argument.</p>
    *
    * @param layer A String identifying the message layer for which the registered AuthConfigProvider is to be returned.
    *              This argument may be null.
    * @param appContext A String that identifies the application messaging context for which the registered
    *                   AuthConfigProvider is to be returned. The value of this argument may be null.
    * @param listener the RegistrationListener whose notify method is to be invoked
    *             if the corresponding registration is unregistered or replaced. The
    *             value of this argument may be null.
    *
    * @return The implementation of the AuthConfigProvider interface registered at the factory for the layer and
    *         appContext or null, if no AuthConfigProvider is selected.An argument listener is attached even if the return value is null.
    */
   public abstract AuthConfigProvider  getConfigProvider( String layer,
         String appContext, RegistrationListener listener);

   /**
    * <p>Get the system-wide AuthConfigFactory implementation.</p>
    *
    * <p>If a non-null system-wide factory instance is defined at the time of the call,
    * for example, with setFactory, it will be returned. Otherwise, an attempt will be made to
    * construct an instance of the default AuthConfigFactory implementation class. The
    * fully qualified class name of the default factory implementation class is obtained
    * from the value of the DEFAULT_FACTORY_SECURITY_PROPERTY security property.  When an
    * instance of the default factory implementation class is successfully constructed by
    * this method, this method will set it as the system-wide factory instance.</p>
    *
    * <p>The absolute pathname of the Java security properties file is JAVA_HOME/lib/security/java.security, where
    * JAVA_HOME refers to the directory where the JDK was installed. </p>
    *
    * <p>When a SecurityManager is enabled, the getFactorySecurityPermission will be required to
    * call this method. If at the time of the call, a system-wide factory instance has not already been defined, then
    * the setFactorySecurityPermission will also be required.</p>
    *
    * @return The non-null system-wide AuthConfigFactory instance set at the time of the
    *         call, or if that value was null, the value of the system-wide factory
    *         instance established by this method. This method returns null when the
    *         system-wide factory was not defined when this method was called and no
    *         default factory name was defined via the security property.
    *
    * @throws SecurityException If the caller does not have permission to retrieve the factory,
    *                           or set it as the system-wide instance. Also thrown if an exception was thrown during the class loading,
    *                           or construction of the default AuthConfigFactory implementation class; in which case the
    *                           SecurityException will contain the root Exception as its cause.
    */
   public static AuthConfigFactory getFactory()
   {
      //Validate the caller permission
      SecurityManager sm = System.getSecurityManager();
      if (sm != null)
         sm.checkPermission(getFactorySecurityPermission);

      if (_factory == null)
      {
          if (sm != null)
              sm.checkPermission(setFactorySecurityPermission);

         String factoryName = null;
         Class clazz = null;
         try
         {
            LoadAction action = new LoadAction();
            try
            {
               clazz = (Class) AccessController.doPrivileged(action);
               factoryName = action.getName();
            }
            catch (PrivilegedActionException ex)
            {
               factoryName = action.getName();
               Exception e = ex.getException();
               if (e instanceof ClassNotFoundException)
                  throw (ClassNotFoundException) e;
               else
                  throw new IllegalStateException("Failure during load of class: " + action.getName() + e);
            }
            _factory = (AuthConfigFactory) clazz.newInstance();
         }
         catch (ClassNotFoundException e)
         {
            String msg = "Failed to find AuthConfigFactory : " + factoryName;
            IllegalStateException ise = new IllegalStateException(msg);
            ise.initCause(e);
            throw ise;
         }
         catch (IllegalAccessException e)
         {
            String msg = "Unable to access class : " + factoryName;
            IllegalStateException ise = new IllegalStateException(msg);
            ise.initCause(e);
            throw ise;
         }
         catch (InstantiationException e)
         {
            String msg = "Failed to create instance of: " + factoryName;
            IllegalStateException ise = new IllegalStateException(msg);
            ise.initCause(e);
            throw ise;
         }
         catch (ClassCastException e)
         {
            StringBuffer msg = new StringBuffer(factoryName + " Is not a AuthConfigFactory, ");
            msg.append("ACF.class.CL: "+ AuthConfigFactory.class.getClassLoader());
            msg.append("\nACF.class.CS: " + AuthConfigFactory.class.getProtectionDomain().getCodeSource());
            msg.append("\nACF.class.hash: "+System.identityHashCode(AuthConfigFactory.class));
            msg.append("\nclazz.CL: "+clazz.getClassLoader());
            msg.append("\nclazz.CS: "+clazz.getProtectionDomain().getCodeSource());
            msg.append("\nclazz.super.CL: "+clazz.getSuperclass().getClassLoader());
            msg.append("\nclazz.super.CS: "+clazz.getSuperclass().getProtectionDomain().getCodeSource());
            msg.append("\nclazz.super.hash: "+System.identityHashCode(clazz.getSuperclass()));
            ClassCastException cce = new ClassCastException(msg.toString());
            cce.initCause(e);
            throw cce;
         }
      }
      return _factory;
   }

   /**
    * <p>Get the the registration context for the identified registration.</p>
    *
    * @param registrationID A String that identifies a provider registration at the factory.
    *
    * @return A RegistrationContext or null. When a Non-null value is returned, it is a copy of the registration
    *         context corresponding to the registration. Null is returned when the registration identifier does not
    *         correspond to an active registration.
    */
   public abstract RegistrationContext getRegistrationContext(String registrationID);

   /**
    * <p>Get the registration identifiers for all registrations of the provider instance at the factory.</p>
    *
    * @param provider - The AuthConfigurationProvider whose registration identifiers are to be returned. This
    *                   argument may be null, in which case it indicates that the IDs of all active registrations within the
    *                   factory are to be returned.
    *
    * @return An array of String values where each value identifies a provider registration at the factory. This
    *         method never returns null; it returns an empty array when there are no registrations at the factory for
    *         the identified provider.
    */
   public abstract String[] getRegistrationIDs(AuthConfigProvider provider);

   /**
    * <p>Cause the factory to reprocess its persistent declarative representation of provider registrations.</p>
    *
    * <p>A factory should only replace an existing registration when a change of provider implementation class or
    * initialization properties has occurred.</p>
    *
    * <p>When a SecurityManager is enabled, and before the point where this method could have caused any
    * changes to the factory, this method must confirm that the calling access control context has been granted the
    * providerRegistrationSecurityPermission.</p>
    *
    * @throws SecurityException If the caller does not have permission to refresh the factory,
    *                           or if an error occurred during the reinitialization.
    */
   public abstract void refresh();

   /**
    * <p>Registers within the factory and records within the factory’s persistent declarative representation of
    * provider registrations a provider of ServerAuthConfig and/or ClientAuthConfig objects for a message layer
    * and application context identifier. This method typically constructs an instance of the provider before
    * registering it with the factory. Factories may extend or modify the persisted registrations of existing
    * provider instances, if those instances were registered with ClassName and properties arguments equivalent
    * to those passed in the current call.</p>
    *
    * <p>This method employs the two argument constructor required to be supported by every implementation of
    * the AuthConfigProvider interface, and this method must pass a null value for the factory argument of the
    * constructor. AuthConfigProviderImpl AuthConfigProviderImpl(Map properties,
    * AuthConfigFactory factory).</p>
    *
    * <p>At most one registration may exist within the factory for a given combination of message layer and
    * appContext. Any pre-existing registration with identical values for layer and appContext is replaced by a
    * subsequent registration. When replacement occurs, the registration identifier, layer, and appContext
    * identifier remain unchanged, and the AuthConfigProvider (with initialization properties) and description
    * are replaced.</p>
    *
    * <p>Within the lifetime of its Java process, a factory must assign unique registration identifiers to registrations,
    * and must never assign a previously used registration identifier to a registration whose message layer and or
    * appContext identifier differ from the previous use.</p>
    *
    * <p>Programmatic registrations performed by using this method must update (according to the replacement
    * rules described above) the persistent declarative representation of provider registrations employed by the
    * factory constructor.</p>
    *
    * <p>When a SecurityManager is enabled, before loading the argument provider, and before making any changes
    * to the factory, this method must confirm that the calling access control context has been granted the
    * providerRegistrationSecurityPermission.</p>
    *
    * @param className The fully qualified name of an AuthConfigProvider implementation class (or null).
    *                  Calling this method with a null value for this parameter shall cause getConfigProvider to return
    *                  null when it is called with layer and appContext values for which the resulting registration is the best match.
    * @param properties A Map object containing the initialization properties to be passed to the properties
    *                   argument of the provider constructor. This argument may be null. When this argument is not null, all
    *                   the values and keys occurring in the Map must be of type String.
    * @param layer A String identifying the message layer for which the provider will be registered at the factory.
    *              A null value may be passed as an argument for this parameter, in which case the provider is registered at all layers.
    * @param appContext A String value that may be used by a runtime to request a configuration object from
    *                   this provider. A null value may be passed as an argument for this parameter, in which case the provider
    *                   is registered for all configuration ids (at the indicated layers).
    * @param description A text String describing the provider. This value may be null.
    *
    * @return A String identifier assigned by the factory to the provider registration, and that may be used to
    *         remove the registration from the factory.
    *
    * @throws SecurityException If the caller does not have permission to register a provider
    *                           at the factory, or if the the provider construction (given a non-null className) or registration fails.
    */
   public abstract String registerConfigProvider( String className, Map properties,String layer,
           String appContext,  String description);

   /**
    * <p>Registers within the (in-memory) factory, a provider of ServerAuthConfig and/or ClientAuthConfig objects
    * for a message layer and application context identifier. This method does NOT effect the factory's persistent
    * declarative representation of provider registrations, and is intended to be used by providers to perform self-Registration.</p>
    *
    * <p>At most one registration may exist within the factory for a given combination of message layer and
    * appContext. Any pre-existing registration with identical values for layer and appContext is replaced by a
    * subsequent registration. When replacement occurs, the registration identifier, layer, and appContext
    * identifier remain unchanged, and the AuthConfigProvider (with initialization properties) and description
    * are replaced.</p>
    *
    * <p>Within the lifetime of its Java process, a factory must assign unique registration identifiers to registrations,
    * and must never assign a previously used registration identifier to a registration whose message layer and or
    * appContext identifier differ from the previous use.</p>
    *
    * <p>When a SecurityManager is enabled, and before making any changes to the factory, this method must
    * confirm that the calling access control context has been granted the providerRegistrationSecurityPermission.</p>
    *
    * @param provider The AuthConfigProvider to be registered at the factory (or null). Calling this method
                      with a null value for this parameter shall cause getConfigProvider to return null when it is called
                      with layer and appContext values for which the resulting registration is the best match.
    * @param layer    A String identifying the message layer for which the provider will be registered at the factory.
                      A null value may be passed as an argument for this parameter, in which case the provider is registered
                      at all layers.
    * @param appContext A String value that may be used by a runtime to request a configuration object from
                        this provider. A null value may be passed as an argument for this parameter, in which case the provider
                        is registered for all configuration ids (at the indicated layers).
    * @param description A text String describing the provider. This value may be null.
    *
    * @return A String identifier assigned by the factory to the provider registration, and that may be used to
    *         remove the registration from the factory.
    *
    * @throws SecurityException If the caller does not have permission to register a provider
    *                           at the factory, or if the provider registration fails.
    */
   public abstract java.lang.String registerConfigProvider(AuthConfigProvider provider,
          String layer,  String appContext,  String description);

   /**
    * <p>Remove the identified provider registration from the factory (and from the persistent declarative
    * representation of provider registrations, if appropriate) and invoke any listeners associated with the
    * removed registration.</p>
    *
    * <p>When a SecurityManager is enabled, and before making any changes to the factory, this method must
    * confirm that the calling access control context has been granted the providerRegistrationSecurityPermission.</p>
    *
    * @param registrationID A String that identifies a provider registration at the factory.
    *
    * @return True if there was a registration with the specified identifier and it was removed. Return false if
    *         the registrationID was invalid.
    *
    * @throws SecurityException If the caller does not have permission to unregister the provider at the factory.
    */
   public abstract boolean removeRegistration( String registrationID);

   /**
    * <p>Set the system-wide AuthConfigFactory implementation.</p>
    *
    * <p>If an implementation was set previously, it will be replaced.</p>
    *
    * <p>Listeners are not notified of a change to the registered factory.</p>
    *
    * @param factory The AuthConfigFactory instance, which may be null.
    *
    * @throws SecurityException If the caller does not have permission to set the factory.
    */
   public static void setFactory(AuthConfigFactory factory)
   {
       SecurityManager securityManager = System.getSecurityManager();
       if (securityManager != null)
           securityManager.checkPermission(setFactorySecurityPermission);

      _factory = factory;
   }

   /**
    * <p>Represents the layer identifier, application context identifier., and description
    * components of an AuthConfigProvider registration at the factory.</p>
    */
   public static interface RegistrationContext
   {
      /**
       * <p>Get the application context identifier from the registration context.</p>
       *
       * @return A String identifying the application context for which the
       *         AuthConfigProvider was registered. The returned value may be null.
       */
      String getAppContext();

      /**
       * <p>Get the description from the registration context.</p>
       *
       * @return The description String from the registration, or null, if no
       *         description string was included in the registration.
       */
      String getDescription();

      /**
       * <p>Get the layer name from the registration context.</p>
       *
       * @return A String identifying the message layer for which the AuthConfigProvider
       *                  was registered. the returned value may be null.
       */
      String getMessageLayer();

      /**
       * <p>Get the persisted status from the registration context.</p>
       *
       * @return A boolean indicating whether the registration is the result of a className based registration, or
       *         an instance-based (for example, self-) registration. Only registrations performed using the five
       *         argument registerConfigProvider method are persistent.
       */
      public boolean isPersistent();
   }

   /** <p>A PrivilegedExceptionAction that looks up the class name identified
    * by the authcontextfactory.provider system property and loads the class
    * using the thread context class loader.</p>
    */
   private static class LoadAction implements PrivilegedExceptionAction
   {
      private String name;
      public String getName()
      {
         return name;
      }
      public Object run()
         throws Exception
      {
         name = System.getProperty(DEFAULT_FACTORY_SECURITY_PROPERTY);
         if( name == null )
         {
            // Use the default factory impl
            name = FACTORY_IMPL;
         }
         ClassLoader loader = Thread.currentThread().getContextClassLoader();
         Class factoryClass = loader.loadClass(name);
         return factoryClass;
      }
   }
}
