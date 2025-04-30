package com.example.demo.Repository;

import com.example.demo.Model.RequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestHistoryDAO extends JpaRepository<RequestHistory, Long> {


}
