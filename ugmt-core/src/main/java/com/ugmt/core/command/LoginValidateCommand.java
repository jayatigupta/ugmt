package com.ugmt.core.command;

import java.security.NoSuchAlgorithmException;

import com.ugmt.common.dto.AuthnContext;
import com.ugmt.common.dto.AuthnResponse;
import com.ugmt.common.dto.BaseDTO;
import com.ugmt.common.dto.UserDTO;
import com.ugmt.core.crypt.AESCipher;
import com.ugmt.core.crypt.HashingUtil;
import com.ugmt.core.dao.UserDAO;
import com.ugmt.core.entity.User;

public class LoginValidateCommand extends AbstractCommand {

	private UserDAO userDAO = appContext.getBean(UserDAO.class);

	public AuthnResponse authenticate(AuthnContext authnContext)
			throws NoSuchAlgorithmException {

		AuthnResponse authnResponse = new AuthnResponse();
		UserDTO userdto = new UserDTO();
		userdto.setEmail(authnContext.getLoginUid());
		authnResponse.setUser(userdto);

		if (authnContext.getLoginUid() == null
				|| authnContext.getLoginUid().trim().equals("")) {
			authnResponse.setStatus(AuthnResponse.AuthnStatus.FAILURE.name());
			authnResponse.setMsg("login uid found empty");
		} else if (authnContext.getPassword() == null
				|| authnContext.getPassword().trim().equals("")) {
			authnResponse.setStatus(AuthnResponse.AuthnStatus.FAILURE.name());
			authnResponse.setMsg("password found empty");
		} else {
			User user = userDAO.findByEmail(authnContext.getLoginUid());
			if (user == null) {
				authnResponse.setStatus(AuthnResponse.AuthnStatus.FAILURE
						.name());
				authnResponse.setMsg("Login/Password did not match");
			} else {
				if (!HashingUtil.generateSHA512Hash(authnContext.getPassword())
						.equals(user.getPassword())) {
					authnResponse.setStatus(AuthnResponse.AuthnStatus.FAILURE
							.name());
					authnResponse.setMsg("Login/Password did not match");
				} else {
					authnResponse.setStatus(AuthnResponse.AuthnStatus.SUCCESS
							.name());
					// no need to set back the password
					userdto.setId(user.getId());
					userdto.setName(user.getName());
					authnResponse.setEmailVerified(user.isVerified());
					// Add Encrypted Session Token Here
					authnResponse.setAuthnToken(getToken(user));
				}
			}
		}
		return authnResponse;
	}

	private static final String seperator = "%$#";

	private String getToken(User user) {
		StringBuffer sb = new StringBuffer();
		sb.append(user.getId()).append(seperator).append(user.getEmail())
				.append(seperator).append(user.getName()).append(seperator)
				.append(System.currentTimeMillis());
		return AESCipher.encrypt(sb.toString());
	}

	public UserDTO getUserDetails(String token) {
		UserDTO userDTO = new UserDTO();
		String decryptedToken = AESCipher.decrypt(token);
		String[] arr = decryptedToken.split(seperator);
		if (arr.length == 4) {
			userDTO.setId(arr[0]);
			userDTO.setEmail(arr[1]);
			userDTO.setName(arr[2]);
			// Time factor can help decide expiration of token.
		} else {
			userDTO.setStatus(BaseDTO.AuthnStatus.FAILURE.name());
			userDTO.setMsg("Invalid Token");
		}
		return userDTO;
	}
}
