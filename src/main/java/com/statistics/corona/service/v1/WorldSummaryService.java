package com.statistics.corona.service.v1;

import com.statistics.corona.repository.DataWorldSummaryRepository;
import com.statistics.corona.model.v1.DataWorldSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorldSummaryService {

    private final DataWorldSummaryRepository dataWorldSummaryRepository;

    public DataWorldSummary saveDataWorldSummary(DataWorldSummary dataWorldSummary) {
        log.info("Save new data world summary.");
        return dataWorldSummaryRepository.save(dataWorldSummary);
    }

    public List<DataWorldSummary> getAllDataWorldSummary() {
        log.debug("Get all data world summary.");
        List<DataWorldSummary> allDataWorldSummary = dataWorldSummaryRepository.findAll();
        if (allDataWorldSummary.isEmpty()) {
            log.warn("No data world summary found.");
            return allDataWorldSummary;
        }
        log.debug("Got all data world summary successfully.");
        return allDataWorldSummary;
    }

    public Optional<DataWorldSummary> getLastEntryWorldSummary() {
        log.debug("Get last entry of world summary.");
        Optional<DataWorldSummary> getLastEntryWorldSummary = dataWorldSummaryRepository.findTopByOrderByDataWorldSummaryIdDesc();
        if (getLastEntryWorldSummary.isPresent()) {
            log.debug("Found last entry of data world summary. {}", getLastEntryWorldSummary.get().getLocalDate());
            return getLastEntryWorldSummary;
        }
        log.warn("Found no last entry of data world summary.");
        return getLastEntryWorldSummary;
    }

    public Optional<DataWorldSummary> findDataWorldSummaryById(long id) {
        log.info("Find data of world summary by id {}.", id);
        return dataWorldSummaryRepository.findById(id);
    }

    public Optional<DataWorldSummary> findDataWorldSummaryByLocalTime(LocalTime localTime) {
        log.debug("Get data of world summary by last time {}.", localTime);
        Optional<DataWorldSummary> findDataWorldByTime = dataWorldSummaryRepository.findDataWorldSummaryByLocalTime(localTime);
        if (findDataWorldByTime.isPresent()) {
            log.debug("Found data of world summary by last time {}.", localTime);
            return findDataWorldByTime;
        }
        log.warn("Found no last data of world summary by local time {}.", localTime);
        return findDataWorldByTime;
    }

    public void deleteDataWorldSummary(DataWorldSummary dataWorldSummary) {
        log.info("Delete data of world {}.", dataWorldSummary);
        dataWorldSummaryRepository.delete(dataWorldSummary);
    }
}