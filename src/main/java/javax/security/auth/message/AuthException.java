package javax.security.auth.message;

//$Id$

import javax.security.auth.login.LoginException;

/**
 *  <p>A generic authentication exception.</p>
 *  @see LoginException
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana@jboss.org</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)
 *  @since  Dec 5, 2005
 */
public class AuthException extends LoginException
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -1424234495120552796L;

   /**
    * Constructs an AuthException with no detail message. 
    * A detail message is a String that describes this particular exception. 
    */
   public AuthException()
   {
      super(); 
   }

   /**
    * Constructs an AuthException with the specified detail message. A detail 
    * message is a String that describes this particular exception.
    * 
    * @param msg the detail message.
    */
   public AuthException(String msg)
   {
      super(msg); 
   }
  
}
