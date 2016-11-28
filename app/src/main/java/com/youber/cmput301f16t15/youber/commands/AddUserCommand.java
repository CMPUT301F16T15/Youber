package com.youber.cmput301f16t15.youber.commands;



import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchUser;
import com.youber.cmput301f16t15.youber.users.User;


/**
 * Created by Jess on 2016-11-16.
 *
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 *
 * *
 * This class implements the command pattern to add a user to elastic search.
 *
 * @see DeleteRequestCommand
 * @see ElasticSearchUser
 * @see Command
 */

public class AddUserCommand implements Command {

    User user;
    Boolean executionState = false;

    public AddUserCommand(User u) {
        user = u;
    }

    @Override
    public void execute() {
        ElasticSearchUser.add adder = new ElasticSearchUser.add();
        adder.execute(user);
        executionState = true;
    }

    @Override
    public void unexecute() {

    }

    @Override
    public Boolean isExecuted() {
        return executionState;
    }

    public User getUser() {
        return user;
    }
}
