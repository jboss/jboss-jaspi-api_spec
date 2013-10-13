package javax.security.auth.message.callback;

import javax.security.auth.callback.Callback;
import java.security.KeyStore;

//$Id$

/**
 *  <p>Callback for trusted certificate KeyStore.</p>
 *
 *  <p>A trusted certificate KeyStore may be used to determine whether a given certificate chain can be trusted.</p>
 *
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)
 *  @since  May 11, 2006 
 *  @version $Revision$
 */
public class TrustStoreCallback implements Callback
{
   private KeyStore trustStore;

   /** 
    * <p>Create a new TrustStoreCallback.</p>
    */
   public TrustStoreCallback()
   { 
   }
   
   /**
    * <p>Used by the TrustStore user to obtain the TrustStore set within the Callback.</p>
    *
    * @return The trusted certificate KeyStore. The KeyStore is guaranteed to already be loaded.
    */
   public KeyStore getTrustStore()
   {
      return trustStore;
   }

   /**
    * <p>Used by the CallbackHandler to set the trusted certificate keystore within the Callback.</p>
    *
    * @param trustStore The trusted certificate KeyStore, which must already be loaded.
    */
   public void setTrustStore(KeyStore trustStore)
   {
      this.trustStore = trustStore;
   } 
}
