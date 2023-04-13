import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { getGroupPosts } from "../../api/api";
import { UserAuth } from "../../context/AuthContext";
import CommonPostList from "../CommonPostList/CommonPostList";

const GroupPostList = ({ parent, idGroup }) => {
    const { user } = UserAuth();
    const [groupPosts, setGroupPosts] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        if (user.accessToken !== undefined) {
            getGroupPosts(user?.accessToken, idGroup).then((resp) =>
                setGroupPosts(resp)
            );
            setIsLoading(false);
        }
    }, [user]);

    return (
        <CommonPostList
            posts={groupPosts}
            parent={parent}
            search={""}
            color="bg-secondary"
            isLoading={isLoading}
        />
    );
};

export default GroupPostList;
