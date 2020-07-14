package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.service.TradeHistoryService;

@RestController
@RequestMapping("/trade")
public class TradeHistoryController {

    @Autowired
    TradeHistoryService tradeHistoryService;
}
