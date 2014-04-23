package com.ugmt.client.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.ugmt.client.AbstractServiceClient;
import com.ugmt.common.dto.UserDTO;

/**
 * 
 * @author Puneet
 * 
 */
public class UserServiceClient extends AbstractServiceClient {
    private static String endPointUrl = "http://localhost:8080/ugmt-core/rest/user/";
    
    public static void main(String[] args) throws ClientProtocolException, IOException {
        UserServiceClient client = new UserServiceClient();

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("appsecret", "apps3cr3t");
		
        UserDTO user = new UserDTO();
        user.setName("Puneet Jaiswal" );
        user.setEmail("punit.kj"+System.currentTimeMillis()+"@gmail.com");
        user.setPassword("password");

        // create user
        UserDTO userret = client.post(endPointUrl, user, UserDTO.class, headers);
        System.out.println(userret.getId());
        System.out.println(userret.getStatus());
        System.out.println(userret.getMsg());
        
        // get user
        UserDTO user2 = client.get(endPointUrl+userret.getId(), UserDTO.class, headers);
        System.out.println(user2.getId());
        System.out.println(user2.getStatus());
        System.out.println(user2.getMsg());
        
        // update user
        
//        user2.setName("Puneet");
//        client.put(endPointUrl, user2, UserDTO.class, null);

        // delete user
//        client.delete(endPointUrl+user2.getId(), UserDTO.class, null);
    }
}
