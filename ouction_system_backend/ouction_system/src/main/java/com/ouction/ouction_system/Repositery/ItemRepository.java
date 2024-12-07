package com.ouction.ouction_system.Repositery;

import com.ouction.ouction_system.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ItemRepository extends JpaRepository<Item,Long> {

}
