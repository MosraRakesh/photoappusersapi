package com.photoapp.api.users.photoappusersapi.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<UsersEntity, Long> {

	UsersEntity findByEmailId(String email);
}
