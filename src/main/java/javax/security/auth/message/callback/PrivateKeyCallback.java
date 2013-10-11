package javax.security.auth.message.callback;

import javax.security.auth.callback.Callback;
import javax.security.auth.x500.X500Principal;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.cert.Certificate;

//$Id$

/**
 *  <p>Callback for acquiring a Public Key Infrastructure (PKI) private key and its corresponding certificate chain.
 *  This Callback may be used by client or server authentication modules to obtain private keys or private key
 *  references, from key repositories available to the CallbackHandler that processes the Callback.</p>
 *
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
    * <p>Request type for private keys that are identified using an alias.</p>
    */
   public static class AliasRequest implements Request
   {
      private String alias = null;
      
      /** 
       * <p>Construct an AliasRequest with an alias.</p>
       *
       * <p>The alias is used to directly identify the private key to be returned. 
       * The corresponding certificate chain for the private key is also returned.</p>
       * 
       * <p>If the alias is null, the handler of the callback relies on its own default.</p>
       *
       * @param alias Name identifier for the private key, or null.
       */
      public AliasRequest(String alias)
      {
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
   
   /** 
    * <p>Request type for private keys that are identified using an issuer/serial number.</p>
    */
   public static class IssuerSerialNumRequest implements Request
   {
      private X500Principal issuer; 
      private java.math.BigInteger serialNumber;
      
      /** 
       * <p>Constructs a IssuerSerialNumRequest with an issuer/serial number.</p>
       *
       * <p>The issuer/serial number is used to identify a public key certificate. The corresponding private key is
       * returned in the callback. The corresponding certificate chain for the private key is also returned. If the
       * issuer/serialNumber parameters are null, the handler of the callback relies on its own defaults.</p>
       * 
       * @param issuer The X500Principal name of the certificate issuer, or null.
       * @param serialNumber The serial number of the certificate, or null.
       */
      public IssuerSerialNumRequest(X500Principal issuer, BigInteger serialNumber)
      {
         this.issuer = issuer;
         this.serialNumber = serialNumber;
      }

      /**
       * <p>Get the issuer.</p>
       *
       * @return The issuer, or null.
       */
      public X500Principal getIssuer()
      {
         return issuer;
      }

      /**
       * <p>Get the serial number.</p>
       *
       * @return The issuer, or null.
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
       * @param keyID Identifier for the private key, or null.
       */
      public SubjectKeyIDRequest(byte[] keyID)
      {  
         subjectKeyID = keyID;
      }
      
      /**
       * <p>Get the subjectKeyID.</p>
       *
       * @return The subjectKeyID, or null.
       */
      public byte[] getSubjectKeyID()
      {
         return subjectKeyID;
      } 
   } 

   /**
    *  <p>Request type for private keys that are identified using a certificate digest or thumbprint.</p>
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
       *
       * @param digest The digest value to use to select the corresponding certificate and private key (or null).
       * @param algorithm - A string value identifying the digest algorithm. The value passed to this parameter may
       *                    be null. If it is not null, it must conform to the requirements for the algorithm parameter of
       *                    java.security.MessageDigest.getInstance().
       **/
      public DigestRequest( byte[] digest, String algorithm )
      {
         theDigest = digest;
         theAlgorithm = algorithm;
      }

      /**
       * <p>Get the digest value.</p>
       *
       * @return The digest value which must match the digest of the certificate corresponding to the returned
       * private key
       */
      public byte[] getDigest()
      {
          return theDigest;
      }

      /**
       * <p>Get the algorithm identifier.</p>
       *
       * @return The identifier of the algorithm used to compute the digest.
       */
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
    *
    * <p>The request object identifies the private key to be returned. The corresponding 
    * certificate chain for the private key is also returned.</p>
    * 
    * <p>If the request object is null, the handler of the callback relies on its 
    * own default.</p>
    * 
    * @param request Identifier for the private key, or null.
    */
   public PrivateKeyCallback(Request request)
   {
      this.request = request;
   }
   
   /**
    * <p>Used to obtain the certificate chain set within the Callback.</p>
    *
    * @return The certificate chain, or null if the chain could not be found.
    */
   public Certificate[] getChain()
   {
      return chain;
   }
   
   /**
    * <p>Used to obtain the private key set within the Callback.</p>
    *
    * @return The private key, or null if the key could not be found.
    */
   public PrivateKey getKey()
   {
      return key;
   }

   /**
    * <p>Used by the CallbackHandler to get the Request object that identifies the private key to be returned.</p>
    *
    * @return The Request object which identifies the private key to be returned,
    *         or null. If null, the handler of the callback relies on its own default.
    */
   public Request getRequest()
   {
      return request;
   }

   /**
    * <p>Used by the CallbackHandler to set the requested private key and the corresponding certificate chain within
    * the Callback.</p>
    *
    * <p>If the requested private key or chain could not be found, then both values must be set to null.</p>
    * 
    * @param key The private key, or null.
    * @param chain The corresponding certificate chain, or null.
    */
   public void setKey(PrivateKey key, Certificate[] chain)
   {
      this.key = key;
      this.chain = chain;
   } 
}
