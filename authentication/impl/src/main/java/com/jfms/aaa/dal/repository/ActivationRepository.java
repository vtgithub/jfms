package com.jfms.aaa.dal.repository;

import com.jfms.aaa.dal.entity.ActivationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationRepository extends CrudRepository<ActivationEntity, String> {
//    @Query("select a from ActivationEntity a where a.activationCode = :activationCode and a.used = :isUsed")
    ActivationEntity findActivationEntityByActivationCodeAndIsUsedAndMobileNumber(
            String activationCode,
            Boolean isUsed,
            String mobileNumber
    );
    ActivationEntity findTopByMobileNumberOrderByCreationTimeDesc(String mobileNumber);
}
