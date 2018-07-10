package mx.infotec.smartcity.backend.service;

import java.io.Serializable;

import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.TokenInfo;
import mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException;
import mx.infotec.smartcity.backend.service.exception.InvalidTokenException;
import mx.infotec.smartcity.backend.service.exception.ServiceException;

/**
 *
 * @author Erik Valdivieso
 */
public interface LoginService extends Serializable {

  /**
   * Performs authentication on the IDM server, returns the user information with a valid token.
   *
   * @param username Username
   * @param password Password
   * @return User information with a valid token
   * @throws mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException
   */
  IdentityUser performLogin(String username, char[] password) throws InvalidCredentialsException;
  
  IdentityUser findUserByValidToken(String token) throws InvalidTokenException, ServiceException;

  /**
   * Performs a token authentication and refresh the Token Info object within the IdentityUser object
   * @param token
   * @return
   * @throws InvalidTokenException
   */
  TokenInfo refreshToken(String token) throws InvalidTokenException;

  /**
   * Verifies if the token is valid
   * 
   * @param token
   * @return
   */
  boolean isValidToken(String token);

  /**
   * Revoke current token from session (logout)
   * 
   * @param token
   * @return
   */
  boolean invalidToken(String token);

}
