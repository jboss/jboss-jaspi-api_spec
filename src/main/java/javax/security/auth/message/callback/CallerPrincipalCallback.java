package javax.security.auth.message.callback;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import java.security.Principal;

//$Id$

/**
 *  <p>Callback for setting the container's caller (or Remote user) principal. This callback is intended to be called by a
 *  serverAuthModule during its validateRequest processing.</p>
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

   /**
    * <p>Create a CallerPrincipalCallback to set the container's representation of the caller principal.</p>
    *
    * @param s The Subject in which the container will distinguish the caller identity.
    * @param p The Principal that will be distinguished as the caller principal. This value may be null.
    *            The CallbackHandler must use the argument Principal to establish the caller principal associated with
    *            the invocation being processed by the container. When the argument Principal is null, the handler must
    *            establish the container's representation of the unauthenticated caller principal. The handler may
    *            perform principal mapping of non-null argument Principal values, but it must be possible to configure
    *            the handler such that it establishes the non-null argument Principal as the caller principal.
    */
   public CallerPrincipalCallback(javax.security.auth.Subject s, java.security.Principal p)
   {
      this.subject = s;
      this.principal = p;
   }

   /**
    * <p>Create a CallerPrincipalCallback to set the container's representation of the caller principal.</p>
    *
    * @param s The Subject in which the container will distinguish the caller identity.
    * @param n The String value that will be returned when getName() is called on the principal established as the
    *          caller principal or null.
    *          The CallbackHandler must use the n argument to establish the caller principal associated with the
    *          invocation being processed by the container. When the n argument is null, the handler must establish
    *          the container’s representation of the unauthenticated caller principal (which may or may not be equal to
    *          null, depending on the requirements of the container type ). The handler may perform principal
    *          mapping of non-null values of n, but it must be possible to configure the handler such that it establishes
    *          the non-null argument value as the value returned when getName is called on the established principal
    */
   public CallerPrincipalCallback(javax.security.auth.Subject s, java.lang.String n)
   {
      this.subject = s;
      this.name = n;
   }

   /**
    * <p>Get the caller principal name.</p>
    *
    * @return The principal name or null.
    *         When the values returned by this method and the getPrincipal methods are null, the handler must
    *         establish the container's representation of the unauthenticated caller principal within the Subject.
    */
   public String getName()
   {
      return this.name;
   }

   /**
    * <p>Get the caller principal.</p>
    *
    * @return The principal or null.
    *         When the values returned by this method and the getName methods are null, the handler must establish
    *         the container’s representation of the unauthenticated caller principal within the Subject.
    */
   public Principal getPrincipal()
   {
      return this.principal;
   }

   /**
    * <p>Get the Subject in which the handler will distinguish the caller principal</p>
    *
    * @return The subject.
    */
   public Subject getSubject()
   {
      return this.subject;
   }
}
