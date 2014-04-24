package com.ugmt.core.endpoint;

import javax.ws.rs.GET;
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
import com.ugmt.core.command.LoginValidateCommand;

@Path("verifytoken")
public class TokenValidationService extends AbstractService {

	@GET
	@Path("{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getToeknDetails(@Context HttpHeaders httpHeaders,
			@PathParam("token") String token) {
		try {
			validateHeader(httpHeaders);
			LoginValidateCommand command = new LoginValidateCommand();
			return Response.status(Status.OK)
					.entity(command.getUserDetails(token)).build();
		} catch (InvalidDataException e) {
			UserDTO user = new UserDTO();
			user.setMsg(e.getMessage());
			user.setStatus(BaseDTO.AuthnStatus.FAILURE.name());
			return Response.status(Status.OK).entity(user).build();
		}
	}
}
