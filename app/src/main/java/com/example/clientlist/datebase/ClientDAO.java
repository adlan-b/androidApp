package com.example.clientlist.datebase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClientDAO
{
    @Query("Select * From client_list")
    List<Client> getClientList();
    @Insert
    void insertClient(Client client);
    @Update
    void updateClient(Client client);
    @Delete
    void deleteClient(Client client);

}
