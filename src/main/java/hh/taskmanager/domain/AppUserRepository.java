package hh.taskmanager.domain;

import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

// this method will be used by Spring Security to load user details during authentication
<Optional>AppUser findByUsername(String username);

}
