package javax.security.auth.message.callback;

import java.security.Principal;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;

//$Id$

/**
 *  
 *  @author Anil.Saldhana@redhat.com
 *  @since  Jul 11, 2007 
 *  @version $Revision$
 */
public class CallerPrincipalCallback implements Callback
{
   private Subject subject;
   private Principal principal;
   private String name;

   public CallerPrincipalCallback(javax.security.auth.Subject s, java.security.Principal p)
   {
      this.subject = s;
      this.principal = p;
   }
   
   public CallerPrincipalCallback(javax.security.auth.Subject s, java.lang.String n)
   {
      this.subject = s;
      this.name = n;
   }
   
   public String getName()
   {
      return this.name;
   }
   
   public Principal getPrincipal()
   {
      return this.principal;
   }
   
   public Subject getSubject()
   {
      return this.subject;
   }
}
