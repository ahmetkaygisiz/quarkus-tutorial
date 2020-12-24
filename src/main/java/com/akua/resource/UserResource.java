package com.akua.resource;

import com.akua.api.Response;
import com.akua.domain.User;
import com.akua.repository.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/users")
public class UserResource {

    private final UserRepository userRepository;

    public UserResource(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> all(){
        return userRepository.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> get(@PathParam("id") UUID id){
        return userRepository.findById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(User user){
        boolean status = userRepository.insert(new User(UUID.randomUUID(), user.getEmail(), user.getPassword()));

        if (status)
            return new Response("User created!");
        return new Response("Something goes wrong");
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response put(@PathParam("id") UUID id, User user){
        if ( userRepository.findById(id) == null){
            throw new NotFoundException("User not found!");
        }
        boolean status = userRepository.update(new User(id, user.getEmail(), user.getPassword()));

        if (status)
            return new Response("User updated!");
        return new Response("Something goes wrong");
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") UUID id){
        if ( userRepository.findById(id) == null){
            throw new NotFoundException("User not found");
        }
        boolean status = userRepository.deleteById(id);

        if (status)
            return new Response("User deleted!");
        return new Response("Something goes wrong");
    }
}
