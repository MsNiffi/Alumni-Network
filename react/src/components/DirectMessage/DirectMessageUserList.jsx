import React, { useEffect, useState } from "react";
import { getDms } from "../../api/api";
import { UserAuth } from "../../context/AuthContext";
import UserList from "./UserList";

const DirectMessageUserList = () => {
    const { user } = UserAuth();
    const [dms, setDms] = useState([]);

    useEffect(() => {
        if (user.accessToken !== undefined) {
            getDms(user.accessToken).then((resp) => setDms(resp));
        }
    }, [user]);

    const senders = [];

    // Goes through every message, and add the sender to the arrays sender
    dms?.forEach((e) => {
        senders.push(e.sender);
    });

    // Creates a new array that filters out unique senders.
    const uniqueSender = senders.reduce((prev, curr) => {
        if (!prev.find((item) => item.id === curr.id)) {
            prev.push(curr);
        }
        return prev;
    }, []);

    // If there's a sender, it shows each sender
    return uniqueSender.length > 0 ? (
        uniqueSender.map((sender) => {
            return <UserList key={sender.id} sender={sender} />;
        })
    ) : (
        <h2 className="text-center text-2xl">No DM's found</h2>
    );
};

export default DirectMessageUserList;
