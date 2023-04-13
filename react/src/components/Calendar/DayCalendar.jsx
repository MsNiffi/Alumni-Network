import dayGridPlugin from "@fullcalendar/daygrid";
import FullCalendar from "@fullcalendar/react";

/**
 *
 * @param {Object[]} events
 * Takes in an array of events, and render them in the calendar
 */
const DayCalendar = ({ events }) => {
    return (
        <div className="w-3/5">
            <FullCalendar
                plugins={[dayGridPlugin]}
                initialView="dayGridMonth"
                events={events.map((event) => {
                    return {
                        title: event.eventName,
                        start: event.startTime,
                        end: event.endTime,
                        groupId: event.eventId,
                        description: event.eventDescription,
                    };
                })}
            />
        </div>
    );
};

export default DayCalendar;
