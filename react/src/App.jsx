import { Route, Routes } from "react-router-dom";
import Header from "./components/Header/Header";
import { AuthContextProvider } from "./context/AuthContext";
import Login from "./pages/Login";
import NotFound from "./pages/NotFound";
import Timeline from "./pages/Timeline";
import UpdateAccount from "./pages/UpdateAccount";
import Groups from "./pages/Groups";
import CreateGroup from "./pages/CreateGroup";
import Topics from "./pages/Topics";
import Events from "./pages/Events";
import Profile from "./pages/Profile";
import Calendar from "./pages/Calendar";
import DirectMessage from "./pages/DirectMessage";
import CreateEvent from "./pages/CreateEvent";
import EditEvent from "./pages/EditEvent";
import CreateTopic from "./pages/CreateTopic";
import Logout from "./pages/Logout";
import CreatePost from "./pages/CreatePost";
import Thread from "./pages/Thread";
import TopicPostThread from "./pages/TopicPostThread";
import GroupPostThread from "./pages/GroupPostThread";
import UpdatePost from "./pages/UpdatePost";
import EventPostThread from "./pages/EventPostThread";
import { Dm } from "./pages/Dm";

function App() {
    return (
        <>
            <AuthContextProvider>
                <Header />
                <Routes>
                    <Route exact path="/" element={<Login />} />
                    <Route path="/timeline" element={<Timeline />} />
                    <Route path="/updateAccount" element={<UpdateAccount />} />
                    <Route path="*" element={<NotFound />} />
                    <Route path="/groups" element={<Groups />} />
                    <Route path="/createGroup" element={<CreateGroup />} />
                    <Route path="/createEvent" element={<CreateEvent />} />
                    <Route path="/editEvent" element={<EditEvent />} />
                    <Route path="/topics" element={<Topics />} />
                    <Route path="/events" element={<Events />} />
                    <Route path="/calendar" element={<Calendar />} />
                    <Route path="/directMessage" element={<DirectMessage />} />
                    <Route path="/dm/:personId" element={<Dm />} />
                    <Route path="/profile/:uid" element={<Profile />} />
                    <Route path="/createTopic" element={<CreateTopic />} />
                    <Route path="/createPost" element={<CreatePost />} />
                    <Route
                        path="/updatePost/:postId"
                        element={<UpdatePost />}
                    />
                    <Route path="/thread/:postId" element={<Thread />} />
                    <Route
                        path="/event/:idEvent"
                        element={<EventPostThread />}
                    />
                    <Route
                        path="/group/:idGroup"
                        element={<GroupPostThread />}
                    />
                    <Route
                        path="/topic/:idTopic"
                        element={<TopicPostThread />}
                    />
                    <Route path="/logout" element={<Logout />} />
                </Routes>
            </AuthContextProvider>
        </>
    );
}

export default App;
