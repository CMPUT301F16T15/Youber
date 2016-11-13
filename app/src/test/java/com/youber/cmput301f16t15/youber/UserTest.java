package com.youber.cmput301f16t15.youber;

import android.util.Log;

import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchUser;
import com.youber.cmput301f16t15.youber.exceptions.UniqueUserNameConstaintException;
import com.youber.cmput301f16t15.youber.gui.LoginActivity;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import org.junit.Test;

/**
 * Created by Reem on 2016-10-13.
 */
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
public class UserTest
{
    @Test
    public void testGetFirstName()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        assertEquals("First Name", user1.getFirstName());
    }

    @Test
    public void testSetFirstName()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setFirstName("Another name Lol");
        assertEquals("Another name Lol", user1.getFirstName());
    }

    @Test
    public void testSetEmail()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setEmail("email");
        assertEquals("email", user1.getEmail());

    }

    @Test
    public void testGetEmail()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        assertEquals("email",user1.getEmail());
    }

    @Test
    public void testGetLastName()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        assertEquals("Last Name", user1.getLastName());
    }

    @Test
    public void testSetLastName()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setLastName("Another name Lol");
        assertEquals("Another name Lol", user1.getLastName());
    }


    @Test
    public void testGetDateOfBirth()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        assertEquals("Date of birth", user1.getDateOfBirth());
    }

    @Test
    public void testSetDateOfBirthday()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setDateOfBirth("Another date Lol");
        assertEquals("Another date Lol", user1.getDateOfBirth());
    }


    @Test
    public void testGetPhoneNumber()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        assertEquals("phone number", user1.getPhoneNumber());
    }

    @Test
    public void testSetPhoneNumber()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setPhoneNumber("another number Lol");
        assertEquals("another number Lol", user1.getPhoneNumber());
    }
}
