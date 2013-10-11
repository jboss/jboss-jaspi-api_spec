package javax.security.auth.message.callback;

import javax.security.auth.callback.Callback;
import java.security.cert.CertStore;

//$Id$

/**
 *  <p>Callback for CertStore.</p>
 *
 *  <p>A CertStore is a generic repository for certificates. CertStores may be searched to locate public key certificates,
 *  as well as to put together certificate chains. Such a search may be necessary when the caller needs to verify a
 *  signature.</p>
 *  
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)
 *  @since  May 11, 2006 
 *  @version $Revision$
 */
public class CertStoreCallback implements Callback
{
   private CertStore certStore;
   
   /**
    * <p>Create a CertStoreCallback.</p>
    */
   public CertStoreCallback()
   { 
   }

   /**
    * <p>Used by the CertStore user to obtain the CertStore set within the Callback.</p>
    * 
    * @return The CertStore, or null.
    */
   public CertStore getCertStore()
   {
      return certStore;
   }

   /** 
    * <p>Used by the CallbackHandler to set the CertStore within the Callback.</p>
    *
    * @param certStore The certificate store, which may be null.
    */
   public void setCertStore(CertStore certStore)
   {
      this.certStore = certStore;
   } 
}
