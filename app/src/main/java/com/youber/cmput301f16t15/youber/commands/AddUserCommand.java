package com.youber.cmput301f16t15.youber.commands;



import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchUser;
import com.youber.cmput301f16t15.youber.users.User;


/**
 * Created by Jess on 2016-11-16.
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
