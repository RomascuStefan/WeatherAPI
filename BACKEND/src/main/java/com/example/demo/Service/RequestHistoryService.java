package com.example.demo.Service;

import com.example.demo.DTO.Mapper.RequestHistoryMapper;
import com.example.demo.DTO.RequestHistoryDTO;
import com.example.demo.DTO.WeatherResponseDTO;
import com.example.demo.Model.RequestHistory;
import com.example.demo.Model.User;
import com.example.demo.Repository.RequestHistoryDAO;
import com.example.demo.Repository.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RequestHistoryService {

    private final RequestHistoryDAO requestHistoryDAO;

    @Autowired
    public RequestHistoryService(RequestHistoryDAO requestHistoryDAO) {
        this.requestHistoryDAO = requestHistoryDAO;
    }

    public void saveRequestHistory(User user, float lat, float lon, Boolean q, Boolean aqi, WeatherResponseDTO response) {
        RequestHistory history = new RequestHistory();

        history.setLat(String.valueOf(lat));
        history.setLon(String.valueOf(lon));
        history.setResponse(response.toString());

        if(q!=null)
            history.setQ(q);

        if(aqi!=null)
            history.setAqi(aqi);

        history.setUser(user);

        requestHistoryDAO.save(history);
    }

    public Page<RequestHistoryDTO> findAll(int page, int itemPerPage) {
        Page<RequestHistory> historyPage = requestHistoryDAO.findAll(PageRequest.of(page, itemPerPage));
        return historyPage.map(RequestHistoryMapper::toDTO);
    }
}
