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
    @Query("Select * From client_list where special is 1")
    List<Client> getClientListSpecial();
    @Query("Select * From client_list where importance is :importance")
    List<Client> getClientListImportance(int importance);
    @Query("Select * From client_list  where name Like '%' || :name || '%' ")
    List<Client> getClientListName(String name);
    @Insert
    void insertClient(Client client);
    @Update
    void updateClient(Client client);
    @Delete
    void deleteClient(Client client);

}
