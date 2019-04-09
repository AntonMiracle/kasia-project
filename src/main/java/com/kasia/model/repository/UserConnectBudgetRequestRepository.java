package com.kasia.model.repository;

import com.kasia.model.UserConnectBudgetRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserConnectBudgetRequestRepository extends CrudRepository<UserConnectBudgetRequest, Long> {

    @Query("SELECT ucbr FROM UserConnectBudgetRequest ucbr WHERE ucbr.fromUserId=:id")
    Set<UserConnectBudgetRequest> findFromUserId(@Param("id") long id);

    @Query("SELECT ucbr FROM UserConnectBudgetRequest ucbr WHERE ucbr.toUserId=:id")
    Set<UserConnectBudgetRequest> findToUserId(@Param("id") long id);

    @Query("SELECT ucbr FROM UserConnectBudgetRequest ucbr WHERE ucbr.toUserId=:toId AND ucbr.fromUserId=:fromId ")
    Set<UserConnectBudgetRequest> findToUserIdAndFromUserId(@Param("toId") long toId, @Param("fromId") long fromId);
}
