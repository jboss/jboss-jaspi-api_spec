package javax.security.auth.message.callback;

import java.security.cert.CertStore;

import javax.security.auth.callback.Callback;

//$Id$

/**
 *  Callback for CertStore.
 *  A CertStore is a generic repository for certificates. 
 *  CertStores may be searched to locate public key certificates, as well 
 *  as to put together certificate chains. Such a search may be necessary 
 *  when the caller needs to verify a signature.
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
    *  Create a CertStoreCallback. 
    */
   public CertStoreCallback()
   { 
   }

   /**
    *  Get the requested CertStore.
    * 
    * @return the CertStore, or null. If null, the requester is assumed to 
    * already have access to the relevant certificate and/or chain.
    */
   public CertStore getCertStore()
   {
      return certStore;
   }

   /** 
    * Set the CertStore.
    * @param certStore the certificate store, which may be null If null, the 
    * requester is assumed to already have access to the relevant certificate 
    * and/or chain.
    */
   public void setCertStore(CertStore certStore)
   {
      this.certStore = certStore;
   } 
}
