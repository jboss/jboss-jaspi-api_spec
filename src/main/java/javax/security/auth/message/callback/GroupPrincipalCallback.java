package javax.security.auth.message.callback;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;

//$Id$

/**
 *  
 *  @author Anil.Saldhana@redhat.com
 *  @since  Jul 11, 2007 
 *  @version $Revision$
 */
public class GroupPrincipalCallback implements Callback
{
   private Subject subject;
   private String[] groups;

   public GroupPrincipalCallback(javax.security.auth.Subject s, java.lang.String[] g)
   {
      this.subject = s;
      this.groups = g;
   }
   
   public String[] getGroups()
   {
      return this.groups;
   }
   
   public Subject getSubject()
   {
      return this.subject;
   }
}
