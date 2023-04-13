import React, { useState, useEffect } from "react";
import Group from "./Group";
import { UserAuth } from "../../context/AuthContext";
import { getGroups, getUser } from "../../api/api";

const GroupList = () => {
    const { user } = UserAuth();
    const [groups, setGroupes] = useState([]);
    const [currentUser, setCurrentUser] = useState([]);

    useEffect(() => {
        if (user.accessToken !== undefined) {
            getUser(user?.uid, user?.accessToken).then((resp) =>
                setCurrentUser(resp)
            );
            getGroups(user?.accessToken).then((resp) => setGroupes(resp));
        }
    }, [user]);

    return groups?.map((gang) => {
        return (
            <Group
                key={gang.gangId}
                gangId={gang.gangId}
                title={gang.gangName}
                description={gang.gangDescription}
                userGangs={currentUser.gangs}
            />
        );
    });
};
export default GroupList;
