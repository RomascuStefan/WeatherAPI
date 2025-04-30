package com.example.demo.Controller;

import com.example.demo.DTO.RequestHistoryDTO;
import com.example.demo.Model.RequestHistory;
import com.example.demo.Service.RequestHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request_history")
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Authorization")
public class RequestHistoryController {
    private final RequestHistoryService requestHistoryService;

    @Autowired
    public RequestHistoryController(RequestHistoryService requestHistoryService) {
        this.requestHistoryService = requestHistoryService;
    }

    @GetMapping
    public ResponseEntity<Page<RequestHistoryDTO>> getAll(@RequestParam int page, @RequestParam(required = false, defaultValue = "10") int itemPerPage) {
        Page<RequestHistoryDTO> historyPage = requestHistoryService.findAll(page, itemPerPage);
        return ResponseEntity.ok(historyPage);
    }
}
