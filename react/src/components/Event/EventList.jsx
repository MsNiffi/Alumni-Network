import React, { useState, useEffect } from "react";
import Event from "./../Event/Event";
import { UserAuth } from "../../context/AuthContext";
import { getEvents, getUser } from "../../api/api";

const EventList = () => {
    const { user } = UserAuth();
    const [events, setEvents] = useState([]);
    const [currentUser, setCurrentUser] = useState([]);

    useEffect(() => {
        if (user.accessToken !== undefined) {
            getUser(user?.uid, user?.accessToken).then((resp) =>
                setCurrentUser(resp)
            );
            getEvents(user?.accessToken).then((resp) => setEvents(resp));
        }
    }, [user]);

    return events?.map((event) => {
        return (
            <Event
                key={event.eventId}
                eventId={event.eventId}
                title={event.eventName}
                description={event.eventDescription}
                userEvents={currentUser.events}
            />
        );
    });
};
export default EventList;
