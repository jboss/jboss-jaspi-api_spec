package javax.security.auth.message.config;
 
/**
 * <p>A Listener that may associated with a provider registration by a user 
 * of the registration. The Listener will be notified if the corresponding 
 * provider is unregistered or replaced.</p>
 *  @author Anil.Saldhana@redhat.com
 *  @since  Jul 10, 2007 
 *  @version $Revision$    
 */
public interface RegistrationListener
{
   /**
    * Notify the listener that a registration with which it was associated, was 
    * replaced or unregistered. When a RegistrationListener is associated with a 
    * provider registration within the factory, the factory must call its notify 
    * method when the corresponding registration is unregistered or replaced.
    * 
    * @param layer a String identifying the message layer(s) corresponding to 
    *              registration for which the listerner is being notified.
    * @param appContext a String value identifying the application context(s) 
    *              corresponding to registration for which the listerner is being 
    *              notified. The factory detaches the listener from the 
    *              corresponding registration once the listener has been notified 
    *              for the registration. The detachListerner method must be called 
    *              to detach listeners that are no longer in use.
    */
   public void notify( String layer, String appContext); 
}
