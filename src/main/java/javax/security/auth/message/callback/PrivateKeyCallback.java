package javax.security.auth.message.callback;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import javax.security.auth.callback.Callback;
import javax.security.auth.x500.X500Principal;

//$Id$

/**
 *  Callback for private key and corresponding certificate chain.
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)
 *  @since  May 11, 2006 
 *  @version $Revision$
 */
public class PrivateKeyCallback implements Callback
{ 
   /**
    * <p>Marker interface for private key request types.</p> 
    */
   public static interface Request
   {  
   }
   
   
   /** 
    * Request type for private keys that are identified via an alias. 
    */
   public static class AliasRequest implements Request
   {
      private String alias = null;
      
      /** 
       * <p>Construct an AliasRequest with an alias.</p>
       * <p>The alias is used to directly identify the private key to be returned. 
       * The corresponding certificate chain for the private key is also returned.</p>
       * 
       * <p>If the alias is null, the handler of the callback relies on its own default.</p>
       * @param alias name identifier for the private key, or null.
       */
      public AliasRequest(String alias)
      {
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
   
   /** 
    * <p>Request type for private keys that are identified via an issuer/serial number.</p> 
    */
   public static class IssuerSerialNumRequest implements Request
   {
      private X500Principal issuer; 
      private java.math.BigInteger serialNumber;
      
      /** 
       * <p>Constructs a IssuerSerialNumRequest with an issuer/serial number.</p>
       * 
       * @param issuer the X500Principal name of the certificate issuer, or null.
       * @param serialNumber the serial number of the certificate, or null.
       */
      public IssuerSerialNumRequest(X500Principal issuer, BigInteger serialNumber)
      {
         this.issuer = issuer;
         this.serialNumber = serialNumber;
      }

      /**
       * Get the issuer. 
       * @return the issuer, or null.
       */
      public X500Principal getIssuer()
      {
         return issuer;
      }

      /**
       * Get the serial number.
       * @return the issuer, or null.
       */
      public BigInteger getSerialNum()
      {
         return serialNumber;
      } 
   }
   /**
    * <p>Request type for private keys that are identified via a SubjectKeyID</p>
    */
   public static class SubjectKeyIDRequest implements Request
   {
      private byte[] subjectKeyID;
      
      /** 
       * <p>Construct a SubjectKeyIDRequest with an subjectKeyID.</p>
       * 
       * <p>The subjectKeyID is used to directly identify the private key to be returned. 
       * The corresponding certificate chain for the private key is also returned.</p>
       * 
       * <p>If the subjectKeyID is null, the handler of the callback relies on its 
       * own default.</p>
       * 
       * @param keyID identifier for the private key, or null.
       */
      public SubjectKeyIDRequest(byte[] keyID)
      {  
         subjectKeyID = keyID;
      }
      
      /**
       * Get the subjectKeyID.
       * @return the subjectKeyID, or null.
       */
      public byte[] getSubjectKeyID()
      {
         return subjectKeyID;
      } 
   } 

   /**
    *  Request type for private keys that are identified using a certificate digest or thumbprint. 
    */
   public static class DigestRequest implements Request
   {
      private  byte[] theDigest;
      private  String theAlgorithm;

      /**
       *<p>
       * Constructs a DigestRequest with a digest value and algorithm identifier.
       *</p>
       *
       * <p> 
       * The digest of the certificate whose private key is returned must match the provided digest. 
       * The certificate digest is computed by applying the specified algorithm to the bytes of the 
       * certificate. For example: MessageDigest.getInstance(algorithm).digest(cert.getEncoded()) . 
       * The corresponding certificate chain for the private key is also returned. If the digest or 
       * algorithm parameters are null, the handler of the callback relies on its own defaults.
       * </p>
       **/
      public DigestRequest( byte[] digest, String algorithm )
      {
         theDigest = digest;
         theAlgorithm = algorithm;
      }

      public byte[] getDigest()
      {
          return theDigest;
      }

      public java.lang.String getAlgorithm()
      {
          return theAlgorithm;
      }

      
   }
   
   //Private Variables
   private Request request = null;
   private Certificate[] chain = null;
   private PrivateKey key = null;
   
   /**
    * 
    * <p>Constructs this PrivateKeyCallback with a private key Request object.</p>
    * <p>The request object identifies the private key to be returned. The corresponding 
    * certificate chain for the private key is also returned.</p>
    * 
    * <p>If the request object is null, the handler of the callback relies on its 
    * own default.</p>
    * 
    * @param request identifier for the private key, or null.
    */
   public PrivateKeyCallback(Request request)
   {
      this.request = request;
   }
   
   /**
    * 
    * @return the certificate chain, or null if the chain could not be found.
    */
   public Certificate[] getChain()
   {
      return chain;
   }
   
   /**
    * Get the requested private key.
    * @return the private key, or null if the key could not be found.
    */
   public PrivateKey getKey()
   {
      return key;
   }

   /**
    * Get the Request object which identifies the private key to be returned.
    * @return the Request object which identifies the private key to be returned, 
    *         or null. If null, the handler of the callback relies on its own default.
    */
   public Request getRequest()
   {
      return request;
   }

   /**
    * <p>Set the requested private key.</p>
    * <p>If the requested private key or chain could not be found, then 
    * both values must be set to null.</p>
    * 
    * @param key the private key, or null.
    * @param chain the corresponding certificate chain, or null.
    */
   public void setKey(PrivateKey key, Certificate[] chain)
   {
      this.key = key;
      this.chain = chain;
   } 
}
