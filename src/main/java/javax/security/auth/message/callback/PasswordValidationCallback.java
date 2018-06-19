package javax.security.auth.message.callback;

import java.util.Arrays;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;

//$Id$

/**
 *  <p>Callback for PasswordValidation. This callback may be used by an authentication module to employ the
 *  password validation facilities of its containing runtime. This Callback would typically be called by a
 *  ServerAuthModule during validateRequest processing.</p>
 *
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)
 *  @since  May 11, 2006
 *  @version $Revision$
 */
public class PasswordValidationCallback implements Callback
{
   private final String username;
   private volatile char[] password;

   private boolean resultOfAuthentication = false;
   private final Subject subject;

   /**
    * <p>Create a PasswordValidationCallback.</p>
    *
    * @param subject  The subject for authentication
    * @param username The username to authenticate
    * @param password The user's password, which may be null.
    */
   public PasswordValidationCallback(Subject subject, String username, char[] password)
   {
      this.subject = subject;
      this.username = username;
      this.password = password;
   }

   /**
    *  <p>Clear the password.</p>
    */
    public void clearPassword() {
        char[] password = this.password;
        this.password = null;
        if (password != null) {
            Arrays.fill(password, (char) 0);
        }
    }

   /**
    * <p>Get the password.</p>
    *
    * <p><b>Note</b> that this method returns a reference to the password. If a clone
    * of the array is created it is the caller's responsibility to zero out
    * the password information after it is no longer needed.</p>
    *
    * @return the password, which may be null.
    */
   public char[] getPassword()
   {
      return this.password;
   }

   /**
    * <p>Get the subject.</p>
    *
    * @return The subject.
    */
   public Subject getSubject()
   {
      return this.subject;
   }

   /**
    * <p>Get the authentication result.</p>
    *
    * @return true if authentication succeeded, false otherwise.
    */
   public boolean getResult()
   {
      return this.resultOfAuthentication;
   }

   /**
    * <p>Get the username.</p>
    *
    * @return The username.
    */
   public String getUsername()
   {
      return this.username;
   }

   /**
    * <p>Set the authentication result.</p>
    *
    * @param result True if authentication succeeded, false otherwise.
    */
   public void setResult(boolean result)
   {
      this.resultOfAuthentication = result;
   }
}
