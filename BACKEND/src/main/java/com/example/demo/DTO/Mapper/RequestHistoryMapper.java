package com.example.demo.DTO.Mapper;

import com.example.demo.DTO.RequestHistoryDTO;
import com.example.demo.Model.RequestHistory;

import java.util.ArrayList;
import java.util.List;

public class RequestHistoryMapper {

    public static RequestHistoryDTO toDTO(RequestHistory requestHistory) {
        RequestHistoryDTO requestHistoryDTO = new RequestHistoryDTO();

        requestHistoryDTO.setLon(requestHistory.getLon());
        requestHistoryDTO.setLat(requestHistory.getLat());
        requestHistoryDTO.setResponse(requestHistory.getResponse());
        requestHistoryDTO.setUsername(requestHistory.getUser().getUsername());

        return requestHistoryDTO;
    }

    public static List<RequestHistoryDTO> toDTO(List<RequestHistory> listRequestHistory) {
        List<RequestHistoryDTO> listRequestHistoryDTO = new ArrayList<>();

        for(RequestHistory requestHistory : listRequestHistory) {
            listRequestHistoryDTO.add(toDTO(requestHistory));
        }

        return listRequestHistoryDTO;
    }
}
