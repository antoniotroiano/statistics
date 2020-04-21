package com.valtech.statistics.controller;

import com.valtech.statistics.model.DataGermany;
import com.valtech.statistics.model.DataWorld;
import com.valtech.statistics.model.DataWorldSummary;
import com.valtech.statistics.service.GermanyService;
import com.valtech.statistics.service.WorldService;
import com.valtech.statistics.service.WorldSummaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("covid19")
@RequiredArgsConstructor
public class Covid19Controller {

    private final WorldService worldService;
    private final GermanyService germanyService;
    private final WorldSummaryService worldSummaryService;

    @GetMapping("/world")
    public String showDataWorld(Model model) {
        log.info("Invoke controller to show data of world.");
        DataWorld dataWorldNull = new DataWorld(0, 0, 0, "0000-00-00");

        Optional<DataWorld> dataWorld = worldService.getLastEntryWorld().isEmpty() ? Optional.of(dataWorldNull) : worldService.getLastEntryWorld();
        worldService.getLastEntryWorld();
        model.addAttribute("confirmed", dataWorld.get().getConfirmed());
        model.addAttribute("recovered", dataWorld.get().getRecovered());
        model.addAttribute("deaths", dataWorld.get().getDeaths());
        model.addAttribute("lastUpdate", dataWorld.get().getLastUpdate());

        Optional<DataWorldSummary> dataWorldSummary = worldSummaryService.getLastEntryWorldSummary();
        if (dataWorldSummary.isPresent()) {
            model.addAttribute("newConfirmed", dataWorldSummary.get().getNewConfirmed());
            model.addAttribute("totalConfirmed", dataWorldSummary.get().getTotalConfirmed());
            model.addAttribute("newDeaths", dataWorldSummary.get().getNewDeaths());
            model.addAttribute("totalDeaths", dataWorldSummary.get().getTotalDeaths());
            model.addAttribute("newRecovered", dataWorldSummary.get().getNewRecovered());
            model.addAttribute("totalRecovered", dataWorldSummary.get().getTotalRecovered());
            model.addAttribute("date", DateTimeFormatter.ofPattern("dd.MM.yyy")
                    .format(dataWorldSummary.get().getLocalDate()));
            model.addAttribute("time", dataWorldSummary.get().getLocalTime());
        } else {
            model.addAttribute("noFirstData", true);
        }
        model.addAttribute("confirmedList", worldService.getAllData()
                .stream()
                .map(DataWorld::getConfirmed)
                .collect(Collectors.toList()));
        model.addAttribute("recoveredList", worldService.getAllData()
                .stream()
                .map(DataWorld::getRecovered)
                .collect(Collectors.toList()));
        model.addAttribute("deathsList", worldService.getAllData()
                .stream()
                .map(DataWorld::getDeaths)
                .collect(Collectors.toList()));
        model.addAttribute("dates", worldService.getAllData()
                .stream()
                .map(DataWorld::getLocalDate)
                .collect(Collectors.toList()));
        log.debug("Return data of world.");
        return "covid19World";
    }

    @GetMapping("/germany")
    public String showDataGermany(Model model) {
        log.info("Invoke controller to show data of germany.");
        DataGermany dataGermanyNull = new DataGermany(0, 0, 0, "0000-00-00");

        Optional<DataGermany> dataGermany = germanyService.getLastEntryGermany().isEmpty() ? Optional.of(dataGermanyNull) : germanyService.getLastEntryGermany();
        germanyService.getLastEntryGermany();
        model.addAttribute("confirmed", dataGermany.get().getConfirmed());
        model.addAttribute("recovered", dataGermany.get().getRecovered());
        model.addAttribute("deaths", dataGermany.get().getDeaths());
        model.addAttribute("lastUpdate", dataGermany.get().getLastUpdate());

        model.addAttribute("noFirstData", true);

        model.addAttribute("confirmedList", germanyService.getAllDataGermany()
                .stream()
                .map(DataGermany::getConfirmed)
                .collect(Collectors.toList()));
        model.addAttribute("recoveredList", germanyService.getAllDataGermany()
                .stream()
                .map(DataGermany::getRecovered)
                .collect(Collectors.toList()));
        model.addAttribute("deathsList", germanyService.getAllDataGermany()
                .stream()
                .map(DataGermany::getDeaths)
                .collect(Collectors.toList()));
        model.addAttribute("dates", germanyService.getAllDataGermany()
                .stream()
                .map(DataGermany::getLocalDate)
                .collect(Collectors.toList()));
        log.debug("Return data of germany.");
        return "covid19Germany";
    }
}