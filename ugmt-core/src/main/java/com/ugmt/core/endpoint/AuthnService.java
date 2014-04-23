package com.ugmt.core.endpoint;

import java.security.NoSuchAlgorithmException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ugmt.common.dto.AuthnContext;
import com.ugmt.common.dto.AuthnResponse;
import com.ugmt.common.dto.BaseDTO;
import com.ugmt.common.exception.InvalidDataException;
import com.ugmt.core.command.LoginValidateCommand;

@Path("/authn")
public class AuthnService extends AbstractService {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticate(@Context HttpHeaders httpHeaders,
			AuthnContext authnContext) throws NoSuchAlgorithmException {
		LoginValidateCommand loginCommand = new LoginValidateCommand();
		try {
			validateHeader(httpHeaders);
			return Response.status(Status.OK)
					.entity(loginCommand.authenticate(authnContext)).build();
		} catch (InvalidDataException e) {
			AuthnResponse authnResponse = new AuthnResponse();
			authnResponse.setMsg(e.getMessage());
			authnResponse.setStatus(BaseDTO.AuthnStatus.FAILURE.name());
			return Response.status(Status.OK).entity(authnResponse).build();
		}
	}
}
