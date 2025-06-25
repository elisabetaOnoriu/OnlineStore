package com.beci.product_service.repository;

import com.beci.product_service.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

    List<Address> findByAddressType(String addressType);

    @Query("{'city': {$regex: ?0, $options: 'i'}}")
    List<Address> findByCityContainingIgnoreCase(String city);

    @Query("{'state': {$regex: ?0, $options: 'i'}}")
    List<Address> findByStateContainingIgnoreCase(String state);

    @Query("{'country': {$regex: ?0, $options: 'i'}}")
    List<Address> findByCountryContainingIgnoreCase(String country);

    @Query("{'postalCode': ?0}")
    List<Address> findByPostalCode(String postalCode);

    @Query("{'isDefault': true}")
    List<Address> findDefaultAddresses();
}
