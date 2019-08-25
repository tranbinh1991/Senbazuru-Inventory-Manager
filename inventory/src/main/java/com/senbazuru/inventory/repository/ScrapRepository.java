/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.repository;


import com.senbazuru.inventory.model.Acquisition;
import com.senbazuru.inventory.model.Scrap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Binh
 */
@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {


    List<Scrap> findAll();

    Optional<Scrap> findById(Long id);

}
