package com.guya.services;

import com.guya.DAOs.ReimbursementDAO;
import com.guya.DAOs.UserDAO;
import com.guya.models.DTOs.IncomingUserDTO;
import com.guya.models.Reimbursement;
import com.guya.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final ReimbursementDAO reimbursementDAO;

    @Autowired
    public UserService(UserDAO userDAO, ReimbursementDAO reimbursementDAO) {
        this.userDAO = userDAO;
        this.reimbursementDAO = reimbursementDAO;
    }


    //Insert a new user into the DB
    public User insertUser(IncomingUserDTO  userDTO){

        //ToDo: make sure the userDTO fileds are present and valid
        //TODO: make sure the incoming username is unique


        //we need to turn the userDTO into a user (DAO takes a user object)
        //userId will be autogenerated, so 0 is fine for now
        //The other columns comes from the DTO
        //reimbursement will be set using the reimbId from the DTO (find by ID)
        User user = new User(0,
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getRole(),
                null);

        Optional<Reimbursement> reimbursement = reimbursementDAO.findById(userDTO.getReimbId());

        //if the  Reimbursement is not present , throw an exception
        if(reimbursement.isEmpty()){
            throw new IllegalArgumentException("No reimbursement  with ID " +userDTO.getReimbId());
        } else {
            // if the reimbursement is present, set the user's reimbursement to the found reimbursement
            // .get() is the method we use to extract values from Optionals

            user.setReimbursement(reimbursement.get());

            //finally, we can send the userto the DAO
            return userDAO.save(user);
        }
    }
    public List<User> getAllUser(){

        return userDAO.findAll();
    }

    public void deleteUser(int userId){
        userDAO.deleteById(userId);
    }
}