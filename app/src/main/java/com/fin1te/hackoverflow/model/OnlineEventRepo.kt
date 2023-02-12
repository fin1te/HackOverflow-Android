package com.fin1te.hackoverflow.model

class OnlineEventRepo {
    //solo
    val eventList: List<Event>
        get() {
            val eventList = ArrayList<Event>()
            eventList.add(Event("15-02-2023", "Booking Starts", "registrations open"))

            eventList.add(Event("10-03-2023", "Booking Ends", "registrations closes"))

            eventList.add(Event("10-03-2023", "Join Discord Server", "Before 10th March"))
            eventList.add(Event("16-03-2023", "Day 01", "09:00 - Orientation"))
            eventList.add(Event("16-03-2023", "Day 01", "09:00 - Opening Ceremony"))
            eventList.add(Event("16-03-2023", "Day 01", "10:00 - Hackathon begins"))
            eventList.add(Event("16-03-2023", "Day 01", "18:00 - Networking Session"))
            eventList.add(Event("16-03-2023", "Day 01", "19:00 - Coding"))

            eventList.add(Event("17-03-2023", "Day 02", "10:00 - Tech Workshop"))
            eventList.add(Event("17-03-2023", "Day 02", "12:00 - Coding"))
            eventList.add(Event("17-03-2023", "Day 02", "22:00 - Code Submission"))
            eventList.add(Event("17-03-2023", "Day 02", "14:00 - Project review"))

            eventList.add(Event("18-03-2023", "Day 03", "12:00 - Online Job Fair (I.A)"))
            eventList.add(Event("18-03-2023", "Day 03", "18:00 - Winners Announcement"))

            eventList.add(Event("18-03-2023", "Conclusion", "Hackathon Ends"))


            return eventList
        }
}