import { API_URL, AUTH_HEADERS } from "./apiConfig";

// Universal function to get/post/put/patch data. Makes it easier to create and implement API calls.

const fetchData = async (url, token, method, postData) => {
    try {
        const headers = await AUTH_HEADERS(token);
        const options = {
            method,
            headers,
            body: postData && JSON.stringify(postData),
        };
        const response = await fetch(`${API_URL}${url}`, options);
        if (
            !response.ok &&
            response.status !== 404 &&
            response.status !== 201
        ) {
            throw new Error("Request could not be completed");
        }
        // Posting data will not return any data. That's why it's ok to return response.text() to not get warnings in frontend
        return method == "POST" ? await response.text() : await response.json();
    } catch (e) {
        console.log(e);
    }
};

// GET API methods

const getUser = async (uid, token) => {
    return fetchData(`/users/${uid}`, token, "GET");
};

const getPosts = async (token) => {
    return fetchData("/posts", token, "GET");
};

const getGroups = async (token) => {
    return fetchData("/groups", token, "GET");
};
const getTopics = async (token) => {
    return fetchData("/topics", token, "GET");
};

const getDms = async (token) => {
    return fetchData("/posts/user", token, "GET");
};

const getDmsById = async (token, personId) => {
    return fetchData(`/posts/user/${personId}`, token, "GET");
};

const getEvents = async (token) => {
    return fetchData("/events", token, "GET");
};

const getEventPosts = async (token, event_id) => {
    return fetchData(`/posts/event/${event_id}`, token, "GET");
};

const getTopicPosts = async (token, topic_id) => {
    return fetchData(`/posts/topic/${topic_id}`, token, "GET");
};

const getGroupPosts = async (token, gang_id) => {
    return fetchData(`/posts/group/${gang_id}`, token, "GET");
};

const getTopicById = async (token, topicId) => {
    return fetchData(`/topics/${topicId}`, token, "GET");
};

const getGroupById = async (token, gang_id) => {
    return fetchData(`/groups/${gang_id}`, token, "GET");
};

// POST API methods

const updateAddUser = async (token, method, data) => {
    return fetchData("/users", token, method, data);
};

const addPost = async (token, data) => {
    return fetchData("/posts", token, "POST", data);
};

const addTopic = async (token, data) => {
    return fetchData("/topics", token, "POST", data);
};

const addGroup = async (token, data) => {
    return fetchData("/groups", token, "POST", data);
};

const addEvent = async (token, data) => {
    return fetchData("/events", token, "POST", data);
};

const sendDm = async () => {};

const joinGroup = async (group_id, token) => {
    return fetchData(`/groups/${group_id}/join`, token, "POST");
};

const joinEvent = async (event_id, token) => {
    return fetchData(`/events/${event_id}/rsvp`, token, "POST");
};

const subscribeToTopic = async (id, token) => {
    return fetchData(`/topics/${id}/join`, token, "POST");
};

const sendGroupEventInvite = async (event_id, group_id, token) => {
    return fetchData(
        `/events/${event_id}/invite/group/${group_id}`,
        token,
        "POST"
    );
};

const sendTopicEventInvite = async (event_id, topic_id, token) => {
    return fetchData(
        `/events/${event_id}/invite/topic/${topic_id}`,
        token,
        "POST"
    );
};

const sendUserEventInvite = async (event_id, uid, token) => {
    return fetchData(`/events/${event_id}/invite/topic/${uid}`, token, "POST");
};

// PUT/PATCH API methods

const updateUser = async () => {};

const updatePost = async (post_id, token, data) => {
    return fetchData(`/posts/${post_id}`, token, "PUT", data);
};

const updateEvent = async () => {};

// DELETE API methods

const deleteGroupEventInvite = async (event_id, group_id, token) => {
    return fetchData(
        `/events/${event_id}/invite/group/${group_id}`,
        token,
        "DELETE"
    );
};

const deleteTopicEventInvite = async (event_id, topic_id, token) => {
    return fetchData(
        `/events/${event_id}/invite/topic/${topic_id}`,
        token,
        "DELETE"
    );
};

const deleteUserEventInvite = async (event_id, uid, token) => {
    return fetchData(
        `/events/${event_id}/invite/topic/${uid}`,
        token,
        "DELETE"
    );
};

export {
    getPosts,
    getUser,
    getEvents,
    getGroups,
    getTopics,
    addGroup,
    addEvent,
    addTopic,
    updateAddUser,
    updateEvent,
    joinGroup,
    joinEvent,
    subscribeToTopic,
    addPost,
    sendGroupEventInvite,
    sendTopicEventInvite,
    sendUserEventInvite,
    getEventPosts,
    getTopicPosts,
    getGroupPosts,
    getTopicById,
    getGroupById,
    updatePost,
    getDms,
    getDmsById,
};
