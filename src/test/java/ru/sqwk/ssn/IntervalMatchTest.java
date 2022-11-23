package ru.sqwk.ssn;

import lombok.Builder;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.sqwk.ssn.IntervalMatchTest.EventStatus.END;
import static ru.sqwk.ssn.IntervalMatchTest.EventStatus.START;
import static ru.sqwk.ssn.IntervalMatchTest.EventType.MASTER;
import static ru.sqwk.ssn.IntervalMatchTest.EventType.SLAVE;

/**
 * Описание класса
 */
public class IntervalMatchTest {

    enum EventStatus {
        END,
        START;
    }

    enum EventType {
        MASTER,
        SLAVE;

    }

    @Builder
    static class Event implements Comparable<Event>{
        EventStatus status;
        EventType type;
        int query;
        int bound;

        @Override
        public int compareTo(Event o) {
            if (this.bound == o.bound) {
                return Integer.compare(this.status.ordinal(), o.status.ordinal());
            } else {
                return Integer.compare(this.bound, o.bound);
            }
        }

        @Override
        public String toString() {
            return "Event{" +
                    "status=" + status +
                    ", type=" + type +
                    ", query=" + query +
                    ", bound=" + bound +
                    '}';
        }
    }

    @Builder
    @ToString
    static class Match {
        int start;
        int end;
        int masterQuery;
        int slaveQuery;
    }


    public static void main(String[] args){
        List<Event> events = new java.util.ArrayList<>(List.of(
                Event.builder()
                        .status(START)
                        .type(MASTER)
                        .query(0)
                        .bound(1)
                        .build(),
                Event.builder()
                        .status(END)
                        .type(MASTER)
                        .query(0)
                        .bound(10)
                        .build(),

                Event.builder()
                        .status(START)
                        .type(SLAVE)
                        .query(1)
                        .bound(3)
                        .build(),
                Event.builder()
                        .status(END)
                        .type(SLAVE)
                        .query(1)
                        .bound(5)
                        .build(),

                Event.builder()
                        .status(START)
                        .type(SLAVE)
                        .query(2)
                        .bound(8)
                        .build(),
                Event.builder()
                        .status(END)
                        .type(SLAVE)
                        .query(2)
                        .bound(10)
                        .build(),

                Event.builder()
                        .status(START)
                        .type(SLAVE)
                        .query(3)
                        .bound(7)
                        .build(),
                Event.builder()
                        .status(END)
                        .type(SLAVE)
                        .query(3)
                        .bound(9)
                        .build()
        ));

        Collections.sort(events);
        System.out.println(events);

//        Map<Integer, Set<Integer>> masterToSlaves = new HashMap<>();
        Map<Integer, Integer> actualSlaveQueriesStart = new HashMap<>();
        Map<Integer, Integer> actualMasterQueriesStart = new HashMap<>();
        List<Match> matches = new ArrayList<>();
        // Match
        for (Event event : events) {
            if (event.status == START) {
                if (event.type == MASTER) {
                    actualMasterQueriesStart.put(event.query, event.bound);
                } else {
                    actualSlaveQueriesStart.put(event.query, event.bound);
                }
            } else {
                if (event.type == MASTER) {
                    actualSlaveQueriesStart.keySet().forEach(s -> {
                        Integer slaveStart = actualSlaveQueriesStart.get(s);
                        Integer masterStart = actualMasterQueriesStart.get(event.query);
                        matches.add(Match.builder()
                                        .start(Math.max(slaveStart, masterStart))
                                        .end(event.bound)
                                        .masterQuery(event.query)
                                        .slaveQuery(s)
                                .build());
                    });
                    actualMasterQueriesStart.remove(event.query);
                } else {
                    actualMasterQueriesStart.keySet().forEach(m -> {
                        Integer masterStart = actualMasterQueriesStart.get(m);
                        Integer slaveStart = actualSlaveQueriesStart.get(event.query);
                        matches.add(Match.builder()
                                .start(Math.max(slaveStart, masterStart))
                                .end(event.bound)
                                .masterQuery(m)
                                .slaveQuery(event.query)
                                .build());
                    });
                    actualSlaveQueriesStart.remove(event.query);
                }
            }
        }

        System.out.println(matches);


    }


}
