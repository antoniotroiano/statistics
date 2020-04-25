package com.valtech.statistics.controller;

import com.valtech.statistics.model.DataGermany;
import com.valtech.statistics.model.DataGermanySummary;
import com.valtech.statistics.service.GermanyService;
import com.valtech.statistics.service.GermanySummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("covid19/germany")
@RequiredArgsConstructor
public class GermanyController {

    private final GermanyService germanyService;
    private final GermanySummaryService germanySummaryService;

    @GetMapping
    public String showDataGermany(Model model) {
        log.info("Invoke show data of germany.");

        Optional<DataGermany> dataGermany = germanyService.getLastEntryGermany();
        if (dataGermany.isPresent()) {
            model.addAttribute("dataGermany", dataGermany);
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.GERMAN);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyy", Locale.GERMAN);
            DateTimeFormatter outputFormatterTime = DateTimeFormatter.ofPattern("HH:mm", Locale.GERMAN);
            LocalDate date = LocalDate.parse(dataGermany.get().getLastUpdate(), inputFormatter);
            LocalTime time = LocalTime.parse(dataGermany.get().getLastUpdate(), inputFormatter);
            String formattedDate = outputFormatter.format(date);
            String formattedTime = outputFormatterTime.format(time);
            model.addAttribute("date", formattedDate + " " + formattedTime);

            log.info("Show last entry of germany {}", dataGermany.get().getLastUpdate());
        } else {
            model.addAttribute("noFirstDataGermany", true);
            log.warn("No last entry in database of germany.");
        }

        List<Optional<DataGermany>> yesterdayLastEntry = germanyService.getLastDateLastEntry();
        int newConfirmed = dataGermany.get().getConfirmed() - yesterdayLastEntry.get(0).get().getConfirmed();
        model.addAttribute("testNewConfirmed", newConfirmed);

        Optional<DataGermanySummary> dataGermanySummary = germanySummaryService.getLastEntryGermanySummary();
        if (dataGermanySummary.isPresent()) {
            model.addAttribute("dataGermanySummary", dataGermanySummary);
            log.info("Show last entry for germany summary {}.", dataGermanySummary.get().getLocalDate());
        } else {
            model.addAttribute("noFirstDataGermany", true);
            log.warn("No last daily entry in database for germany summary.");
        }

        List<DataGermany> dataGermanyList = germanyService.getAllDataGermany();
        if (dataGermanyList.isEmpty()) {
            model.addAttribute("noDataGermany", true);
            log.warn("Found no data germany.");
            return "covid19Germany";
        }
        model.addAttribute("confirmedListG", dataGermanyList
                .stream()
                .map(DataGermany::getConfirmed)
                .collect(Collectors.toList()));
        model.addAttribute("recoveredListG", dataGermanyList
                .stream()
                .map(DataGermany::getRecovered)
                .collect(Collectors.toList()));
        model.addAttribute("deathsListG", dataGermanyList
                .stream()
                .map(DataGermany::getDeaths)
                .collect(Collectors.toList()));
        model.addAttribute("datesG", dataGermanyList
                .stream()
                .map(DataGermany::getLocalDate)
                .collect(Collectors.toList()));
        log.debug("Return data of germany.");
        return "covid19Germany";
    }

    @GetMapping("/update")
    public String updateDatabase(Model model) throws IOException {
        germanyService.saveDataOfJson();
        return showDataGermany(model);
    }
}