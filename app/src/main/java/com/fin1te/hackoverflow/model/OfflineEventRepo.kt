package com.fin1te.hackoverflow.model

class OfflineEventRepo {
    //solo
    val eventList: List<Event>
        get() {
            val eventList = ArrayList<Event>()
            eventList.add(Event("15-02-2023", "Booking Starts", "registrations open"))

            eventList.add(Event("01-03-2023", "Booking Ends", "registrations closes"))

            eventList.add(Event("16-03-2023", "Day 01", "11:00 - Reach Venue"))
            eventList.add(Event("16-03-2023", "Day 01", "11:30 - Check In"))
            eventList.add(Event("16-03-2023", "Day 01", "11:30 - Get T-Shirt & ID "))
            eventList.add(Event("16-03-2023", "Day 01", "13:00 - Lunch"))
            eventList.add(Event("16-03-2023", "Day 01", "14:00 - Opening Ceremony"))
            eventList.add(Event("16-03-2023", "Day 01", "16:00 - Lab Allotment"))
            eventList.add(Event("16-03-2023", "Day 01", "17:00 - Coding Begins"))
            eventList.add(Event("16-03-2023", "Day 01", "21:00 - Dinner"))
            eventList.add(Event("16-03-2023", "Day 01", "22:00 - Networking Session"))
            eventList.add(Event("16-03-2023", "Day 01", "23:00 - Coding"))

            eventList.add(Event("17-03-2023", "Day 02", "08:00 - Breakfast"))
            eventList.add(Event("17-03-2023", "Day 02", "09:00 - Coding"))
            eventList.add(Event("17-03-2023", "Day 02", "13:00 - Lunch"))
            eventList.add(Event("17-03-2023", "Day 02", "14:00 - Coding"))
            eventList.add(Event("17-03-2023", "Day 02", "21:00 - Dinner"))
            eventList.add(Event("17-03-2023", "Day 02", "22:00 - Networking"))
            eventList.add(Event("17-03-2023", "Day 02", "23:00 - Coding"))

            eventList.add(Event("18-03-2023", "Day 03", "07:00 - Project Submission"))
            eventList.add(Event("18-03-2023", "Day 03", "08:00 - Breakfast"))
            eventList.add(Event("18-03-2023", "Day 03", "09:00 - Judging"))
            eventList.add(Event("18-03-2023", "Day 03", "12:00 - Winner Announcement"))
            eventList.add(Event("18-03-2023", "Day 03", "Prizes for winners"))
            eventList.add(Event("18-03-2023", "Day 03", "12:00 - Closing Ceremony"))
            eventList.add(Event("18-03-2023", "Day 03", "13:30 - Job Fair"))

            eventList.add(Event("18-03-2023", "Conclusion", "Hackathon Ends"))

            return eventList
        }
}