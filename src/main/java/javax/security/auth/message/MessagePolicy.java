package javax.security.auth.message; 

/** 
 *  <p>This class defines a message authentication policy.</p>
 *
 *  <p>A ClientAuthContext uses this class to communicate (at module initialization time) request and response
 *  message protection policies to its ClientAuthModule objects. A ServerAuthContext uses this class to
 *  communicate request and response message protection policies to its ServerAuthModule objects.</p>
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana@jboss.org</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)</a> 
 *  @since  May 11, 2006 
 *  @version $Revision$
 *
 *  @see javax.security.auth.message.config.ClientAuthContext
 *  @see javax.security.auth.message.config.ServerAuthContext
 *  @see javax.security.auth.message.module.ClientAuthModule
 *  @see javax.security.auth.message.module.ServerAuthModule
 */
public class MessagePolicy
{ 
   private TargetPolicy[] targetPolicies = null;
   private boolean mandatory;
   
   /** 
    * <p>Create a MessagePolicy instance with an array of target policies.</p>
    * 
    * @param targetPolicies an array of target policies.
    * @param mandatory - A boolean value indicating whether the MessagePolicy 
    *                    is mandatory or optional.
    * @throws IllegalArgumentException if the specified targetPolicies is null.
    */
   public MessagePolicy(TargetPolicy[] targetPolicies, boolean mandatory)
   {
      if( targetPolicies == null)
         throw new IllegalArgumentException("specified targetPolicies is null");
      
      this.targetPolicies = targetPolicies; 
      this.mandatory = mandatory;
   }
   
   /**
    * <p>Get the target policies that comprise the authentication policy.</p>
    * 
    * @return an array of target authentication policies, where each element describes an 
    *         authentication policy and the parts of the message to which the authentication 
    *         policy applies. This method returns null to indicate that no security operations 
    *         should be performed on the messages to which the policy applies. <b>This method 
    *         never returns a zero-length array</b>. When this method returns an array of target 
    *         policies, the order of elements in the array represents the order that the 
    *         corresponding message transformations or validations described by the target 
    *         policies are to be performed by the authentication module.
    */
   public TargetPolicy[]getTargetPolicies()
   {
      if(targetPolicies != null && targetPolicies.length == 0 )
         throw new IllegalStateException("Target Policies should not be of zero length");
      return this.targetPolicies;
   }

   /**
    * <p>Get the MessagePolicy modifier.</p>
    *
    * @return A boolean indicating whether the MessagePolicy is optional(false) or required(true).
    */
   public boolean isMandatory()
   {
      return this.mandatory;
   }
   
   /**
    * <p>This interface is used to represent and perform message targeting. Targets are used by message authentication
    * modules to operate on the corresponding content within messages.</p>
    *
    * <p>The internal state of a Target indicates whether it applies to the request or
    * response message of a MessageInfo and to which components it applies within the
    * identified message.</p>
    */
   public static interface Target
   {
      /**
       * <p>Get the Object identified by the Target from the MessageInfo.</p>
       * 
       * @param messageInfo the MessageInfo containing the request or response message from which 
       *                  the target is to be obtained.
       * @return an Object representing the target, or null when the target could not be found 
       *                  in the MessageInfo.
       */
      public Object get(MessageInfo messageInfo);
      
      /** 
       * <p>Put the Object into the MessageInfo at the location identified by the target.</p>
       *
       * @param messageInfo the MessageInfo containing the request or response message 
       *               into which the object is to be put.
       * @param data
       */
      public void put(MessageInfo messageInfo, Object data);
      
      /**
       * <p>Remove the Object identified by the Target from the MessageInfo.</p>
       * 
       * @param messageInfo the MessageInfo containing the request or response message from
       *                  which the target is to be removed.
       */
      public void remove(MessageInfo messageInfo);
   }

   /**
    * <p>This class defines the message protection policies for specific Targets</p>
    *
    * <p>This class is used to associate a message protection policy with targets within a message. Message targets are
    * represented using an implementation of the Target interface matched to the message types in MessageInfo. The
    * message protection policy is identified by an implementation of the ProtectionPolicy interface.</p>
    *
    * @see javax.security.auth.message.module.ClientAuthModule
    * @see javax.security.auth.message.module.ServerAuthModule
    */
   public static class TargetPolicy
   {
      
      private ProtectionPolicy protectionPolicy;
      private Target[] targets;
      
      /**
       * <p>Create a TargetPolicy instance with an array of Targets and with a ProtectionPolicy.</p>
       * 
       * @param targets - An array of Targets. This argument may be null.
       * @param protectionPolicy - The object that describes the message authentication policy that applies to
       *                           the targets.
       * @throws IllegalArgumentException - if the specified targets or protectionPolicy is null
       */
      public TargetPolicy(Target[] targets,  ProtectionPolicy protectionPolicy)
      {
         this.targets = targets;
         this.protectionPolicy = protectionPolicy;
      }
      
      /**
       * <p>Get the ProtectionPolicy that applies to the targets.</p>
       * 
       * @return A ProtectionPolicy object that identifies the message authentication requirements that apply to
       *         the targets.
       */
      public ProtectionPolicy getProtectionPolicy()
      {
         return this.protectionPolicy;
      }
      
      /**
       * <p>Get the array of layer-specific target descriptors that identify the one or more message parts to which the
       * specified message protection policy applies.</p>
       *
       * @return An array of Target that identify targets within a message. This method returns null when the
       *         specified policy applies to the whole message (excluding any metadata added to the message to satisfy
       *         the policy). This method never returns a zero-length array.
       */
      public Target[] getTargets()
      {
         if(targets != null && targets.length == 0 )
            throw new IllegalStateException(" Targets cannot be of length zero");
         return this.targets;
      } 
   }
   
   /**
    * <p>This interface is used to represent message authentication policy.</p>
    *
    * <p>The internal state of a ProtectionPolicy object defines the message authentication requirements to be applied
    * to the associated Target.</p>
    */
   public static interface ProtectionPolicy
   {
      /**
       * The identifier for a ProtectionPolicy that indicates that the sending entity is to be authenticated.
       */
      public static final String AUTHENTICATE_SENDER = "#authenticateSender";
      
      /**
       * The identifier for a ProtectionPolicy that indicates that the message recipient is to be authenticated.
       */
      public static final String AUTHENTICATE_RECIPIENT = "#authenticateRecipient";
      
      /**
       * The identifier for a ProtectionPolicy that indicates that the origin of data within the message is to be
       * authenticated (that is, the message is to be protected such that its recipients can establish who defined the
       * message content).
       */
      public static final String AUTHENTICATE_CONTENT = "#authenticateContent";
       
      /**
       * <p>Get the ProtectionPolicy identifier. An identifier may represent a conceptual protection policy (as is the
       * case with the static identifiers defined within this interface) or it may identify a procedural policy
       * expression or plan that may be more difficult to categorize in terms of a conceptual identifier.</p>
       *
       * @return A String containing a policy identifier. This interface defines some 
       *         policy identifier constants. Configuration systems may define and employ 
       *         other policy identifiers values.
       */
      public String getID();
   }
   
}
