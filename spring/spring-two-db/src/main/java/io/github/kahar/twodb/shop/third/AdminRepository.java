package io.github.kahar.twodb.shop.third;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<Admin, Long> {

}