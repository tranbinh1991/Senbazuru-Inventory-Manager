/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.repository;


import com.senbazuru.inventory.model.Acquisition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Binh
 */
@Repository
public interface AcquistionRepository extends JpaRepository<Acquisition, Long> {


    List<Acquisition> findAll();

    Optional<Acquisition> findById(Long id);

}
