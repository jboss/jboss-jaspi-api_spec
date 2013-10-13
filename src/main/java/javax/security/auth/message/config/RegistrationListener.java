package javax.security.auth.message.config;
 
/**
 * <p>An implementation of this interface may be associated with an AuthConfigProvider registration at an
 * AuthConfigFactory at the time the AuthConfigProvider is obtained for use from the factory. The
 * AuthConfigFactory will invoke the notify method of the RegistrationListener if the corresponding provider
 * registration is unregistered or replaced at the factory.</p>
 *
 *  @author Anil.Saldhana@redhat.com
 *  @since  Jul 10, 2007 
 *  @version $Revision$    
 */
public interface RegistrationListener
{
   /**
    * <p>Notify the listener that a registration with which it was associated was replaced or unregistered.</p>
    *
    * <p>When a RegistrationListener is associated with a provider registration within the factory, the factory must
    * call its notify method when the corresponding registration is unregistered or replaced.</p>
    * 
    * @param layer A String identifying the message layers corresponding to the registration for which the listener is being notified.
    * @param appContext a String value identifying the application contexts
    *              corresponding to the registration for which the listener is being
    *              notified. The factory detaches the listener from the 
    *              corresponding registration once the listener has been notified 
    *              for the registration. The detachListener method must be called
    *              to detach listeners that are no longer in use.
    */
   public void notify( String layer, String appContext); 
}
