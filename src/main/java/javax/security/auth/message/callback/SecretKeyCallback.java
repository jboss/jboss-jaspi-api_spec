package javax.security.auth.message.callback;

import javax.security.auth.callback.Callback;

//$Id$

/**
 *  Callback for secret key
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)
 *  @since  May 11, 2006 
 *  @version $Revision$
 */
public class SecretKeyCallback implements Callback
{
   /**
    * Marker interface for secret key request types.
    */
   public static interface Request
   { 
   }
   
   /**
    * Request type for secret keys that are identified via an alias.
    */
   public static class AliasRequest implements Request
   {
      private String alias = "Alias";
      
      /**
       * 
       * <p>Construct an AliasRequest with an alias.</p>
       * 
       * <p>The alias is used to directly identify the secret key to be returned.</p>
       * <p>If the alias is null, the handler of the callback relies on its own default.</p> 
       * @param alias name identifier for the secret key, or null
       */
      public AliasRequest(String alias)
      { 
         if(alias != null)
           this.alias = alias;
      }
      
      /**
       * Get the alias.
       * @return the alias, or null.
       */
      public String getAlias()
      {
         return alias;
      } 
   }


   private Request request;
   private  javax.crypto.SecretKey key; 

   public SecretKeyCallback(SecretKeyCallback.Request request)
   {
      this.request = request;
   }

   public Request getRequest()
   {
      return request;
   }

   public void setKey(javax.crypto.SecretKey key)
   {
      this.key = key;
   }

   public javax.crypto.SecretKey getKey()
   {
      return key;
   }
}
