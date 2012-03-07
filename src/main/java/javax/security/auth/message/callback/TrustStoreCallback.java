package javax.security.auth.message.callback;

import java.security.KeyStore;

import javax.security.auth.callback.Callback;

//$Id$

/**
 *  Callback for trusted certificate KeyStore.
 *  <p>A trusted certificate KeyStore may be used to determine whether a
 *  given certificate chain can be trusted.</p>
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)
 *  @since  May 11, 2006 
 *  @version $Revision$
 */
public class TrustStoreCallback implements Callback
{
   private KeyStore trustStore;

   /** 
    * Create a new TrustStoreCallback.
    */
   public TrustStoreCallback()
   { 
   }
   
   /**
    * Get the requested trusted certificate KeyStore.
    * @return the trusted certificate KeyStore. 
    *         The KeyStore is guaranteed to already be loaded.
    */
   public KeyStore getTrustStore()
   {
      return trustStore;
   }

   /**
    * Set the trusted certificate KeyStore.
    * @param trustStore the trusted certificate KeyStore, which 
    *           must already be loaded.
    */
   public void setTrustStore(KeyStore trustStore)
   {
      this.trustStore = trustStore;
   } 
}
