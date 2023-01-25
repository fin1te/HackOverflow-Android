package com.fin1te.hackoverflow.model

class OnlineEventRepo {
    //solo
    val eventList: List<Event>
        get() {
            val eventList = ArrayList<Event>()
            eventList.add(Event("01-02-2023", "Booking Starts", "registrations open"))

            eventList.add(Event("10-03-2023", "Booking Ends", "registrations closes"))




            return eventList
        }
}