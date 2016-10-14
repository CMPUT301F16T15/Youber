package com.youber.cmput301f16t15.youber;

import org.junit.Test;

/**
 * Created by Reem on 2016-10-13.
 */
import static org.junit.Assert.*;
public class UserTest
{
    @Test (expected = UniqueUserNameConstaintException.class)
    public void testUniqueUsername()
    {
        UserCollection users = Helper.getUsers();

        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number" );
        User user2 = new User("username","First Name", "Last Name", "Date of birth","phone number" );

        users.add(user1);
        users.add(user2);
    }

    @Test
    public void testGetFirstName()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number" );
        assertEquals("First Name", user1.getFirstName());
    }

    @Test
    public void testSetFirstName()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number" );
        user1.setFirstName("Another name Lol");
        assertEquals("Another name Lol", user1.getFirstName());
    }


    @Test
    public void testGetLastName()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number" );
        assertEquals("Last Name", user1.getLastName());
    }

    @Test
    public void testSetLastName()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number" );
        user1.setLastName("Another name Lol");
        assertEquals("Another name Lol", user1.getLastName());
    }


    @Test
    public void testGetDateOfBirth()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number" );
        assertEquals("Date of birth", user1.getDateOfBirth());
    }

    @Test
    public void testSetDateOfBirthday()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number" );
        user1.setDateOfBirth("Another date Lol");
        assertEquals("Another date Lol", user1.getDateOfBirth());
    }


    @Test
    public void testGetPhoneNumber()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number" );
        assertEquals("phone number", user1.getPhoneNumber());
    }

    @Test
    public void testSetPhoneNumber()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number" );
        user1.setPhoneNumber("another number Lol");
        assertEquals("another number Lol", user1.getPhoneNumber());
    }


    @Test
    public void testGetUserByUsername()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number" );
        Helper.addUser(user1);
        UserCollection users = Helper.getUsers();
        assertEquals(users.get("username").getFirstName(), "First Name");
    }




}
