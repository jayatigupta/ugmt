package com.ugmt.core.endpoint;

import java.security.NoSuchAlgorithmException;

import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ugmt.common.dto.BaseDTO;
import com.ugmt.common.dto.UserDTO;
import com.ugmt.common.exception.InvalidDataException;
import com.ugmt.core.command.UserCommand;

/**
 * 
 * @author Puneet
 * 
 */
@Path("/user")
public class UserService extends AbstractService {

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@Context HttpHeaders httpHeaders,
			@PathParam("id") String id) {
		UserCommand command = new UserCommand();
		try {
			validateHeader(httpHeaders);
			return Response.status(Status.OK).entity(command.retrieve(id))
					.build();
		} catch (InvalidDataException e) {
			UserDTO user = new UserDTO();
			user.setMsg(e.getMessage());
			user.setStatus(BaseDTO.AuthnStatus.FAILURE.name());
			return Response.status(Status.OK).entity(user).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(@Context HttpHeaders httpHeaders, UserDTO userDTO)
			throws NoSuchAlgorithmException {
		UserCommand command = new UserCommand();
		try {
			validateHeader(httpHeaders);
			return Response.status(Status.OK).entity(command.create(userDTO))
					.build();
		} catch (InvalidDataException e) {
			UserDTO user = new UserDTO();
			user.setMsg(e.getMessage());
			user.setStatus(BaseDTO.AuthnStatus.FAILURE.name());
			return Response.status(Status.OK).entity(user).build();
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(@Context HttpHeaders httpHeaders, UserDTO userDTO) {
		UserCommand command = new UserCommand();
		try {
			validateHeader(httpHeaders);
			return Response.status(Status.OK).entity(command.update(userDTO))
					.build();
		} catch (Exception e) {
			UserDTO user = new UserDTO();
			user.setMsg(e.getMessage());
			user.setStatus(BaseDTO.AuthnStatus.FAILURE.name());
			return Response.status(Status.OK).entity(user).build();
		}
	}

	/*
	 * User deletion should not be allowed ??
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(@Context HttpHeaders httpHeaders,
			@PathParam("id") String id) {
		UserCommand command = new UserCommand();
		try {
			validateHeader(httpHeaders);
			return Response.status(Status.OK).entity(command.delete(id))
					.build();
		} catch (Exception e) {
			UserDTO user = new UserDTO();
			user.setMsg(e.getMessage());
			user.setStatus(BaseDTO.AuthnStatus.FAILURE.name());
			return Response.status(Status.OK).entity(user).build();
		}
	} */
}
