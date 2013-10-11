package javax.security.auth.message;

import javax.security.auth.Subject;

//$Id$

/**
 *  <p>An implementation of this interface is used to secure service request messages, and validate received
 *  service response messages.</p>
 *
 *  @author <a href="mailto:Anil.Saldhana@jboss.org">Anil Saldhana</a>
 *  @author Charlie Lai, Ron Monzillo (Javadoc for JSR-196)
 *  @since  May 11, 2006 
 *  @version $Revision$
 *
 *  @see MessageInfo, javax.security.auth.Subject subject
 */
public interface ClientAuth
{
   /**
    * <p>Remove implementation specific principals and credentials from the subject.</p>
    * @param messageInfo - A contextual object that encapsulates the client request 
    *                      and server response objects, and that may be used to save 
    *                      state across a sequence of calls made to the methods of 
    *                      this interface for the purpose of completing a secure 
    *                      message exchange.
    * @param subject - The Subject instance from which the Principals and credentials 
    *                      are to be removed. 
    * @throws AuthException if an error occurs during the Subject processing.
    */
   public void cleanSubject( MessageInfo messageInfo, Subject subject)
   throws AuthException;
   
   /**
    * <p>Secure a service request message before sending it to the service.</p>
    *
    * <p>This method is called to transform the request message acquired by calling getRequestMessage (on
    * messageInfo) into the mechanism-specific form to be sent by the runtime.</p>
    *
    * <p>This method conveys the outcome of its message processing either by returning an AuthStatus value or by
    * throwing an AuthException.</p>
    * 
    * @param messageInfo - A contextual object that encapsulates the client request 
    *                      and server response objects, and that may be used to save 
    *                      state across a sequence of calls made to the methods of 
    *                      this interface for the purpose of completing a secure 
    *                      message exchange.
    * @param clientSubject - A Subject that represents the source of the service request,
    *                      or null. It may be used by the method implementation as the 
    *                      source of Principals or credentials to be used to secure 
    *                      the request. If the Subject is not null, the method 
    *                      implementation may add additional Principals or credentials 
    *                      (pertaining to the source of the service request) to the Subject.
    * @return An AuthStatus object representing the completion status of the processing performed by the
    *         method. The AuthStatus values that may be returned by this method are defined as follows:
    *         <ul>
    *            <li>AuthStatus.SUCCESS when the application request message was successfully secured. The secured
    *                request message may be obtained by calling getRequestMessage on messageInfo.</li>
    *            <li>AuthStatus.SEND_CONTINUE to indicate that the application request message (within
    *                messageInfo) was replaced with a security message that should elicit a security-specific response
    *                from the peer security system. This status value also indicates that the application message has not
    *                yet been secured. This status value serves to inform the calling runtime that (to successfully complete
    *                the message exchange) it will need to be capable of continuing the message dialog by conducting at
    *                least one additional request/response exchange after having received the security-specific response
    *                elicited by sending the security message. When this status value is returned, the corresponding
    *                invocation of validateResponse must be able to obtain the original application request
    *                message.</li>
    *           <li>AuthStatus.FAILURE to indicate that a failure occured while securing the request message, and that
    *               an appropriate failure response message is available by calling getResponseMessage on
    *               messageInfo.</li>
    *         </ul>
    * @throws AuthException When the message processing failed without establishing a failure response
    *                       message (in messageInfo).
    */
   public AuthStatus secureRequest(MessageInfo messageInfo, Subject clientSubject)
   throws AuthException;
   
   /**
    * <p>Validate a received service response.</p>
    * 
    * <p>This method is called to transform the mechanism-specific response message 
    * acquired by calling getResponseMessage (on messageInfo) into the validated 
    * application message to be returned to the message processing runtime. If 
    * the response message is a (mechanism-specific) meta-message, the method 
    * implementation must attempt to transform the meta-message into the next 
    * mechanism-specific request message to be sent by the runtime.</p>
    *
    * <p>This method conveys the outcome of its message processing either by returning an AuthStatus value or by
    * throwing an AuthException.</p>
    * 
    * @param messageInfo - A contextual object that encapsulates the client 
    *                      request and server response objects, and that may be 
    *                      used to save state across a sequence of calls made to 
    *                      the methods of this interface for the purpose of 
    *                      completing a secure message exchange.
    * 
    * @param clientSubject - A Subject that represents the recipient of the 
    *                      service response, or null. It may be used by the method 
    *                      implementation as the source of Principals or credentials 
    *                      to be used to validate the response. If the Subject is 
    *                      not null, the method implementation may add additional 
    *                      Principals or credentials (pertaining to the recipient 
    *                      of the service request) to the Subject.
    *                  
    * @param serviceSubject - A Subject that represents the source of the service 
    *                      response, or null. If the Subject is not null, the method 
    *                      implementation may add additional Principals or credentials 
    *                      (pertaining to the source of the service response) to the Subject.
    * @return An AuthStatus object representing the completion status of the processing performed by the
    *         method. The AuthStatus values that may be returned by this method are defined as follows:
    *         <ul>
    *            <li>AuthStatus.SUCCESS when the application response message was successfully validated. The
    *                validated message is available by calling getResponseMessage on messageInfo.</li>
    *            <li>AuthStatus.SEND_CONTINUE to indicate that response validation is incomplete, and that a
    *                continuation request was returned as the request message within messageInfo. This status value
    *                serves to inform the calling runtime that (to successfully complete the message exchange) it will
    *                need to be capable of continuing the message dialog by conducting at least one additional request/
    *                response exchange.</li>
    *            <li>AuthStatus.FAILURE to indicate that validation of the response failed, and that a failure response
    *                message has been established in messageInfo.</li>
    *         </ul>
    * @throws AuthException When the message processing failed without establishing a failure response
    *                       message (in messageInfo).
    */
   public AuthStatus validateResponse(MessageInfo messageInfo, Subject clientSubject, 
         Subject serviceSubject)
   throws AuthException;
}
