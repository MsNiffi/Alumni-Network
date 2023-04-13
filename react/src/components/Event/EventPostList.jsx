import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { getEventPosts } from "../../api/api";
import { UserAuth } from "../../context/AuthContext";
import CommonPostList from "../CommonPostList/CommonPostList";

const EventPostList = ({ parent, idEvent }) => {
    const { user } = UserAuth();
    const [eventPosts, setEventPosts] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        if (user.accessToken !== undefined) {
            getEventPosts(user?.accessToken, idEvent).then((resp) =>
                setEventPosts(resp)
            );
            setIsLoading(false);
        }
    }, [user]);

    return (
        <CommonPostList
            posts={eventPosts}
            parent={parent}
            search={""}
            color="bg-secondary"
            isLoading={isLoading}
        />
    );
};

export default EventPostList;
