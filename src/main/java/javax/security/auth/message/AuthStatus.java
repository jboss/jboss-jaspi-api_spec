package javax.security.auth.message;

//$Id$

/**
 *  <p>The AuthStatus class is used to represent return values from Authentication modules and Authentication
 *  Contexts. An AuthStatus value is returned when the module processing has established a corresponding request
 *  or response message within the message parameters exchanged with the runtime.</p>
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)
 *  @since  May 11, 2006 
 *  @version $Revision$
 */
public class AuthStatus
{ 
   /**
    * Indicates that the message processing by the authentication module 
    * was NOT successful, and that the module replaced the application 
    * message with an error message.
    */
   public static final AuthStatus FAILURE = new AuthStatus(-1); 
   
   /**
    * Indicates the message processing by the authentication module is 
    * NOT complete, that the module replaced the application message 
    * with a security message, and that the runtime is to proceed by 
    * sending the security message.
    */
   public static final AuthStatus SEND_CONTINUE = new AuthStatus(3);
   
   /**
    * Indicates that the message processing by the authentication module
    * was NOT successful, that the module replaced the application message 
    * with an error message, and that the runtime is to proceed by sending 
    * the error message.
    */
   public static final AuthStatus SEND_FAILURE =  new AuthStatus(-2);
   
   /** Indicates that the message processing by the authentication module 
    * was successful and that the runtime is to proceed by sending a message 
    * returned by the authentication module.
   */
   public static final AuthStatus SEND_SUCCESS = new AuthStatus(2);
   
   /**
    * Indicates that the message processing by the authentication module 
    * was successful and that the runtime is to proceed with its normal processing 
    * of the resulting message.
    */
   public static final AuthStatus SUCCESS = new AuthStatus(1);
   
   private int status = -1;

   private AuthStatus(int status)
   {
      this.status = status;
   }

   @Override
   public String toString()
   {
      return AuthStatus.class.getSimpleName() + " : " + this.status;
   }
}
