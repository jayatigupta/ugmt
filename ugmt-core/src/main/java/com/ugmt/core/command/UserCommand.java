package com.ugmt.core.command;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

import com.ugmt.common.dto.UserDTO;
import com.ugmt.common.exception.InvalidDataException;
import com.ugmt.core.crypt.HashingUtil;
import com.ugmt.core.dao.UserDAO;
import com.ugmt.core.entity.User;
import com.ugmt.core.util.UniqueIDGenerator;

/**
 * 
 * @author Puneet
 * 
 */
public class UserCommand extends AbstractCommand {

	private UserDAO userDAO = appContext.getBean(UserDAO.class);

	public UserDTO create(UserDTO userDTO) throws NoSuchAlgorithmException {
		User user = convertFromDTO(userDTO);
		// add audit details
		user.setCreatedAt(new Timestamp(new Date().getTime()));
		user.setModifiedAt(new Timestamp(new Date().getTime()));
		userDAO.save(user);
		userDTO.setId(user.getId());
		return userDTO;
	}

	public UserDTO retrieve(String id) throws InvalidDataException {
		User user = userDAO.findOne(id);
		if(user==null) {
			throw new InvalidDataException("user not found with id : " + id);
		}
		return convertFromRecord(user);
	}

	public UserDTO update(UserDTO userDTO) throws InvalidDataException {
		User user = userDAO.findOne(userDTO.getId());
		if (user == null) {
			throw new InvalidDataException("user not found with id : "
					+ userDTO.getId());
		}
		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getName());
		// user.setPassword(userDTO.getPassword()); /* not saving updated
		// password as it should be done via a change password service */
		userDAO.save(user);
		return userDTO;
	}

	public UserDTO delete(String id) {
		userDAO.delete(id);
		UserDTO userDTO = new UserDTO();
		userDTO.setId(id);
		return userDTO;
	}

	private User convertFromDTO(UserDTO userDTO)
			throws NoSuchAlgorithmException {
		// also do the validation of all/specific fields from the DTO here.
		User user = new User();
		user.setId(UniqueIDGenerator.generateUniqueID());
		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getName());
		/* hash the password before saving it */
		user.setPassword(HashingUtil.generateSHA512Hash(userDTO.getPassword()));
		return user;
	}

	private UserDTO convertFromRecord(User userDB) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(userDB.getId());
		userDTO.setName(userDB.getName());
		userDTO.setEmail(userDB.getEmail());
		// userDTO.setPassword(userDB.getPassword());
		// we might not want to return this to caller ?
		return userDTO;
	}
}
