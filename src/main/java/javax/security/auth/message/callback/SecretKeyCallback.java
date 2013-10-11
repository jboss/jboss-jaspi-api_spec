package javax.security.auth.message.callback;

import javax.security.auth.callback.Callback;

//$Id$

/**
 *  <p>Callback for acquiring a shared secret from a key repository. This Callback may be used by client or server
 *  authentication modules to obtain shared secrets (for example, passwords) without relying on a user during the
 *  Callback processing. This Callback is typically employed by ClientAuthModules invoked from
 *  intermediate components that need to acquire a password to authenticate to their target service.</p>
 *
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)
 *  @since  May 11, 2006 
 *  @version $Revision$
 */
public class SecretKeyCallback implements Callback
{
   /**
    * <p>Marker interface for secret key request types.</p>
    */
   public static interface Request
   { 
   }
   
   /**
    * <p>Request type for secret keys that are identified via an alias.</p>
    */
   public static class AliasRequest implements Request
   {
      private String alias = "Alias";
      
      /**
       * 
       * <p>Construct an AliasRequest with an alias.</p>
       * 
       * <p>The alias is used to directly identify the secret key to be returned.</p>
       *
       * <p>If the alias is null, the handler of the callback relies on its own default.</p>
       *
       * @param alias Name identifier for the secret key, or null
       */
      public AliasRequest(String alias)
      { 
         if(alias != null)
           this.alias = alias;
      }
      
      /**
       * <p>Get the alias.</p>
       *
       * @return The alias, or null.
       */
      public String getAlias()
      {
         return alias;
      } 
   }


   private Request request;
   private  javax.crypto.SecretKey key;

   /**
    * <p>Constructs this SecretKeyCallback with a secret key Request object.</p>
    *
    * <p>The request object identifies the secret key to be returned. If the alias is null, the handler of the callback
    * relies on its own default.</p>
    *
    * @param request Request object identifying the secret key, or null.
    */
   public SecretKeyCallback(SecretKeyCallback.Request request)
   {
      this.request = request;
   }

   /**
    * <p>Used by the CallbackHandler to get the Request object which identifies the secret key to be returned.</p>
    *
    * @return The Request object which identifies the private key to be returned, or null. If null, the handler of
    *         the callback relies on its own default.
    */
   public Request getRequest()
   {
      return request;
   }

   /**
    * <p>Used by the CallbackHandler to set the requested secret key within the Callback.</p>
    *
    * @param key The secret key, or null if no key was found.
    */
   public void setKey(javax.crypto.SecretKey key)
   {
      this.key = key;
   }

   /**
    * <p>Used to obtain the secret key set within the Callback.</p>
    *
    * @return The secret key, or null if no key was found.
    */
   public javax.crypto.SecretKey getKey()
   {
      return key;
   }
}
