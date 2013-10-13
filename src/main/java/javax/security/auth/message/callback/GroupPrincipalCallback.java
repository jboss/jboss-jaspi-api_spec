package javax.security.auth.message.callback;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;

//$Id$

/**
 *  <p>Callback establishing group principals within the argument subject. This callback is intended to be called by a
 *  serverAuthModule during its validateRequest processing.</p>
 *
 *  @author Anil.Saldhana@redhat.com
 *  @since  Jul 11, 2007 
 *  @version $Revision$
 */
public class GroupPrincipalCallback implements Callback
{
   private Subject subject;
   private String[] groups;

   /**
    * <p>Create a GroupPrincipalCallback to establish the container’s representation of the corresponding group
    * principals within the Subject.</p>
    * @param s The Subject in which the container will create group principals.
    * @param g An array of Strings, where each element contains the name of a group that will be used to create a
    *          corresponding group principal within the Subject.
    *          When a null value is passed to the g argument, the handler will establish the container’s representation
    *          of no group principals within the Subject. Otherwise, the handler’s processing of this callback is
    *          additive, yielding the union (without duplicates) of the principals existing within the Subject, and those
    *          created with the names occuring within the argument array. The CallbackHandler will define the type
    *          of the created principals.
    */
   public GroupPrincipalCallback(javax.security.auth.Subject s, java.lang.String[] g)
   {
      this.subject = s;
      this.groups = g;
   }

   /**
    * <p>Get the array of group names.</p>
    *
    * @return Null, or an array containing 0 or more String group names.
    *         When the return value is null, the handler will establish the container’s representation of no group
    *         principals within the Subject. Otherwise, the handler’s processing of this callback is additive, yielding
    *         the union (without duplicates) of the principals created with the names in the returned array and those
    *         existing within the Subject.
    */
   public String[] getGroups()
   {
      return this.groups;
   }

   /**
    * <p>Get the Subject in which the handler will establish the group principals.</p>
    *
    * @return The subject.
    */
   public Subject getSubject()
   {
      return this.subject;
   }
}
